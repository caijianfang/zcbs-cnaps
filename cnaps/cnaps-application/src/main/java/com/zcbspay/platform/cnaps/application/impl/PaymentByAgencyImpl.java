package com.zcbspay.platform.cnaps.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.zcbspay.platform.cnaps.application.PaymentByAgency;
import com.zcbspay.platform.cnaps.application.dao.ChannelPaymentBatchDAO;
import com.zcbspay.platform.cnaps.application.dao.ChannelPaymentDetaDAO;
import com.zcbspay.platform.cnaps.application.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.application.dao.MerchBankAccountDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelPaymentBatchDO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelPaymentDetaDO;
import com.zcbspay.platform.cnaps.application.dao.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.application.dao.pojo.MerchBankAccountDO;
import com.zcbspay.platform.cnaps.application.enums.CategoryPurposeEnum;
import com.zcbspay.platform.cnaps.application.sequence.SerialNumberService;
import com.zcbspay.platform.cnaps.application.utils.Constant;
import com.zcbspay.platform.cnaps.beps.message.BEPSMessageService;
import com.zcbspay.platform.cnaps.beps.message.bean.BusiQueryBean;
import com.zcbspay.platform.cnaps.beps.message.bean.PaymentDetailBean;
import com.zcbspay.platform.cnaps.beps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.beps.message.bean.SinglePaymentBean;
import com.zcbspay.platform.cnaps.ccms.message.CCMSMessageService;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;
import com.zcbspay.platform.cnaps.common.enums.PurposeEnum;
import com.zcbspay.platform.cnaps.common.enums.TradeStatFlagEnum;
import com.zcbspay.platform.cnaps.dao.TxnsLogDAO;
import com.zcbspay.platform.cnaps.pojo.PojoTxnsLog;

@Service
public class PaymentByAgencyImpl implements PaymentByAgency {

	@Autowired
	private ChannelPaymentBatchDAO channelPaymentBatchDAO;
	@Autowired
	private ChannelPaymentDetaDAO channelPaymentDetaDAO;
	@Autowired
	private CnapsLogDAO cnapsLogDAO;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private SerialNumberService serialNumberService;
	@Autowired
	private MerchBankAccountDAO merchBankAccountDAO;
	@Reference(version="1.0")
	private BEPSMessageService bepsMessageService;
	@Reference(version="1.0")
	private CCMSMessageService ccmsMessageService;
	
	@Override
	public ResultBean batchPaymentByAgency(String batchNo) {
		ChannelPaymentBatchDO paymentBatch = channelPaymentBatchDAO.getPaymentBatchByBatchNo(batchNo);
		PaymentTotalBean totalBean = new PaymentTotalBean();
		totalBean.setMsgid(paymentBatch.getMsgid());
		totalBean.setDebtorName(paymentBatch.getAccountname());
		totalBean.setDebtorAccountNo(paymentBatch.getAccountno());
		totalBean.setDebtorAgentCode(paymentBatch.getBankcode());
		totalBean.setDebtorBranchCode(paymentBatch.getBanknode());
		totalBean.setCreditorAgentCode(paymentBatch.getCreditoragentcode());
		totalBean.setTotalAmount(paymentBatch.getTotalamount().toString());
		totalBean.setCategoryPurposeCode(CategoryPurposeEnum.PaymentByAgency.getCode());
		totalBean.setCreditorNumber(paymentBatch.getTotalcount().toString());
		totalBean.setEndToEndIdentification(paymentBatch.getEndtoendidentification());
		List<ChannelPaymentDetaDO> paymentList = channelPaymentDetaDAO.getPaymentListByBatchNo(batchNo);
		List<PaymentDetailBean> detailList = Lists.newArrayList();
		for(ChannelPaymentDetaDO paymentDeta : paymentList){
			PaymentDetailBean detailBean = new PaymentDetailBean();
			detailBean.setTxId(paymentDeta.getTxid());
			detailBean.setTxnseqno(paymentDeta.getTxnseqno());
			detailBean.setCreditorName(paymentDeta.getAccountname());
			detailBean.setCreditorAccountNo(paymentDeta.getAccountno());
			detailBean.setCreditorBranchCode(paymentDeta.getBanknode());
			detailBean.setAmount(paymentDeta.getAmount().toString());
			detailBean.setAdditionalInformation(paymentDeta.getAddinfo());
			detailList.add(detailBean);
		}
		
		
		bepsMessageService.batchPaymentByAgencyRequest(totalBean);
		return null;
	}

	@Override
	public ResultBean batchPaymentByAgencyQuery(String msgId) {
		CnapsLogDO cnapsLog = cnapsLogDAO.getCNAPSLogByMsgid(msgId);
		BusiQueryBean busiQueryBean = new BusiQueryBean();
		busiQueryBean.setPaymentInstructionReference(cnapsLog.getMsgid());
		busiQueryBean.setProprietaryReferenceInstruction(cnapsLog.getInstructingparty());
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = ccmsMessageService.queryTransactionRequest(busiQueryBean);
		return null;
	}

	@Override
	public ResultBean realTimePaymentByAgency(TradeBean tradeBean) {
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
		TradeStatFlagEnum statFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
		if(statFlagEnum==TradeStatFlagEnum.PAYING){
			ResultBean resultBean = new ResultBean();
			resultBean.setResultBool(false);
			resultBean.setErrCode("");
			resultBean.setErrMsg("交易正在处理中，请不要重复支付");
			return resultBean;
		}
		if(statFlagEnum==TradeStatFlagEnum.FINISH_SUCCESS||statFlagEnum==TradeStatFlagEnum.FINISH_ACCOUNTING||
				statFlagEnum==TradeStatFlagEnum.FINSH){
			ResultBean resultBean = new ResultBean();
			resultBean.setResultBool(false);
			resultBean.setErrCode("");
			resultBean.setErrMsg("交易成功，请不要重复支付");
			return resultBean;
		}
		SinglePaymentBean singleBean = new SinglePaymentBean();
		singleBean.setMsgId(serialNumberService.generateMsgId());
		singleBean.setBatchNo(serialNumberService.generateRealTimePaymentBatchNo());
		singleBean.setTxId(serialNumberService.generateRealTimePaymentSerialNo());
		
		//付款人信息
		String merchNo = tradeBean.getMerchNo();
		String channelCode = Constant.getInstance().getChannelCode();
		MerchBankAccountDO bankAccount = merchBankAccountDAO.getBankAccountByMerchNoAndChannlCode(merchNo, channelCode);
		singleBean.setDebtorAccountNo(bankAccount.getAccountno());
		singleBean.setDebtorName(bankAccount.getAccountname());
		singleBean.setDebtorAgentCode(bankAccount.getBankcode());
		singleBean.setDebtorBranchCode(bankAccount.getBanknode());

		//收款人信息
		singleBean.setCreditorName(tradeBean.getCardKeeper());
		singleBean.setCreditorAccountNo(tradeBean.getCardNo());
		singleBean.setCreditorAgentCode(tradeBean.getBankCode());
		singleBean.setCreditorBranchCode(tradeBean.getBankNode());
		
		//其他信息
		singleBean.setAmount(tradeBean.getTxnsAmt());
		singleBean.setCategoryPurposeCode(CategoryPurposeEnum.PaymentByAgency.getCode());
		singleBean.setPurposeCode(PurposeEnum.Other.getCode());
		singleBean.setEndToEndIdentification(tradeBean.getProtocolNo());
		singleBean.setCheckFlag("CE02");
		
		bepsMessageService.realTimePaymentByAgencyRequest(singleBean);
		
		return null;
	}

	@Override
	public ResultBean realTimePaymentByAgencyQuery(String txnseqno) {
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
		CnapsLogDO cnapsLog = cnapsLogDAO.getCNAPSLogByMsgid(txnsLog.getPayordno());
		BusiQueryBean busiQueryBean = new BusiQueryBean();
		busiQueryBean.setPaymentInstructionReference(cnapsLog.getMsgid());
		busiQueryBean.setProprietaryReferenceInstruction(cnapsLog.getInstructingparty());
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = ccmsMessageService.queryTransactionRequest(busiQueryBean);
		return null;
	}

}

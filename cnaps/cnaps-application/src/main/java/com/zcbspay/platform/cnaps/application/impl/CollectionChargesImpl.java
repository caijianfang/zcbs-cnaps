package com.zcbspay.platform.cnaps.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.zcbspay.platform.cnaps.application.CollectionCharges;
import com.zcbspay.platform.cnaps.application.dao.ChannelCollectDetaDAO;
import com.zcbspay.platform.cnaps.application.dao.ChannelColletctBatchDAO;
import com.zcbspay.platform.cnaps.application.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.application.dao.MerchBankAccountDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectBatchDO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectDetaDO;
import com.zcbspay.platform.cnaps.application.dao.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.application.dao.pojo.MerchBankAccountDO;
import com.zcbspay.platform.cnaps.application.enums.CategoryPurposeEnum;
import com.zcbspay.platform.cnaps.application.sequence.SerialNumberService;
import com.zcbspay.platform.cnaps.application.utils.Constant;
import com.zcbspay.platform.cnaps.beps.message.BEPSMessageService;
import com.zcbspay.platform.cnaps.beps.message.bean.BusiQueryBean;
import com.zcbspay.platform.cnaps.beps.message.bean.CollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.beps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.beps.message.bean.SingleCollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.ccms.message.CCMSMessageService;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;
import com.zcbspay.platform.cnaps.common.enums.PurposeEnum;
import com.zcbspay.platform.cnaps.common.enums.TradeStatFlagEnum;
import com.zcbspay.platform.cnaps.dao.TxnsLogDAO;
import com.zcbspay.platform.cnaps.pojo.PojoTxnsLog;
@Service
public class CollectionChargesImpl implements CollectionCharges {

	//private static final Logger logger = LoggerFactory.getLogger(CollectionChargesImpl.class);
	
	@Autowired
	private ChannelColletctBatchDAO channelColletctBatchDAO;
	@Autowired
	private ChannelCollectDetaDAO channelCollectDetaDAO;
	@Autowired
	private MerchBankAccountDAO merchBankAccountDAO;
	@Autowired
	private TxnsLogDAO txnsLogDAO;
	@Autowired
	private CnapsLogDAO cnapsLogDAO;
	@Autowired
	private SerialNumberService serialNumberService;
	@Reference(version="1.0")
	private BEPSMessageService bepsMessageService;
	@Reference(version="1.0")
	private CCMSMessageService ccmsMessageService;
	@Override
	public ResultBean batchCollectionCharges(String batchNo) {
		//批次数据
		ChannelCollectBatchDO collectBatch = channelColletctBatchDAO.getCollectBatchByBatchNo(batchNo);
		CollectionChargesTotalBean totalBean = new CollectionChargesTotalBean();
		totalBean.setBatchNo(batchNo);
		totalBean.setCategoryPurposeCode(CategoryPurposeEnum.CollectionCharges.getCode());
		totalBean.setCreditorAccountNo(collectBatch.getAccountno());
		totalBean.setCreditorAgentCode(collectBatch.getBankcode());
		totalBean.setCreditorBranchCode(collectBatch.getBanknode());
		totalBean.setCreditorName(collectBatch.getAccountname());
		totalBean.setDebtorNumber(collectBatch.getTotalcount().toString());
		totalBean.setTotalAmount(collectBatch.getTotalamount().toString());
		totalBean.setDebtorAgentCode(collectBatch.getDebtoragentcode());
		totalBean.setMsgId(collectBatch.getMsgid());
		//明细数据
		List<ChannelCollectDetaDO> collectList = channelCollectDetaDAO.getCollectListByBatchNo(batchNo);
		List<CollectionChargesDetailBean> detaList = Lists.newArrayList();
		for(ChannelCollectDetaDO collectDeta : collectList){
			CollectionChargesDetailBean detailBean = new CollectionChargesDetailBean();
			detailBean.setDebtorName(collectDeta.getAccountname());
			detailBean.setDebtorAccountNo(collectDeta.getAccountno());
			detailBean.setDebtorBranchCode(collectDeta.getBanknode());
			detailBean.setAmount(collectDeta.getAmount().toString());
			detailBean.setEndToEndIdentification(collectDeta.getEndtoendidentification());
			detailBean.setCheckFlag("CE02");
			detailBean.setAdditionalInformation(collectDeta.getAddinfo());
			detailBean.setTxId(collectDeta.getTxid());
			//代收付协议检查
			
			detaList.add(detailBean);
		}
		totalBean.setDetailList(detaList);
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = bepsMessageService.batchCollectionChargesRequest(totalBean);
		
		return null;
	}

	@Override
	public ResultBean batchCollectionChargesQuery(String msgid) {
		CnapsLogDO cnapsLog = cnapsLogDAO.getCNAPSLogByMsgid(msgid);
		BusiQueryBean busiQueryBean = new BusiQueryBean();
		busiQueryBean.setPaymentInstructionReference(cnapsLog.getMsgid());
		busiQueryBean.setProprietaryReferenceInstruction(cnapsLog.getInstructingparty());
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = ccmsMessageService.queryTransactionRequest(busiQueryBean);
		return null;
	}

	@Override
	public ResultBean realTimeCollectionCharges(TradeBean tradeBean) {
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
		
		SingleCollectionChargesDetailBean singleBean = new SingleCollectionChargesDetailBean();
		singleBean.setMsgId(serialNumberService.generateMsgId());
		singleBean.setBatchNo(serialNumberService.generateRealTimeCollectBatchNo());
		singleBean.setTxId(serialNumberService.generateRealTimeCollectSerialNo());
		//付款人信息
		singleBean.setDebtorName(tradeBean.getCardKeeper());
		singleBean.setDebtorAccountNo(tradeBean.getCardNo());
		singleBean.setDebtorAgentCode(tradeBean.getBankCode());
		singleBean.setDebtorBranchCode(tradeBean.getBankNode());
		//收款人信息
		String merchNo = tradeBean.getMerchNo();
		String channelCode = Constant.getInstance().getChannelCode();
		MerchBankAccountDO bankAccount = merchBankAccountDAO.getBankAccountByMerchNoAndChannlCode(merchNo, channelCode);
		singleBean.setCreditorAccountNo(bankAccount.getAccountno());
		singleBean.setCreditorName(bankAccount.getAccountname());
		singleBean.setCreditorAgentCode(bankAccount.getBankcode());
		singleBean.setCreditorBranchCode(bankAccount.getBanknode());
		//其他信息
		singleBean.setAmount(tradeBean.getTxnsAmt());
		singleBean.setCategoryPurposeCode(CategoryPurposeEnum.CollectionCharges.getCode());
		singleBean.setPurposeCode(PurposeEnum.Other.getCode());
		singleBean.setEndToEndIdentification(tradeBean.getProtocolNo());
		singleBean.setCheckFlag("CE02");
		
		//实时代收业务报文接口
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = bepsMessageService.realTimeCollectionChargesRequest(singleBean);
		
		
		return null;
	}

	@Override
	public ResultBean realTimeCollectionChargesQuery(String txnseqno) {
		PojoTxnsLog txnsLog = txnsLogDAO.getTxnsLogByTxnseqno(txnseqno);
		CnapsLogDO cnapsLog = cnapsLogDAO.getCNAPSLogByMsgid(txnsLog.getPayordno());
		BusiQueryBean busiQueryBean = new BusiQueryBean();
		busiQueryBean.setPaymentInstructionReference(cnapsLog.getMsgid());
		busiQueryBean.setProprietaryReferenceInstruction(cnapsLog.getInstructingparty());
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = ccmsMessageService.queryTransactionRequest(busiQueryBean);
		return null;
	}

}

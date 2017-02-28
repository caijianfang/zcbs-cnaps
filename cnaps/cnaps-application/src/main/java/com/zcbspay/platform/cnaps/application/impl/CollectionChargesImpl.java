package com.zcbspay.platform.cnaps.application.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.zcbspay.platform.cnaps.application.CollectionCharges;
import com.zcbspay.platform.cnaps.application.dao.ChannelCollectDetaDAO;
import com.zcbspay.platform.cnaps.application.dao.ChannelColletctBatchDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectBatchDO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectDetaDO;
import com.zcbspay.platform.cnaps.application.enums.CategoryPurposeEnum;
import com.zcbspay.platform.cnaps.beps.message.BEPSMessageService;
import com.zcbspay.platform.cnaps.beps.message.bean.CollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.beps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.beps.message.bean.SingleCollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;

public class CollectionChargesImpl implements CollectionCharges {

	//private static final Logger logger = LoggerFactory.getLogger(CollectionChargesImpl.class);
	
	@Autowired
	private ChannelColletctBatchDAO channelColletctBatchDAO;
	@Autowired
	private ChannelCollectDetaDAO channelCollectDetaDAO;
	@Reference(version="1.0")
	private BEPSMessageService bepsMessageService;
	@Override
	public ResultBean batchCollectionCharges(String batchNo) {
		//批次数据
		ChannelCollectBatchDO collectBatch = channelColletctBatchDAO.getCollectBatchByBatchNo(batchNo);
		//String merchNo = collectBatch.getMerchno();
		//String channelCode = Constant.getInstance().getChannelCode();
		//MerchBankAccountDO bankAccount = merchBankAccountDAO.getBankAccountByMerchNoAndChannlCode(merchNo, channelCode);
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
			detailBean.setMsgId("");
			detailBean.setDebtorName(collectDeta.getAccountname());
			detailBean.setDebtorAccountNo(collectDeta.getAccountno());
			detailBean.setDebtorBranchCode(collectDeta.getBanknode());
			detailBean.setAmount(collectDeta.getAmount().toString());
			detailBean.setEndToEndIdentification(collectDeta.getEndtoendidentification());
			detailBean.setCheckFlag("CE02");
			detailBean.setAdditionalInformation(collectDeta.getAddinfo());
			detailBean.setTxId(collectDeta.getTxid());
			detaList.add(detailBean);
		}
		totalBean.setDetailList(detaList);
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = bepsMessageService.batchCollectionChargesRequest(totalBean);
		
		return null;
	}

	@Override
	public ResultBean batchCollectionChargesQuery(String txnseqno) {
		
		return null;
	}

	@Override
	public ResultBean realTimeCollectionCharges(TradeBean tradeBean) {
		SingleCollectionChargesDetailBean singleBean = new SingleCollectionChargesDetailBean();
		
		
		
		
		bepsMessageService.realTimeCollectionChargesRequest(singleBean);
		return null;
	}

	@Override
	public ResultBean realTimeCollectionChargesQuery(String txnseqno) {
		
		return null;
	}

}

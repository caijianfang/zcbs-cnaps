package com.zcbspay.platform.cnaps.application.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zcbspay.platform.cnaps.application.CollectionCharges;
import com.zcbspay.platform.cnaps.application.dao.ChannelColletctBatchDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectBatchDO;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;

public class CollectionChargesImpl implements CollectionCharges {

	private static final Logger logger = LoggerFactory.getLogger(CollectionChargesImpl.class);
	
	@Autowired
	private ChannelColletctBatchDAO channelColletctBatchDAO;
	
	@Override
	public ResultBean batchCollectionCharges(String batchNo) {
		ChannelCollectBatchDO collectBatch = channelColletctBatchDAO.getCollectBatchByBatchNo(batchNo);
		
		
		return null;
	}

	@Override
	public ResultBean batchCollectionChargesQuery(String txnseqno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean realTimeCollectionCharges(TradeBean tradeBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean realTimeCollectionChargesQuery(String txnseqno) {
		// TODO Auto-generated method stub
		return null;
	}

}

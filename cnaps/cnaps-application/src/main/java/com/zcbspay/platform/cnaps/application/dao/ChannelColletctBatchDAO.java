package com.zcbspay.platform.cnaps.application.dao;

import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectBatchDO;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;

public interface ChannelColletctBatchDAO extends BaseDAO<ChannelCollectBatchDO>{

	public ChannelCollectBatchDO getCollectBatchByBatchNo(String batchno);
}

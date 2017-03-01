package com.zcbspay.platform.cnaps.application.dao;

import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelPaymentBatchDO;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;

public interface ChannelPaymentBatchDAO extends BaseDAO<ChannelPaymentBatchDO>{

	/**
	 * 通过批次号获取代付批次数据
	 * @param batchno 批次号
	 * @return
	 */
	public ChannelPaymentBatchDO getPaymentBatchByBatchNo(String batchno);
}

package com.zcbspay.platform.cnaps.application.dao;

import java.util.List;

import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelPaymentDetaDO;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;

public interface ChannelPaymentDetaDAO extends BaseDAO<ChannelPaymentDetaDO>{

	/**
	 * 获取代付批次内明细数据
	 * @param batchNo 批次号
	 * @return
	 */
	public List<ChannelPaymentDetaDO> getPaymentListByBatchNo(String batchNo);
}

package com.zcbspay.platform.cnaps.application.dao;

import java.util.List;

import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectDetaDO;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;

public interface ChannelCollectDetaDAO extends BaseDAO<ChannelCollectDetaDO>{

	/**
	 * 获取代收批次内明细数据
	 * @param batchNo 批次号
	 * @return
	 */
	public List<ChannelCollectDetaDO> getCollectListByBatchNo(String batchNo);
}

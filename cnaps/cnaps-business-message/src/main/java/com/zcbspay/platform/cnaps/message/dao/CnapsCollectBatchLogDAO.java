package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectBatchLogDO;

public interface CnapsCollectBatchLogDAO extends BaseDAO<CnapsCollectBatchLogDO> {

	/**
	 * 保存人行批次代收数据
	 * @param cnapsCollectBatchLog
	 */
	public void saveCollectBatch(CnapsCollectBatchLogDO cnapsCollectBatchLog);
	
	public void updateCollectBatchCommRSP();
	
}

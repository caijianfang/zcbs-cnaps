package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectBatchLogDO;

public interface CnapsCollectBatchLogDAO extends BaseDAO<CnapsCollectBatchLogDO> {

	/**
	 * 保存人行批次代收数据
	 * @param cnapsCollectBatchLog
	 */
	public void saveCollectBatch(CnapsCollectBatchLogDO cnapsCollectBatchLog);
	
	/**
	 * 更新批量代收通用应答报文信息
	 * @param cmonConfInfoBean
	 */
	public void updateCollectBatchCommRSP(CmonConfInfoBean cmonConfInfoBean);
	
	/**
	 * 保存批量代收交易流水
	 * @param totalBean
	 */
	public void saveBatchCollectionCharges(CollectionChargesTotalBean totalBean);
}

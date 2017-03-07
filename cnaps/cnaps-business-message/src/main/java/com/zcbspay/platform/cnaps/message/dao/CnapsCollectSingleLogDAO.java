package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.bean.SingleCollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectSingleLogDO;

public interface CnapsCollectSingleLogDAO extends BaseDAO<CnapsCollectSingleLogDO> {

	/**
	 * 保存实时代收交易流水
	 * @param singleBean
	 */
	public void saveCollectSingleLog(SingleCollectionChargesDetailBean singleBean);
	
	/**
	 * 更新实时代收交易流水回执应答信息
	 * @param document
	 */
	public void updateRealTimeCollectionChargesRSP(com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.Document document);
	
	/**
	 * 更新实时代收通用应答信息
	 * @param cmonConfInfoBean
	 */
	public void updateCollectSingleCommRSP(CmonConfInfoBean cmonConfInfoBean);
}

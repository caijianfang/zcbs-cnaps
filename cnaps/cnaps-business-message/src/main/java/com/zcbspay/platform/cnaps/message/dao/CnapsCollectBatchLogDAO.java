package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.dao.bean.NPCPrcInfoBean;
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
	
	/**
	 * 根据回执报文更新原交易批次和明细数据
	 * @param document 回执报文
	 */
	public void updateBatchCollectionChargesRSP(com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document document);
	
	/**
	 * 根据msgId获取代收付批次数据
	 * @param msgId
	 * @return
	 */
	public CnapsCollectBatchLogDO getCollectBatchLogByMsgId(String msgId);
	
	/**
	 * 更新代收批次NPC应答信息
	 * @param npcPrcInfoBean
	 */
	public void updateCollectBatchNPCRSP(NPCPrcInfoBean npcPrcInfoBean);
}

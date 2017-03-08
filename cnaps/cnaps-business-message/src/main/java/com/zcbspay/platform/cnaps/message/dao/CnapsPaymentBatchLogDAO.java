package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.Document;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.message.dao.bean.NPCPrcInfoBeanComm;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentBatchLogDO;

public interface CnapsPaymentBatchLogDAO extends BaseDAO<CnapsPaymentBatchLogDO> {

    /**
     * 保存批量代付交易流水
     * 
     * @param totalBean
     */
    public void savePaymentBatchSerialInfo(PaymentTotalBean totalBean);

    /**
     * 保存人行批次代付数据
     * 
     * @param cnapsPaymentBatchLogDO
     */
    public void savePaymentBatch(CnapsPaymentBatchLogDO cnapsPaymentBatchLogDO);

    /**
     * 根据回执报文更新原交易批次和明细数据
     * 
     * @param document
     *            回执报文
     */
    public void updateBatchCollectionChargesRSP(Document document);

    /**
     * 更新代付批次NPC应答信息
     * 
     * @param npcPrcInfoBean
     */
    public void updatePaymentBatchNPCRSP(NPCPrcInfoBeanComm npcPrcInfoBean);

    /**
     * 根据msgId获取代收付批次数据
     * 
     * @param msgId
     * @return
     */
    public CnapsPaymentBatchLogDO getPaymentBatchLogByMsgId(String msgId);
}

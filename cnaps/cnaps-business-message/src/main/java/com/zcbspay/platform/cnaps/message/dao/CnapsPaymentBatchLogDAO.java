package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentBatchLogDO;

public interface CnapsPaymentBatchLogDAO extends BaseDAO<CnapsPaymentBatchLogDO> {
    /**
     * 保存批量代收交易流水
     * @param totalBean
     */
    public void savePaymentBatchSerialInfo(PaymentTotalBean totalBean);
    
    /**
     * 保存人行批次代付数据
     * @param cnapsPaymentBatchLogDO
     */
    public void savePaymentBatch(CnapsPaymentBatchLogDO cnapsPaymentBatchLogDO);
}

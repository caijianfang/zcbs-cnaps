package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.bean.SinglePaymentBean;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentSingleLogDO;

public interface CnapsPaymentSingleLogDAO extends BaseDAO<CnapsPaymentSingleLogDO> {

    /**
     * 保存实时代收交易流水
     * 
     * @param singleBean
     */
    public void savePaymentSingleLog(SinglePaymentBean singleBean);

    /**
     * 更新实时代收交易流水回执应答信息
     * 
     * @param document
     */
    public void updateRealTimePaymentRSP(com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.Document document);

    /**
     * 更新实时代收通用应答信息
     * 
     * @param cmonConfInfoBean
     */
    public void updatePaymentSingleCommRSP(CmonConfInfoBean cmonConfInfoBean);
}

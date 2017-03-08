package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.RspnInfoBeanComm;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentDetaLogDO;

public interface CnapsPaymentDetaLogDAO extends BaseDAO<CnapsPaymentDetaLogDO> {
    /**
     * 保存代付明细交易数据
     * @param paymentDetaLog
     */
    public void savePaymentDetaLog(CnapsPaymentDetaLogDO paymentDetaLog);
    
    /**
     * 更新批量代付回执明细信息
     * @param rspnInfoBean
     */
    public void updatePaymentDetaRSP(RspnInfoBeanComm rspnInfoBean);

}

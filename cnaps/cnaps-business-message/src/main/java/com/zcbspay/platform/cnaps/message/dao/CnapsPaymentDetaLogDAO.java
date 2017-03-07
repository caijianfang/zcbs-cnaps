package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectDetaLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentDetaLogDO;

public interface CnapsPaymentDetaLogDAO extends BaseDAO<CnapsPaymentDetaLogDO> {
    /**
     * 保存代付明细交易数据
     * @param paymentDetaLog
     */
    public void savePaymentDetaLog(CnapsPaymentDetaLogDO paymentDetaLog);

}

package com.zcbspay.platform.cnaps.message.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentDetaLogDAO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentDetaLogDO;

@Repository
public class CnapsPaymentDetaLogDAOImpl extends HibernateBaseDAOImpl<CnapsPaymentDetaLogDO> implements CnapsPaymentDetaLogDAO{


    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void savePaymentDetaLog(CnapsPaymentDetaLogDO paymentDetaLog) {
        saveEntity(paymentDetaLog);
    }

	
}

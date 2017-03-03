package com.zcbspay.platform.cnaps.application.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.application.dao.ChannelPaymentBatchDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelPaymentBatchDO;
import com.zcbspay.platform.cnaps.application.enums.CollectBatchStatusEnum;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;

@Repository
public class ChannelPaymentBatchDAOImpl extends HibernateBaseDAOImpl<ChannelPaymentBatchDO> implements ChannelPaymentBatchDAO {

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public ChannelPaymentBatchDO getPaymentBatchByBatchNo(String batchno) {
		Criteria criteria = getSession().createCriteria(ChannelPaymentBatchDO.class);
		criteria.add(Restrictions.eq("batchno", batchno));
		criteria.add(Restrictions.eq("status", CollectBatchStatusEnum.WaitToPay));
		ChannelPaymentBatchDO uniqueResult = (ChannelPaymentBatchDO) criteria.uniqueResult();
		return uniqueResult;
	}

	

}

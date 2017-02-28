package com.zcbspay.platform.cnaps.application.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.application.dao.ChannelColletctBatchDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectBatchDO;
import com.zcbspay.platform.cnaps.application.enums.CollectBatchStatusEnum;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;

@Repository
public class ChannelColletctBatchDAOImpl extends HibernateBaseDAOImpl<ChannelCollectBatchDO> implements ChannelColletctBatchDAO{

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public ChannelCollectBatchDO getCollectBatchByBatchNo(String batchno) {
		Criteria criteria = getSession().createCriteria(ChannelCollectBatchDO.class);
		criteria.add(Restrictions.eq("batchno", batchno));
		criteria.add(Restrictions.eq("status", CollectBatchStatusEnum.WaitToPay));
		ChannelCollectBatchDO uniqueResult = (ChannelCollectBatchDO) criteria.uniqueResult();
		return uniqueResult;
	}

	
}

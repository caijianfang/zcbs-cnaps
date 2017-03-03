package com.zcbspay.platform.cnaps.application.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.application.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;

public class CnapsLogDAOImpl extends HibernateBaseDAOImpl<CnapsLogDO> implements CnapsLogDAO {

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public CnapsLogDO getCNAPSLogByMsgid(String msgid) {
		Criteria criteria = getSession().createCriteria(CnapsLogDO.class);
		criteria.add(Restrictions.eq("msgid", msgid));
		CnapsLogDO uniqueResult = (CnapsLogDO)criteria.uniqueResult();
		return uniqueResult;
	}

	

}

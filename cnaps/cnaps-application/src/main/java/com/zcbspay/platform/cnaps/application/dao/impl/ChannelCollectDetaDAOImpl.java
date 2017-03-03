package com.zcbspay.platform.cnaps.application.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.application.dao.ChannelCollectDetaDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.ChannelCollectDetaDO;
import com.zcbspay.platform.cnaps.application.enums.CollectBatchStatusEnum;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;

@Repository
public class ChannelCollectDetaDAOImpl extends HibernateBaseDAOImpl<ChannelCollectDetaDO> implements ChannelCollectDetaDAO {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public List<ChannelCollectDetaDO> getCollectListByBatchNo(String batchNo) {
		Criteria criteria = getSession().createCriteria(ChannelCollectDetaDO.class);
		criteria.add(Restrictions.eq("batchno", batchNo));
		criteria.add(Restrictions.eq("status", CollectBatchStatusEnum.WaitToPay.getCode()));
		return criteria.list();
	}

	

}

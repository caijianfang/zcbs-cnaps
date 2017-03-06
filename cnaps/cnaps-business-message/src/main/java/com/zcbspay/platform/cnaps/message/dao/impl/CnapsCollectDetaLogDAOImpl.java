package com.zcbspay.platform.cnaps.message.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectDetaLogDAO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectDetaLogDO;

@Repository
public class CnapsCollectDetaLogDAOImpl extends HibernateBaseDAOImpl<CnapsCollectDetaLogDO> implements CnapsCollectDetaLogDAO{

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveCollectDetaLog(CnapsCollectDetaLogDO cnapsCollectDetaLog) {
		saveCollectDetaLog(cnapsCollectDetaLog);
	}

	
}

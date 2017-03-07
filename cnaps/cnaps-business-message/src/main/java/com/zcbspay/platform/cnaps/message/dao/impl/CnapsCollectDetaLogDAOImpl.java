package com.zcbspay.platform.cnaps.message.dao.impl;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectDetaLogDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.RspnInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectDetaLogDO;
import com.zcbspay.platform.cnaps.utils.DateUtil;

@Repository
public class CnapsCollectDetaLogDAOImpl extends HibernateBaseDAOImpl<CnapsCollectDetaLogDO> implements CnapsCollectDetaLogDAO{

	private static final Logger logger = LoggerFactory.getLogger(CnapsCollectDetaLogDAOImpl.class);
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveCollectDetaLog(CnapsCollectDetaLogDO cnapsCollectDetaLog) {
		saveCollectDetaLog(cnapsCollectDetaLog);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateCollectDetaRSP(RspnInfoBean rspnInfoBean) {
		// TODO Auto-generated method stub
		//String hql = "update CnapsCollectDetaLogDO set rspstatus = ?,rsprejectcode = ?,rsprejectinformation = ?,rspprocessparty = ?,rspdate = ? where txid = ?";
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("update CnapsCollectDetaLogDO set ");
		hqlBuffer.append("rspstatus = ?,");
		hqlBuffer.append("rsprejectcode = ?,");
		hqlBuffer.append("rsprejectinformation = ?,");
		hqlBuffer.append("rspprocessparty = ?,");
		hqlBuffer.append("rspdate = ?");
		hqlBuffer.append(" where ");
		hqlBuffer.append(" txid = ?");
		Query query = getSession().createQuery(hqlBuffer.toString());
		query.setParameter(0, rspnInfoBean.getStatus());
		query.setParameter(1, rspnInfoBean.getRejectCode());
		query.setParameter(2, rspnInfoBean.getRejectInformation());
		query.setParameter(3, rspnInfoBean.getProcessParty());
		query.setParameter(4, DateUtil.getCurrentDateTime());
		query.setParameter(5, rspnInfoBean.getTxId());
		int effectRows = query.executeUpdate();
		logger.info("hql:{},effect rows:{}",hqlBuffer.toString(),effectRows);
	}

	
}

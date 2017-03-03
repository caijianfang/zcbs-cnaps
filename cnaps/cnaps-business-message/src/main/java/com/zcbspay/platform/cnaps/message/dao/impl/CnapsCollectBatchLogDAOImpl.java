package com.zcbspay.platform.cnaps.message.dao.impl;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectBatchLogDO;

public class CnapsCollectBatchLogDAOImpl extends HibernateBaseDAOImpl<CnapsCollectBatchLogDO>
		implements CnapsCollectBatchLogDAO {

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveCollectBatch(CnapsCollectBatchLogDO cnapsCollectBatchLog) {
		// TODO Auto-generated method stub
		saveEntity(cnapsCollectBatchLog);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateCollectBatchCommRSP(CmonConfInfoBean cmonConfInfoBean) {
		// TODO Auto-generated method stub
		//String hql = "update CnapsCollectBatchLogDO set comprocessstatus = ?,comprocesscode = ?,compartyidentification = ?,compartyprocesscode = ?,comrejectinformation = ?,comprocessdate = ?,comnettinground = ?,comdate = ? where msgid = ?";
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("update CnapsCollectBatchLogDO set ");
		hqlBuffer.append("comprocessstatus = ?,");
		hqlBuffer.append("comprocesscode = ?,");
		hqlBuffer.append("compartyidentification = ?,");
		hqlBuffer.append("compartyprocesscode = ?,");
		hqlBuffer.append("comrejectinformation = ?,");
		hqlBuffer.append("comprocessdate = ?,");
		hqlBuffer.append("comnettinground = ?,");
		hqlBuffer.append("comdate = ?,");
		hqlBuffer.append("msgid = ? ");
		hqlBuffer.append("where msgid = ? ");
		Query query = getSession().createQuery(hqlBuffer.toString());
		query.setParameter(0, cmonConfInfoBean.getProcessstatus());
		query.setParameter(1, cmonConfInfoBean.getProcesscode());
		query.setParameter(2, cmonConfInfoBean.getPartyidentification());
		query.setParameter(3, cmonConfInfoBean.getPartyprocesscode());
		query.setParameter(4, cmonConfInfoBean.getRejectinformation());
		query.setParameter(5, cmonConfInfoBean.getProcessdate());
		query.setParameter(6, cmonConfInfoBean.getNettinground());
		query.setParameter(7, cmonConfInfoBean.getDate());
		query.setParameter(8, cmonConfInfoBean.getMsgId());
		query.executeUpdate();
	}

	
	
}

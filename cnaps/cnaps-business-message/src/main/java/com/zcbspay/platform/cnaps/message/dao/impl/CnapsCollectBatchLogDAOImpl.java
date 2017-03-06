package com.zcbspay.platform.cnaps.message.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectDetaLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectBatchLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectDetaLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsLogDO;

@Repository
public class CnapsCollectBatchLogDAOImpl extends HibernateBaseDAOImpl<CnapsCollectBatchLogDO>
		implements CnapsCollectBatchLogDAO {

	private static final Logger logger = LoggerFactory.getLogger(CnapsCollectBatchLogDAOImpl.class);
	
	@Autowired
	private CnapsLogDAO cnapsLogDAO;
	@Autowired
	private CnapsCollectDetaLogDAO cnapsCollectDetaLogDAO;
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
		hqlBuffer.append("comdate = ? ");
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
		int rows = query.executeUpdate();
		logger.info("hql:{},effect rows:{}",hqlBuffer.toString(),rows);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveBatchCollectionCharges(CollectionChargesTotalBean totalBean) {
		//保存人行核心交易流水
		CnapsLogDO cnapsLog = new CnapsLogDO();
		cnapsLog.setMsgid(totalBean.getMsgId());
		cnapsLog.setSyscode("BEPS");
		cnapsLog.setMsgtype(MessageTypeEnum.BEPS380.value());
		cnapsLog.setCreatedate(totalBean.getCreatedate());
		cnapsLog.setInstructingdirectparty(totalBean.getInstructingdirectparty());
		cnapsLog.setInstructingparty(totalBean.getInstructingparty());
		cnapsLog.setInstructeddirectparty(totalBean.getInstructeddirectparty());
		cnapsLog.setInstructedparty(totalBean.getInstructedparty());
		cnapsLogDAO.saveCNAPSLog(cnapsLog);
		//保存代收批次数据
		CnapsCollectBatchLogDO cnapsCollectBatchLog = new CnapsCollectBatchLogDO();
		cnapsCollectBatchLog.setMsgid(totalBean.getMsgId());
		cnapsCollectBatchLog.setBatchno(totalBean.getBatchno());
		cnapsCollectBatchLog.setTransmitdate(totalBean.getTransmitdate());
		cnapsCollectBatchLog.setReturnlimited(totalBean.getReturnlimited());
		cnapsCollectBatchLog.setDebtoragentcode(totalBean.getDebtorAgentCode());
		cnapsCollectBatchLog.setCreditoragentcode(totalBean.getCreditorAgentCode());
		cnapsCollectBatchLog.setCreditorbranchcode(totalBean.getCreditorBranchCode());
		cnapsCollectBatchLog.setCreditorname(totalBean.getCreditorName());
		cnapsCollectBatchLog.setCreditoraccountno(totalBean.getCreditorAccountNo());
		cnapsCollectBatchLog.setTotalamount(Long.valueOf(totalBean.getTotalAmount()));
		cnapsCollectBatchLog.setCategorypurposecode(totalBean.getCategoryPurposeCode());
		cnapsCollectBatchLog.setDebtornumber(Integer.valueOf(totalBean.getDebtorNumber()));
		saveCollectBatch(cnapsCollectBatchLog);
		//保存明细信息
		List<CollectionChargesDetailBean> detailList = totalBean.getDetailList();
		for(CollectionChargesDetailBean detailBean : detailList){
			CnapsCollectDetaLogDO collectDetaLog = new CnapsCollectDetaLogDO();
			collectDetaLog.setBatchno(totalBean.getBatchno());
			collectDetaLog.setTxid(detailBean.getTxId());
			collectDetaLog.setPurposeproprietary(detailBean.getPurposeProprietary());
			collectDetaLog.setDebtorname(detailBean.getDebtorName());
			collectDetaLog.setDebtoraccountno(detailBean.getDebtorAccountNo());
			collectDetaLog.setDebtorbranchcode(detailBean.getDebtorBranchCode());
			collectDetaLog.setAmount(Long.valueOf(detailBean.getAmount()));
			collectDetaLog.setEndtoendidentification(detailBean.getEndToEndIdentification());
			collectDetaLog.setCheckflag(detailBean.getCheckFlag());
			collectDetaLog.setAddinfo(detailBean.getAdditionalInformation());
			collectDetaLog.setTxnseqno(detailBean.getTxnseqno());
			cnapsCollectDetaLogDAO.saveCollectDetaLog(collectDetaLog);
		}
		
	}

	
	
}

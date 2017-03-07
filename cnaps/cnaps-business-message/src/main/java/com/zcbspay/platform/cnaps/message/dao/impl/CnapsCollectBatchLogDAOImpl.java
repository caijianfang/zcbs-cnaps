package com.zcbspay.platform.cnaps.message.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.BtchColltnChrgsRspnInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.BtchColltnChrgsRspnV01;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.GroupHeader1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.NPCPrcInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.OrgnlMsgInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.RcvgDtls1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.RspnInf1;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectDetaLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.dao.bean.NPCPrcInfoBean;
import com.zcbspay.platform.cnaps.message.dao.bean.RspnInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectBatchLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectDetaLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.utils.DateUtil;

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

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateBatchCollectionChargesRSP(Document document) {
		// TODO Auto-generated method stub
		BtchColltnChrgsRspnV01 btchColltnChrgsRspn = document.getBtchColltnChrgsRspn();
		GroupHeader1 grpHdr = btchColltnChrgsRspn.getGrpHdr();
		OrgnlMsgInf1 orgnlMsgInf = btchColltnChrgsRspn.getOrgnlMsgInf();
		NPCPrcInf1 npcPrcInf = btchColltnChrgsRspn.getNPCPrcInf();
		BtchColltnChrgsRspnInf1 btchColltnChrgsRspnInf = btchColltnChrgsRspn.getBtchColltnChrgsRspnInf();
		//保存应答报文数据
		CnapsLogDO cnapsLog = new CnapsLogDO();
		cnapsLog.setMsgid(grpHdr.getMsgId());
		cnapsLog.setSyscode(grpHdr.getSysCd().value());
		cnapsLog.setMsgtype(MessageTypeEnum.BEPS381.value());
		try {
			cnapsLog.setCreatedate(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(grpHdr.getCreDtTm())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cnapsLog.setInstructingdirectparty(grpHdr.getInstgPty().getInstgDrctPty());
		cnapsLog.setInstructingparty(grpHdr.getInstgPty().getInstgPty());
		cnapsLog.setInstructeddirectparty(grpHdr.getInstdPty().getInstdDrctPty());
		cnapsLog.setInstructedparty(grpHdr.getInstdPty().getInstdPty());
		cnapsLogDAO.saveCNAPSLog(cnapsLog);
		
		//原批量代收报文标示号
		String orgnMsgId = orgnlMsgInf.getOrgnlMsgId();
		//判断原报批量代收交易是否存在
		CnapsLogDO orgnCNAPSLogDO = cnapsLogDAO.getCnapsLogDOByMsgId(orgnMsgId);
		if(orgnCNAPSLogDO==null){
			//抛出异常，暂时未定义
		}
		NPCPrcInfoBean npcPrcInfoBean = new NPCPrcInfoBean(npcPrcInf);
		npcPrcInfoBean.setMsgid(orgnMsgId);
		//更新代付批次NPC应答信息
		updateCollectBatchNPCRSP(npcPrcInfoBean);
		//更新代收批次应答信息
		BigDecimal successAmount = btchColltnChrgsRspnInf.getRcvgTtlAmt().getValue();//成功付款总金额
		String successCount = btchColltnChrgsRspnInf.getRcvgTtlNb();//成功付款总笔数
		CnapsCollectBatchLogDO batchLog = getCollectBatchLogByMsgId(grpHdr.getMsgId());
		long failtotalamount = batchLog.getTotalamount()-successAmount.longValue();
		int failtotalnumber = batchLog.getDebtornumber() - Integer.valueOf(successCount);
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("update CnapsCollectBatchLogDO set ");
		hqlBuffer.append("receivingtotalnumber = ?");
		hqlBuffer.append("receivingtotalamount = ?,");
		hqlBuffer.append("failtotalnumber = ?,");
		hqlBuffer.append("failtotalamount = ?");
		hqlBuffer.append(" where ");
		hqlBuffer.append("msgid = ?");
		Query query = getSession().createQuery(hqlBuffer.toString());
		query.setParameter(0, Integer.valueOf(successCount));
		query.setParameter(1, successAmount.longValue());
		query.setParameter(2, failtotalnumber);
		query.setParameter(3, failtotalamount);
		query.setParameter(4, orgnMsgId);
		int rows = query.executeUpdate();
		logger.info("hql:{},effect rows:{}",hqlBuffer.toString(),rows);
		//更新明细
		List<RcvgDtls1> rcvgDtList = btchColltnChrgsRspnInf.getRcvgDtls();
		for(RcvgDtls1 dtls : rcvgDtList){
			RspnInf1 rspnInf = dtls.getRspnInf();
			RspnInfoBean rspnInfoBean = new RspnInfoBean(rspnInf);
			rspnInfoBean.setTxId(dtls.getTxId());
			cnapsCollectDetaLogDAO.updateCollectDetaRSP(rspnInfoBean);
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public CnapsCollectBatchLogDO getCollectBatchLogByMsgId(String msgId) {
		Criteria criteria = getSession().createCriteria(CnapsCollectBatchLogDO.class);
		criteria.add(Restrictions.eq("msgid", msgId));
		CnapsCollectBatchLogDO uniqueResult = (CnapsCollectBatchLogDO) criteria.uniqueResult();
		return uniqueResult;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateCollectBatchNPCRSP(NPCPrcInfoBean npcPrcInfoBean) {
		// TODO Auto-generated method stub
		String hql = "update CnapsCollectBatchLogDO set npcprocessstatus = ?,npcprocesscode = ?,npcrejectinformation = ?,npcnettingdate = ?,npcnettinground = ?,npcsettlementdate = ?,npcreceivetime = ?,npctransmittime where msgid = ?";
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("update CnapsCollectBatchLogDO set ");
		hqlBuffer.append("npcprocessstatus = ?,");
		hqlBuffer.append("npcprocesscode = ?,");
		hqlBuffer.append("npcrejectinformation = ?,");
		hqlBuffer.append("npcnettingdate = ?,");
		hqlBuffer.append("npcnettinground = ?,");
		hqlBuffer.append("npcsettlementdate = ?,");
		hqlBuffer.append("npcreceivetime = ?,");
		hqlBuffer.append("npctransmittime = ? ");
		hqlBuffer.append(" where ");
		hqlBuffer.append("msgid = ?");
		Query query = getSession().createQuery(hqlBuffer.toString());
		query.setParameter(0, npcPrcInfoBean.getProcessStatus());
		query.setParameter(1, npcPrcInfoBean.getProcessCode());
		query.setParameter(2, npcPrcInfoBean.getRejectInformation());
		query.setParameter(3, npcPrcInfoBean.getNettingDate());
		query.setParameter(4, npcPrcInfoBean.getNettingRound());
		query.setParameter(5, npcPrcInfoBean.getSettlementDate());
		query.setParameter(6, npcPrcInfoBean.getReceiveTime());
		query.setParameter(7, npcPrcInfoBean.getTransmitTime());
		query.setParameter(8, npcPrcInfoBean.getMsgid());
		int rows = query.executeUpdate();
		logger.info("hql:{},effect rows:{}",hqlBuffer.toString(),rows);
		
	}

	
	
}

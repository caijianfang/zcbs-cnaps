package com.zcbspay.platform.cnaps.message.dao.impl;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.Document;
import com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.GroupHeader1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.NPCPrcInf1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.OrgnlMsgInf1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.RealTmColltnChrgsRspnInf1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.RealTmColltnChrgsRspnV01;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.bean.SingleCollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectSingleLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.dao.bean.NPCPrcInfoBean;
import com.zcbspay.platform.cnaps.message.dao.bean.RspnInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectSingleLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.utils.BeanCopyUtil;
import com.zcbspay.platform.cnaps.utils.DateUtil;

@Repository
public class CnapsCollectSingleLogDAOImpl extends HibernateBaseDAOImpl<CnapsCollectSingleLogDO> implements CnapsCollectSingleLogDAO {

	private static final Logger logger = LoggerFactory.getLogger(CnapsCollectSingleLogDAOImpl.class);
	@Autowired
	private CnapsLogDAO cnapsLogDAO; 
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void saveCollectSingleLog(SingleCollectionChargesDetailBean singleBean) {
		//保存人行核心交易流水
		CnapsLogDO cnapsLog = new CnapsLogDO();
		cnapsLog.setMsgid(singleBean.getMsgId());
		cnapsLog.setSyscode("BEPS");
		cnapsLog.setMsgtype(MessageTypeEnum.BEPS384.value());
		cnapsLog.setCreatedate(singleBean.getCreatedate());
		cnapsLog.setInstructingdirectparty(singleBean.getInstructingdirectparty());
		cnapsLog.setInstructingparty(singleBean.getInstructingparty());
		cnapsLog.setInstructeddirectparty(singleBean.getInstructeddirectparty());
		cnapsLog.setInstructedparty(singleBean.getInstructedparty());
		cnapsLogDAO.saveCNAPSLog(cnapsLog);
		//保存实时代收交易流水
		CnapsCollectSingleLogDO collectSingleLog = new CnapsCollectSingleLogDO();
		collectSingleLog.setMsgid(singleBean.getMsgId());
		collectSingleLog.setBatchno(singleBean.getBatchNo());
		collectSingleLog.setTxid(singleBean.getTxId());
		collectSingleLog.setDebtorname(singleBean.getDebtorName());
		collectSingleLog.setDebtoraccountno(singleBean.getDebtorAccountNo());
		collectSingleLog.setDebtoraccountno(singleBean.getDebtorAccountNo());
		collectSingleLog.setDebtoragentcode(singleBean.getDebtorAgentCode());
		collectSingleLog.setDebtorbranchcode(singleBean.getDebtorBranchCode());
		collectSingleLog.setCreditoragentcode(singleBean.getCreditorAgentCode());
		collectSingleLog.setCreditorbranchcode(singleBean.getCreditorBranchCode());
		collectSingleLog.setCreditorname(singleBean.getCreditorName());
		collectSingleLog.setCreditoraccountno(singleBean.getCreditorAccountNo());
		collectSingleLog.setAmount(Long.valueOf(singleBean.getAmount()));
		collectSingleLog.setCategorypurposecode(singleBean.getCategoryPurposeCode());
		collectSingleLog.setPurposeproprietary(singleBean.getPurposeCode());
		collectSingleLog.setEndtoendidentification(singleBean.getEndToEndIdentification());
		collectSingleLog.setCheckflag(singleBean.getCheckFlag());
		collectSingleLog.setTxnseqno(singleBean.getTxnseqno());
		saveEntity(collectSingleLog);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void updateRealTimeCollectionChargesRSP(Document document){
		RealTmColltnChrgsRspnV01 realTmColltnChrgsRspn = document.getRealTmColltnChrgsRspn();
		GroupHeader1 groupHeader = realTmColltnChrgsRspn.getGrpHdr();
		OrgnlMsgInf1 orgnlMsgInf = realTmColltnChrgsRspn.getOrgnlMsgInf();
		NPCPrcInf1 npcPrcInf = realTmColltnChrgsRspn.getNPCPrcInf();
		RealTmColltnChrgsRspnInf1 realTmColltnChrgsRspnInf = realTmColltnChrgsRspn.getRealTmColltnChrgsRspnInf();
		
		//保存人行核心交易流水
		CnapsLogDO cnapsLog = new CnapsLogDO();
		cnapsLog.setMsgid(groupHeader.getMsgId());
		cnapsLog.setSyscode(groupHeader.getSysCd().value());
		cnapsLog.setMsgtype(MessageTypeEnum.BEPS385.value());
		try {
			cnapsLog.setCreatedate(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(groupHeader.getCreDtTm())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cnapsLog.setInstructingdirectparty(groupHeader.getInstgPty().getInstgDrctPty());
		cnapsLog.setInstructingparty(groupHeader.getInstgPty().getInstgPty());
		cnapsLog.setInstructeddirectparty(groupHeader.getInstdPty().getInstdDrctPty());
		cnapsLog.setInstructedparty(groupHeader.getInstdPty().getInstdPty());
		cnapsLogDAO.saveCNAPSLog(cnapsLog);
		//保存人行核心交易流水 END
		
		//原批量代收报文标示号
		String orgnMsgId = orgnlMsgInf.getOrgnlMsgId();
		//判断原报批量代收交易是否存在
		CnapsLogDO orgnCNAPSLogDO = cnapsLogDAO.getCnapsLogDOByMsgId(orgnMsgId);
		if(orgnCNAPSLogDO==null){
			//抛出异常，暂时未定义
		}
		//更新实时代收NPC应答信息
		NPCPrcInfoBean npcPrcInfoBean = new NPCPrcInfoBean(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.NPCPrcInf1.class, npcPrcInf));
		npcPrcInfoBean.setMsgid(orgnMsgId);
		updateCollectRealTimeNPCRSP(npcPrcInfoBean);
		
		//更新实时代收应答信息 
		RspnInfoBean rspnInfoBean = new RspnInfoBean();
		rspnInfoBean.setStatus( realTmColltnChrgsRspnInf.getRspnInf().getSts());
		rspnInfoBean.setRejectCode( realTmColltnChrgsRspnInf.getRspnInf().getRjctCd());
		rspnInfoBean.setRejectInformation(realTmColltnChrgsRspnInf.getRspnInf().getRjctInf());
		rspnInfoBean.setProcessParty( realTmColltnChrgsRspnInf.getRspnInf().getPrcPty());
		rspnInfoBean.setTxId(orgnMsgId);
		updateRealTimeCollectRSP(rspnInfoBean);
		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateCollectSingleCommRSP(CmonConfInfoBean cmonConfInfoBean) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("update CnapsCollectSingleLogDO set ");
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
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateCollectRealTimeNPCRSP(NPCPrcInfoBean npcPrcInfoBean){
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("update CnapsCollectSingleLogDO set ");
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
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateRealTimeCollectRSP(RspnInfoBean rspnInfoBean) {
		// TODO Auto-generated method stub
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("update CnapsCollectSingleLogDO set ");
		hqlBuffer.append("rspstatus = ?,");
		hqlBuffer.append("rsprejectcode = ?,");
		hqlBuffer.append("rsprejectinformation = ?,");
		hqlBuffer.append("rspprocessparty = ?,");
		hqlBuffer.append("rspdate = ?");
		hqlBuffer.append(" where ");
		hqlBuffer.append(" msgid = ?");
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

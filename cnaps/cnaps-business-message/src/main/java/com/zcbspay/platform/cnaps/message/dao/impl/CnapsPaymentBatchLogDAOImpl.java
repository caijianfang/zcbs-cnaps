package com.zcbspay.platform.cnaps.message.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.ChckFlgCode1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.BtchPmtByAgcyRspnInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.BtchPmtByAgcyRspnV01;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.Document;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.GroupHeader1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.NPCPrcInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.OrgnlMsgInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.RspnInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.SndgDtls1;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.bean.PaymentDetailBean;
import com.zcbspay.platform.cnaps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.message.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentDetaLogDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.NPCPrcInfoBeanComm;
import com.zcbspay.platform.cnaps.message.dao.bean.RspnInfoBeanComm;
import com.zcbspay.platform.cnaps.message.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentBatchLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentDetaLogDO;
import com.zcbspay.platform.cnaps.utils.DateUtil;

public class CnapsPaymentBatchLogDAOImpl extends HibernateBaseDAOImpl<CnapsPaymentBatchLogDO> implements CnapsPaymentBatchLogDAO {

    private static final Logger logger = LoggerFactory.getLogger(CnapsCollectBatchLogDAOImpl.class);

    @Autowired
    private CnapsLogDAO cnapsLogDAO;
    @Autowired
    private CnapsPaymentDetaLogDAO cnapsPaymentDetaLogDAO;

    @Override
    public void savePaymentBatchSerialInfo(PaymentTotalBean totalBean) {
        // 保存人行核心交易流水
        CnapsLogDO cnapsLog = new CnapsLogDO();
        cnapsLog.setMsgid(totalBean.getMsgId());
        cnapsLog.setSyscode("BEPS");
        cnapsLog.setMsgtype(MessageTypeEnum.BEPS382.value());
        cnapsLog.setCreatedate(totalBean.getCreatedate());
        cnapsLog.setInstructingdirectparty(totalBean.getInstructingdirectparty());
        cnapsLog.setInstructingparty(totalBean.getInstructingparty());
        cnapsLog.setInstructeddirectparty(totalBean.getInstructeddirectparty());
        cnapsLog.setInstructedparty(totalBean.getInstructedparty());
        cnapsLogDAO.saveCNAPSLog(cnapsLog);
        // 保存代付批次数据
        CnapsPaymentBatchLogDO cnapsPaymentBatchLogDO = new CnapsPaymentBatchLogDO();
        cnapsPaymentBatchLogDO.setMsgid(totalBean.getMsgId());
        cnapsPaymentBatchLogDO.setBatchno(totalBean.getBatchno());
        cnapsPaymentBatchLogDO.setTransmitsenderdate(totalBean.getTransmitdate());
        cnapsPaymentBatchLogDO.setTransmitreceiverdate(totalBean.getTransmitreceiverdate());
        cnapsPaymentBatchLogDO.setReturnlimited(totalBean.getReturnlimited());
        cnapsPaymentBatchLogDO.setReceivetype("RT00");
        cnapsPaymentBatchLogDO.setEndtoendidentification(totalBean.getEndToEndIdentification());
        cnapsPaymentBatchLogDO.setCheckflag(ChckFlgCode1.CE_01.value());
        cnapsPaymentBatchLogDO.setDebtorname(totalBean.getDebtorName());
        cnapsPaymentBatchLogDO.setDebtoraccountno(totalBean.getDebtorAccountNo());
        cnapsPaymentBatchLogDO.setDebtoragentcode(totalBean.getDebtorAgentCode());
        cnapsPaymentBatchLogDO.setCreditoragentcode(totalBean.getCreditorAgentCode());
        cnapsPaymentBatchLogDO.setTotalamount(Long.parseLong(totalBean.getTotalAmount()));
        cnapsPaymentBatchLogDO.setCategorypurposecode(totalBean.getCategoryPurposeCode());
        cnapsPaymentBatchLogDO.setDebtornumber(Integer.parseInt(totalBean.getCreditorNumber()));
        savePaymentBatch(cnapsPaymentBatchLogDO);
        // 保存明细信息
        List<PaymentDetailBean> detailList = totalBean.getDetailList();
        for (PaymentDetailBean detailBean : detailList) {
            CnapsPaymentDetaLogDO paymentDetaLog = new CnapsPaymentDetaLogDO();
            paymentDetaLog.setBatchno(totalBean.getBatchno());
            paymentDetaLog.setTxid(detailBean.getTxId());
            paymentDetaLog.setPurposeproprietary(detailBean.getProprietary());
            paymentDetaLog.setCreditorname(detailBean.getCreditorName());
            paymentDetaLog.setCreditoraccountno(detailBean.getCreditorAccountNo());
            paymentDetaLog.setCreditorbranchcode(detailBean.getCreditorBranchCode());
            paymentDetaLog.setAmount(Long.parseLong(detailBean.getAmount()));
            paymentDetaLog.setAddinfo(detailBean.getAdditionalInformation());

            cnapsPaymentDetaLogDAO.savePaymentDetaLog(paymentDetaLog);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void savePaymentBatch(CnapsPaymentBatchLogDO cnapsPaymentBatchLogDO) {
        saveEntity(cnapsPaymentBatchLogDO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void updateBatchCollectionChargesRSP(Document document) {
        BtchPmtByAgcyRspnV01 btchPmtByAgcyRspnV01 = document.getBtchPmtByAgcyRspn();
        GroupHeader1 grpHdr = btchPmtByAgcyRspnV01.getGrpHdr();
        OrgnlMsgInf1 orgnlMsgInf = btchPmtByAgcyRspnV01.getOrgnlMsgInf();
        NPCPrcInf1 npcPrcInf = btchPmtByAgcyRspnV01.getNPCPrcInf();
        BtchPmtByAgcyRspnInf1 btchPmtByAgcyRspnInf1 = btchPmtByAgcyRspnV01.getBtchPmtByAgcyRspnInf();
        // 保存应答报文数据
        CnapsLogDO cnapsLog = new CnapsLogDO();
        cnapsLog.setMsgid(grpHdr.getMsgId());
        cnapsLog.setSyscode(grpHdr.getSysCd().value());
        cnapsLog.setMsgtype(MessageTypeEnum.BEPS383.value());
        try {
            cnapsLog.setCreatedate(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(grpHdr.getCreDtTm())));
        }
        catch (Exception e) {
            // TODO
            logger.error("date parse failed!!!", e);
        }
        cnapsLog.setInstructingdirectparty(grpHdr.getInstgPty().getInstgDrctPty());
        cnapsLog.setInstructingparty(grpHdr.getInstgPty().getInstgPty());
        cnapsLog.setInstructeddirectparty(grpHdr.getInstdPty().getInstdDrctPty());
        cnapsLog.setInstructedparty(grpHdr.getInstdPty().getInstdPty());
        cnapsLogDAO.saveCNAPSLog(cnapsLog);

        // 原批量代收报文标示号
        String orgnMsgId = orgnlMsgInf.getOrgnlMsgId();
        // 判断原报批量代收交易是否存在
        CnapsLogDO orgnCNAPSLogDO = cnapsLogDAO.getCnapsLogDOByMsgId(orgnMsgId);
        if (orgnCNAPSLogDO == null) {
            // TODO 抛出异常，暂时未定义
        }
        NPCPrcInfoBeanComm npcPrcInfoBean = new NPCPrcInfoBeanComm(npcPrcInf.getPrcSts(), npcPrcInf.getPrcCd(), npcPrcInf.getRjctInf(), npcPrcInf.getNetgDt(), npcPrcInf.getSttlmDt(),
                npcPrcInf.getRcvTm(), npcPrcInf.getTrnsmtTm(), npcPrcInf.getNetgRnd());
        npcPrcInfoBean.setMsgid(orgnMsgId);
        // 更新代付批次NPC应答信息
        updatePaymentBatchNPCRSP(npcPrcInfoBean);
        // 更新代付批次应答信息
        BigDecimal successAmount = btchPmtByAgcyRspnInf1.getSndgTtlAmt().getValue();// 成功付款总金额
        String successCount = btchPmtByAgcyRspnInf1.getSndgTtlNb();// 成功付款总笔数
        CnapsPaymentBatchLogDO batchLog = getPaymentBatchLogByMsgId(grpHdr.getMsgId());
        long failtotalamount = batchLog.getTotalamount() - successAmount.longValue();
        int failtotalnumber = batchLog.getDebtornumber() - Integer.valueOf(successCount);
        StringBuffer hqlBuffer = new StringBuffer();
        hqlBuffer.append("update CnapsPaymentBatchLogDO set ");
        hqlBuffer.append("sendingtotalnumber = ?");
        hqlBuffer.append("sendingtotalamount = ?,");
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
        logger.info("hql:{},effect rows:{}", hqlBuffer.toString(), rows);
        // 更新明细
        List<SndgDtls1> sndgDtls = btchPmtByAgcyRspnInf1.getSndgDtls();
        for (SndgDtls1 sndgDtl : sndgDtls) {
            RspnInf1 rspnInf = sndgDtl.getRspnInf();
            RspnInfoBeanComm rspnInfoBean = new RspnInfoBeanComm(rspnInf.getSts(), rspnInf.getRjctCd(), rspnInf.getRjctInf(), rspnInf.getPrcPty());
            rspnInfoBean.setTxId(sndgDtl.getTxId());
            cnapsPaymentDetaLogDAO.updatePaymentDetaRSP(rspnInfoBean);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void updatePaymentBatchNPCRSP(NPCPrcInfoBeanComm npcPrcInfoBean) {
        // String hql =
        // "update CnapsPaymentBatchLogDO set npcprocessstatus = ?,npcprocesscode = ?,npcrejectinformation = ?,npcnettingdate = ?,npcnettinground = ?,npcsettlementdate = ?,npcreceivetime = ?,npctransmittime where msgid = ?";
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
        logger.info("hql:{},effect rows:{}", hqlBuffer.toString(), rows);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public CnapsPaymentBatchLogDO getPaymentBatchLogByMsgId(String msgId) {
        Criteria criteria = getSession().createCriteria(CnapsPaymentBatchLogDO.class);
        criteria.add(Restrictions.eq("msgid", msgId));
        CnapsPaymentBatchLogDO uniqueResult = (CnapsPaymentBatchLogDO) criteria.uniqueResult();
        return uniqueResult;
    }

}

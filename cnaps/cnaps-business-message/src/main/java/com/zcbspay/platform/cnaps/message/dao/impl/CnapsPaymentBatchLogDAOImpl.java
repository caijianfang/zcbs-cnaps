package com.zcbspay.platform.cnaps.message.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.ChckFlgCode1;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;
import com.zcbspay.platform.cnaps.message.bean.PaymentDetailBean;
import com.zcbspay.platform.cnaps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.message.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentDetaLogDAO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentBatchLogDO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsPaymentDetaLogDO;

public class CnapsPaymentBatchLogDAOImpl extends HibernateBaseDAOImpl<CnapsPaymentBatchLogDO> implements CnapsPaymentBatchLogDAO {

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

}

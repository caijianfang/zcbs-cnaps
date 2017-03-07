package com.zcbspay.platform.cnaps.message.beps.assembly;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zcbspay.platform.cnaps.bean.DefaultMessageBean;
import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.ActiveCurrencyAndAmount;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.BrnchId1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.BtchPmtByAgcyInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.BtchPmtByAgcyV01;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.CdtrAgt1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.CdtrDtls1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.ChckFlgCode1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.ClrSysMmbId1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.CtgyPurp1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.DbtrAgt1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.DbtrCdtr1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.DbtrCdtrAcct1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Document;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.FIId1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.GroupHeader1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Id1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.InstdPty1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.InstgPty1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Othr1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.PmtTpInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Purp1;
import com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.SystemCode1;
import com.zcbspay.platform.cnaps.message.bean.PaymentDetailBean;
import com.zcbspay.platform.cnaps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.message.untils.Constant;
import com.zcbspay.platform.cnaps.utils.DateUtil;

/**
 * 批量代付业务报文组装类
 * 
 * @author AlanMa
 *
 */
public class WithholdMessageAssembly {

    private static final Logger logger = LoggerFactory.getLogger(WithholdMessageAssembly.class);

    public static MessageBean batchWithholding(PaymentTotalBean totalBean) {
        // 报文体
        Document document = new Document();
        BtchPmtByAgcyV01 btchPmtByAgcy = new BtchPmtByAgcyV01();
        // 业务头
        GroupHeader1 groupHeader = new GroupHeader1();
        groupHeader.setSysCd(SystemCode1.BEPS);
        groupHeader.setCreDtTm(XMLDateUtils.convert2XMLGregorianCalendar(new Date()));
        groupHeader.setMsgId(totalBean.getMsgId());
        InstgPty1 instgPty = new InstgPty1();
        instgPty.setInstgDrctPty(Constant.getInstance().getInstgDrctPty());// 发起直接参与机构
        instgPty.setInstgPty(Constant.getInstance().getInstgPty());// 发起参与机构
        groupHeader.setInstgPty(instgPty);
        InstdPty1 instdPty = new InstdPty1();
        instdPty.setInstdDrctPty(totalBean.getDebtorAgentCode());// 接收直接参与机构
        instdPty.setInstdPty(totalBean.getDebtorAgentCode());// 接收参与机构
        groupHeader.setInstdPty(instdPty);
        btchPmtByAgcy.setGrpHdr(groupHeader);
        // 业务报文体
        BtchPmtByAgcyInf1 btchPmtByAgcyInf1 = new BtchPmtByAgcyInf1();
        btchPmtByAgcyInf1.setBtchNb("");// TODO 批次序号
        btchPmtByAgcyInf1.setTrnsmtSndrDt(XMLDateUtils.convert2XMLGregorianCalendar(new Date()));// 转发付款方日期
        btchPmtByAgcyInf1.setTrnsmtRcvrDt(XMLDateUtils.convert2XMLGregorianCalendar(new Date()));// TODO 转发收款方日期
        btchPmtByAgcyInf1.setRtrLtd("0");// TODO 回执期限
        btchPmtByAgcyInf1.setRcvTp("RT00");// 接收标识,RT00:付款人开户行接收; RT01:收款单位开户行接收
        btchPmtByAgcyInf1.setEndToEndId(totalBean.getEndToEndIdentification());
        btchPmtByAgcyInf1.setChckFlg(ChckFlgCode1.CE_01);// TODO CE01已核验、CE02未核验
        // 业务报文体--Debtor<Dbtr>
        DbtrCdtr1 dbtrCdtr = new DbtrCdtr1();
        btchPmtByAgcyInf1.setDbtr(dbtrCdtr);
        dbtrCdtr.setNm(totalBean.getDebtorName());
        // 业务报文体-- DebtorAccount<DbtrAcct>
        DbtrCdtrAcct1 dbtrCdtrAcct1 = new DbtrCdtrAcct1();
        Id1 identificate = new Id1();
        dbtrCdtrAcct1.setId(identificate);
        Othr1 other = new Othr1();
        identificate.setOthr(other);
        other.setId(totalBean.getDebtorAccountNo());
        DbtrAgt1 dbtrAgt1 = new DbtrAgt1();
        btchPmtByAgcyInf1.setDbtrAgt(dbtrAgt1);
        FIId1 fiId = new FIId1();
        dbtrAgt1.setFIId(fiId);
        ClrSysMmbId1 clrSysMmbId1 = new ClrSysMmbId1();
        fiId.setClrSysMmbId(clrSysMmbId1);
        clrSysMmbId1.setMmbId(totalBean.getDebtorAgentCode());
        BrnchId1 brnchId1 = new BrnchId1();
        dbtrAgt1.setBrnchId(brnchId1);
        brnchId1.setId(totalBean.getDebtorBranchCode());
        // 业务报文体-- <CdtrAgt>
        CdtrAgt1 cdtrAgt1 = new CdtrAgt1();
        btchPmtByAgcyInf1.setCdtrAgt(cdtrAgt1);
        FIId1 fIId1 = new FIId1();
        cdtrAgt1.setFIId(fIId1);
        ClrSysMmbId1 clrSysMmbId2 = new ClrSysMmbId1();
        fIId1.setClrSysMmbId(clrSysMmbId2);
        clrSysMmbId2.setMmbId(totalBean.getCreditorAgentCode());
        ActiveCurrencyAndAmount amount = new ActiveCurrencyAndAmount();
        amount.setCcy("CNY");
        amount.setValue(new BigDecimal(totalBean.getTotalAmount()));// 金额注意单位
        btchPmtByAgcyInf1.setTtlAmt(amount);
        // 业务报文体-- <PmtTpInf>
        PmtTpInf1 pmtTpInf1 = new PmtTpInf1();
        btchPmtByAgcyInf1.setPmtTpInf(pmtTpInf1);
        CtgyPurp1 ctgyPurp1 = new CtgyPurp1();
        pmtTpInf1.setCtgyPurp(ctgyPurp1);
        ctgyPurp1.setPrtry(totalBean.getCategoryPurposeCode());
        List<PaymentDetailBean> paymentDetailBeans = totalBean.getDetailList();
        btchPmtByAgcyInf1.setCdtrNb(Integer.toString(paymentDetailBeans.size()));
        // 收款人明细
        List<CdtrDtls1> cdtrDtls = btchPmtByAgcyInf1.getCdtrDtls();
        for (PaymentDetailBean paymentDetailBean : paymentDetailBeans) {
            CdtrDtls1 cdtrdtl = new CdtrDtls1();
            Purp1 purp1 = new Purp1();
            cdtrdtl.setPurp(purp1);
            purp1.setPrtry(paymentDetailBean.getProprietary());
            cdtrdtl.setTxId(paymentDetailBean.getTxId());
            DbtrCdtr1 dbtrCdtr1 = new DbtrCdtr1();
            cdtrdtl.setCdtr(dbtrCdtr1);
            dbtrCdtr1.setNm(paymentDetailBean.getCreditorName());
            DbtrCdtrAcct1 dbtrCdtrAcct = new DbtrCdtrAcct1();
            cdtrdtl.setCdtrAcct(dbtrCdtrAcct);
            Id1 id = new Id1();
            dbtrCdtrAcct.setId(id);
            Othr1 otherId = new Othr1();
            id.setOthr(otherId);
            otherId.setId(paymentDetailBean.getCreditorAccountNo());
            cdtrdtl.setBrnchId(paymentDetailBean.getCreditorBranchCode());
            ActiveCurrencyAndAmount amountDetail = new ActiveCurrencyAndAmount();
            amountDetail.setCcy("CNY");
            amountDetail.setValue(new BigDecimal(paymentDetailBean.getAmount()));// 金额注意单位
            cdtrdtl.setAmt(amountDetail);
            cdtrdtl.setAddtlInf(paymentDetailBean.getAdditionalInformation());
            cdtrDtls.add(cdtrdtl);
        }
        btchPmtByAgcy.setBtchPmtByAgcyInf(btchPmtByAgcyInf1);
        document.setBtchPmtByAgcy(btchPmtByAgcy);

        // 赋值报文流水记录信息
        try {
            totalBean.setCreatedate(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(groupHeader.getCreDtTm())));
        }
        catch (Exception e) {
            // TODO
            logger.error("parse time failed", e);
        }
        totalBean.setInstructingdirectparty(Constant.getInstance().getInstgDrctPty());
        totalBean.setInstructingparty(Constant.getInstance().getInstgPty());
        totalBean.setInstructeddirectparty(totalBean.getDebtorAgentCode());
        totalBean.setInstructingparty(totalBean.getDebtorAgentCode());
        totalBean.setTransmitdate(btchPmtByAgcyInf1.getTrnsmtSndrDt().toString());
        totalBean.setTransmitreceiverdate(btchPmtByAgcyInf1.getTrnsmtRcvrDt().toString());
        totalBean.setReturnlimited(btchPmtByAgcyInf1.getRtrLtd());

        MessageBean messageBean = new DefaultMessageBean(document, MessageTypeEnum.BEPS382);
        return messageBean;
    }
}

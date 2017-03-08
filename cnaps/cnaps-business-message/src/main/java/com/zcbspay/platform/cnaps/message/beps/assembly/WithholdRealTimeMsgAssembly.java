package com.zcbspay.platform.cnaps.message.beps.assembly;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zcbspay.platform.cnaps.bean.DefaultMessageBean;
import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.ActiveCurrencyAndAmount;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.BrnchId1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.ChckFlgCode1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.ClrSysMmbId1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.CtgyPurp1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.DbtrCdtr1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.DbtrCdtrAcct1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.DbtrCdtrAgt1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.Document;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.FIId1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.GroupHeader1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.Id1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.InstdPty1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.InstgPty1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.Othr1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.PmtTpInf1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.Purp1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.RealTmPmtByAgcyInf1;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.RealTmPmtByAgcyV01;
import com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.SystemCode1;
import com.zcbspay.platform.cnaps.message.bean.SinglePaymentBean;
import com.zcbspay.platform.cnaps.message.untils.Constant;
import com.zcbspay.platform.cnaps.utils.DateUtil;

public class WithholdRealTimeMsgAssembly {

    private static final Logger logger = LoggerFactory.getLogger(WithholdRealTimeMsgAssembly.class);

    public static MessageBean realTimePayment(SinglePaymentBean singleBean) {
        Document document = new Document();
        RealTmPmtByAgcyV01 realTmPmtByAgcyV01 = new RealTmPmtByAgcyV01();
        // 业务头
        GroupHeader1 groupHeader = new GroupHeader1();
        groupHeader.setSysCd(SystemCode1.BEPS);
        groupHeader.setCreDtTm(XMLDateUtils.convert2XMLGregorianCalendar(new Date()));
        try {
            singleBean.setCreatedate(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(groupHeader.getCreDtTm())));
        }
        catch (Exception e) {
            // TODO
            logger.error("date convert failed", e);
        }
        groupHeader.setMsgId(singleBean.getMsgId());
        InstgPty1 instgPty = new InstgPty1();
        instgPty.setInstgDrctPty(Constant.getInstance().getInstgDrctPty());// 发起直接参与机构
        instgPty.setInstgPty(Constant.getInstance().getInstgPty());// 发起参与机构
        singleBean.setInstructingdirectparty(Constant.getInstance().getInstgDrctPty());
        singleBean.setInstructingparty(Constant.getInstance().getInstgPty());
        groupHeader.setInstgPty(instgPty);
        InstdPty1 instdPty = new InstdPty1();
        groupHeader.setInstdPty(instdPty);
        instdPty.setInstdDrctPty(singleBean.getDebtorAgentCode());// 接收直接参与机构
        instdPty.setInstdPty(singleBean.getDebtorAgentCode());// 接收参与机构
        singleBean.setInstructeddirectparty(singleBean.getDebtorAgentCode());
        singleBean.setInstructingparty(singleBean.getDebtorAgentCode());
        realTmPmtByAgcyV01.setGrpHdr(groupHeader);

        // 业务报文体
        RealTmPmtByAgcyInf1 realTmPmtByAgcyInf1 = new RealTmPmtByAgcyInf1();
        realTmPmtByAgcyInf1.setBtchNb(singleBean.getBatchNo());
        realTmPmtByAgcyInf1.setTxId(singleBean.getTxId());
        DbtrCdtr1 cdtr = new DbtrCdtr1();
        realTmPmtByAgcyInf1.setDbtr(cdtr);
        cdtr.setNm(singleBean.getDebtorName());
        DbtrCdtrAcct1 dbtrCdtrAcct1 = new DbtrCdtrAcct1();
        realTmPmtByAgcyInf1.setDbtrAcct(dbtrCdtrAcct1);
        Id1 drId = new Id1();
        dbtrCdtrAcct1.setId(drId);
        Othr1 otherDr = new Othr1();
        drId.setOthr(otherDr);
        otherDr.setId(singleBean.getDebtorAccountNo());
        DbtrCdtrAgt1 dbtrCdtrAgt1 = new DbtrCdtrAgt1();
        realTmPmtByAgcyInf1.setDbtrAgt(dbtrCdtrAgt1);
        FIId1 fIId1 = new FIId1();
        dbtrCdtrAgt1.setFIId(fIId1);
        ClrSysMmbId1 clrSysMmbId1 = new ClrSysMmbId1();
        fIId1.setClrSysMmbId(clrSysMmbId1);
        clrSysMmbId1.setMmbId(singleBean.getDebtorAgentCode());
        BrnchId1 brnchId1 = new BrnchId1();
        dbtrCdtrAgt1.setBrnchId(brnchId1);
        brnchId1.setId(singleBean.getDebtorBranchCode());
        DbtrCdtrAgt1 dbtrCdtrAgt2 = new DbtrCdtrAgt1();
        realTmPmtByAgcyInf1.setCdtrAgt(dbtrCdtrAgt2);
        FIId1 fIId2 = new FIId1();
        dbtrCdtrAgt2.setFIId(fIId2);
        ClrSysMmbId1 clrSysMmbId2 = new ClrSysMmbId1();
        fIId2.setClrSysMmbId(clrSysMmbId2);
        clrSysMmbId2.setMmbId(singleBean.getCreditorAgentCode());
        BrnchId1 brnchId2 = new BrnchId1();
        brnchId2.setId(singleBean.getCreditorBranchCode());
        dbtrCdtrAgt2.setBrnchId(brnchId2);
        DbtrCdtr1 dbtrCdtr1 = new DbtrCdtr1();
        realTmPmtByAgcyInf1.setCdtr(dbtrCdtr1);
        dbtrCdtr1.setNm(singleBean.getCreditorName());
        DbtrCdtrAcct1 dbtrCdtrAcct2 = new DbtrCdtrAcct1();
        realTmPmtByAgcyInf1.setCdtrAcct(dbtrCdtrAcct2);
        Id1 credId = new Id1();
        dbtrCdtrAcct2.setId(credId);
        Othr1 otherCred = new Othr1();
        credId.setOthr(otherCred);
        otherCred.setId(singleBean.getCreditorAccountNo());
        ActiveCurrencyAndAmount amount = new ActiveCurrencyAndAmount();
        amount.setCcy("CNY");
        amount.setValue(new BigDecimal(singleBean.getAmount()));
        realTmPmtByAgcyInf1.setAmt(amount);
        PmtTpInf1 pmtTpInf1 = new PmtTpInf1();
        realTmPmtByAgcyInf1.setPmtTpInf(pmtTpInf1);
        CtgyPurp1 ctgyPurp1 = new CtgyPurp1();
        pmtTpInf1.setCtgyPurp(ctgyPurp1);
        ctgyPurp1.setPrtry(singleBean.getCategoryPurposeCode());
        Purp1 purp1 = new Purp1();
        realTmPmtByAgcyInf1.setPurp(purp1);
        purp1.setPrtry(singleBean.getPurposeCode());
        realTmPmtByAgcyInf1.setEndToEndId(singleBean.getEndToEndIdentification());
        realTmPmtByAgcyInf1.setChckFlg(ChckFlgCode1.CE_01);
        realTmPmtByAgcyV01.setRealTmPmtByAgcyInf(realTmPmtByAgcyInf1);
        document.setRealTmPmtByAgcy(realTmPmtByAgcyV01);
        MessageBean messageBean = new DefaultMessageBean(document, MessageTypeEnum.BEPS386);
        return messageBean;
    }
}

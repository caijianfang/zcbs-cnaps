package com.zcbspay.platform.cnaps.message.beps.assembly;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zcbspay.platform.cnaps.bean.DefaultMessageBean;
import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.ActiveCurrencyAndAmount;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.BrnchId1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.BtchColltnChrgsInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.BtchColltnChrgsV01;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.CdtrAgt1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.ChckFlgCode1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.ClrSysMmbId1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.CtgyPurp1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.DbtrAgt1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.DbtrCdtr1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.DbtrCdtrAcct1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.DbtrDtls1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.FIId1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.GroupHeader1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Id1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.InstdPty1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.InstgPty1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Othr1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.PmtTpInf1;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Purp1;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.message.untils.Constant;
import com.zcbspay.platform.cnaps.utils.DateUtil;

/**
 * 批量代收业务报文组装类
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2017年3月6日 上午9:52:07
 * @since
 */
public class CollectionChargesMessageAssembly {

	public static MessageBean batchCollectionCharges(CollectionChargesTotalBean totalBean){
		com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Document document = new com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Document();
		BtchColltnChrgsV01 btchColltnChrgs = new BtchColltnChrgsV01();
		//业务头
		GroupHeader1 groupHeader = new GroupHeader1();
		groupHeader.setSysCd(com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.SystemCode1.BEPS);
		groupHeader.setCreDtTm(XMLDateUtils.convert2XMLGregorianCalendar(new Date()));
		try {
			totalBean.setCreatedate(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(groupHeader.getCreDtTm())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		groupHeader.setMsgId(totalBean.getMsgId());
		InstgPty1 instgPty = new InstgPty1();
		instgPty.setInstgDrctPty(Constant.getInstance().getInstgDrctPty());//发起直接参与机构
		instgPty.setInstgPty(Constant.getInstance().getInstgPty());//发起参与机构
		totalBean.setInstructingdirectparty(Constant.getInstance().getInstgDrctPty());
		totalBean.setInstructingparty(Constant.getInstance().getInstgPty());
		groupHeader.setInstgPty(instgPty);
		InstdPty1 instdPty =new InstdPty1();
		instdPty.setInstdDrctPty(totalBean.getDebtorAgentCode());//接收直接参与机构
		instdPty.setInstdPty(totalBean.getDebtorAgentCode());//接收参与机构
		totalBean.setInstructeddirectparty(totalBean.getDebtorAgentCode());
		totalBean.setInstructingparty(totalBean.getDebtorAgentCode());
		
		groupHeader.setInstdPty(instdPty);
		btchColltnChrgs.setGrpHdr(groupHeader);
		//业务报文体
		BtchColltnChrgsInf1 btchColltnChrgsInf = new BtchColltnChrgsInf1();
		btchColltnChrgsInf.setBtchNb("");//批次序号
		btchColltnChrgsInf.setTrnsmtDt(XMLDateUtils.convert2XMLGregorianCalendar(new Date()));//转发日期
		btchColltnChrgsInf.setRtrLtd("1");//回执期限
		
		totalBean.setBatchno(btchColltnChrgsInf.getBtchNb());
		try {
			totalBean.setTransmitdate(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(btchColltnChrgsInf.getTrnsmtDt())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		totalBean.setReturnlimited(btchColltnChrgsInf.getRtrLtd());
		DbtrAgt1 dbtrAgt = new DbtrAgt1();
		FIId1 fiId = new FIId1();
		ClrSysMmbId1 clrSysMmbId1 = new ClrSysMmbId1();
		clrSysMmbId1.setMmbId(totalBean.getDebtorAgentCode());
		fiId.setClrSysMmbId(clrSysMmbId1);
		dbtrAgt.setFIId(fiId);
		btchColltnChrgsInf.setDbtrAgt(dbtrAgt);
		CdtrAgt1 cdtrAgt1 = new CdtrAgt1();
		FIId1 fiId1 = new FIId1();
		ClrSysMmbId1 clrSysMmbId2 = new ClrSysMmbId1();
		clrSysMmbId2.setMmbId(totalBean.getCreditorAgentCode());
		fiId1.setClrSysMmbId(clrSysMmbId2);
		cdtrAgt1.setFIId(fiId1);
		BrnchId1 brnchId1 = new BrnchId1();
		brnchId1.setId(totalBean.getCreditorBranchCode());
		cdtrAgt1.setBrnchId(brnchId1 );
		btchColltnChrgsInf.setCdtrAgt(cdtrAgt1);
		DbtrCdtr1 dbtrCdtr1 = new DbtrCdtr1();
		dbtrCdtr1.setNm(totalBean.getCreditorName());
		btchColltnChrgsInf.setCdtr(dbtrCdtr1);
		DbtrCdtrAcct1 dbtrCdtrAcct1 = new DbtrCdtrAcct1();
		Id1 id1 = new Id1();
		Othr1 othr1 = new Othr1();
		othr1.setId(totalBean.getCreditorAccountNo());
		id1.setOthr(othr1 );
		dbtrCdtrAcct1.setId(id1);
		btchColltnChrgsInf.setCdtrAcct(dbtrCdtrAcct1);
		ActiveCurrencyAndAmount activeCurrencyAndAmount = new ActiveCurrencyAndAmount();
		activeCurrencyAndAmount.setCcy("CNY");
		activeCurrencyAndAmount.setValue(new BigDecimal(totalBean.getTotalAmount()));//金额注意单位
		btchColltnChrgsInf.setTtlAmt(activeCurrencyAndAmount);
		PmtTpInf1 pmtTpInf1 = new PmtTpInf1();
		CtgyPurp1 ctgyPurp1 = new CtgyPurp1();
		ctgyPurp1.setPrtry(totalBean.getCategoryPurposeCode());
		pmtTpInf1.setCtgyPurp(ctgyPurp1);
		btchColltnChrgsInf.setPmtTpInf(pmtTpInf1 );
		
		btchColltnChrgsInf.setDbtrNb(totalBean.getDetailList().size()+"");
		List<DbtrDtls1> dbtrDtls = btchColltnChrgsInf.getDbtrDtls();
		List<CollectionChargesDetailBean> detailList = totalBean.getDetailList();
		for(CollectionChargesDetailBean detailBean : detailList){
			DbtrDtls1 dbtrDtls1 = new DbtrDtls1();
			Purp1 purp1 = new Purp1();
			purp1.setPrtry(detailBean.getPurposeProprietary());
			dbtrDtls1.setPurp(purp1);
			dbtrDtls1.setTxId(detailBean.getTxId());
			DbtrCdtr1 dbtrCdtr12 = new DbtrCdtr1();
			dbtrCdtr12.setNm(detailBean.getDebtorName());
			dbtrDtls1.setDbtr(dbtrCdtr12 );
			DbtrCdtrAcct1 dbtrCdtrAcct12 = new DbtrCdtrAcct1();
			Id1 id12 = new Id1();
			Othr1 othr12 = new Othr1();
			othr12.setId(detailBean.getDebtorAccountNo());
			id12.setOthr(othr12 );
			dbtrCdtrAcct12.setId(id12);
			dbtrDtls1.setDbtrAcct(dbtrCdtrAcct12);
			dbtrDtls1.setBrnchId(detailBean.getDebtorBranchCode());
			ActiveCurrencyAndAmount activeCurrencyAndAmount2 = new ActiveCurrencyAndAmount();
			activeCurrencyAndAmount2.setCcy("CNY");
			activeCurrencyAndAmount2.setValue(new BigDecimal(detailBean.getAmount()));
			dbtrDtls1.setAmt(activeCurrencyAndAmount2 );
			dbtrDtls1.setEndToEndId(detailBean.getEndToEndIdentification());
			ChckFlgCode1 chckFlgCode1 = ChckFlgCode1.fromValue(detailBean.getCheckFlag());
			dbtrDtls1.setChckFlg(chckFlgCode1);
			dbtrDtls1.setAddtlInf(detailBean.getAdditionalInformation());
			dbtrDtls.add(dbtrDtls1);
		}
		btchColltnChrgs.setBtchColltnChrgsInf(btchColltnChrgsInf);
		document.setBtchColltnChrgs(btchColltnChrgs);
		
		MessageBean messageBean = new DefaultMessageBean(document, MessageTypeEnum.BEPS380);
		return messageBean;
	}
}

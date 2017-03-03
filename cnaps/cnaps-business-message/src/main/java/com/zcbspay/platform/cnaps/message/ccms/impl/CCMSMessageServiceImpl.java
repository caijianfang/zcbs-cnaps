package com.zcbspay.platform.cnaps.message.ccms.impl;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.bean.utils.XMLUtils;
import com.zcbspay.platform.cnaps.ccms.bean.CommonConfirmation.CmonConfInf1;
import com.zcbspay.platform.cnaps.ccms.bean.CommonConfirmation.GroupHeader1;
import com.zcbspay.platform.cnaps.ccms.bean.CommonConfirmation.OrgnlGrpHdr1;
import com.zcbspay.platform.cnaps.message.bean.BusiQueryBean;
import com.zcbspay.platform.cnaps.message.bean.ResultBean;
import com.zcbspay.platform.cnaps.message.ccms.CCMSMessageService;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsLogDAO;
import com.zcbspay.platform.cnaps.message.dao.bean.CmonConfInfoBean;
import com.zcbspay.platform.cnaps.message.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.utils.DateUtil;

@Service
public class CCMSMessageServiceImpl implements CCMSMessageService {

	@Autowired
	private CnapsLogDAO cnapsLogDAO;
	@Autowired
	private CnapsCollectBatchLogDAO cnapsCollectBatchLogDAO; 
	@Override
	public void freeFormat() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fiToFIPaymentCancellationRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fiToFIPaymentStatuReport() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commonNotSignatureInformationBusiness() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commonNotSignatureInformationBusiNessResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commonSignatureInformationBusiness() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commonSignatureInformationBusiNessResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void businessQueryRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void businessQueryResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultBean queryTransactionRequest(BusiQueryBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void queryTransactionResponse(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paymentReturnRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paymentReturnResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void systemStatusNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void partOrgStatusNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loginRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loginResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void kickoutNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stoppingNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ACSSpecialTimeEndNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commonConfirmation(String message) {
		// TODO Auto-generated method stub
		try {
			MessageBean messageBean = XMLUtils.parseToBean(message, MessageTypeEnum.CCMS900);
			com.zcbspay.platform.cnaps.ccms.bean.CommonConfirmation.Document document = (com.zcbspay.platform.cnaps.ccms.bean.CommonConfirmation.Document)messageBean.getCNAPSMessageBean();
			GroupHeader1 grpHdr = document.getCmonConf().getGrpHdr();
			OrgnlGrpHdr1 orgnlGrpHdr = document.getCmonConf().getOrgnlGrpHdr();
			CmonConfInf1 cmonConfInf = document.getCmonConf().getCmonConfInf();
			CnapsLogDO cnapsLog = new CnapsLogDO();
			//业务头组件
			cnapsLog.setMsgid(grpHdr.getMsgId());
			cnapsLog.setSyscode(grpHdr.getSysCd().value());
			cnapsLog.setMsgtype(MessageTypeEnum.CCMS900.value());
			try {
				cnapsLog.setCreatedate(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, XMLDateUtils.convertToDate(grpHdr.getCreDtTm())));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cnapsLog.setInstructeddirectparty(grpHdr.getInstdPty().getInstdDrctPty());
			cnapsLog.setInstructedparty(grpHdr.getInstdPty().getInstdPty());
			cnapsLog.setInstructingdirectparty(grpHdr.getInstgPty().getInstgDrctPty());
			cnapsLog.setInstructingparty(grpHdr.getInstgPty().getInstgPty());
			//原报文主键组件
			cnapsLog.setOrgnlmsgid(orgnlGrpHdr.getOrgnlMsgId());
			cnapsLog.setOrgnlinstgpty(orgnlGrpHdr.getOrgnlInstgPty());
			cnapsLog.setOriginalmessagetype(orgnlGrpHdr.getOrgnlMT());
			//保存人行核心交易流水数据
			cnapsLogDAO.saveCNAPSLog(cnapsLog);
			
			
			
			MessageTypeEnum OrgnMsgType = MessageTypeEnum.fromValue(cnapsLog.getOriginalmessagetype());
			switch (OrgnMsgType) {
				case BEPS380:
					CmonConfInfoBean cmonConfInfoBean = new CmonConfInfoBean();
					cmonConfInfoBean.setDate(DateUtil.getCurrentDateTime());
					cmonConfInfoBean.setMsgId(orgnlGrpHdr.getOrgnlMsgId());
					cmonConfInfoBean.setNettinground(cmonConfInf.getNetgRnd());
					cmonConfInfoBean.setPartyidentification(cmonConfInf.getPtyId());
					cmonConfInfoBean.setPartyprocesscode(cmonConfInf.getPtyPrcCd());
					cmonConfInfoBean.setProcesscode(cmonConfInf.getPrcCd());
					try {
						cmonConfInfoBean.setProcessdate(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, XMLDateUtils.convertToDate(cmonConfInf.getPrcDt())));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cmonConfInfoBean.setProcessstatus(cmonConfInf.getPrcSts());
					cmonConfInfoBean.setRejectinformation(cmonConfInf.getRjctInf());
					cnapsCollectBatchLogDAO.updateCollectBatchCommRSP(cmonConfInfoBean);
					break;
				case CCMS900:
					
					break;
				default:
					break;
			}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void certificateNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void businessClassTypeManagement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void systemParameterNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void discardMessageNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void CISAgencyChangeNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void authorityChangeNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bankCodeChangeNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void basisChangeNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void certificateDownLoadApply() {
		// TODO Auto-generated method stub

	}

	@Override
	public void certificateDownLoadResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void transactionAmontUpperLimit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void communicationConfirmation() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkResponse() {
		// TODO Auto-generated method stub

	}

}

package com.zcbspay.platform.cnaps.message.beps.impl;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;

import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLUtils;
import com.zcbspay.platform.cnaps.message.bean.BusiQueryBean;
import com.zcbspay.platform.cnaps.message.bean.ResultBean;
import com.zcbspay.platform.cnaps.message.ccms.CCMSMessageService;

@Service
public class CCMSMessageServiceImpl implements CCMSMessageService {

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

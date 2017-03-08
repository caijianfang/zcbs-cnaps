package java.com.zcbspay.platform.cnaps.transfer.message.assemble.impl;

import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.AssembleBase;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchCollectionCharges;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchCollectionChargesResponse;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchCustomersAccountQueryRequest;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchCustomersAccountQueryResponse;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchCustomersContractManageRequest;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchCustomersContractManageResponse;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchPaymentByAgency;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BatchPaymentByAgencyResponse;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BusinessConfirmation;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.BusinessRejectNotice;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.CustomerPaymentCancellationRequest;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.CustomerPaymentCancellationResponse;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.RealTimeCollectionCharges;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.RealTimeCollectionChargesResponse;
import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.RealTimePaymentByAgency;

import com.zcbspay.platform.cnaps.transfer.message.assemble.MessageAssemble;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageType;

public class MessageAssembleImpl implements MessageAssemble {

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		MessageHeadBean messageHeadBean = new MessageHeadBean();
		MessageType messageType = bean.getBeanType();
		AssembleBase assemble = null;
		if (messageType == MessageType.beps380) { // 批量代收业务
			assemble = new BatchCollectionCharges();
		} else if (messageType == MessageType.beps382) {// 批量代付业务
			assemble = new BatchPaymentByAgency();
		} else if (messageType == MessageType.beps384) {// 实时代收业务
			assemble = new RealTimeCollectionCharges();
		} else if (messageType == MessageType.beps386) {// 实时代付业务
			assemble = new RealTimePaymentByAgency();
		} else if (messageType == MessageType.beps390) {// 代收代付撤销申请
			assemble = new CustomerPaymentCancellationRequest();
		} else if (messageType == MessageType.beps392) {// 批量客户签约协议管理
			assemble = new BatchCustomersContractManageRequest();
		} else if (messageType == MessageType.beps394) {// 批量客户账户信息查询
			assemble = new BatchCustomersAccountQueryRequest();
		}
		messageHeadBean = assemble.createMessageHead(bean);
		return messageHeadBean;
	}

	@Override
	public String signature(MessageBean bean) {
		MessageType messageType = bean.getBeanType();
		
		/********** 拼接签名要素串 **********/
		String signatureElement = null;
		AssembleBase assemble = null;
		if (messageType == MessageType.beps380) { // 批量代收业务
			assemble = new BatchCollectionCharges();
		} else if (messageType == MessageType.beps381) { // 批量代收业务回执
			assemble = new BatchCollectionChargesResponse();
		} else if (messageType == MessageType.beps382) { // 批量代付业务
			assemble = new BatchPaymentByAgency();
		} else if (messageType == MessageType.beps383) { // 批量代付业务回执
			assemble = new BatchPaymentByAgencyResponse();
		} else if (messageType == MessageType.beps384) { // 实时代收业务
			assemble = new RealTimeCollectionCharges();
		} else if (messageType == MessageType.beps385) { // 实时代收业务回执报文
			assemble = new RealTimeCollectionChargesResponse();
		} else if (messageType == MessageType.beps386) { // 实时代付业务
			assemble = new RealTimePaymentByAgency();
		} else if (messageType == MessageType.beps387) { // 实时代付业务回执
			assemble = new RealTimePaymentByAgencyResponse();
		} else if (messageType == MessageType.beps388) { // 代收付业务拒绝通知
			assemble = new BusinessRejectNotice();
		} else if (messageType == MessageType.beps389) { // 代收付业务收付款确认
			assemble = new BusinessConfirmation();
		} else if (messageType == MessageType.beps390) { // 代收代付撤销申请
			assemble = new CustomerPaymentCancellationRequest();
		} else if (messageType == MessageType.beps391) { // 代收代付撤销应答
			assemble = new CustomerPaymentCancellationResponse();
		} else if (messageType == MessageType.beps392) { // 批量客户签约协议管理
			assemble = new BatchCustomersContractManageRequest();
		} else if (messageType == MessageType.beps393) { // 批量客户签约协议管理应答
			assemble = new BatchCustomersContractManageResponse();
		} else if (messageType == MessageType.beps394) { // 批量客户账户信息查询
			assemble = new BatchCustomersAccountQueryRequest();
		} else if (messageType == MessageType.beps395) { // 批量客户账户查询应答
			assemble = new BatchCustomersAccountQueryResponse();
		}
		signatureElement = assemble.signatureElement(bean);
		
		return null;
	}

	@Override
	public String assemble(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

}

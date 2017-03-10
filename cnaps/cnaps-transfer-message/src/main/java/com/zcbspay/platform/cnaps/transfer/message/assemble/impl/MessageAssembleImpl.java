package com.zcbspay.platform.cnaps.transfer.message.assemble.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.transfer.message.assemble.MessageAssemble;
import com.zcbspay.platform.cnaps.transfer.message.assemble.detail.AssembleBase;
//import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
//import com.zcbspay.platform.cnaps.transfer.message.bean.MessageType;

@Service
public class MessageAssembleImpl implements MessageAssemble {
	@Autowired
	@Qualifier("batchCollectionCharges")
	private AssembleBase assembleBase380;
	
	@Autowired
	@Qualifier("batchCollectionChargesResponse")
	private AssembleBase assembleBase381;
	
	@Autowired
	@Qualifier("batchPaymentByAgency")
	private AssembleBase assembleBase382;
	
	@Autowired
	@Qualifier("batchPaymentByAgencyResponse")
	private AssembleBase assembleBase383;
	
	@Autowired
	@Qualifier("realTimeCollectionCharges")
	private AssembleBase assembleBase384;
	
	@Autowired
	@Qualifier("realTimeCollectionChargesResponse")
	private AssembleBase assembleBase385;
	
	@Autowired
	@Qualifier("realTimePaymentByAgency")
	private AssembleBase assembleBase386;
	
	@Autowired
	@Qualifier("realTimePaymentByAgencyResponse")
	private AssembleBase assembleBase387;
	
	@Autowired
	@Qualifier("businessRejectNotice")
	private AssembleBase assembleBase388;
	
	@Autowired
	@Qualifier("businessConfirmation")
	private AssembleBase assembleBase389;
	
	@Autowired
	@Qualifier("customerPaymentCancellationRequest")
	private AssembleBase assembleBase390;
	
	@Autowired
	@Qualifier("customerPaymentCancellationResponse")
	private AssembleBase assembleBase391;
	
	@Autowired
	@Qualifier("batchCustomersContractManageRequest")
	private AssembleBase assembleBase392;
	
	@Autowired
	@Qualifier("batchCustomersContractManageResponse")
	private AssembleBase assembleBase393;
	
	@Autowired
	@Qualifier("batchCustomersAccountQueryRequest")
	private AssembleBase assembleBase394;
	
	@Autowired
	@Qualifier("batchCustomersAccountQueryResponse")
	private AssembleBase assembleBase395;
	
	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		MessageTypeEnum messageType = bean.getBeanType();
		if (messageType == MessageTypeEnum.BEPS380) { // 批量代收业务
			return assembleBase380.createMessageHead(bean);
		} else if (messageType == MessageTypeEnum.BEPS382) {// 批量代付业务
			return assembleBase382.createMessageHead(bean);
		} else if (messageType == MessageTypeEnum.BEPS384) {// 实时代收业务
			return assembleBase384.createMessageHead(bean);
		} else if (messageType == MessageTypeEnum.BEPS386) {// 实时代付业务
			return assembleBase386.createMessageHead(bean);
		} else if (messageType == MessageTypeEnum.BEPS390) {// 代收代付撤销申请
			return assembleBase390.createMessageHead(bean);
		} else if (messageType == MessageTypeEnum.BEPS392) {// 批量客户签约协议管理
			return assembleBase392.createMessageHead(bean);
		} else if (messageType == MessageTypeEnum.BEPS394) {// 批量客户账户信息查询
			return assembleBase394.createMessageHead(bean);
		}
		return null;
	}

	@Override
	public String signature(MessageBean bean) {
		MessageTypeEnum messageType = bean.getBeanType();
		
		/********** 拼接签名要素串 **********/
		if (messageType == MessageTypeEnum.BEPS380) { 			// 批量代收业务
			return assembleBase380.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS381) { 	// 批量代收业务回执
			return assembleBase381.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS382) { 	// 批量代付业务
			return assembleBase382.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS383) { 	// 批量代付业务回执
			return assembleBase383.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS384) { 	// 实时代收业务
			return assembleBase384.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS385) { 	// 实时代收业务回执报文
			return assembleBase385.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS386) { 	// 实时代付业务
			return assembleBase386.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS387) { 	// 实时代付业务回执
			return assembleBase387.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS388) { 	// 代收付业务拒绝通知
			return assembleBase388.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS389) { 	// 代收付业务收付款确认
			return assembleBase389.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS390) { 	// 代收代付撤销申请
			return assembleBase390.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS391) { 	// 代收代付撤销应答
			return assembleBase391.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS392) { 	// 批量客户签约协议管理
			return assembleBase392.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS393) { 	// 批量客户签约协议管理应答
			return assembleBase393.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS394) { 	// 批量客户账户信息查询
			return assembleBase394.signatureElement(bean);
		} else if (messageType == MessageTypeEnum.BEPS395) { 	// 批量客户账户查询应答
			return assembleBase395.signatureElement(bean);
		}
		
		return null;
	}

	@Override
	public String assemble(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

}

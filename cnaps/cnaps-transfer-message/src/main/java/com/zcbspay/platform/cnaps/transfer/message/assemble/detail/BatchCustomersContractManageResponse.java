package com.zcbspay.platform.cnaps.transfer.message.assemble.detail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zcbspay.platform.cnaps.bean.MessageBean;
//import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
import com.zcbspay.platform.cnaps.utils.TypeCast;

/**
 * 批量客户签约协议管理应答 - 393
 * @author lianhai
 *
 */
@Component
public class BatchCustomersContractManageResponse implements AssembleBase{

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
		com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.Document) bean
				.getCNAPSMessageBean();
		com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.GroupHeader1 head = document
				.getBtchCstmrsCtrctMgRspn().getGrpHdr();
		com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.OriginalGroupHeader1 orgnlGrpHdr = document
				.getBtchCstmrsCtrctMgRspn().getOrgnlGrpHdr();
		com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.BatchCustomersContractManagResponseInformation1 btchCstmrsCtrctMgRspnInf = document
				.getBtchCstmrsCtrctMgRspn().getBtchCstmrsCtrctMgRspnInf();

		/************ 业务头组件 ************/
		// 报文标识号
		msgList.add(head.getMsgId());
		// 报文发送时间(表示日期和时间，格式为yyyy-mm-ddTHH:MM:SS)
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM:SS");
		String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime()) + "T" + timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
		msgList.add(dataString);
		// 发起直接参与机构
		msgList.add(head.getInstgPty().getInstgDrctPty());
		// 发起参与机构
		msgList.add(head.getInstgPty().getInstgPty());
		// 接收直接参与机构
		msgList.add(head.getInstdPty().getInstdDrctPty());
		// 接收参与机构
		msgList.add(head.getInstdPty().getInstdPty());
		// 系统编号
		msgList.add("BEPS");
		// 备注
		msgList.add(head.getRmk());

		/************ 原报文主键组件 ************/
		// 原报文标识号
		msgList.add(orgnlGrpHdr.getOrgnlMsgId());
		// 原发起参与机构
		msgList.add(orgnlGrpHdr.getOrgnlInstgPty());
		// 原报文类型
		msgList.add(orgnlGrpHdr.getOrgnlMT());

		/************ 业务信息 ************/
		// 协议数目
		msgList.add(btchCstmrsCtrctMgRspnInf.getAcctCnt());

		// 结果清单
		for (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.AccountDetails1 acctDtls : btchCstmrsCtrctMgRspnInf
				.getAcctDtls()) {
			// 协议号
			msgList.add(acctDtls.getAgrmtNb());
			// 付款人名称
			msgList.add(acctDtls.getDbtr().getNm());
			// 付款人账号
			msgList.add(acctDtls.getDbtrAcct().getId().getOthr().getId());
			// 付款人开户行行号
			msgList.add(acctDtls.getDbtrAcct().getId().getOthr().getIssr());
			// 收款人名称
			msgList.add(acctDtls.getCdtr().getNm());

			// 应答状态 -- 业务状态
			msgList.add(acctDtls.getRspnInf().getSts());
			// 应答状态 -- 业务拒绝处理码
			msgList.add(acctDtls.getRspnInf().getRjctCd());

			// 付款人账户状态
			msgList.add(acctDtls.getAcctSts().value());
		}

		// 签名要素串
		return TypeCast.listToString(msgList, "|");
	}

}

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
 * 代收付业务拒绝通知 - 388
 * @author lianhai
 *
 */
@Component
public class BusinessRejectNotice implements AssembleBase {

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
		com.zcbspay.platform.cnaps.beps.bean.BusinessRejectNotice.Document document = (com.zcbspay.platform.cnaps.beps.bean.BusinessRejectNotice.Document) bean
				.getCNAPSMessageBean();
		com.zcbspay.platform.cnaps.beps.bean.BusinessRejectNotice.GroupHeader1 head = document.getBizRjctNtce()
				.getGrpHdr();
		com.zcbspay.platform.cnaps.beps.bean.BusinessRejectNotice.OrgnlMsgInf1 orgnlMsgInf = document
				.getBizRjctNtce().getOrgnlMsgInf();
		com.zcbspay.platform.cnaps.beps.bean.BusinessRejectNotice.BizRjctNtceInf1 bizRjctNtceInf = document
				.getBizRjctNtce().getBizRjctNtceInf();

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

		/************ 业务信息 ************/
		// 原报文标识号
		msgList.add(orgnlMsgInf.getOrgnlMsgId());
		// 原发起参与机构
		msgList.add(orgnlMsgInf.getOrgnlInstgPty());
		// 原批次序号
		msgList.add(orgnlMsgInf.getOrgnlBtchNb());

		// 回执状态 -- 业务状态
		msgList.add(bizRjctNtceInf.getRspnInf().getSts());
		// 回执状态 -- 业务拒绝处理码
		msgList.add(bizRjctNtceInf.getRspnInf().getRjctCd());

		// 签名要素串
		return TypeCast.listToString(msgList, "|");
	}

}

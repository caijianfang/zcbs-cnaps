package com.zcbspay.platform.cnaps.transfer.message.assemble.detail;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zcbspay.platform.cnaps.bean.MessageBean;
//import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
import com.zcbspay.platform.cnaps.utils.TypeCast;

/**
 * 批量代付业务回执 - 383
 * @author lianhai
 *
 */
@Component
public class BatchPaymentByAgencyResponse implements AssembleBase {

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
		com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.Document) bean
				.getCNAPSMessageBean();
		com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.GroupHeader1 head = document
				.getBtchPmtByAgcyRspn().getGrpHdr();
		com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.OrgnlMsgInf1 orgnlMsgInf = document
				.getBtchPmtByAgcyRspn().getOrgnlMsgInf();
		com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.BtchPmtByAgcyRspnInf1 btchPmtByAgcyRspnInf = document
				.getBtchPmtByAgcyRspn().getBtchPmtByAgcyRspnInf();

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
		// 原总金额
		msgList.add(orgnlMsgInf.getOrgnlTtlAmt().getCcy() + orgnlMsgInf.getOrgnlTtlAmt().getValue());
		// 原总笔数
		msgList.add(orgnlMsgInf.getOrgnlTtlNb());
		// 成功收款总金额
		msgList.add(
				btchPmtByAgcyRspnInf.getSndgTtlAmt().getCcy() + btchPmtByAgcyRspnInf.getSndgTtlAmt().getValue());
		// 成功收款总笔数
		msgList.add(btchPmtByAgcyRspnInf.getSndgTtlNb());
		// 付款行行号
		msgList.add(btchPmtByAgcyRspnInf.getDbtrAgt().getBrnchId().getId());
		// 付款人名称
		msgList.add(btchPmtByAgcyRspnInf.getDbtr().getNm());
		// 付款人账号
		msgList.add(btchPmtByAgcyRspnInf.getDbtrAcct().getId().getOthr().getId());

		// 收款结果清单
		for (com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.SndgDtls1 sndgDtls : btchPmtByAgcyRspnInf
				.getSndgDtls()) {

			// 回执状态 -- 业务状态
			msgList.add(sndgDtls.getRspnInf().getSts());
			// 回执状态 -- 业务拒绝处理码
			msgList.add(sndgDtls.getRspnInf().getRjctCd());

			// 原明细标识号
			msgList.add(sndgDtls.getTxId());
			// 收款人名称
			msgList.add(sndgDtls.getCdtr().getNm());
			// 收款人账号
			msgList.add(sndgDtls.getCdtrAcct().getId().getOthr().getId());
			// 收款行行号
			msgList.add(sndgDtls.getBrnchId());
			// 货币符号、金额
			msgList.add(
					sndgDtls.getAmt().getCcy() + new DecimalFormat("0.00").format(sndgDtls.getAmt().getValue()));
		}

		// 签名要素串
		return TypeCast.listToString(msgList, "|");
	}

}

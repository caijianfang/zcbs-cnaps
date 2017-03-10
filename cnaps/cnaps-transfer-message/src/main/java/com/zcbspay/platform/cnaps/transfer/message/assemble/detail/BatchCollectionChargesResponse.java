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
 * 批量代收业务回执 - 381 
 * @author lianhai
 *
 */
@Component
public class BatchCollectionChargesResponse implements AssembleBase {

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document) bean
				.getCNAPSMessageBean();
		com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.GroupHeader1 head = document
				.getBtchColltnChrgsRspn().getGrpHdr();
		com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.OrgnlMsgInf1 orgnlMsgInf = document
				.getBtchColltnChrgsRspn().getOrgnlMsgInf();
		com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.BtchColltnChrgsRspnInf1 btchColltnChrgsRspnInf = document
				.getBtchColltnChrgsRspn().getBtchColltnChrgsRspnInf();

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
		// 原转发日期
		msgList.add(new SimpleDateFormat("yyyy-mm-dd").format(orgnlMsgInf.getOrgnlTrnsmtDt().toGregorianCalendar().getTime()));
		// 原回执期限
		msgList.add(orgnlMsgInf.getOrgnlRtrLtd());
		// 原总金额
		msgList.add(orgnlMsgInf.getOrgnlTtlAmt().getCcy() + orgnlMsgInf.getOrgnlTtlAmt().getValue());
		// 原总笔数
		msgList.add(orgnlMsgInf.getOrgnlTtlNb());
		// 成功付款总金额
		msgList.add(btchColltnChrgsRspnInf.getRcvgTtlAmt().getCcy()
				+ btchColltnChrgsRspnInf.getRcvgTtlAmt().getValue());
		// 成功付款总笔数
		msgList.add(btchColltnChrgsRspnInf.getRcvgTtlNb());
		// 原业务类型编码
		msgList.add(btchColltnChrgsRspnInf.getOrgnlTxTpCd());
		// 收款行行号
		msgList.add(btchColltnChrgsRspnInf.getCdtrAgt().getBrnchId().getId());
		// 收款人名称
		msgList.add(btchColltnChrgsRspnInf.getCdtr().getNm());
		// 收款人账号
		msgList.add(btchColltnChrgsRspnInf.getCdtrAcct().getId().getOthr().getId());

		// 收款结果清单
		for (com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.RcvgDtls1 rcvgDtls : btchColltnChrgsRspnInf
				.getRcvgDtls()) {

			// 回执状态 -- 业务状态
			msgList.add(rcvgDtls.getRspnInf().getSts());
			// 回执状态 -- 业务拒绝处理码
			msgList.add(rcvgDtls.getRspnInf().getRjctCd());

			// 原明细标识号
			msgList.add(rcvgDtls.getTxId());
			// 付款人名称
			msgList.add(rcvgDtls.getDbtr().getNm());
			// 付款人账号
			msgList.add(rcvgDtls.getDbtrAcct().getId().getOthr().getId());
			// 付款行行号
			msgList.add(rcvgDtls.getBrnchId());
			// 货币符号、金额
			msgList.add(
					rcvgDtls.getAmt().getCcy() + new DecimalFormat("0.00").format(rcvgDtls.getAmt().getValue()));
			// 原业务种类编码
			msgList.add(rcvgDtls.getOrgnlCtgyPurpCd());
		}

		// 签名要素串
		return TypeCast.listToString(msgList, "|");
	}

}

package java.com.zcbspay.platform.cnaps.transfer.message.assemble.impl;

import java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail.AssembleBase;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
import com.zcbspay.platform.cnaps.utils.TypeCast;

/**
 * 实时代付业务回执 - 387
 * @author lianhai
 *
 */
public class RealTimePaymentByAgencyResponse implements AssembleBase {

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
		com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.Document) bean
				.getCNAPSMessageBean();
		com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.GroupHeader1 head = document
				.getRealTmPmtByAgcyRspn().getGrpHdr();
		com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.OrgnlMsgInf1 orgnlMsgInf = document
				.getRealTmPmtByAgcyRspn().getOrgnlMsgInf();
		com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.RealTmPmtByAgcyRspnInf1 realTmPmtByAgcyRspnInf = document
				.getRealTmPmtByAgcyRspn().getRealTmPmtByAgcyRspnInf();

		/************ 业务头组件 ************/
		// 报文标识号
		msgList.add(head.getMsgId());
		// 报文发送时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-ddTHH:MM:SS");
		String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
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
		msgList.add(realTmPmtByAgcyRspnInf.getRspnInf().getSts());
		// 回执状态 -- 业务拒绝处理码
		msgList.add(realTmPmtByAgcyRspnInf.getRspnInf().getRjctCd());

		// 原明细标识号
		msgList.add(realTmPmtByAgcyRspnInf.getTxId());
		// 付款人名称
		msgList.add(realTmPmtByAgcyRspnInf.getDbtr().getNm());
		// 付款人账号
		msgList.add(realTmPmtByAgcyRspnInf.getCdtrAcct().getId().getOthr().getId());
		// 付款行行号
		msgList.add(realTmPmtByAgcyRspnInf.getCdtrAgt().getBrnchId().getId());
		// 收款行行号
		msgList.add(realTmPmtByAgcyRspnInf.getCdtrAgt().getBrnchId().getId());
		// 收款人名称
		msgList.add(realTmPmtByAgcyRspnInf.getCdtr().getNm());
		// 收款人账号
		msgList.add(realTmPmtByAgcyRspnInf.getCdtrAcct().getId().getOthr().getId());
		// 货币符号、金额
		msgList.add(realTmPmtByAgcyRspnInf.getAmt().getCcy()
				+ new DecimalFormat("0.00").format(realTmPmtByAgcyRspnInf.getAmt().getValue()));
		// 业务类型编码
		msgList.add(realTmPmtByAgcyRspnInf.getPmtTpInf().getCtgyPurp().getPrtry());
		// 业务种类编码
		msgList.add(realTmPmtByAgcyRspnInf.getPurp().getPrtry());

		// 签名要素串
		return TypeCast.listToString(msgList, "|");
	}

}

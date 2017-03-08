package java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
import com.zcbspay.platform.cnaps.utils.TypeCast;

/**
 * 代收代付撤销应答 - 391
 * @author lianhai
 *
 */
public class CustomerPaymentCancellationResponse implements AssembleBase {

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
		com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.Document) bean
				.getCNAPSMessageBean();
		com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.GroupHeader36 head = document
				.getCstmrPmtStsRpt().getGrpHdr();
		com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.OriginalGroupInformation20 orgnlGrpInfAndSts = document
				.getCstmrPmtStsRpt().getOrgnlGrpInfAndSts();

		/************ 业务信息 ************/
		// 报文标识号
		msgList.add(head.getMsgId());
		// 报文发送时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-ddTHH:MM:SS");
		String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
		msgList.add(dataString);
		// 发起直接参与机构
		msgList.add(head.getInitgPty().getNm());
		// 接收直接参与机构
		msgList.add(head.getFwdgAgt().getFinInstnId().getClrSysMmbId().getMmbId());
		// 原报文标识号
		msgList.add(orgnlGrpInfAndSts.getOrgnlMsgId());
		// 原报文类型号
		msgList.add(orgnlGrpInfAndSts.getOrgnlMsgNmId());
		for (com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.OriginalPaymentInformation1 orgnlPmtInfAndSts : document
				.getCstmrPmtStsRpt().getOrgnlPmtInfAndSts()) {
			for (com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.PaymentTransactionInformation25 txInfAndSts : orgnlPmtInfAndSts
					.getTxInfAndSts()) {
				// 撤销处理状态
				msgList.add(txInfAndSts.getStsId());
				for (com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.StatusReasonInformation8 StsRsnInf : txInfAndSts
						.getStsRsnInf()) {
					// 业务处理码
					msgList.add(StsRsnInf.getRsn().getPrtry());
				}
			}
		}

		// 签名要素串
		return TypeCast.listToString(msgList, "|");
	}

}

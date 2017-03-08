package java.com.zcbspay.platform.cnaps.transfer.message.assemble.detail;

import java.com.zcbspay.platform.cnaps.transfer.message.constant.PathConstants;
import java.com.zcbspay.platform.cnaps.transfer.message.dao.MessageAssembleDao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
import com.zcbspay.platform.cnaps.utils.JSONUtils;
import com.zcbspay.platform.cnaps.utils.TypeCast;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 代收代付撤销申请 - 390
 * 
 * @author lianhai
 *
 */
public class CustomerPaymentCancellationRequest implements AssembleBase {
	@Autowired
	private MessageAssembleDao messageAssembleDao;

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		MessageHeadBean messageHeadBean = new MessageHeadBean();
		com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationRequest.Document document = (com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationRequest.Document) bean
				.getCNAPSMessageBean();
		// 起始标识
		messageHeadBean.setBeginFlag("{H:");
		// 版本号
		messageHeadBean.setVersionID("02");

		// 报文发起人
		JSONArray arr = null;
		try {
			arr = JSONUtils.xml2json(PathConstants.baseConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String origSender = JSONUtils.elemText(JSONObject.fromObject(arr.get(0)), "instiCode");
		messageHeadBean.setOrigSender(String.format("%1$-14s", origSender));

		// 发送系统号
		messageHeadBean.setOrigSenderSID("BEPS");
		// 报文接收人
		messageHeadBean.setOrigReceiver(String.format("%1$-14s", document.getCstmrPmtCxlReq().getAssgnmt().getAssgne().getAgt().getFinInstnId().getClrSysMmbId().getMmbId()));
		// 接收系统号
		messageHeadBean.setOrigReceiverSID("BEPS");
		// 报文发起日期
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dataString = dateFormat.format(document.getCstmrPmtCxlReq().getAssgnmt().getCreDtTm().toGregorianCalendar().getTime());
		messageHeadBean.setOrigSendDate(dataString);
		// 报文发起时间
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		String timeString = timeFormat.format(document.getCstmrPmtCxlReq().getAssgnmt().getCreDtTm().toGregorianCalendar().getTime());
		messageHeadBean.setOrigSendTime(timeString);
		// 格式类型
		messageHeadBean.setStructType("XML");
		// 报文类型代码
		messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
		// 通信级标识号
		messageHeadBean.setMesgID(String.format("%1$-20s", document.getCstmrPmtCxlReq().getAssgnmt().getId()));
		// 通讯级参考号
		messageHeadBean.setMesgRefID(String.format("%1$-20s", messageAssembleDao.getMesgRefID(origSender)));
		// 报文优先级
		messageHeadBean.setMesgPriority("3");
		// 报文传输方向
		messageHeadBean.setMesgDirection("U");
		// 保留域
		messageHeadBean.setReserve(String.format("%1$-9s", ""));
		// 结束标识
		messageHeadBean.setEndFlag("}\r\n");
		return messageHeadBean;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
		com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationRequest.Document document = (com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationRequest.Document) bean
				.getCNAPSMessageBean();
		com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationRequest.CaseAssignment2 assgnmt = document
				.getCstmrPmtCxlReq().getAssgnmt();

		/************ 业务信息 ************/
		// 报文标识号
		msgList.add(assgnmt.getId());
		// 发起直接参与机构
		msgList.add(assgnmt.getAssgnr().getAgt().getFinInstnId().getClrSysMmbId().getMmbId());
		// 接收直接参与机构
		msgList.add(assgnmt.getAssgne().getAgt().getFinInstnId().getClrSysMmbId().getMmbId());
		// 报文发送时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-ddTHH:MM:SS");
		String dataString = dateFormat.format(assgnmt.getCreDtTm().toGregorianCalendar().getTime());
		msgList.add(dataString);

		// 原代收代付业务报文信息
		for (com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationRequest.UnderlyingTransaction1 undrlyg : document
				.getCstmrPmtCxlReq().getUndrlyg()) {
			// 原发起直接参与机构
			msgList.add(undrlyg.getOrgnlGrpInfAndCxl().getCase().getCretr().getAgt().getFinInstnId()
					.getClrSysMmbId().getMmbId());
			// 原报文标识号
			msgList.add(undrlyg.getOrgnlGrpInfAndCxl().getOrgnlMsgId());
			// 原报文类型号
			msgList.add(undrlyg.getOrgnlGrpInfAndCxl().getOrgnlMsgNmId());
		}

		// 签名要素串
		return TypeCast.listToString(msgList, "|");
	}

}

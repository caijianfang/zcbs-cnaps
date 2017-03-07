package java.com.zcbspay.platform.cnaps.transfer.message.assembleImpl;

import java.com.zcbspay.platform.cnaps.transfer.message.constant.PathConstants;
import java.com.zcbspay.platform.cnaps.transfer.message.dao.MessageAssembleDao;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.BatchCustomersAccountQueryInformation1;
import com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.BatchCustomersAccountQueryResponseInformation1;
import com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.BatchCustomersContractManageInformation1;
import com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.BizConfInf1;
import com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.OrgnlGrpHdr1;
import com.zcbspay.platform.cnaps.beps.bean.BusinessRejectNotice.BizRjctNtceInf1;
import com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.OriginalGroupInformation20;
import com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.PaymentTransactionInformation25;
import com.zcbspay.platform.cnaps.transfer.message.assemble.MessageAssemble;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageType;
import com.zcbspay.platform.cnaps.utils.JSONUtils;
import com.zcbspay.platform.cnaps.utils.TypeCast;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MessageAssembleImpl implements MessageAssemble {
	@Autowired
	private MessageAssembleDao messageAssembleDao;

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		MessageHeadBean messageHeadBean = new MessageHeadBean();
		MessageType messageType = bean.getBeanType();
		if (messageType == MessageType.beps380) { // 批量代收业务
			com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.GroupHeader1 head = document
					.getBtchColltnChrgs().getGrpHdr();
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
			messageHeadBean.setOrigReceiver(String.format("%1$-14s", head.getInstdPty().getInstdPty()));
			// 接收系统号
			messageHeadBean.setOrigReceiverSID("BEPS");
			// 报文发起日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendDate(dataString);
			// 报文发起时间
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String timeString = timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendTime(timeString);
			// 格式类型
			messageHeadBean.setStructType("XML");
			// 报文类型代码
			messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
			// 通信级标识号
			messageHeadBean.setMesgID(String.format("%1$-20s", head.getMsgId()));
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
		} else if (messageType == MessageType.beps382) {// 批量代付业务
			com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.GroupHeader1 head = document.getBtchPmtByAgcy()
					.getGrpHdr();
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
			messageHeadBean.setOrigReceiver(String.format("%1$-14s", head.getInstdPty().getInstdPty()));
			// 接收系统号
			messageHeadBean.setOrigReceiverSID("BEPS");
			// 报文发起日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendDate(dataString);
			// 报文发起时间
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String timeString = timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendTime(timeString);
			// 格式类型
			messageHeadBean.setStructType("XML");
			// 报文类型代码
			messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
			// 通信级标识号
			messageHeadBean.setMesgID(String.format("%1$-20s", head.getMsgId()));
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
		} else if (messageType == MessageType.beps384) {// 实时代收业务
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.GroupHeader1 head = document
					.getRealTmColltnChrgs().getGrpHdr();
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
			messageHeadBean.setOrigReceiver(String.format("%1$-14s", head.getInstdPty().getInstdPty()));
			// 接收系统号
			messageHeadBean.setOrigReceiverSID("BEPS");
			// 报文发起日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendDate(dataString);
			// 报文发起时间
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String timeString = timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendTime(timeString);
			// 格式类型
			messageHeadBean.setStructType("XML");
			// 报文类型代码
			messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
			// 通信级标识号
			messageHeadBean.setMesgID(String.format("%1$-20s", head.getMsgId()));
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
		} else if (messageType == MessageType.beps386) {// 实时代付业务
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.GroupHeader1 head = document
					.getRealTmColltnChrgs().getGrpHdr();
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
			messageHeadBean.setOrigReceiver(String.format("%1$-14s", head.getInstdPty().getInstdPty()));
			// 接收系统号
			messageHeadBean.setOrigReceiverSID("BEPS");
			// 报文发起日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendDate(dataString);
			// 报文发起时间
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String timeString = timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendTime(timeString);
			// 格式类型
			messageHeadBean.setStructType("XML");
			// 报文类型代码
			messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
			// 通信级标识号
			messageHeadBean.setMesgID(String.format("%1$-20s", head.getMsgId()));
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
		} else if (messageType == MessageType.beps390) {// 代收代付撤销申请
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.GroupHeader1 head = document
					.getRealTmColltnChrgs().getGrpHdr();
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
			messageHeadBean.setOrigReceiver(String.format("%1$-14s", head.getInstdPty().getInstdPty()));
			// 接收系统号
			messageHeadBean.setOrigReceiverSID("BEPS");
			// 报文发起日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendDate(dataString);
			// 报文发起时间
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String timeString = timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendTime(timeString);
			// 格式类型
			messageHeadBean.setStructType("XML");
			// 报文类型代码
			messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
			// 通信级标识号
			messageHeadBean.setMesgID(String.format("%1$-20s", head.getMsgId()));
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
		} else if (messageType == MessageType.beps392) {// 批量客户签约协议管理
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.Document document = (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.GroupHeader1 head = document
					.getBtchCstmrsCtrctMg().getGrpHdr();
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
			messageHeadBean.setOrigReceiver(String.format("%1$-14s", head.getInstdPty().getInstdPty()));
			// 接收系统号
			messageHeadBean.setOrigReceiverSID("BEPS");
			// 报文发起日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendDate(dataString);
			// 报文发起时间
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String timeString = timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendTime(timeString);
			// 格式类型
			messageHeadBean.setStructType("XML");
			// 报文类型代码
			messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
			// 通信级标识号
			messageHeadBean.setMesgID(String.format("%1$-20s", head.getMsgId()));
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
		} else if (messageType == MessageType.beps394) {// 批量客户账户信息查询
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.Document document = (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.GroupHeader1 head = document
					.getBtchCstmrsAcctQry().getGrpHdr();
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
			messageHeadBean.setOrigReceiver(String.format("%1$-14s", head.getInstdPty().getInstdPty()));
			// 接收系统号
			messageHeadBean.setOrigReceiverSID("BEPS");
			// 报文发起日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dataString = dateFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendDate(dataString);
			// 报文发起时间
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String timeString = timeFormat.format(head.getCreDtTm().toGregorianCalendar().getTime());
			messageHeadBean.setOrigSendTime(timeString);
			// 格式类型
			messageHeadBean.setStructType("XML");
			// 报文类型代码
			messageHeadBean.setMesgType(String.format("%1$-20s", bean.getBeanType().getType()));
			// 通信级标识号
			messageHeadBean.setMesgID(String.format("%1$-20s", head.getMsgId()));
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
		}
		return messageHeadBean;
	}

	@Override
	public String signature(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		MessageType messageType = bean.getBeanType();
		String signString = null;
		if (messageType == MessageType.beps380) { // 批量代收业务
			com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.GroupHeader1 head = document
					.getBtchColltnChrgs().getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.BtchColltnChrgsInf1 btchColltnChrgsInf = document
					.getBtchColltnChrgs().getBtchColltnChrgsInf();

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
			// 批次序号
			msgList.add(btchColltnChrgsInf.getBtchNb());
			// 回执期限
			msgList.add(btchColltnChrgsInf.getRtrLtd());
			// 付款清算行行号
			msgList.add(btchColltnChrgsInf.getDbtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 收款清算行行号
			msgList.add(btchColltnChrgsInf.getCdtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 收款行行号
			msgList.add(btchColltnChrgsInf.getCdtrAgt().getBrnchId().getId());
			// 收款人名称
			msgList.add(btchColltnChrgsInf.getCdtr().getNm());
			// 收款人账号
			msgList.add(btchColltnChrgsInf.getCdtrAcct().getId().getOthr().getId());
			// 总金额
			DecimalFormat df = new DecimalFormat("0.00");
			String value = df.format(btchColltnChrgsInf.getTtlAmt().getValue());
			msgList.add(btchColltnChrgsInf.getTtlAmt().getCcy() + value);
			// 业务类型编码
			msgList.add(btchColltnChrgsInf.getPmtTpInf().getCtgyPurp().getPrtry());
			// 付款人数目
			msgList.add(btchColltnChrgsInf.getDbtrNb());

			for (com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.DbtrDtls1 dbtrDtls : btchColltnChrgsInf
					.getDbtrDtls()) {
				// 业务种类编码
				msgList.add(dbtrDtls.getPurp().getPrtry());
				// 明细标识号
				msgList.add(dbtrDtls.getTxId());
				// 付款人名称
				msgList.add(dbtrDtls.getDbtr().getNm());
				// 付款人账号
				msgList.add(dbtrDtls.getDbtrAcct().getId().getOthr().getId());
				// 付款行行号
				msgList.add(dbtrDtls.getBrnchId());
				// 货币符号、金额
				msgList.add(
						dbtrDtls.getAmt().getCcy() + new DecimalFormat("0.00").format(dbtrDtls.getAmt().getValue()));
				// 合同（协议）号
				msgList.add(dbtrDtls.getEndToEndId());
				// 核验标识
				msgList.add(dbtrDtls.getChckFlg().value());
			}

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps381) { // 批量代收业务回执
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
			// 原转发日期
			msgList.add(new SimpleDateFormat("yyyy-mm-dd").format(orgnlMsgInf.getOrgnlTrnsmtDt()));
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
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps382) { // 批量代付业务
			com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.GroupHeader1 head = document.getBtchPmtByAgcy()
					.getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.BtchPmtByAgcyInf1 btchPmtByAgcyInf = document
					.getBtchPmtByAgcy().getBtchPmtByAgcyInf();

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
			// 批次序号
			msgList.add(btchPmtByAgcyInf.getBtchNb());
			// 回执期限
			msgList.add(btchPmtByAgcyInf.getRtrLtd());
			// 合同（协议）号
			msgList.add(btchPmtByAgcyInf.getEndToEndId());
			// 核验标识
			msgList.add(btchPmtByAgcyInf.getChckFlg().value());
			// 付款人名称
			msgList.add(btchPmtByAgcyInf.getDbtr().getNm());
			// 付款人账号
			msgList.add(btchPmtByAgcyInf.getDbtrAcct().getId().getOthr().getId());
			// 付款清算行行号
			msgList.add(btchPmtByAgcyInf.getDbtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 付款行行号
			msgList.add(btchPmtByAgcyInf.getDbtrAgt().getBrnchId().getId());
			// 收款清算行行号
			msgList.add(btchPmtByAgcyInf.getCdtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 总金额
			msgList.add(btchPmtByAgcyInf.getTtlAmt().getCcy() + btchPmtByAgcyInf.getTtlAmt().getValue());
			// 业务类型编码
			msgList.add(btchPmtByAgcyInf.getPmtTpInf().getCtgyPurp().getPrtry());
			// 收款人数目
			msgList.add(btchPmtByAgcyInf.getCdtrNb());

			for (com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.CdtrDtls1 cdtrDtls : btchPmtByAgcyInf
					.getCdtrDtls()) {
				// 业务种类编码
				msgList.add(cdtrDtls.getPurp().getPrtry());
				// 明细标识号
				msgList.add(cdtrDtls.getTxId());
				// 收款行行号
				msgList.add(cdtrDtls.getBrnchId());
				// 收款人名称
				msgList.add(cdtrDtls.getCdtr().getNm());
				// 收款人账号
				msgList.add(cdtrDtls.getCdtrAcct().getId().getOthr().getId());
				// 货币符号、金额
				msgList.add(
						cdtrDtls.getAmt().getCcy() + new DecimalFormat("0.00").format(cdtrDtls.getAmt().getValue()));
			}

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps383) { // 批量代付业务回执
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
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps384) { // 实时代收业务
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.GroupHeader1 head = document
					.getRealTmColltnChrgs().getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.RealTmColltnChrgsInf1 realTmColltnChrgsInf = document
					.getRealTmColltnChrgs().getRealTmColltnChrgsInf();

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
			// 批次序号
			msgList.add(realTmColltnChrgsInf.getBtchNb());
			// 明细标识号
			msgList.add(realTmColltnChrgsInf.getTxId());
			// 付款人名称
			msgList.add(realTmColltnChrgsInf.getDbtr().getNm());
			// 付款人账号
			msgList.add(realTmColltnChrgsInf.getDbtrAcct().getId().getOthr().getId());
			// 付款清算行行号
			msgList.add(realTmColltnChrgsInf.getDbtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 付款行行号
			msgList.add(realTmColltnChrgsInf.getDbtrAgt().getBrnchId().getId());
			// 收款清算行行号
			msgList.add(realTmColltnChrgsInf.getCdtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 收款行行号
			msgList.add(realTmColltnChrgsInf.getCdtrAgt().getBrnchId().getId());
			// 收款人名称
			msgList.add(realTmColltnChrgsInf.getCdtr().getNm());
			// 收款人账号
			msgList.add(realTmColltnChrgsInf.getCdtrAcct().getId().getOthr().getId());
			// 货币符号、金额
			msgList.add(realTmColltnChrgsInf.getAmt().getCcy() + realTmColltnChrgsInf.getAmt().getValue());
			// 业务类型编码
			msgList.add(realTmColltnChrgsInf.getPmtTpInf().getCtgyPurp().getPrtry());
			// 业务种类编码
			msgList.add(realTmColltnChrgsInf.getPurp().getPrtry());
			// 合同（协议）号
			msgList.add(realTmColltnChrgsInf.getEndToEndId());
			// 核验标识
			msgList.add(realTmColltnChrgsInf.getChckFlg().value());

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps385) { // 实时代收业务回执报文
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.GroupHeader1 head = document
					.getRealTmColltnChrgsRspn().getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.OrgnlMsgInf1 orgnlMsgInf = document
					.getRealTmColltnChrgsRspn().getOrgnlMsgInf();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.RealTmColltnChrgsRspnInf1 realTmColltnChrgsRspnInf = document
					.getRealTmColltnChrgsRspn().getRealTmColltnChrgsRspnInf();

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
			msgList.add(realTmColltnChrgsRspnInf.getRspnInf().getSts());
			// 回执状态 -- 业务拒绝处理码
			msgList.add(realTmColltnChrgsRspnInf.getRspnInf().getRjctCd());

			// 原明细标识号
			msgList.add(realTmColltnChrgsRspnInf.getTxId());
			// 付款人名称
			msgList.add(realTmColltnChrgsRspnInf.getDbtr().getNm());
			// 付款人账号
			msgList.add(realTmColltnChrgsRspnInf.getCdtrAcct().getId().getOthr().getId());
			// 付款行行号
			msgList.add(realTmColltnChrgsRspnInf.getCdtrAgt().getBrnchId().getId());
			// 收款行行号
			msgList.add(realTmColltnChrgsRspnInf.getCdtrAgt().getBrnchId().getId());
			// 收款人名称
			msgList.add(realTmColltnChrgsRspnInf.getCdtr().getNm());
			// 收款人账号
			msgList.add(realTmColltnChrgsRspnInf.getCdtrAcct().getId().getOthr().getId());
			// 货币符号、金额
			msgList.add(realTmColltnChrgsRspnInf.getAmt().getCcy()
					+ new DecimalFormat("0.00").format(realTmColltnChrgsRspnInf.getAmt().getValue()));
			// 业务类型编码
			msgList.add(realTmColltnChrgsRspnInf.getPmtTpInf().getCtgyPurp().getPrtry());
			// 业务种类编码
			msgList.add(realTmColltnChrgsRspnInf.getPurp().getPrtry());

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps386) { // 实时代收业务
			com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.GroupHeader1 head = document
					.getRealTmPmtByAgcy().getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.RealTmPmtByAgcyInf1 realTmPmtByAgcyInf = document
					.getRealTmPmtByAgcy().getRealTmPmtByAgcyInf();

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
			// 批次序号
			msgList.add(realTmPmtByAgcyInf.getBtchNb());
			// 明细标识号
			msgList.add(realTmPmtByAgcyInf.getTxId());
			// 付款人名称
			msgList.add(realTmPmtByAgcyInf.getDbtr().getNm());
			// 付款人账号
			msgList.add(realTmPmtByAgcyInf.getDbtrAcct().getId().getOthr().getId());
			// 付款清算行行号
			msgList.add(realTmPmtByAgcyInf.getDbtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 付款行行号
			msgList.add(realTmPmtByAgcyInf.getDbtrAgt().getBrnchId().getId());
			// 收款清算行行号
			msgList.add(realTmPmtByAgcyInf.getCdtrAgt().getFIId().getClrSysMmbId().getMmbId());
			// 收款行行号
			msgList.add(realTmPmtByAgcyInf.getCdtrAgt().getBrnchId().getId());
			// 收款人名称
			msgList.add(realTmPmtByAgcyInf.getCdtr().getNm());
			// 收款人账号
			msgList.add(realTmPmtByAgcyInf.getCdtrAcct().getId().getOthr().getId());
			// 货币符号、金额
			msgList.add(realTmPmtByAgcyInf.getAmt().getCcy() + realTmPmtByAgcyInf.getAmt().getValue());
			// 业务类型编码
			msgList.add(realTmPmtByAgcyInf.getPmtTpInf().getCtgyPurp().getPrtry());
			// 业务种类编码
			msgList.add(realTmPmtByAgcyInf.getPurp().getPrtry());
			// 合同（协议）号
			msgList.add(realTmPmtByAgcyInf.getEndToEndId());
			// 核验标识
			msgList.add(realTmPmtByAgcyInf.getChckFlg().value());

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps387) { // 实时代付业务回执
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
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps388) { // 代收付业务拒绝通知
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
			msgList.add(bizRjctNtceInf.getRspnInf().getSts());
			// 回执状态 -- 业务拒绝处理码
			msgList.add(bizRjctNtceInf.getRspnInf().getRjctCd());

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps389) { // 代收付业务收付款确认
			com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.Document document = (com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.GroupHeader1 head = document.getBizConf()
					.getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.OrgnlGrpHdr1 orgnlGrpHdr = document.getBizConf()
					.getOrgnlGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.BizConfInf1 bizConfInf = document.getBizConf()
					.getBizConfInf();

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

			/************ 原报文主键组件 ************/
			// 原报文标识号
			msgList.add(orgnlGrpHdr.getOrgnlMsgId());
			// 原发起参与机构
			msgList.add(orgnlGrpHdr.getOrgnlInstgPty());
			// 原报文类型
			msgList.add(orgnlGrpHdr.getOrgnlMT());

			// 回执状态 -- 业务状态
			msgList.add(bizConfInf.getRspnInf().getSts());
			// 回执状态 -- 业务拒绝处理码
			msgList.add(bizConfInf.getRspnInf().getRjctCd());

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps390) { // 代收代付撤销申请
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
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps391) { // 代收代付撤销应答
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
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps392) { // 批量客户签约协议管理
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.Document document = (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.GroupHeader1 head = document
					.getBtchCstmrsCtrctMg().getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.BatchCustomersContractManageInformation1 btchCstmrsCtrctMgInf = document
					.getBtchCstmrsCtrctMg().getBtchCstmrsCtrctMgInf();

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
			// 协议查询或调整标识
			String qryOrOprTp = btchCstmrsCtrctMgInf.getQryOrOprTp().value();
			msgList.add(qryOrOprTp);

			if (qryOrOprTp.equals("QT01")) {
				// 合同（协议）类型
				msgList.add(btchCstmrsCtrctMgInf.getCtrctAgrmtTp().value());
			}

			// 协议数目
			msgList.add(btchCstmrsCtrctMgInf.getNbOfAgrmt());

			// 协议清单
			for (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.AgreementDetails1 agrmtDtls : btchCstmrsCtrctMgInf
					.getAgrmtDtls()) {
				if (qryOrOprTp.equals("QT01")) {
					// 变更类型
					msgList.add(agrmtDtls.getChngTpCd().value());
				}

				// 付款人名称
				msgList.add(agrmtDtls.getDbtr().getNm());
				// 付款人账号
				msgList.add(agrmtDtls.getDbtrAcct().getId().getOthr().getId());
				// 付款人开户行行号
				msgList.add(agrmtDtls.getDbtrAcct().getId().getOthr().getIssr());
				// 付款清算行行号
				msgList.add(agrmtDtls.getDbtrAgt().getFIId().getClrSysMmbId().getMmbId());
				// 付款行行号
				msgList.add(agrmtDtls.getDbtrAgt().getBrnchId().getId());

				if (qryOrOprTp.equals("QT01") && btchCstmrsCtrctMgInf.getCtrctAgrmtTp().value().equals("CT00")) {
					// 收款人名称
					msgList.add(agrmtDtls.getCdtr().getNm());
				}
			}

			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps393) { // 批量客户签约协议管理应答
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
			for (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.AccountDetails1 acctDtls : btchCstmrsCtrctMgRspnInf.getAcctDtls()) {
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
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps394) { // 批量客户账户信息查询
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.Document document = (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.GroupHeader1 head = document
					.getBtchCstmrsAcctQry().getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.BatchCustomersAccountQueryInformation1 btchCstmrsAcctQryInf = document
					.getBtchCstmrsAcctQry().getBtchCstmrsAcctQryInf();

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
			// 查询账户数目
			msgList.add(btchCstmrsAcctQryInf.getAcctCnt());
			
			// 结果清单
			for (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.AccountDetails1 acctDtls : btchCstmrsAcctQryInf.getAcctDtls()) {
				// 账户账号(卡号)
				msgList.add(acctDtls.getId());
				// 账户名称
				msgList.add(acctDtls.getNm());
				// 开户银行行号
				msgList.add(acctDtls.getAcctBk());
			}
			
			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} else if (messageType == MessageType.beps395) { // 批量客户账户查询应答
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.GroupHeader1 head = document
					.getBtchCstmrsAcctQryRspn().getGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.OriginalGroupHeader1 orgnlGrpHdr = document
					.getBtchCstmrsAcctQryRspn().getOrgnlGrpHdr();
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.BatchCustomersAccountQueryResponseInformation1 btchCstmrsAcctQryRspnInf = document
					.getBtchCstmrsAcctQryRspn().getBtchCstmrsAcctQryRspnInf();

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

			/************ 原报文主键组件 ************/
			// 原报文标识号
			msgList.add(orgnlGrpHdr.getOrgnlMsgId());
			// 原发起参与机构
			msgList.add(orgnlGrpHdr.getOrgnlInstgPty());
			// 原报文类型
			msgList.add(orgnlGrpHdr.getOrgnlMT());

			/************ 业务信息 ************/
			// 协议数目
			msgList.add(btchCstmrsAcctQryRspnInf.getAcctCnt());
			
			// 结果清单
			for (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.AccountDetails1 acctDtls : btchCstmrsAcctQryRspnInf.getAcctDtls()) {
				// 账户账号(卡号)
				msgList.add(acctDtls.getId());
				// 账户名称
				msgList.add(acctDtls.getNm());
				
				// 应答状态 -- 业务状态
				msgList.add(acctDtls.getRspnInf().getSts());
				// 应答状态 -- 业务拒绝处理码
				msgList.add(acctDtls.getRspnInf().getRjctCd());
				
				// 账户状态
				msgList.add(acctDtls.getAcctSts().value());
				// 开户行行号
				msgList.add(acctDtls.getAcctBk());
			}
			
			// 签名要素串
			signString = TypeCast.listToString(msgList, "|");
		} 

		return signString;
	}

	@Override
	public String assemble(MessageBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

}

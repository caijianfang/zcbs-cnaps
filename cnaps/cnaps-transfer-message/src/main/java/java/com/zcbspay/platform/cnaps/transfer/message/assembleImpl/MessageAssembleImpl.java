package java.com.zcbspay.platform.cnaps.transfer.message.assembleImpl;

import java.com.zcbspay.platform.cnaps.transfer.message.constant.PathConstants;
import java.com.zcbspay.platform.cnaps.transfer.message.dao.MessageAssembleDao;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.GroupHeader1 head = document.getRealTmColltnChrgs().getGrpHdr();
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
		}else if (messageType == MessageType.beps386) {// 实时代付业务
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.GroupHeader1 head = document.getRealTmColltnChrgs().getGrpHdr();
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
		}else if (messageType == MessageType.beps390) {// 代收代付撤销申请
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document) bean
					.getCNAPSMessageBean();
			com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.GroupHeader1 head = document.getRealTmColltnChrgs().getGrpHdr();
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

	@SuppressWarnings("static-access")
	@Override
	public String signature(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		MessageType messageType = bean.getBeanType();
		String signString = null;
		if (messageType == MessageType.beps380) { // 代收
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
				msgList.add(dbtrDtls.getChckFlg().CE_02.value());
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

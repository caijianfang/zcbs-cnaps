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
 * 实时代收业务 -384
 * 
 * @author lianhai
 *
 */
public class RealTimeCollectionCharges implements AssembleBase {
	@Autowired
	private MessageAssembleDao messageAssembleDao;

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		MessageHeadBean messageHeadBean = new MessageHeadBean();
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
		return messageHeadBean;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
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
		return TypeCast.listToString(msgList, "|");
	}

}

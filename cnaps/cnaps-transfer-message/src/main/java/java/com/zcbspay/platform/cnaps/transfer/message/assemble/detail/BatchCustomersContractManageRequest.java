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
 * 批量客户签约协议管理
 * 
 * @author lianhai
 *
 */
public class BatchCustomersContractManageRequest implements AssembleBase {
	@Autowired
	private MessageAssembleDao messageAssembleDao;

	@Override
	public MessageHeadBean createMessageHead(MessageBean bean) {
		MessageHeadBean messageHeadBean = new MessageHeadBean();
		
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
		return messageHeadBean;
	}

	@Override
	public String signatureElement(MessageBean bean) {
		List<String> msgList = new ArrayList<String>();
		
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
		return TypeCast.listToString(msgList, "|");
	}

}

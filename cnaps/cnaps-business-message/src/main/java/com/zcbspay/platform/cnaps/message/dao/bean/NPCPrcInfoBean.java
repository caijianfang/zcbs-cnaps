package com.zcbspay.platform.cnaps.message.dao.bean;

import java.io.Serializable;

import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.NPCPrcInf1;
import com.zcbspay.platform.cnaps.utils.DateUtil;

public class NPCPrcInfoBean implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4936694111519941826L;

	/**
	 * NPC处理状态
	 */
	private String processStatus;
	
	/**
	 * NPC处理码
	 */
	private String processCode;
	
	/**
	 * NPC拒绝信息
	 */
	private String rejectInformation;
	
	/**
	 * NPC轧差日期
	 */
	private String nettingDate;
	
	/**
	 * NPC轧差场次
	 */
	private String nettingRound;
	
	/**
	 * NPC清算日期/终态日期
	 */
	private String settlementDate;
	
	/**
	 * NPC接收时间
	 */
	private String receiveTime;
	
	/**
	 * NPC转发时间
	 */
	private String transmitTime;

	private String msgid;
	
	public NPCPrcInfoBean(NPCPrcInf1 npcPrcInf) {
		this.processStatus = npcPrcInf.getPrcSts();
		this.processCode = npcPrcInf.getPrcCd();
		this.rejectInformation = npcPrcInf.getRjctInf();
		try {
			this.nettingDate = DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, XMLDateUtils.convertToDate(npcPrcInf.getNetgDt()));
			this.settlementDate = DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, XMLDateUtils.convertToDate(npcPrcInf.getSttlmDt()));
			this.receiveTime = DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(npcPrcInf.getRcvTm()));
			this.transmitTime = DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, XMLDateUtils.convertToDate(npcPrcInf.getTrnsmtTm()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.nettingRound = npcPrcInf.getNetgRnd();
		
		
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getRejectInformation() {
		return rejectInformation;
	}

	public void setRejectInformation(String rejectInformation) {
		this.rejectInformation = rejectInformation;
	}

	public String getNettingDate() {
		return nettingDate;
	}

	public void setNettingDate(String nettingDate) {
		this.nettingDate = nettingDate;
	}

	public String getNettingRound() {
		return nettingRound;
	}

	public void setNettingRound(String nettingRound) {
		this.nettingRound = nettingRound;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getTransmitTime() {
		return transmitTime;
	}

	public void setTransmitTime(String transmitTime) {
		this.transmitTime = transmitTime;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	
	
}

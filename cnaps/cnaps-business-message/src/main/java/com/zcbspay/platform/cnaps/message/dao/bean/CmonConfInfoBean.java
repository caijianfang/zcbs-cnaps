package com.zcbspay.platform.cnaps.message.dao.bean;

import java.io.Serializable;

public class CmonConfInfoBean implements Serializable{

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4747931481175031308L;
	/**
	 * 业务状态
	 */
	private String processstatus;
	/**
	 * 业务处理码
	 */
	private String processcode;
	/**
	 * 拒绝业务的参与机构行号
	 */
	private String partyidentification;
	/**
	 * 参与机构业务拒绝码
	 */
	private String partyprocesscode;
	/**
	 * 业务拒绝信息
	 */
	private String rejectinformation;
	/**
	 * 处理日期（终态日期）
	 */
	private String processdate;
	/**
	 * 轧差场次
	 */
	private String nettinground;
	/**
	 * 应答时间
	 */
	private String date;
	/**
	 * 原报文标示号
	 */
	private String msgId;
	public String getProcessstatus() {
		return processstatus;
	}
	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}
	public String getProcesscode() {
		return processcode;
	}
	public void setProcesscode(String processcode) {
		this.processcode = processcode;
	}
	public String getPartyidentification() {
		return partyidentification;
	}
	public void setPartyidentification(String partyidentification) {
		this.partyidentification = partyidentification;
	}
	public String getPartyprocesscode() {
		return partyprocesscode;
	}
	public void setPartyprocesscode(String partyprocesscode) {
		this.partyprocesscode = partyprocesscode;
	}
	public String getRejectinformation() {
		return rejectinformation;
	}
	public void setRejectinformation(String rejectinformation) {
		this.rejectinformation = rejectinformation;
	}
	public String getProcessdate() {
		return processdate;
	}
	public void setProcessdate(String processdate) {
		this.processdate = processdate;
	}
	public String getNettinground() {
		return nettinground;
	}
	public void setNettinground(String nettinground) {
		this.nettinground = nettinground;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	
}

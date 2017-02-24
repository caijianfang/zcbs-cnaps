package com.zcbspay.platform.cnaps.transfer.message.bean;

import java.io.Serializable;

public class MessageHeadBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2886084174291829579L;
	/**
	 * 起始标识
	 */
	private String beginFlag;
	/**
	 * 版本号
	 */
	private String versionID;
	/**
	 * 报文发起人
	 */
	private String origSender;
	/**
	 * 发送系统号
	 */
	private String origSenderSID;
	/**
	 * 报文接收人
	 */
	private String origReceiver;
	/**
	 * 接收系统号
	 */
	private String origReceiverSID;
	/**
	 * 报文发起日期
	 */
	private String origSendDate;
	/**
	 * 报文发起时间
	 */
	private String origSendTime;
	/**
	 * 格式类型
	 */
	private String structType;
	/**
	 * 报文类型代码
	 */
	private String mesgType;
	/**
	 * 通信级标识号
	 */
	private String mesgID;
	/**
	 * 通信级参考号
	 */
	private String mesgRefID;
	/**
	 * 报文优先级
		1：特急；
		2：紧急；
		3：普通；
	 */
	private String mesgPriority;
	/**
	 * 报文传输方向
		由行内发出：U
		由NPC发出：D
	 */
	private String mesgDirection;
	/**
	 * （保留域）
	 */
	private String reserve;
	/**
	 * 结束标识
	 */
	private String endFlag;
	
	public String toString(){
		return null;
	}
	
	public MessageHeadBean(String headMsg){
		
	}
	
	public MessageHeadBean(){
		
	}

	public String getBeginFlag() {
		return beginFlag;
	}

	public void setBeginFlag(String beginFlag) {
		this.beginFlag = beginFlag;
	}

	public String getVersionID() {
		return versionID;
	}

	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}

	public String getOrigSender() {
		return origSender;
	}

	public void setOrigSender(String origSender) {
		this.origSender = origSender;
	}

	public String getOrigSenderSID() {
		return origSenderSID;
	}

	public void setOrigSenderSID(String origSenderSID) {
		this.origSenderSID = origSenderSID;
	}

	public String getOrigReceiver() {
		return origReceiver;
	}

	public void setOrigReceiver(String origReceiver) {
		this.origReceiver = origReceiver;
	}

	public String getOrigReceiverSID() {
		return origReceiverSID;
	}

	public void setOrigReceiverSID(String origReceiverSID) {
		this.origReceiverSID = origReceiverSID;
	}

	public String getOrigSendDate() {
		return origSendDate;
	}

	public void setOrigSendDate(String origSendDate) {
		this.origSendDate = origSendDate;
	}

	public String getOrigSendTime() {
		return origSendTime;
	}

	public void setOrigSendTime(String origSendTime) {
		this.origSendTime = origSendTime;
	}

	public String getStructType() {
		return structType;
	}

	public void setStructType(String structType) {
		this.structType = structType;
	}

	public String getMesgType() {
		return mesgType;
	}

	public void setMesgType(String mesgType) {
		this.mesgType = mesgType;
	}

	public String getMesgID() {
		return mesgID;
	}

	public void setMesgID(String mesgID) {
		this.mesgID = mesgID;
	}

	public String getMesgRefID() {
		return mesgRefID;
	}

	public void setMesgRefID(String mesgRefID) {
		this.mesgRefID = mesgRefID;
	}

	public String getMesgPriority() {
		return mesgPriority;
	}

	public void setMesgPriority(String mesgPriority) {
		this.mesgPriority = mesgPriority;
	}

	public String getMesgDirection() {
		return mesgDirection;
	}

	public void setMesgDirection(String mesgDirection) {
		this.mesgDirection = mesgDirection;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	
	
	
}

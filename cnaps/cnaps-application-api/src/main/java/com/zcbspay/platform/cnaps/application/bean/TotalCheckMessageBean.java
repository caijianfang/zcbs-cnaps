package com.zcbspay.platform.cnaps.application.bean;

import java.io.Serializable;

/**
 * 信息报文对账bean
 *
 * @author guojia
 * @version
 * @date 2017年2月27日 下午4:56:38
 * @since
 */
public class TotalCheckMessageBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4942774314956870050L;
	/**
	 * 报文类型代码
	 */
	private String messageType;
	/**
	 * 往账包总笔数
	 */
	private String sendTotalCount;
	/**
	 * 来账包总笔数
	 */
	private String receiveTotalCount;
	/**
	 * 受理日期
	 */
	private String receiveDate;
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getSendTotalCount() {
		return sendTotalCount;
	}
	public void setSendTotalCount(String sendTotalCount) {
		this.sendTotalCount = sendTotalCount;
	}
	public String getReceiveTotalCount() {
		return receiveTotalCount;
	}
	public void setReceiveTotalCount(String receiveTotalCount) {
		this.receiveTotalCount = receiveTotalCount;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	
}

package com.zcbspay.platform.cnaps.beps.message.bean;

/**
 * 查询类交易对账bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:06:41
 * @since
 */
public class DetailCheckMessage {

	/**
	 * 申请明细数目
	 */
	private String numberOfTransactions;
	/**
	 * 发送、接收标志
	   SR00：发送
       SR01：接收
	 */
	private String sendReceiveType;
	/**
	 * 消息类型
	 */
	private String messageType;
	/**
	 * 接收日期
	 */
	private String receiveDate;
	
	
	public String getNumberOfTransactions() {
		return numberOfTransactions;
	}
	public void setNumberOfTransactions(String numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}
	public String getSendReceiveType() {
		return sendReceiveType;
	}
	public void setSendReceiveType(String sendReceiveType) {
		this.sendReceiveType = sendReceiveType;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	
}

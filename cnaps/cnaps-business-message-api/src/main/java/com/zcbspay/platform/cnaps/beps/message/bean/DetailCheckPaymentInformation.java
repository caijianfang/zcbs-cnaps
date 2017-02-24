package com.zcbspay.platform.cnaps.beps.message.bean;

import java.io.Serializable;

/**
 * 资金交易类对账bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:13:16
 * @since
 */
public class DetailCheckPaymentInformation implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4724671434092619177L;
	/**
	 * 对账日期
	 */
	private String checkDate;
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
	 * 轧差日期
	 */
	private String transactionNettingDate;
	/**
	 * 轧差场次
	 */
	private String transactionNettingRound;
	/**
	 * 报文类型代码
	 */
	private String messageType;
	/**
	 * 包处理状态
	 */
	private String processStatus;
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
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
	public String getTransactionNettingDate() {
		return transactionNettingDate;
	}
	public void setTransactionNettingDate(String transactionNettingDate) {
		this.transactionNettingDate = transactionNettingDate;
	}
	public String getTransactionNettingRound() {
		return transactionNettingRound;
	}
	public void setTransactionNettingRound(String transactionNettingRound) {
		this.transactionNettingRound = transactionNettingRound;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	
}

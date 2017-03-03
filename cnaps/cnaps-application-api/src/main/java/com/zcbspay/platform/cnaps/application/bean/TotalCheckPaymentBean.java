package com.zcbspay.platform.cnaps.application.bean;

import java.io.Serializable;

/**
 * 交易类业务报文对账bean
 *
 * @author guojia
 * @version
 * @date 2017年2月27日 下午4:56:14
 * @since
 */
public class TotalCheckPaymentBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2535779318846007825L;
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
	 * 往账包总笔数
	 */
	private String sendTotalCount;
	/**
	 * 往账包总金额
	 */
	private String sendTotalAmount;
	/**
	 * 来账包总笔数
	 */
	private String receiveTotalCount;
	/**
	 * 来账包总金额
	 */
	private String receiveTotalAmount;
	/**
	 * 包处理状态
	 */
	private String processStatus;
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
	public String getSendTotalCount() {
		return sendTotalCount;
	}
	public void setSendTotalCount(String sendTotalCount) {
		this.sendTotalCount = sendTotalCount;
	}
	public String getSendTotalAmount() {
		return sendTotalAmount;
	}
	public void setSendTotalAmount(String sendTotalAmount) {
		this.sendTotalAmount = sendTotalAmount;
	}
	public String getReceiveTotalCount() {
		return receiveTotalCount;
	}
	public void setReceiveTotalCount(String receiveTotalCount) {
		this.receiveTotalCount = receiveTotalCount;
	}
	public String getReceiveTotalAmount() {
		return receiveTotalAmount;
	}
	public void setReceiveTotalAmount(String receiveTotalAmount) {
		this.receiveTotalAmount = receiveTotalAmount;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	
}

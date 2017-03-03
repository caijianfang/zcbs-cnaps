package com.zcbspay.platform.cnaps.message.bean;

import java.io.Serializable;

/**
 * 单笔代付交易bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:40:54
 * @since
 */
public class PaymentDetailBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1034923939448370909L;
	
	/**
	 * 收款人名称
	 */
	private String creditorName;
	/**
	 * 收款人账号
	 */
	private String creditorAccountNo;
	/**
	 * 收款行行号
	 */
	private String creditorBranchCode;
	/**
	 * 货币符号、金额
	 */
	private String amount;
	/**
	 * 附言
	 */
	private String additionalInformation;
	/**
	 * 代付明细标示
	 */
	private String txId;
	/**
	 * 交易序列号
	 */
	private String txnseqno;
	
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getCreditorAccountNo() {
		return creditorAccountNo;
	}
	public void setCreditorAccountNo(String creditorAccountNo) {
		this.creditorAccountNo = creditorAccountNo;
	}
	public String getCreditorBranchCode() {
		return creditorBranchCode;
	}
	public void setCreditorBranchCode(String creditorBranchCode) {
		this.creditorBranchCode = creditorBranchCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public String getTxId() {
		return txId;
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public String getTxnseqno() {
		return txnseqno;
	}
	public void setTxnseqno(String txnseqno) {
		this.txnseqno = txnseqno;
	}
	
	
	
}

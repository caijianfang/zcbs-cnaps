package com.zcbspay.platform.cnaps.message.bean;

import java.io.Serializable;

/**
 * 单笔代付bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:27:31
 * @since
 */
public class SinglePaymentBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4676821653551544847L;
	
	/**
	 * 报文标示号
	 */
	private String msgId;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 明细流水号
	 */
	private String txId;
	/**
	 * 付款人名称
	 */
	private String debtorName;
	/**
	 * 付款人账号
	 */
	private String debtorAccountNo;
	/**
	 * 付款清算行行号
	 */
	private String debtorAgentCode;
	/**
	 *  付款行行号
	 */
	private String debtorBranchCode;
	/**
	 * 收款清算行行号
	 */
	private String creditorAgentCode;
	/**
	 * 收款行行号
	 */
	private String creditorBranchCode;
	/**
	 * 收款人名称
	 */
	private String creditorName;
	/**
	 * 收款人账号
	 */
	private String creditorAccountNo;
	/**
	 * 金额
	 */
	private String amount;
	/**
	 * 业务类型编码
	 */
	private String categoryPurposeCode;
	/**
	 * 业务类型编码
	 */
	private String purposeCode;
	/**
	 * 合同（协议）号
	 */
	private String endToEndIdentification;
	/**
	 *  核验标识
	 */
	private String checkFlag;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getTxId() {
		return txId;
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getDebtorAccountNo() {
		return debtorAccountNo;
	}
	public void setDebtorAccountNo(String debtorAccountNo) {
		this.debtorAccountNo = debtorAccountNo;
	}
	public String getDebtorAgentCode() {
		return debtorAgentCode;
	}
	public void setDebtorAgentCode(String debtorAgentCode) {
		this.debtorAgentCode = debtorAgentCode;
	}
	public String getDebtorBranchCode() {
		return debtorBranchCode;
	}
	public void setDebtorBranchCode(String debtorBranchCode) {
		this.debtorBranchCode = debtorBranchCode;
	}
	public String getCreditorAgentCode() {
		return creditorAgentCode;
	}
	public void setCreditorAgentCode(String creditorAgentCode) {
		this.creditorAgentCode = creditorAgentCode;
	}
	public String getCreditorBranchCode() {
		return creditorBranchCode;
	}
	public void setCreditorBranchCode(String creditorBranchCode) {
		this.creditorBranchCode = creditorBranchCode;
	}
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCategoryPurposeCode() {
		return categoryPurposeCode;
	}
	public void setCategoryPurposeCode(String categoryPurposeCode) {
		this.categoryPurposeCode = categoryPurposeCode;
	}
	public String getPurposeCode() {
		return purposeCode;
	}
	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}
	public String getEndToEndIdentification() {
		return endToEndIdentification;
	}
	public void setEndToEndIdentification(String endToEndIdentification) {
		this.endToEndIdentification = endToEndIdentification;
	}
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	
	
}

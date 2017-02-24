package com.zcbspay.platform.cnaps.beps.message.bean;

import java.io.Serializable;

/**
 * 代收交易数据bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:38:05
 * @since
 */
public class CollectionChargesTotalBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1068753445544177792L;
	/**
	 * 付款清算行行号
	 */
	private String debtorAgentCode;
	/**
	 * 收款清算行行号
	 */
	private String creditorAgentCode;
	/**
	 *  收款行行号
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
	 * 总金额
	 */
	private String totalAmount;
	/**
	 * 业务类型编码
	 */
	private String categoryPurposeCode;
	/**
	 * 付款人数目
	 */
	private String debtorNumber;
	/**
	 * 原始批次号
	 */
	private String batchNo;
	public String getDebtorAgentCode() {
		return debtorAgentCode;
	}
	public void setDebtorAgentCode(String debtorAgentCode) {
		this.debtorAgentCode = debtorAgentCode;
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
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCategoryPurposeCode() {
		return categoryPurposeCode;
	}
	public void setCategoryPurposeCode(String categoryPurposeCode) {
		this.categoryPurposeCode = categoryPurposeCode;
	}
	public String getDebtorNumber() {
		return debtorNumber;
	}
	public void setDebtorNumber(String debtorNumber) {
		this.debtorNumber = debtorNumber;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
}

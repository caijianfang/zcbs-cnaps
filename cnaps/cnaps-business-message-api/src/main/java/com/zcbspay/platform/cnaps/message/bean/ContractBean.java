package com.zcbspay.platform.cnaps.message.bean;

import java.io.Serializable;

/**
 * 代收付合同协议bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:00:22
 * @since
 */
public class ContractBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6399563862295246595L;
	/**
	 * 合同协议类型
	 */
	private String contractType;
	/**
	 * 协议号
	 */
	private String agreementNumber;
	/**
	 * 付款人名称
	 */
	private String debtorName;
	/**
	 * 付款人账号
	 */
	private String debtorAccountNo;
	/**
	 * 付款人开户行行号
	 */
	private String debtorIssuerCode;
	/**
	 * 付款清算行行号
	 */
	private String debtorAgentCode;
	/**
	 *  付款行行号
	 */
	private String debtorBranchCode;
	/**
	 * 收款人名称
	 */
	private String creditorName;
	/**
	 * 商户号
	 */
	private String merchNo;
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getAgreementNumber() {
		return agreementNumber;
	}
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
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
	public String getDebtorIssuerCode() {
		return debtorIssuerCode;
	}
	public void setDebtorIssuerCode(String debtorIssuerCode) {
		this.debtorIssuerCode = debtorIssuerCode;
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
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getMerchNo() {
		return merchNo;
	}
	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}
	
	
	
}

package com.zcbspay.platform.cnaps.beps.message.bean;

import java.io.Serializable;

/**
 * 代收付交易明细bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:35:05
 * @since
 */
public class CollectionChargesDetailBean implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6191294353737673407L;
	/**
	 * 付款人名称
	 */
	private String debtorName;
	/**
	 * 付款人账号
	 */
	private String debtorAccountNo;
	/**
	 * 付款行行号
	 */
	private String debtorBranchCode;
	/**
	 * 货币符号、金额
	 */
	private String amount;
	/**
	 * 合同（协议）号
	 */
	private String endToEndIdentification;
	/**
	 *  核验标识
	 */
	private String checkFlag;
	/**
	 * 附言
	 */
	private String additionalInformation;
	/**
	 * 代收明细主键
	 */
	private String txnseqno;
	/**
	 * 明细标识号
	 */
	private String txId;
	
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
	public String getDebtorBranchCode() {
		return debtorBranchCode;
	}
	public void setDebtorBranchCode(String debtorBranchCode) {
		this.debtorBranchCode = debtorBranchCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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

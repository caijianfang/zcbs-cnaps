/* 
 * TradeBean.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.cnaps.common.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 下午8:25:07
 * @since
 */
public class TradeBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7990669165684148748L;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 商户号
	 */
	private String merchNo;
	/**
	 * 卡号或账号
	 */
	private String cardNo;
	/**
	 * 持卡人姓名或账户名称
	 */
	private String cardKeeper;
	/**
	 * 证件类型
	 */
	private String certType;
	/**
	 * 证件号
	 */
	private String certNo;
	/**
	 * 交易金额
	 */
	private String txnsAmt;
	/**
	 * 交易序列号
	 */
	private String txnseqno;
	/**
	 * 清算行号
	 */
	private String bankCode;
	/**
	 * 支行行号
	 */
	private String bankNode;
	/**
	 * 手机号
	 */
	private String phoneNo;
	
	/**
	 * 协议号
	 */
	private String protocolNo;
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getMerchNo() {
		return merchNo;
	}
	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardKeeper() {
		return cardKeeper;
	}
	public void setCardKeeper(String cardKeeper) {
		this.cardKeeper = cardKeeper;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getTxnsAmt() {
		return txnsAmt;
	}
	public void setTxnsAmt(String txnsAmt) {
		this.txnsAmt = txnsAmt;
	}
	public String getTxnseqno() {
		return txnseqno;
	}
	public void setTxnseqno(String txnseqno) {
		this.txnseqno = txnseqno;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankNode() {
		return bankNode;
	}
	public void setBankNode(String bankNode) {
		this.bankNode = bankNode;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getProtocolNo() {
		return protocolNo;
	}
	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}
	
	
	

}

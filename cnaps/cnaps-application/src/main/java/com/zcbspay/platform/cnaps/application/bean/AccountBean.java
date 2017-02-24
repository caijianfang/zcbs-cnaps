package com.zcbspay.platform.cnaps.application.bean;

import java.io.Serializable;
/**
 * 
 * 账户查询bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午3:59:35
 * @since
 */
public class AccountBean implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5327461594673655735L;
	/**
	 * 账号
	 */
	private String accountNo;
	/**
	 * 账户名称	
	 */
	private String accountName;
	/**
	 * 开户银行行号
	 */
	private String accountBankCode;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountBankCode() {
		return accountBankCode;
	}
	public void setAccountBankCode(String accountBankCode) {
		this.accountBankCode = accountBankCode;
	}
	
}

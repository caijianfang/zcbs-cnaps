package com.zcbspay.platform.cnaps.message.bean;

import java.io.Serializable;

/**
 * 业务状态查询bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:26:38
 * @since
 */
public class BusiQueryBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1774134552149737343L;
	/**
	 * 原报文标识号
	 */
	private String paymentInstructionReference;
	/**
	 * 原报文发送机构
	 */
	private String proprietaryReferenceInstruction;
	/**
	 * 原报文类型
	 */
	private String proprietaryReferenceMessageCode;
	public String getPaymentInstructionReference() {
		return paymentInstructionReference;
	}
	public void setPaymentInstructionReference(String paymentInstructionReference) {
		this.paymentInstructionReference = paymentInstructionReference;
	}
	public String getProprietaryReferenceInstruction() {
		return proprietaryReferenceInstruction;
	}
	public void setProprietaryReferenceInstruction(
			String proprietaryReferenceInstruction) {
		this.proprietaryReferenceInstruction = proprietaryReferenceInstruction;
	}
	public String getProprietaryReferenceMessageCode() {
		return proprietaryReferenceMessageCode;
	}
	public void setProprietaryReferenceMessageCode(
			String proprietaryReferenceMessageCode) {
		this.proprietaryReferenceMessageCode = proprietaryReferenceMessageCode;
	}
	
	
}

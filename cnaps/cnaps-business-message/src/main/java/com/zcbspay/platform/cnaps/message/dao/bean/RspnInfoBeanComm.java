package com.zcbspay.platform.cnaps.message.dao.bean;

import java.io.Serializable;

public class RspnInfoBeanComm implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5954789252847352368L;
	/**
	 * 业务状态
	 */
	private String status;
	/**
	 * 业务拒绝处理码
	 */
	private String rejectCode;
	/**
	 * 业务拒绝信息
	 */
	private String rejectInformation;
	
	/**
	 * 业务处理参与机构
	 */
	private String processParty;
	
	private String txId;
	public RspnInfoBeanComm() {
	}
	public RspnInfoBeanComm(String status,String rejectCode,String rejectInformation,String processParty) {
		this.status = status;
		this.rejectCode = rejectCode;
		this.rejectInformation = rejectInformation;
		this.processParty = processParty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRejectCode() {
		return rejectCode;
	}

	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}

	public String getRejectInformation() {
		return rejectInformation;
	}

	public void setRejectInformation(String rejectInformation) {
		this.rejectInformation = rejectInformation;
	}

	public String getProcessParty() {
		return processParty;
	}

	public void setProcessParty(String processParty) {
		this.processParty = processParty;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}
	
	
	
}

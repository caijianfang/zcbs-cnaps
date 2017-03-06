package com.zcbspay.platform.cnaps.message.bean;


import java.io.Serializable;
import java.util.List;

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
	
	/**
	 * 报文标识号
	 */
	private String msgId;
	/**
	 * 代收明细列表
	 */
	private List<CollectionChargesDetailBean> detailList;
	
	//保存交易数据时使用，业务应用层不对以下属性赋值
	private String createdate;
	private String instructingdirectparty;
	private String instructingparty;
	private String instructeddirectparty;
	private String instructedparty;
	private String batchno;
	private String transmitdate;
	private String returnlimited;
	
	
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
	public List<CollectionChargesDetailBean> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<CollectionChargesDetailBean> detailList) {
		this.detailList = detailList;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getInstructingdirectparty() {
		return instructingdirectparty;
	}
	public void setInstructingdirectparty(String instructingdirectparty) {
		this.instructingdirectparty = instructingdirectparty;
	}
	public String getInstructingparty() {
		return instructingparty;
	}
	public void setInstructingparty(String instructingparty) {
		this.instructingparty = instructingparty;
	}
	public String getInstructeddirectparty() {
		return instructeddirectparty;
	}
	public void setInstructeddirectparty(String instructeddirectparty) {
		this.instructeddirectparty = instructeddirectparty;
	}
	public String getInstructedparty() {
		return instructedparty;
	}
	public void setInstructedparty(String instructedparty) {
		this.instructedparty = instructedparty;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getTransmitdate() {
		return transmitdate;
	}
	public void setTransmitdate(String transmitdate) {
		this.transmitdate = transmitdate;
	}
	public String getReturnlimited() {
		return returnlimited;
	}
	public void setReturnlimited(String returnlimited) {
		this.returnlimited = returnlimited;
	}
	
}

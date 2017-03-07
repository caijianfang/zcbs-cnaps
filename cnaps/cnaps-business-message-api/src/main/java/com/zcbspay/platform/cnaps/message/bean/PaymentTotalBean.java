package com.zcbspay.platform.cnaps.message.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 代收明细主键 Class Description
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:43:52
 * @since
 */
public class PaymentTotalBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 723772656404937124L;
	
	/**
	 * 报文标示号
	 */
	private String msgId;
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
	 * 付款行行号
	 */
	private String debtorBranchCode;
	/**
	 * 收款清算行行号
	 */
	private String creditorAgentCode;
	/**
	 * 总金额
	 */
	private String totalAmount;
	/**
	 * 业务类型编码
	 */
	private String categoryPurposeCode;
	/**
	 * 收款人数目
	 */
	private String creditorNumber;
	/**
	 * 合同协议号
	 */
	private String endToEndIdentification;
	/**
	 * 原代付批次号
	 */
	private String batchNo;
	/**
	 * 代付明细数据
	 */
	private List<PaymentDetailBean> detailList;
	
	//保存交易数据时使用，业务应用层不对以下属性赋值
    private String createdate;
    private String instructingdirectparty;
    private String instructingparty;
    private String instructeddirectparty;
    private String instructedparty;
    private String batchno;
    private String transmitdate;
    private String returnlimited;
    private String transmitreceiverdate;
	
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
	public String getCreditorNumber() {
		return creditorNumber;
	}
	public void setCreditorNumber(String creditorNumber) {
		this.creditorNumber = creditorNumber;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public List<PaymentDetailBean> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<PaymentDetailBean> detailList) {
		this.detailList = detailList;
	}
    public String getMsgId() {
        return msgId;
    }
    
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    public String getEndToEndIdentification() {
		return endToEndIdentification;
	}
	public void setEndToEndIdentification(String endToEndIdentification) {
		this.endToEndIdentification = endToEndIdentification;
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
    
    public String getTransmitreceiverdate() {
        return transmitreceiverdate;
    }
    
    public void setTransmitreceiverdate(String transmitreceiverdate) {
        this.transmitreceiverdate = transmitreceiverdate;
    }
    
    
}

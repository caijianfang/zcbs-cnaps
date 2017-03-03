package com.zcbspay.platform.cnaps.application.bean;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 总账返回bean
 *
 * @author guojia
 * @version
 * @date 2017年2月27日 下午4:49:28
 * @since
 */
public class TotalCheckBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8251405208043116006L;

	/**
	 * 对账日期
	 */
	private String checkDate;
	
	/**
	 * 交易类对账信息集合
	 */
	private List<TotalCheckPaymentBean> totalCheckPaymentList;
	
	/**
	 * 信息类对账信息集合
	 */
	private List<TotalCheckMessageBean> totalCheckMessageList;

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public List<TotalCheckPaymentBean> getTotalCheckPaymentList() {
		return totalCheckPaymentList;
	}

	public void setTotalCheckPaymentList(
			List<TotalCheckPaymentBean> totalCheckPaymentList) {
		this.totalCheckPaymentList = totalCheckPaymentList;
	}

	public List<TotalCheckMessageBean> getTotalCheckMessageList() {
		return totalCheckMessageList;
	}

	public void setTotalCheckMessageList(
			List<TotalCheckMessageBean> totalCheckMessageList) {
		this.totalCheckMessageList = totalCheckMessageList;
	}
	
	
}

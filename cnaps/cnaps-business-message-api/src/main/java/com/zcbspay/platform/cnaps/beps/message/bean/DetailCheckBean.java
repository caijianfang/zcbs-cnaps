package com.zcbspay.platform.cnaps.beps.message.bean;

import java.io.Serializable;

/**
 * 对账bean
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:05:21
 * @since
 */
public class DetailCheckBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4664302639239808770L;
	/**
	 * 资金交易类对账bean
	 */
	private DetailCheckPaymentInformation detailCheckPaymentInformation;
	/**
	 * 查询类交易对账bean
	 */
	private DetailCheckMessage detailCheckMessage;
	
	public DetailCheckPaymentInformation getDetailCheckPaymentInformation() {
		return detailCheckPaymentInformation;
	}
	public void setDetailCheckPaymentInformation(
			DetailCheckPaymentInformation detailCheckPaymentInformation) {
		this.detailCheckPaymentInformation = detailCheckPaymentInformation;
	}
	public DetailCheckMessage getDetailCheckMessage() {
		return detailCheckMessage;
	}
	public void setDetailCheckMessage(DetailCheckMessage detailCheckMessage) {
		this.detailCheckMessage = detailCheckMessage;
	}
	
	
}

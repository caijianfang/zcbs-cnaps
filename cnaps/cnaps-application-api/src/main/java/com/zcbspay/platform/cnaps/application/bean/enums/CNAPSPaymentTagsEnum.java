/* 
 * InsteadPayEnum.java  
 * 
 * version TODO
 *
 * 2016年10月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zcbspay.platform.cnaps.application.bean.enums;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年10月19日 下午1:53:58
 * @since 
 */
public enum CNAPSPaymentTagsEnum {

	/**
	 * 批量代付
	 */
	BATCHPAYMENT("TAG_IP_001"),
	
	/**
	 * 实时代付
	 */
	REALTIMEPAYMENT("TAG_IP_002");
	private String code;

	/**
	 * @param code
	 */
	private CNAPSPaymentTagsEnum(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	
	public static CNAPSPaymentTagsEnum fromValue(String code){
		for(CNAPSPaymentTagsEnum tagsEnum : values()){
			if(tagsEnum.getCode().equals(code)){
				return tagsEnum;
			}
		}
		return null;
	}
	
}

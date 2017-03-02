/* 
 * WithholdingTagsEnum.java  
 * 
 * version TODO
 *
 * 2016年10月14日 
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
 * @date 2016年10月14日 上午10:19:01
 * @since 
 */
public enum CNAPSCollectTagsEnum {
	/**
	 * 批量代收
	 */
	BATCHCOLLECT("TAG_001"),
	/**
	 * 实时代收
	 */
	REALTIMECOLLECT("TAG_002")
	;
	private String code;

	/**
	 * @param code
	 */
	private CNAPSCollectTagsEnum(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	
	public static CNAPSCollectTagsEnum fromValue(String code){
		for(CNAPSCollectTagsEnum tagsEnum : values()){
			if(tagsEnum.getCode().equals(code)){
				return tagsEnum;
			}
		}
		return null;
	}
	
}

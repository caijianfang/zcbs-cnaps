package com.zcbspay.platform.cnaps.common.enums;

public enum PurposeEnum {

	Other("9001"),
	unknow("0000");
	
	private String code;
	private PurposeEnum(String code){
		this.code = code;
	}
	public String getCode(){
		return code;
	}
}

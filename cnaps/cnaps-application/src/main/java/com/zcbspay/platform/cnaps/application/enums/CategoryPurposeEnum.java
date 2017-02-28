package com.zcbspay.platform.cnaps.application.enums;

public enum CategoryPurposeEnum {

	CollectionCharges("D200"),
	PaymentByAgency("C209");
	
	private String code;
	
	private CategoryPurposeEnum(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
}

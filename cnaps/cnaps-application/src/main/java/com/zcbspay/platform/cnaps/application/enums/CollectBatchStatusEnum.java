package com.zcbspay.platform.cnaps.application.enums;


public enum CollectBatchStatusEnum {

	WaitToPay("01"),
	UNKNOW("99");
	
	private String code;
	
	
	private CollectBatchStatusEnum(String code){
		this.code = code;
	}
	public static CollectBatchStatusEnum fromValue(String value) {
        for(CollectBatchStatusEnum status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
	
	public String getCode(){
		return this.code;
	}
}

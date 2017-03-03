package com.zcbspay.platform.cnaps.bean;



public enum MessageTypeEnum {
	//小额支付系统报文类型
	BEPS380("beps.380.001.01"),
	BEPS381("beps.381.001.01"),
	BEPS382("beps.382.001.01"),
	BEPS383("beps.383.001.01"),
	BEPS384("beps.384.001.01"),
	BEPS385("beps.385.001.01"),
	BEPS386("beps.386.001.01"),
	BEPS387("beps.387.001.01"),
	BEPS388("beps.388.001.01"),
	BEPS389("beps.389.001.01"),
	BEPS390("beps.390.001.01"),
	BEPS391("beps.391.001.01"),
	BEPS392("beps.392.001.01"),
	BEPS393("beps.393.001.01"),
	BEPS394("beps.394.001.01"),
	BEPS395("beps.395.001.01"),
	BEPS720("beps.720.001.01"),
	BEPS721("beps.721.001.01"),
	BEPS722("beps.722.001.01"),
	BEPS723("beps.723.001.01"),
	BEPS724("beps.724.001.01"),
	BEPS725("beps.725.001.01"),
	BEPS726("beps.726.001.01"),
	//公共控制系统报文类型
	CCMS303("ccms.303.001.02"),
	CCMS307("ccms.307.001.02"),
	CCMS308("ccms.308.001.02"),
	CCMS310("ccms.310.001.01"),
	CCMS311("ccms.311.001.01"),
	CCMS312("ccms.312.001.01"),
	CCMS313("ccms.313.001.01"),
	CCMS314("ccms.314.001.01"),
	CCMS315("ccms.315.001.01"),
	CCMS316("ccms.316.001.01"),
	CCMS317("ccms.317.001.01"),
	CCMS318("ccms.318.001.01"),
	CCMS319("ccms.319.001.01"),
	CCMS801("ccms.801.001.02"),
	CCMS803("ccms.803.001.02"),
	CCMS805("ccms.805.001.02"),
	CCMS806("ccms.806.001.02"),
	CCMS807("ccms.807.001.02"),
	CCMS809("ccms.809.001.02"),
	CCMS811("ccms.811.001.01"),
	CCMS900("ccms.900.001.02"),
	CCMS903("ccms.903.001.02"),
	CCMS906("ccms.906.001.01"),
	CCMS907("ccms.907.001.02"),
	CCMS911("ccms.911.001.02"),
	CCMS913("ccms.913.001.01"),
	CCMS915("ccms.915.001.01"),
	CCMS916("ccms.916.001.01"),
	CCMS917("ccms.917.001.01"),
	CCMS919("ccms.919.001.01"),
	CCMS921("ccms.921.001.01"),
	CCMS926("ccms.926.001.01"),
	CCMS990("ccms.990.001.01"),
	CCMS991("ccms.991.001.01"),
	CCMS992("ccms.992.001.01");
	
	private String msgType;
	
	private MessageTypeEnum(String msgType){
		this.msgType = msgType;
	}
	
	public static MessageTypeEnum fromValue(String msgType) {
		for(MessageTypeEnum tagsEnum : values()){
			if(tagsEnum.value().equals(msgType)){
				return tagsEnum;
			}
		}
		return null;
    }
	public String value(){
		return msgType;
	}
}

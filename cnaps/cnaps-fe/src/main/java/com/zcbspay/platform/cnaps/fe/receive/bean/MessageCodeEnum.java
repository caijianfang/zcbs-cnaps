package com.zcbspay.platform.cnaps.fe.receive.bean;


public enum MessageCodeEnum {

    BEPS("BEPS"),
    CCMS("CCMS"),;
    
     private String code;

    /**
     * @param code
     */
    private MessageCodeEnum(String code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    public static MessageCodeEnum fromValue(String code) {
        for (MessageCodeEnum tagsEnum : values()) {
            if (tagsEnum.getCode().equals(code)) {
                return tagsEnum;
            }
        }
        return null;
    }

}

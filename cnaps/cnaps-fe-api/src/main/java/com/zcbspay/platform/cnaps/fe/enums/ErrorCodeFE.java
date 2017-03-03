package com.zcbspay.platform.cnaps.fe.enums;

public enum ErrorCodeFE {

    SEND_MSG_FAIL("TFE001", "发送报文失败"),
    ;

    private String value;
    private final String displayName;

    ErrorCodeFE(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ErrorCodeFE parseOf(String value) {

        for (ErrorCodeFE item : values())
            if (item.getValue().equals(value))
                return item;

        return null;
    }

    // public static void main(String[] args) {
    // System.out.println(ErrorCodeCNAPS.WITHHOLD_REPEAT.getDisplayName());
    // System.out.println(ErrorCodeCNAPS.WITHHOLD_REPEAT.getValue());
    // System.out.println(parseOf(ErrorCodeCNAPS.WITHHOLD_REPEAT.getValue()).getDisplayName());
    // }
}

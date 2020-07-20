package com.lcl.shiro.util;

public enum CodeEnum {
    /**
     * 表示有效
     */
    YES(1,"是"),
    /**
     * 表示无效
     */
    NO(0,"否");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    CodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

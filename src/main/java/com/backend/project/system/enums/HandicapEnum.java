package com.backend.project.system.enums;

/**
 * 盘类型
 */
public enum HandicapEnum {

    sp("sp","体彩"),
    dealer("dealer","zhuang"),
    ;

    private String value;

    private String desc;

    HandicapEnum(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}

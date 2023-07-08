package com.backend.project.system.enums;

/**
 */
public enum HandicapFlagEnum {

    today("today","今日"),
    early("early","早盘"),
    ;

    private String flag;

    private String desc;

    HandicapFlagEnum(String flag, String desc){
        this.flag = flag;
        this.desc = desc;
    }

    public String getFlag() {
        return flag;
    }

    public String getDesc() {
        return desc;
    }

}

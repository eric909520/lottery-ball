package com.backend.project.system.enums;

/**
 * 真/假枚举类
 * 各类非真即假，非是即否情况使用
 */
public enum TotalChooseTeamEnum {

    INSIDE0_1("0~1","总进球0-1"),
    INSIDE2_3("2~3","总进球2-3"),
    INSIDE4_6("4~6","总进球4-6"),
    OVER7("OVER","总进球7+"),
    ;

    private String type;

    private String desc;

    TotalChooseTeamEnum(String type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}

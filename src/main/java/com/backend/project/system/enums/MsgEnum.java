package com.backend.project.system.enums;

/**
 * 真/假枚举类
 * 各类非真即假，非是即否情况使用
 */
public enum MsgEnum {

    win("win","胜"),
    draw("draw","平"),
    lose("lose","负"),
    handicapWin("handicapWin","主让/受让胜"),
    handicapLose("handicapLose","主让/受负"),

    ;

    private String value;

    private String desc;

    MsgEnum(String value, String desc){
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

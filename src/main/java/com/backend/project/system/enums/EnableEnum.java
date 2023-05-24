package com.backend.project.system.enums;

/**
 * 真/假枚举类
 * 各类非真即假，非是即否情况使用
 */
public enum EnableEnum {

    close(0,"关闭，禁用，假，否"),
    open(1,"开启，启用，真，是"),
    delete(2,"删除"),
    ;

    private int value;

    private String desc;

    EnableEnum(int value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}

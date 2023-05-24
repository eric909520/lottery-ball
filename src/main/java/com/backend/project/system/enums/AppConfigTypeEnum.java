package com.backend.project.system.enums;

/**
 * 系统参数配置类型枚举类
 */
public enum AppConfigTypeEnum {

    systemSwitch(9, "系统开关"),
    ;

    AppConfigTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

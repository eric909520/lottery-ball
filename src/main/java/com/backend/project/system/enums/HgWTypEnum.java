package com.backend.project.system.enums;

/**
 * 真/假枚举类
 * 各类非真即假，非是即否情况使用
 */
public enum HgWTypEnum {

    RANG("R","让球"),
    OU("OU","大小球"),
    MYSELF("M","独赢"),
    TOTAL("T","总进球"),
    ;

    private String type;

    private String desc;

    HgWTypEnum(String type, String desc){
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

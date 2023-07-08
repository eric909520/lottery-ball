package com.backend.project.system.enums;

/**
 */
public enum HgWTypeEnum {

    RANG("R","让球"),
    OU("OU","大小球"),
    MYSELF("M","独赢"),
    TOTAL("T","总进球"),
    ;

    private String type;

    private String desc;

    HgWTypeEnum(String type, String desc){
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

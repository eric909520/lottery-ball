package com.backend.project.system.enums;

/**
 *
 */
public enum AgSportTypeEnum {

    football(1, "足球"),
    basketball(2, "篮球"),
    gaming(43, "电竞"),

    ;

    AgSportTypeEnum(int type, String desc) {
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

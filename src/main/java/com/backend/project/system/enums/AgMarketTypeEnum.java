package com.backend.project.system.enums;

/**
 *
 */
public enum AgMarketTypeEnum {

    early(1, "早盘"),
    today(2, "今日"),
    roll(3, "滚球"),
    string(4, "串球"),

    ;

    AgMarketTypeEnum(int type, String desc) {
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

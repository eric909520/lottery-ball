package com.backend.project.system.enums;

/**
 * 设备状态枚举类
 */
public enum WebsocketTitleEnum {

    preOrder("preOrder","生成预支付订单"),
    confirmOrder("confirmOrder","确认订单支付"),
    ;

    private String title;

    private String desc;

    WebsocketTitleEnum(String title, String desc){
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

}

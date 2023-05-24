package com.backend.project.system.enums;

/**
 * 订单状态枚举类
 */
public enum OrderStatusEnum {

    unused(0,"未使用"),
    pending(1,"待支付"),
    confirm(2,"已支付"),
    unpaid(3,"未支付"),
    ;

    private int status;

    private String desc;

    OrderStatusEnum(int status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}

package com.backend.project.system.enums;

/**
 * 设备状态枚举类
 */
public enum DeviceStatusEnum {

    offline(0,"离线"),
    online(1,"在线"),
    lock(2,"锁定"),
    ;

    private int status;

    private String desc;

    DeviceStatusEnum(int status, String desc){
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

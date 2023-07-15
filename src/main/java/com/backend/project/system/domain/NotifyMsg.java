package com.backend.project.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotifyMsg implements Serializable {

    private Long id ;
    private String msgType;
    private Long notifyTime;
    private Long betId;
    // 盘口类型，sp:体彩，dealer:zhuang
    private String handicap;

}

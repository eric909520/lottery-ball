package com.backend.project.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotifyMsg  implements Serializable {

    private Long id ;
    private String msgType;
    private Long notifyTime;
    private Long betId;

}

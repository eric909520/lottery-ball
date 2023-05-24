package com.backend.project.system.domain.websocketVo;

import lombok.Data;

import java.io.Serializable;

/**
 * websocket报文体
 */
@Data
public class WebSocketVo implements Serializable {

    // 业务标题
    private String title;

    // 业务数据
    private Object data;

    public WebSocketVo () {
    }

    public WebSocketVo (String title, Object data) {
        this.title = title;
        this.data = data;
    }
}

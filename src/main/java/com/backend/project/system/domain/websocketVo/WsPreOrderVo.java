package com.backend.project.system.domain.websocketVo;

import lombok.Data;

import java.io.Serializable;

/**
 * 预支付订单报文结构
 */
@Data
public class WsPreOrderVo implements Serializable {

    // 设备id
    private Long deviceId;

    // 订单金额
    private String amount;

    // 产品链接
    private String productLink;

    public WsPreOrderVo() {

    }

    public WsPreOrderVo(Long deviceId, String amount, String productLink) {
        this.deviceId = deviceId;
        this.amount = amount;
        this.productLink = productLink;
    }

}

package com.backend.project.system.domain.websocketVo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单确认支付报文结构
 */
@Data
public class WsOrderConfirmVo implements Serializable {

    // 三方订单号
    private String orderNo;

    // 淘宝订单号
    private String preOrderNo;

    public WsOrderConfirmVo() {

    }

    public WsOrderConfirmVo(String orderNo, String preOrderNo) {
        this.orderNo = orderNo;
        this.preOrderNo = preOrderNo;
    }

}

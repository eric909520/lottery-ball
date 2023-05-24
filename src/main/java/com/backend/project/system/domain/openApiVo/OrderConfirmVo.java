package com.backend.project.system.domain.openApiVo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单确认支付vo
 */
@Data
public class OrderConfirmVo implements Serializable {

    // 入参 - 三方订单号
    private String orderNo;

    // 入参 - 淘宝订单号
    private String preOrderNo;

    // 出参 - 订单支付状态
    private Integer status;

}

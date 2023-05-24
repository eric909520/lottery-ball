package com.backend.project.system.domain.openApiVo;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取待支付订单vo
 */
@Data
public class GetPreOrderVo implements Serializable {

    // 入参 - 订单金额
    private Double amount;

    // 入参 - 三方订单号
    private String orderNo;

    // 出参 - 淘宝订单号
    private String preOrderNo;

    // 出参 - 预支付链接
    private String preLink;

}

package com.backend.project.system.domain;

import lombok.Data;

/**
 * 订单 order_info
 *
 * @author
 * @date
 */
@Data
public class OrderInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备账户/手机号
     */
    private String device;

    /**
     * 旺旺账户
     */
    private String wwAccount;

    /**
     * 链接地址
     */
    private String productLink;

    /**
     * 商品金额
     */
    private Double amount;

    /**
     * 预支付链接
     */
    private String preLink;

    /**
     * 预支付订单号-淘宝订单号
     */
    private String preOrderNo;

    /**
     * 付款人名字
     */
    private String paymentName;

    /**
     * 状态（0未使用 1待支付 2已支付 3核实未支付）
     */
    private Integer status;

    /**
     * 三方订单号
     */
    private String orderNo;

    /**
     * 点卡账号
     */
    private String cardAccount;

    /**
     * 点卡密码
     */
    private String cardPwd;

    /**
     * 确认支付时间
     */
    private Long paymentTime;

    /**
     * 卡密状态（0未核验 1核验通过 2卡密错误）
     */
    private Integer cardStatus;

}

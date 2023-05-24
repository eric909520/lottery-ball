package com.backend.project.system.domain;

import lombok.Data;

/**
 * 生成预支付订单任务 task_pre_order
 *
 * @author
 * @date
 */
@Data
public class TaskPreOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 设备账户/手机号
     */
    private String device;

    /**
     * 链接地址
     */
    private String productLink;

    /**
     * 商品金额
     */
    private Double amount;

    /**
     * 状态（0未执行 1已执行）
     */
    private Integer status;

    /**
     * 最后登陆时间
     */
    private Long runTime;

}

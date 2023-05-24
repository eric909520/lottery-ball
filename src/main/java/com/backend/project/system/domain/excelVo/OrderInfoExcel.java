package com.backend.project.system.domain.excelVo;

import com.backend.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

/**
 * 订单数据导出vo类
 */
@Data
public class OrderInfoExcel {

    /**
     * 设备账户
     */
    @Excel(name = "设备账户")
    private String device;

    /**
     * 旺旺账户
     */
    @Excel(name = "旺旺账户")
    private String wwAccount;

    /**
     * 商品金额
     */
    @Excel(name = "商品金额")
    private Double amount;

    /**
     * 淘宝订单号
     */
    @Excel(name = "淘宝订单号")
    private String preOrderNo;

    /**
     * 三方订单号
     */
    @Excel(name = "三方订单号")
    private String orderNo;

    /**
     * 状态（0未使用 1待支付 2已支付 3核实未支付）
     */
    @Excel(name = "订单状态")
    private String status;

    /**
     * 付款人姓名
     */
    @Excel(name = "付款人姓名")
    private String paymentName;

    /**
     * 确认支付时间
     */
    @Excel(name = "确认支付时间")
    private String paymentTime;

    /**
     * 点卡账号
     */
    @Excel(name = "点卡账号")
    private String cardAccount;

    /**
     * 点卡密码
     */
    @Excel(name = "点卡密码")
    private String cardPwd;

    /**
     * 卡密状态（0未核验 1核验通过 2卡密错误）
     */
    @Excel(name = "卡密状态")
    private String cardStatus;

}

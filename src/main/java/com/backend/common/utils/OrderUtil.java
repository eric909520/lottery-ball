package com.backend.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单号工具类
 */
public class OrderUtil {
    /**
     * 订单类别头
     */
    private static final String ORDER_CODE = "OD";

    /**
     * 充值类别头
     */
    private static final String RECHARGE_CODE = "RG";

    /**
     * 提现类别头
     */
    private static final String WITHDRAWAL_CODE = "WD";

    /**
     * 用户资金流水类别头
     */
    private static final String USER_FUNDFLOW_ORDER = "UF";

    /**
     * 经验流水类别头
     */
    private static final String XPFLOW_ORDER = "XF";

    /**
     * 转账记录类别头
     */
    private static final String TRANSFER_ORDER = "TR";

    /**
     * 商户资金流水类别头
     */
    private static final String MERCHANT_FUNDFLOW_ORDER = "MF";

    /**
     * 代理资金流水类别头
     */
    private static final String AGENT_FUNDFLOW_ORDER = "AF";

    /**
     * 商户代理资金流水类别头
     */
    private static final String MERCHANT_AGENT_FUNDFLOW_ORDER = "MAF";

    /**
     * 商户号类别头
     */
    private static final String MERCHANR_NO = "SH";

    /**
     * 代付订单类别头
     */
    private static final String PAID_ORDER_CODE = "PO";

    /**
     * 随机编码
     */
    private static final int[] r = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
    /**
     * 用户id和随机数总长度
     */
    private static final int maxLength = 7;

    /**
     * 根据id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Long userId) {
        String idStr = userId.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i) - '0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
     * 生成时间戳
     */
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("HHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 生成固定长度随机码
     *
     * @param n 长度
     */

    private static long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min;
        return rangeLong;
    }


    /**
     * 生成不带类别标头的编码
     *
     * @param userId
     */
    private static synchronized String getCode(Long userId) {
        userId = userId == null ? 10000 : userId;
        return getDateTime() + toCode(userId);
    }

    /**
     * 生成商户号编码(调用方法)
     * @param merchantId  网站中该商户唯一ID 防止重复
     */
    public static String getMerchantNo(Long merchantId) {
        return MERCHANR_NO + toCode(merchantId);
    }

    /**
     * 生成订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getOrderCode(Long userId) {
        return ORDER_CODE + getCode(userId);
    }

    /**
     * 生成充值订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getRechargeOrderCode(Long userId) {
        return RECHARGE_CODE + getCode(userId);
    }

    /**
     * 生成提现订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getWithdrawalOrderCode(Long userId) {
        return WITHDRAWAL_CODE + getCode(userId);
    }

    /**
     * 生成资金流水订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getUserFundflowOrderCode(Long userId) {
        return USER_FUNDFLOW_ORDER + getCode(userId);
    }

    /**
     * 生成经验流水订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getXpflowOrderCode(Long userId) {
        return XPFLOW_ORDER + getCode(userId);
    }

    /**
     * 生成转账记录订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getTransferOrderCode(Long userId) {
        return TRANSFER_ORDER + getCode(userId);
    }

    /**
     * 生成商户资金流水号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getMerchantFundflowOrderCode(Long userId) {
        return MERCHANT_FUNDFLOW_ORDER + getCode(userId);
    }

    /**
     * 生成代理资金流水号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getAgentFundflowOrderCode(Long userId) {
        return AGENT_FUNDFLOW_ORDER + getCode(userId);
    }

    /**
     * 生成商户代理资金流水号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getMerchantAgentFundflowOrderCode(Long userId) {
        return MERCHANT_AGENT_FUNDFLOW_ORDER + getCode(userId);
    }

    /**
     * 生成代付订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getPaidOrderCode(Long userId) {
        return PAID_ORDER_CODE + getCode(userId);
    }

}
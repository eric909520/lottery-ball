package com.backend.common.constant;

import io.jsonwebtoken.Claims;

/**
 * 通用常量信息
 *
 * @author
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * UTF-8 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";
    /**
     * 数据格式
     */
    public static final String ALIPAY_FORMAT="json";
    /**
     * 加密方式
     */
    public static final String SIGN_TYPE="RSA2";
    /**
     * 支付宝提现账单标题
     */
    public static final String ALIPAY_TITLE="支付平台提现";
    /**
     * 支付宝收款测试账单标题
     */
    public static final String ALIPAY_COLLEC_TITLE="支付平台收款测试";
    /**
     * 业务产品码 - 单笔无密转账到支付宝账户
     */
    public static final String ALIPAY_PRODUCT_CODE_ALIPAY ="TRANS_ACCOUNT_NO_PWD";
    /**
     * 业务产品码 - 单笔无密转账到银行卡
     */
    public static final String ALIPAY_PRODUCT_CODE_BANK ="TRANS_BANKCARD_NO_PWD";
    /**
     * 业务场景描述
     */
    public static final String ALIPAY_BIZ_SCENE ="DIRECT_TRANSFER";
    /**
     * 参与方标识类型
     */
    public static final String ALIPAY_IDENTITY_TYPE = "ALIPAY_LOGON_ID";
    /**
     * 转账到银行卡的预期到账时间
     */
    public static final String WITHDRAW_TIMELINESS = "T0";
    /**
     * 邀请奖励记录备注话述
     */
    public static final String USER_INVITE_REWARD_REMARK = "用户{{USERID}}完成订单次数达到领取{{PREMISE}}次的要求获得现金{{REWARD}}元";
    /**
     * 订单的下单用户IP
     */
    public static final String REDIS_KEY_ORDER_LIMIT_USER_IP = "ORDER_OF_USER_IP:";
    /**
     * 下单次数过多的锁
     */
    public static final String REDIS_KEY_ORDER_LIMIT_LOCK = "ORDER_LIMIT_LOCK_USER_IP:";
    /**
     * userIP下单集合
     */
    public static final String REDIS_KEY_ORDER_FAILED_IN_REDIS = "ORDER_LIMIT_FAILED_USER_IP:";

}

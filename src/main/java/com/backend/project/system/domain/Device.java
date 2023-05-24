package com.backend.project.system.domain;

import lombok.Data;

/**
 * 设备账户 device
 *
 * @author
 * @date
 */
@Data
public class Device extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 分组ID
     */
    private Long groupId;

    /**
     * 旺旺id
     */
    private Long wwId;

    /**
     * 设备账户
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 设备状态（0离线 1在线接单 2锁定）
     */
    private Integer status;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Long loginTime;

    /**
     * 版本号
     */
    private String version;

    /**
     * imei
     */
    private String imei;

    /**
     * iccid
     */
    private String iccid;

    /**
     * meid
     */
    private String meid;

    /**
     * 旺旺账户 - vo参数
     */
    private String wwAccount;

}

package com.backend.project.system.domain;

import lombok.Data;

/**
 * 旺旺账户 wangwang
 *
 * @author
 * @date
 */
@Data
public class WangWang extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 旺旺账户
     */
    private String wwAccount;

    /**
     * 旺旺密码
     */
    private String wwPassword;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 生成次数
     */
    private Integer generatedNumber;

    /**
     * 成功次数
     */
    private Integer successNumber;

    /**
     * 失败次数
     */
    private Integer failuresNumber;

    // 设备id - 接口vo参数
    private Long deviceId;

}

package com.backend.project.system.domain;

import com.backend.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

/**
 * 系统配置对象 app_config
 *
 * @author
 * @date 2020-06-24
 */
@Data
public class AppConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * key
     */
    @Excel(name = "key")
    private String cKey;

    /**
     * value
     */
    @Excel(name = "value")
    private String cValue;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private Integer type;

}

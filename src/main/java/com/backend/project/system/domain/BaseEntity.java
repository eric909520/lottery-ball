package com.backend.project.system.domain;

import com.backend.framework.web.page.PageDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Entity基类
 *
 * @author
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据权限
     */
    private String dataScope;

    /**
     * 谷歌验证码-VO参数
     */
    private String googleCode;

    private String dictLabel;

    private String dictValue;

}

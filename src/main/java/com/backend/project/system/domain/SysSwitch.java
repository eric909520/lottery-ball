package com.backend.project.system.domain;

import com.backend.framework.aspectj.lang.annotation.Excel;

/**
 * 系统开关对象 sys_switch
 *
 * @author
 * @date 2020-06-26
 */
public class SysSwitch extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * key
     */
    @Excel(name = "key")
    private String sKey;

    /**
     * 启用状态，0:关闭，1:启用
     */
    @Excel(name = "启用状态，0:关闭，1:启用")
    private Integer enable;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey;
    }

    public String getsKey() {
        return sKey;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getEnable() {
        return enable;
    }

}

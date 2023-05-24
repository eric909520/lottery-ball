package com.backend.project.system.domain;

import com.backend.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

/**
 * 公告消息对象 sys_notice
 *
 * @author
 * @date 2020-06-30
 */
@Data
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 类型，1:通知，2:公告
     */
    @Excel(name = "类型，1:通知，2:公告")
    private Integer type;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 启用状态，0:关闭，1:正常
     */
    @Excel(name = "启用状态，0:关闭，1:正常")
    private Integer enable;

    /**
     * 创建用户
     */
    @Excel(name = "创建用户")
    private String createUser;

    /**
     * 更新用户
     */
    @Excel(name = "更新用户")
    private String modifiedUser;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private Long modifiedTime;
}

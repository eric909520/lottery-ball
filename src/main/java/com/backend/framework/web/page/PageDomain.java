package com.backend.framework.web.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.backend.common.utils.StringUtils;

/**
 * 分页数据
 *
 * @author
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDomain {
    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String isAsc;

    /**
     * 开始时间
     */
    private Long queryBeginTime;
    /**
     * 结束时间
     */
    private Long queryEndTime;

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return null;
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public Long getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(Long queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public Long getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(Long queryEndTime) {
        this.queryEndTime = queryEndTime;
    }
}

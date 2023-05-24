package com.backend.framework.web.controller;

import com.backend.framework.web.domain.AjaxResult;
import com.backend.framework.web.page.PageDomain;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.backend.common.constant.HttpStatus;
import com.backend.common.utils.DateUtils;
import com.backend.common.utils.StringUtils;
import com.backend.common.utils.sql.SqlUtil;
import com.backend.framework.web.page.TableDataInfo;
import com.backend.framework.web.page.TableSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理

 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });

    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        startPage(pageDomain);
    }

    protected void startPage(String orderType){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isBlank(pageDomain.getIsAsc()))pageDomain.setIsAsc(orderType);//"desc"或者"asc"
        startPage(pageDomain);
    }

    protected void startPage(String orderType, String orderColumn){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isBlank(pageDomain.getIsAsc()))pageDomain.setIsAsc(orderType);//"desc"或者"asc"
        if (StringUtils.isNotBlank(pageDomain.getIsAsc()) && StringUtils.isBlank(pageDomain.getOrderByColumn()))pageDomain.setOrderByColumn(orderColumn);
        startPage(pageDomain);
    }

    protected void startPage(String orderType, String orderColumn, Integer pageNum, Integer pageSize){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isBlank(pageDomain.getIsAsc()))pageDomain.setIsAsc(orderType);//"desc"或者"asc"
        if (StringUtils.isNotBlank(pageDomain.getIsAsc()) && StringUtils.isBlank(pageDomain.getOrderByColumn()))pageDomain.setOrderByColumn(orderColumn);
        if (pageDomain.getPageNum() == null && pageNum !=null)pageDomain.setPageNum(pageNum);
        if (pageDomain.getPageSize() == null && pageSize !=null)pageDomain.setPageSize(pageSize);
        startPage(pageDomain);
    }

    protected void startPage(PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        if (StringUtils.isNotBlank(orderBy))PageHelper.orderBy(orderBy);
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}

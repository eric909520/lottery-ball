package com.backend.project.system.controller;

import com.backend.framework.aspectj.lang.annotation.Log;
import com.backend.framework.aspectj.lang.enums.BusinessType;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.framework.web.page.TableDataInfo;
import com.backend.project.system.domain.WangWang;
import com.backend.project.system.service.IWangWangService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 旺旺账户Controller
 *
 * @author backend
 * @date 2023-02-28
 */
@RestController
@RequestMapping("/system/wangwang")
public class WangwangController extends BaseController {
    @Resource
    private IWangWangService wangWangService;

    /**
     * 查询旺旺账户列表
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody WangWang wangwang) {
        List<WangWang> list = wangWangService.selectWangWangList(wangwang);
        return getDataTable(list);
    }

    /**
     * 获取旺旺账户详细信息
     */
    @PostMapping(value = "/query/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(wangWangService.selectWangWangById(id));
    }

    /**
     * 新增旺旺账户
     */
    @Log(title = "旺旺账户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WangWang wangwang) {
        return toAjax(wangWangService.insertWangWang(wangwang));
    }

    /**
     * 修改旺旺账户
     */
    @Log(title = "旺旺账户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody WangWang wangwang) {
        return toAjax(wangWangService.updateWangWang(wangwang));
    }

    /**
     * 验证旺旺账户
     */
    @PostMapping(value = "/check/{id}")
    public AjaxResult check(@PathVariable("id") Long id) {
        return wangWangService.checkWangwang(id);
    }

    /**
     * 删除旺旺账户
     */
    @Log(title = "旺旺账户", businessType = BusinessType.DELETE)
    @PostMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        return toAjax(wangWangService.deleteWangWangById(id));
    }

}

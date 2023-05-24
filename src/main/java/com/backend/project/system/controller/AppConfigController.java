package com.backend.project.system.controller;

import java.util.List;

import com.backend.framework.aspectj.lang.annotation.Log;
import com.backend.framework.aspectj.lang.enums.BusinessType;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.framework.web.page.TableDataInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.project.system.domain.AppConfig;
import com.backend.project.system.service.IAppConfigService;
import com.backend.common.utils.poi.ExcelUtil;

/**
 * 系统配置Controller
 *
 * @author
 * @date 2020-06-24
 */
@RestController
@RequestMapping("/system/appconfig")
public class AppConfigController extends BaseController {
    @Autowired
    private IAppConfigService appConfigService;

    /**
     * 查询系统配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:appconfig:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody AppConfig appConfig) {
        List<AppConfig> list = appConfigService.selectAppConfigList(appConfig);
        return getDataTable(list);
    }

    /**
     * 导出系统配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:appconfig:export')")
    @Log(title = "系统配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody AppConfig appConfig) {
        List<AppConfig> list = appConfigService.selectAppConfigList(appConfig);
        ExcelUtil<AppConfig> util = new ExcelUtil<AppConfig>(AppConfig. class);
        return util.exportExcel(list, "appconfig");
    }

    /**
     * 获取系统配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:appconfig:query')")
    @PostMapping(value = "/query/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(appConfigService.selectAppConfigById(id));
    }

    /**
     * 新增系统配置
     */
    @PreAuthorize("@ss.hasPermi('system:appconfig:add')")
    @Log(title = "系统配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AppConfig appConfig) {
        return toAjax(appConfigService.insertAppConfig(appConfig));
    }

    /**
     * 修改系统配置
     */
    @PreAuthorize("@ss.hasPermi('system:appconfig:edit')")
    @Log(title = "系统配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody AppConfig appConfig) {
        return toAjax(appConfigService.updateAppConfig(appConfig));
    }

    /**
     * 删除系统配置
     */
    @PreAuthorize("@ss.hasPermi('system:appconfig:remove')")
    @Log(title = "系统配置", businessType = BusinessType.DELETE)
    @PostMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(appConfigService.deleteAppConfigByIds(ids));
    }
}

package com.backend.project.system.controller;

import com.backend.framework.aspectj.lang.annotation.Log;
import com.backend.framework.aspectj.lang.enums.BusinessType;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.framework.web.page.TableDataInfo;
import com.backend.project.system.domain.Device;
import com.backend.project.system.service.IDeviceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备账户Controller
 *
 * @author backend
 * @date 2023-02-28
 */
@RestController
@RequestMapping("/system/device")
public class DeviceController extends BaseController {
    @Resource
    private IDeviceService deviceService;

    /**
     * 查询设备账户列表
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody Device device) {
        List<Device> list = deviceService.selectDeviceList(device);
        return getDataTable(list);
    }

    /**
     * 获取设备账户详细信息
     */
    @PostMapping(value = "/query/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(deviceService.selectDeviceById(id));
    }

    /**
     * 新增设备账户
     */
    @Log(title = "设备账户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Device device) {
        return deviceService.insertDevice(device);
    }

    /**
     * 修改设备账户
     */
    @Log(title = "设备账户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody Device device) {
        return toAjax(deviceService.updateDevice(device));
    }

    /**
     * 绑定旺旺账户
     */
    @Log(title = "设备账户", businessType = BusinessType.UPDATE)
    @PostMapping("/bindWw")
    public AjaxResult bindWw(@RequestBody Device device) {
        return deviceService.bindWw(device);
    }

}

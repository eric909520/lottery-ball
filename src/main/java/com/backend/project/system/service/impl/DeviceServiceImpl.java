package com.backend.project.system.service.impl;

import com.backend.common.utils.SecurityUtils;
import com.backend.common.utils.security.AesUtil;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.Device;
import com.backend.project.system.mapper.DeviceMapper;
import com.backend.project.system.service.IDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备账户Service业务层处理
 *
 * @author
 * @date 2020-06-24
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Resource
    private DeviceMapper deviceMapper;

    /**
     * 查询设备账户
     *
     * @param id 设备账户ID
     * @return 设备账户
     */
    @Override
    public Device selectDeviceById(Long id) {
        Device device = deviceMapper.selectDeviceById(id);
        device.setPassword(AesUtil.aesDecryptCbc(device.getPassword()));
        return device;
    }

    @Override
    public Device selectDeviceByMobile(String mobile) {
        return deviceMapper.selectDeviceByMobile(mobile);
    }

    /**
     * 查询设备账户列表
     *
     * @param device 设备账户
     * @return 设备账户
     */
    @Override
    public List<Device> selectDeviceList(Device device) {
        List<Device> devices = deviceMapper.selectDeviceList(device);
        for (Device dv : devices) {
            dv.setPassword(AesUtil.aesDecryptCbc(dv.getPassword()));
        }
        return devices;
    }

    /**
     * 新增设备账户
     *
     * @param device 设备账户
     * @return 结果
     */
    @Override
    public AjaxResult insertDevice(Device device) {
        Device deviceCheck = deviceMapper.selectDeviceByMobile(device.getMobile());
        if (deviceCheck != null) {
            return AjaxResult.error("设备账户已存在");
        }
        device.setPassword(AesUtil.aesEncryptCbc(device.getPassword()));
        device.setCreateTime(System.currentTimeMillis());
        device.setCreateBy(SecurityUtils.getUsername());
        int result = deviceMapper.insertDevice(device);
        if (result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.fail();
    }

    /**
     * 修改设备账户
     *
     * @param device 设备账户
     * @return 结果
     */
    @Override
    public int updateDevice(Device device) {
        device.setPassword(AesUtil.aesEncryptCbc(device.getPassword()));
        device.setUpdateTime(System.currentTimeMillis());
        device.setUpdateBy(SecurityUtils.getUsername());
        return deviceMapper.updateDevice(device);
    }

    /**
     * 删除设备账户信息
     *
     * @param id 设备账户ID
     * @return 结果
     */
    @Override
    public int deleteDeviceById(Long id) {
        return deviceMapper.deleteDeviceById(id);
    }

    /**
     * 绑定旺旺账户
     * @param device
     * @return
     */
    @Override
    public AjaxResult bindWw(Device device) {
        device.setUpdateTime(System.currentTimeMillis());
        device.setUpdateBy(SecurityUtils.getUsername());
        int result = deviceMapper.bindWw(device);
        if (result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.fail();
    }

}

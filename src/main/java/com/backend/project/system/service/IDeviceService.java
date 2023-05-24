package com.backend.project.system.service;

import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.Device;

import java.util.List;

/**
 * 设备账户Service接口
 *
 * @author
 * @date 
 */
public interface IDeviceService {
    /**
     * 查询设备账户
     *
     * @param id 设备账户ID
     * @return 设备账户
     */
    public Device selectDeviceById(Long id);

    /**
     * 查询设备账户
     *
     * @param mobile 设备账户
     * @return 设备账户
     */
    public Device selectDeviceByMobile(String mobile);

    /**
     * 查询设备账户列表
     *
     * @param device 设备账户
     * @return 设备账户集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增设备账户
     *
     * @param device 设备账户
     * @return 结果
     */
    public AjaxResult insertDevice(Device device);

    /**
     * 修改设备账户
     *
     * @param device 设备账户
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 删除设备账户信息
     *
     * @param id 设备账户ID
     * @return 结果
     */
    public int deleteDeviceById(Long id);

    /**
     * 绑定旺旺账户
     * @param device
     * @return
     */
    public AjaxResult bindWw(Device device);

}

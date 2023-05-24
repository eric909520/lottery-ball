package com.backend.project.system.mapper;

import com.backend.project.system.domain.Device;
import com.backend.project.system.domain.openApiVo.AuthVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备账户Mapper接口
 *
 * @author
 * @date
 */
public interface DeviceMapper {
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
     * @param mobile
     * @return 设备账户
     */
    public Device selectDeviceByMobile(String mobile);

    /**
     * 随机获取在线设备
     *
     * @return 设备账户
     */
    public Device selectDeviceRand();

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
    public int insertDevice(Device device);

    /**
     * 修改设备账户
     *
     * @param device 设备账户
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 删除设备账户
     *
     * @param id 设备账户ID
     * @return 结果
     */
    public int deleteDeviceById(Long id);

    /**
     * 设备授权登录
     * @param authVo
     * @return
     */
    public Device deviceAuth(AuthVo authVo);

    /**
     * 修改设备在线状态
     *
     * @param deviceId
     * @return 结果
     */
    public int updateDeviceStatus(@Param("deviceId") Long deviceId, @Param("status") Integer status);

    /**
     * 绑定旺旺账户
     * @param device
     * @return
     */
    public int bindWw(Device device);

}

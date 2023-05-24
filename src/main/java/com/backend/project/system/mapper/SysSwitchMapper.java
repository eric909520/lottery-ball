package com.backend.project.system.mapper;

import com.backend.project.system.domain.SysSwitch;
import java.util.List;

/**
 * 系统开关Mapper接口
 *
 * @author
 * @date 2020-06-26
 */
public interface SysSwitchMapper {
    /**
     * 查询系统开关
     *
     * @param id 系统开关ID
     * @return 系统开关
     */
    public SysSwitch selectSysSwitchById(Long id);

    /**
     * 查询系统开关列表
     *
     * @param sysSwitch 系统开关
     * @return 系统开关集合
     */
    public List<SysSwitch> selectSysSwitchList(SysSwitch sysSwitch);

    /**
     * 新增系统开关
     *
     * @param sysSwitch 系统开关
     * @return 结果
     */
    public int insertSysSwitch(SysSwitch sysSwitch);

    /**
     * 修改系统开关
     *
     * @param sysSwitch 系统开关
     * @return 结果
     */
    public int updateSysSwitch(SysSwitch sysSwitch);

    /**
     * 删除系统开关
     *
     * @param id 系统开关ID
     * @return 结果
     */
    public int deleteSysSwitchById(Long id);

    /**
     * 批量删除系统开关
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSwitchByIds(Long[] ids);
}

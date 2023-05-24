package com.backend.project.system.service;

import com.backend.project.system.domain.AppConfig;
import java.util.List;

/**
 * 系统配置Service接口
 *
 * @author
 * @date 2020-06-24
 */
public interface IAppConfigService {
    /**
     * 查询系统配置
     *
     * @param id 系统配置ID
     * @return 系统配置
     */
    public AppConfig selectAppConfigById(Long id);

    /**
     * 查询系统配置列表
     *
     * @param appConfig 系统配置
     * @return 系统配置集合
     */
    public List<AppConfig> selectAppConfigList(AppConfig appConfig);

    /**
     * 新增系统配置
     *
     * @param appConfig 系统配置
     * @return 结果
     */
    public int insertAppConfig(AppConfig appConfig);

    /**
     * 修改系统配置
     *
     * @param appConfig 系统配置
     * @return 结果
     */
    public int updateAppConfig(AppConfig appConfig);

    /**
     * 批量删除系统配置
     *
     * @param ids 需要删除的系统配置ID
     * @return 结果
     */
    public int deleteAppConfigByIds(Long[] ids);

    /**
     * 删除系统配置信息
     *
     * @param id 系统配置ID
     * @return 结果
     */
    public int deleteAppConfigById(Long id);
}

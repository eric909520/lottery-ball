package com.backend.project.system.mapper;

import com.backend.framework.aspectj.lang.annotation.DataSource;
import com.backend.framework.aspectj.lang.enums.DataSourceType;
import com.backend.project.system.domain.AppConfig;
import java.util.List;

/**
 * 系统配置Mapper接口
 *
 * @author
 * @date 2020-06-24
 */
public interface AppConfigMapper {
    /**
     * 查询系统配置
     *
     * @param id 系统配置ID
     * @return 系统配置
     */
    public AppConfig selectAppConfigById(Long id);

    /**
     * 查询系统配置
     *
     * @param cKey
     * @return 系统配置
     */
    public AppConfig selectAppConfigByKey(String cKey);

    /**
     * 查询系统配置
     *
     * @param cKey
     * @return 系统配置
     */
    @DataSource(value = DataSourceType.MASTER)
    public AppConfig selectAppConfigByKy(String cKey);

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
     * 删除系统配置
     *
     * @param id 系统配置ID
     * @return 结果
     */
    public int deleteAppConfigById(Long id);

    /**
     * 批量删除系统配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppConfigByIds(Long[] ids);
}

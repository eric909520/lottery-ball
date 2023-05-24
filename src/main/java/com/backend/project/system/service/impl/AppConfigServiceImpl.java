package com.backend.project.system.service.impl;

import com.backend.common.utils.CalcUtil;
import com.backend.framework.redis.RedisCache;
import com.backend.project.system.domain.AppConfig;
import com.backend.project.system.enums.EnableEnum;
import com.backend.project.system.mapper.AppConfigMapper;
import com.backend.project.system.service.IAppConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统配置Service业务层处理
 *
 * @author
 * @date 2020-06-24
 */
@Service
public class AppConfigServiceImpl implements IAppConfigService {
    @Resource
    private AppConfigMapper appConfigMapper;
    @Resource
    private RedisCache redisCache;

    /**
     * 查询系统配置
     *
     * @param id 系统配置ID
     * @return 系统配置
     */
    @Override
    public AppConfig selectAppConfigById(Long id) {
        return appConfigMapper.selectAppConfigById(id);
    }

    /**
     * 查询系统配置列表
     *
     * @param appConfig 系统配置
     * @return 系统配置
     */
    @Override
    public List<AppConfig> selectAppConfigList(AppConfig appConfig) {
        return appConfigMapper.selectAppConfigList(appConfig);
    }

    /**
     * 新增系统配置
     *
     * @param appConfig 系统配置
     * @return 结果
     */
    @Override
    public int insertAppConfig(AppConfig appConfig) {
        return appConfigMapper.insertAppConfig(appConfig);
    }

    /**
     * 修改系统配置
     *
     * @param appConfig 系统配置
     * @return 结果
     */
    @Override
    public int updateAppConfig(AppConfig appConfig) {
        return appConfigMapper.updateAppConfig(appConfig);
    }

    /**
     * 批量删除系统配置
     *
     * @param ids 需要删除的系统配置ID
     * @return 结果
     */
    @Override
    public int deleteAppConfigByIds(Long[] ids) {
        return appConfigMapper.deleteAppConfigByIds(ids);
    }

    /**
     * 删除系统配置信息
     *
     * @param id 系统配置ID
     * @return 结果
     */
    @Override
    public int deleteAppConfigById(Long id) {
        return appConfigMapper.deleteAppConfigById(id);
    }
}

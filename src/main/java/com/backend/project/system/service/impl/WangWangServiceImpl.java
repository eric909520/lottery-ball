package com.backend.project.system.service.impl;

import com.backend.common.utils.SecurityUtils;
import com.backend.common.utils.security.AesUtil;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.WangWang;
import com.backend.project.system.mapper.WangWangMapper;
import com.backend.project.system.service.IWangWangService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 旺旺账户Service业务层处理
 *
 * @author
 * @date 2020-06-24
 */
@Service
public class WangWangServiceImpl implements IWangWangService {
    @Resource
    private WangWangMapper wangWangMapper;

    /**
     * 查询旺旺账户
     *
     * @param id 旺旺账户ID
     * @return 旺旺账户
     */
    @Override
    public WangWang selectWangWangById(Long id) {
        WangWang wangWang = wangWangMapper.selectWangWangByIdCommon(id);
        wangWang.setWwPassword(AesUtil.aesDecryptCbc(wangWang.getWwPassword()));
        return wangWang;
    }

    /**
     * 验证旺旺账户
     *
     * @param id 旺旺账户ID
     * @return 旺旺账户
     */
    @Override
    public AjaxResult checkWangwang(Long id) {
        WangWang wangWang = wangWangMapper.selectWangWangByIdCommon(id);
        if (wangWang == null) {
            return AjaxResult.error("往往账户不存在，请核实");
        }
        if (wangWang.getStatus() != 0) {
            return AjaxResult.error("当前旺旺账户不可用，请核实");
        }
        return AjaxResult.success(wangWang);
    }

    @Override
    public WangWang selectWangWangByAccount(String account) {
        return wangWangMapper.selectWangWangByAccount(account);
    }

    /**
     * 查询旺旺账户列表
     *
     * @param wangWang 旺旺账户
     * @return 旺旺账户
     */
    @Override
    public List<WangWang> selectWangWangList(WangWang wangWang) {
        List<WangWang> wangWangs = wangWangMapper.selectWangWangList(wangWang);
        for (WangWang ww : wangWangs) {
            ww.setWwPassword(AesUtil.aesDecryptCbc(ww.getWwPassword()));
        }
        return wangWangs;
    }

    /**
     * 新增旺旺账户
     *
     * @param wangWang 旺旺账户
     * @return 结果
     */
    @Override
    public int insertWangWang(WangWang wangWang) {
        wangWang.setWwPassword(AesUtil.aesEncryptCbc(wangWang.getWwPassword()));
        wangWang.setCreateTime(System.currentTimeMillis());
        wangWang.setCreateBy(SecurityUtils.getUsername());
        return wangWangMapper.insertWangWang(wangWang);
    }

    /**
     * 修改旺旺账户
     *
     * @param wangWang 旺旺账户
     * @return 结果
     */
    @Override
    public int updateWangWang(WangWang wangWang) {
        wangWang.setWwPassword(AesUtil.aesEncryptCbc(wangWang.getWwPassword()));
        wangWang.setUpdateTime(System.currentTimeMillis());
        wangWang.setUpdateBy(SecurityUtils.getUsername());
        return wangWangMapper.updateWangWang(wangWang);
    }

    /**
     * 删除旺旺账户信息
     *
     * @param id 旺旺账户ID
     * @return 结果
     */
    @Override
    public int deleteWangWangById(Long id) {
        return wangWangMapper.deleteWangWangById(id);
    }

}

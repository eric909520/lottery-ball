package com.backend.project.system.service;

import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.WangWang;

import java.util.List;

/**
 * 旺旺账户Service接口
 *
 * @author
 * @date 
 */
public interface IWangWangService {
    /**
     * 查询旺旺账户
     *
     * @param id 旺旺账户ID
     * @return 旺旺账户
     */
    public WangWang selectWangWangById(Long id);

    /**
     * 验证旺旺账户
     *
     * @param id 旺旺账户ID
     * @return 旺旺账户
     */
    public AjaxResult checkWangwang(Long id);

    /**
     * 查询旺旺账户
     *
     * @param account 旺旺账户
     * @return 旺旺账户
     */
    public WangWang selectWangWangByAccount(String account);

    /**
     * 查询旺旺账户列表
     *
     * @param wangWang 旺旺账户
     * @return 旺旺账户集合
     */
    public List<WangWang> selectWangWangList(WangWang wangWang);

    /**
     * 新增旺旺账户
     *
     * @param wangWang 旺旺账户
     * @return 结果
     */
    public int insertWangWang(WangWang wangWang);

    /**
     * 修改旺旺账户
     *
     * @param wangWang 旺旺账户
     * @return 结果
     */
    public int updateWangWang(WangWang wangWang);

    /**
     * 删除旺旺账户信息
     *
     * @param id 旺旺账户ID
     * @return 结果
     */
    public int deleteWangWangById(Long id);

}

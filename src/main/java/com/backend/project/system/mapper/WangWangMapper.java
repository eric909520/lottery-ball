package com.backend.project.system.mapper;

import com.backend.project.system.domain.WangWang;

import java.util.List;

/**
 * 旺旺账户Mapper接口
 *
 * @author
 * @date
 */
public interface WangWangMapper {
    /**
     * 查询旺旺账户
     *
     * @param id 旺旺账户ID
     * @return 旺旺账户
     */
    public WangWang selectWangWangById(Long id);

    /**
     * 查询旺旺账户
     *
     * @param id 旺旺账户ID
     * @return 旺旺账户
     */
    public WangWang selectWangWangByIdCommon(Long id);

    /**
     * 查询旺旺账户
     *
     * @param account
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
     * 删除旺旺账户
     *
     * @param id 旺旺账户ID
     * @return 结果
     */
    public int deleteWangWangById(Long id);

}

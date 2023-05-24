package com.backend.project.system.service;

import com.backend.project.system.domain.ProductLink;

import java.util.List;

/**
 * 商品链接Service接口
 *
 * @author
 * @date 
 */
public interface IProductLinkService {
    /**
     * 查询商品链接
     *
     * @param id 商品链接ID
     * @return 商品链接
     */
    public ProductLink selectProductLinkById(Long id);

    /**
     * 查询商品链接
     *
     * @param amount 商品链接
     * @return 商品链接
     */
    public ProductLink selectProductLinkByAmount(Double amount);

    /**
     * 查询商品链接列表
     *
     * @param ProductLink 商品链接
     * @return 商品链接集合
     */
    public List<ProductLink> selectProductLinkList(ProductLink ProductLink);

    /**
     * 新增商品链接
     *
     * @param ProductLink 商品链接
     * @return 结果
     */
    public int insertProductLink(ProductLink ProductLink);

    /**
     * 修改商品链接
     *
     * @param ProductLink 商品链接
     * @return 结果
     */
    public int updateProductLink(ProductLink ProductLink);

    /**
     * 删除商品链接信息
     *
     * @param id 商品链接ID
     * @return 结果
     */
    public int deleteProductLinkById(Long id);

}

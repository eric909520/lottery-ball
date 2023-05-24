package com.backend.project.system.mapper;

import com.backend.project.system.domain.ProductLink;

import java.util.List;

/**
 * 商品链接Mapper接口
 *
 * @author
 * @date
 */
public interface ProductLinkMapper {
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
     * @param amount 商品金额
     * @return 商品链接
     */
    public ProductLink selectProductLinkByAmount(Double amount);

    /**
     * 随机查询商品链接
     *
     * @param amount 商品金额
     * @return 商品链接
     */
    public ProductLink selectProductLinkRandByAmount(Double amount);

    /**
     * 查询商品链接列表
     *
     * @param productLink 商品链接
     * @return 商品链接集合
     */
    public List<ProductLink> selectProductLinkList(ProductLink productLink);

    /**
     * 新增商品链接
     *
     * @param productLink 商品链接
     * @return 结果
     */
    public int insertProductLink(ProductLink productLink);

    /**
     * 修改商品链接
     *
     * @param productLink 商品链接
     * @return 结果
     */
    public int updateProductLink(ProductLink productLink);

    /**
     * 删除商品链接
     *
     * @param id 商品链接ID
     * @return 结果
     */
    public int deleteProductLinkById(Long id);

}

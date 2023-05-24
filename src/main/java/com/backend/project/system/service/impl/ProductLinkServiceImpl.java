package com.backend.project.system.service.impl;

import com.backend.common.utils.SecurityUtils;
import com.backend.project.system.domain.ProductLink;
import com.backend.project.system.mapper.ProductLinkMapper;
import com.backend.project.system.service.IProductLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品链接Service业务层处理
 *
 * @author
 * @date 2020-06-24
 */
@Service
public class ProductLinkServiceImpl implements IProductLinkService {
    @Resource
    private ProductLinkMapper ProductLinkMapper;

    /**
     * 查询商品链接
     *
     * @param id 商品链接ID
     * @return 商品链接
     */
    @Override
    public ProductLink selectProductLinkById(Long id) {
        return ProductLinkMapper.selectProductLinkById(id);
    }

    @Override
    public ProductLink selectProductLinkByAmount(Double amount) {
        return ProductLinkMapper.selectProductLinkByAmount(amount);
    }

    /**
     * 查询商品链接列表
     *
     * @param productLink 商品链接
     * @return 商品链接
     */
    @Override
    public List<ProductLink> selectProductLinkList(ProductLink productLink) {
        return ProductLinkMapper.selectProductLinkList(productLink);
    }

    /**
     * 新增商品链接
     *
     * @param productLink 商品链接
     * @return 结果
     */
    @Override
    public int insertProductLink(ProductLink productLink) {
        productLink.setCreateTime(System.currentTimeMillis());
        productLink.setCreateBy(SecurityUtils.getUsername());
        return ProductLinkMapper.insertProductLink(productLink);
    }

    /**
     * 修改商品链接
     *
     * @param productLink 商品链接
     * @return 结果
     */
    @Override
    public int updateProductLink(ProductLink productLink) {
        productLink.setUpdateTime(System.currentTimeMillis());
        productLink.setUpdateBy(SecurityUtils.getUsername());
        return ProductLinkMapper.updateProductLink(productLink);
    }

    /**
     * 删除商品链接信息
     *
     * @param id 商品链接ID
     * @return 结果
     */
    @Override
    public int deleteProductLinkById(Long id) {
        return ProductLinkMapper.deleteProductLinkById(id);
    }

}

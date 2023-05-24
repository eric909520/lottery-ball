package com.backend.project.system.domain;

import lombok.Data;

/**
 * 商品链接 product_link
 *
 * @author
 * @date
 */
@Data
public class ProductLink extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 链接地址
     */
    private String link;

    /**
     * 商品金额
     */
    private Double amount;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

}

package com.backend.project.system.controller;

import com.backend.framework.aspectj.lang.annotation.Log;
import com.backend.framework.aspectj.lang.enums.BusinessType;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.framework.web.page.TableDataInfo;
import com.backend.project.system.domain.ProductLink;
import com.backend.project.system.service.IProductLinkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品链接Controller
 *
 * @author backend
 * @date 2023-02-28
 */
@RestController
@RequestMapping("/system/productlink")
public class ProductLinkController extends BaseController {
    @Resource
    private IProductLinkService productLinkService;

    /**
     * 查询商品链接列表
     */
    @PreAuthorize("@ss.hasPermi('system:productlink:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody ProductLink productLink) {
        List<ProductLink> list = productLinkService.selectProductLinkList(productLink);
        return getDataTable(list);
    }

    /**
     * 获取商品链接详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:productlink:query')")
    @PostMapping(value = "/query/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(productLinkService.selectProductLinkById(id));
    }

    /**
     * 新增商品链接
     */
    @Log(title = "商品链接", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ProductLink productLink) {
        return toAjax(productLinkService.insertProductLink(productLink));
    }

    /**
     * 修改商品链接
     */
    @Log(title = "商品链接", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody ProductLink productLink) {
        return toAjax(productLinkService.updateProductLink(productLink));
    }

    /**
     * 删除商品链接
     */
    @Log(title = "商品链接", businessType = BusinessType.DELETE)
    @PostMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        return toAjax(productLinkService.deleteProductLinkById(id));
    }

}

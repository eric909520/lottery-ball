package com.backend.project.system.controller;

import com.backend.common.utils.GoogleGenerator;
import com.backend.common.utils.SecurityUtils;
import com.backend.common.utils.ServletUtils;
import com.backend.common.utils.StringUtils;
import com.backend.common.utils.poi.ExcelUtil;
import com.backend.framework.aspectj.lang.annotation.Log;
import com.backend.framework.aspectj.lang.enums.BusinessType;
import com.backend.framework.security.LoginUser;
import com.backend.framework.security.service.TokenService;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.framework.web.page.TableDataInfo;
import com.backend.project.system.domain.OrderInfo;
import com.backend.project.system.domain.SysUser;
import com.backend.project.system.domain.excelVo.OrderInfoExcel;
import com.backend.project.system.service.IOrderInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单Controller
 *
 * @author backend
 * @date 2023-02-28
 */
@RestController
@RequestMapping("/system/orderinfo")
public class OrderInfoController extends BaseController {
    @Resource
    private IOrderInfoService orderInfoService;
    @Resource
    private TokenService tokenService;

    /**
     * 查询订单列表
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody OrderInfo orderInfo) {
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            for (OrderInfo order : list) {
                order.setCardPwd(StringUtils.getStringMiddleHide(order.getCardPwd()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody OrderInfo orderInfo) {
        //导出去除分页
        orderInfo.setPageNum(null);
        orderInfo.setPageSize(null);

        List<OrderInfoExcel> list = orderInfoService.selectOrderInfoListForExcel(orderInfo);
        ExcelUtil<OrderInfoExcel> util = new ExcelUtil<OrderInfoExcel>(OrderInfoExcel.class);
        return util.exportExcel(list, "-订单列表");
    }

    /**
     * 获取订单详细信息
     */
    @PostMapping(value = "/query/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(orderInfoService.selectOrderInfoById(id));
    }

    /**
     * 新增订单
     */
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody OrderInfo orderInfo) {
        return toAjax(orderInfoService.insertOrderInfo(orderInfo));
    }

    /**
     * 修改订单
     */
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody OrderInfo orderInfo) {
        // 验证谷歌密钥
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        // 验证googleCode
        if (!GoogleGenerator.checkCode(user.getSecretKey(), orderInfo.getGoogleCode(), System.currentTimeMillis())) {
            return AjaxResult.error("谷歌验证码错误");
        }
        return toAjax(orderInfoService.updateOrderInfo(orderInfo));
    }

}

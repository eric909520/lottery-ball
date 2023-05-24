package com.backend.project.system.api;

import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.security.AesUtil;
import com.backend.framework.config.websocket.WebSocketServer;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.*;
import com.backend.project.system.domain.openApiVo.AuthVo;
import com.backend.project.system.domain.openApiVo.GetPreOrderVo;
import com.backend.project.system.domain.openApiVo.OrderConfirmVo;
import com.backend.project.system.domain.websocketVo.WebSocketVo;
import com.backend.project.system.domain.websocketVo.WsOrderConfirmVo;
import com.backend.project.system.enums.OrderStatusEnum;
import com.backend.project.system.enums.WebsocketTitleEnum;
import com.backend.project.system.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 开放接口 Controller
 *
 * @author
 * @date
 */
@RestController
@RequestMapping("/system/open/api/")
@Slf4j
public class OpenApiController extends BaseController {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private WangWangMapper wangWangMapper;
    @Resource
    private TaskPreOrderMapper taskPreOrderMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;

    /**
     * 获取用户详细信息
     */
    @PostMapping(value = "/getinfo/{name}")
    public AjaxResult getInfo(@PathVariable("name") String name) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(name);
        return AjaxResult.success(sysUser.getUserName());
    }

    /**
     * 登录授权
     */
    @PostMapping(value = "/auth")
    public AjaxResult auth(@RequestBody AuthVo authVo) {
        authVo.setPassword(AesUtil.aesEncryptCbc(authVo.getPassword()));
        Device device = deviceMapper.deviceAuth(authVo);
        if (device == null) {
            return AjaxResult.error("登录授权失败");
        }
        Long wwId = device.getWwId();
        if (wwId == null) {
            return AjaxResult.error("设备未分配旺旺账号");
        }
        WangWang wangWang = wangWangMapper.selectWangWangById(wwId);
        if (wangWang == null) {
            return AjaxResult.error("旺旺账号不可用，请核实");
        }
        wangWang.setWwPassword(AesUtil.aesDecryptCbc(wangWang.getWwPassword()));
        wangWang.setDeviceId(device.getId());
        return AjaxResult.success(wangWang);
    }

    /**
     * 待处理任务-生成预支付订单
     */
    @PostMapping(value = "/task/preOrder/{mobile}")
    public AjaxResult preOrder(@PathVariable("mobile") String mobile) {
        TaskPreOrder taskPreOrder = taskPreOrderMapper.selectTaskPreOrderByDevice(mobile);
        return AjaxResult.success(taskPreOrder);
    }

    /**
     * 获取待支付订单
     */
    @PostMapping(value = "/getPreOrder")
    public AjaxResult getPreOrder(@RequestBody GetPreOrderVo getPreOrderVo) {
        if (StringUtils.isBlank(getPreOrderVo.getOrderNo()) || getPreOrderVo.getAmount()==null) {
            return AjaxResult.error("1001-参数错误");
        }
        Double amount = getPreOrderVo.getAmount();
        OrderInfo orderInfo = orderInfoMapper.selectPreOrder(amount);
        if (orderInfo == null) {
            return AjaxResult.error("1002-当前金额缺少预付订单，请稍后再试");
        }
        // 更新三方订单号
        orderInfoMapper.updateOrderNo(getPreOrderVo.getOrderNo(), orderInfo.getId());
        // 构造返回数据
        getPreOrderVo.setPreOrderNo(orderInfo.getPreOrderNo());
        getPreOrderVo.setPreLink(orderInfo.getPreLink());
        return AjaxResult.success(getPreOrderVo);
    }

    /**
     * 订单确认支付
     */
    @PostMapping(value = "/orderConfirm")
    public AjaxResult orderConfirm(@RequestBody OrderConfirmVo orderConfirmVo) {
        if (StringUtils.isBlank(orderConfirmVo.getOrderNo()) || StringUtils.isBlank(orderConfirmVo.getPreOrderNo())) {
            return AjaxResult.error("1001-参数错误");
        }
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderConfirmVo.getOrderNo(), orderConfirmVo.getPreOrderNo());
        if (orderInfo == null) {
            return AjaxResult.error("1003-未查询到有效订单");
        }
        try {
            WsOrderConfirmVo wsOrderConfirmVo = new WsOrderConfirmVo(orderInfo.getOrderNo(), orderConfirmVo.getPreOrderNo());
            WebSocketVo webSocketVo = new WebSocketVo(WebsocketTitleEnum.confirmOrder.getTitle(), wsOrderConfirmVo);
            String message = JSONObject.toJSONString(webSocketVo);
            Boolean result = WebSocketServer.sendInfo(message, String.valueOf(orderInfo.getDeviceId()));
            if (!result) {
                return AjaxResult.error("1004-支付结果核对未通过，请稍后重试");
            }
            Thread.sleep(5000l);
        } catch (Exception e) {
            log.info("orderConfirm exception ----->>>>", e);
        }
        orderInfo = orderInfoMapper.selectByOrderNo(orderConfirmVo.getOrderNo(), orderConfirmVo.getPreOrderNo());
        if (orderInfo.getStatus() != OrderStatusEnum.confirm.getStatus()) {
            return AjaxResult.error("1005-支付结果核对未通过，请稍后重试");
        }
        // 构造返回数据
        orderConfirmVo.setStatus(OrderStatusEnum.confirm.getStatus());
        return AjaxResult.success(orderConfirmVo);
    }

    public static void main(String[] args) {



    }

}

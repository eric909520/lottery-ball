package com.backend.project.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.SecurityUtils;
import com.backend.common.utils.StringUtils;
import com.backend.project.system.domain.OrderInfo;
import com.backend.project.system.domain.excelVo.OrderInfoExcel;
import com.backend.project.system.enums.EnableEnum;
import com.backend.project.system.enums.OrderStatusEnum;
import com.backend.project.system.mapper.OrderInfoMapper;
import com.backend.project.system.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author
 * @date
 */
@Service
@Slf4j
public class OrderInfoServiceImpl implements IOrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo) {
        return orderInfoMapper.selectOrderInfoList(orderInfo);
    }

    /**
     * 订单数据导出
     * @param orderInfo
     * @return
     */
    @Override
    public List<OrderInfoExcel> selectOrderInfoListForExcel(OrderInfo orderInfo) {
        List<OrderInfoExcel> orderInfoExcels = orderInfoMapper.selectOrderInfoListForExcel(orderInfo);
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            for (OrderInfoExcel order : orderInfoExcels) {
                order.setCardPwd(StringUtils.getStringMiddleHide(order.getCardPwd()));
            }
        }
        return orderInfoExcels;
    }

    @Override
    public OrderInfo selectOrderInfoById(Long id) {
        return orderInfoMapper.selectOrderInfoById(id);
    }

    /**
     * 处理预支付订单存储
     *
     * @param deviceId 设备id
     * @param data 预支付订单数据
     */
    @Override
    public void savePreOrder(Long deviceId, String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String device = jsonObject.getString("device");
        String wwAccount = jsonObject.getString("wwAccount");
        String productLink = jsonObject.getString("productLink");
        Double amount = jsonObject.getDouble("amount");
        String preLink = jsonObject.getString("preLink");
        String preOrderNo = jsonObject.getString("preOrderNo");
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setDevice(device);
        orderInfo.setDeviceId(deviceId);
        orderInfo.setWwAccount(wwAccount);
        orderInfo.setProductLink(productLink);
        orderInfo.setAmount(amount);
        orderInfo.setPreLink(preLink);
        orderInfo.setPreOrderNo(preOrderNo);
        orderInfo.setCreateTime(System.currentTimeMillis());
        int result = orderInfoMapper.insertOrderInfo(orderInfo);
        if (result < 1) {
            log.error("savePreOrder error, deviceId={}, data={}", deviceId, data);
        }
    }

    /**
     * 处理订单确认支付
     *
     * @param data 订单确认支付数据
     * @return
     */
    @Override
    public void orderConfirm(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        // 支付状态
        Integer status = jsonObject.getInteger("status");
        String orderNo = jsonObject.getString("orderNo");
        String preOrderNo = jsonObject.getString("preOrderNo");
        // 核实未支付 0
        if (status == EnableEnum.close.getValue()) {
            orderInfoMapper.updateStatus(preOrderNo, OrderStatusEnum.unpaid.getStatus());
        } else {
            // 已支付 1
            orderInfoMapper.updateStatus(preOrderNo, OrderStatusEnum.confirm.getStatus());
            OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderNo, preOrderNo);
            String paymentName = jsonObject.getString("paymentName");
            String cardAccount = jsonObject.getString("cardAccount");
            String cardPwd = jsonObject.getString("cardPwd");
            orderInfo.setPaymentName(paymentName);
            orderInfo.setCardAccount(cardAccount);
            orderInfo.setCardPwd(cardPwd);
            orderInfo.setPaymentTime(System.currentTimeMillis());
            orderInfoMapper.updateOrderInfo(orderInfo);
        }
    }

    @Override
    public int insertOrderInfo(OrderInfo orderInfo) {
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setCreateBy(SecurityUtils.getUsername());
        return orderInfoMapper.insertOrderInfo(orderInfo);
    }

    @Override
    public int updateOrderInfo(OrderInfo orderInfo) {
        orderInfo.setUpdateTime(System.currentTimeMillis());
        orderInfo.setUpdateBy(SecurityUtils.getUsername());
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

}

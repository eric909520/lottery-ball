package com.backend.project.system.service;

import com.backend.project.system.domain.OrderInfo;
import com.backend.project.system.domain.excelVo.OrderInfoExcel;

import java.util.List;

/**
 * 订单Service接口
 *
 * @author
 * @date 
 */
public interface IOrderInfoService {

    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 订单数据导出
     * @param orderInfo
     * @return
     */
    public List<OrderInfoExcel> selectOrderInfoListForExcel(OrderInfo orderInfo);

    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 处理预支付订单存储
     *
     * @param deviceId 设备id
     * @param data 预支付订单数据
     * @return
     */
    public void savePreOrder(Long deviceId, String data);

    /**
     * 处理订单确认支付
     *
     * @param data 订单确认支付数据
     * @return
     */
    public void orderConfirm(String data);

    public int insertOrderInfo(OrderInfo orderInfo);

    public int updateOrderInfo(OrderInfo orderInfo);

}

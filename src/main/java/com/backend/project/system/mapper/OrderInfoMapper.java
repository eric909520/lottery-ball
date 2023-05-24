package com.backend.project.system.mapper;

import com.backend.project.system.domain.OrderInfo;
import com.backend.project.system.domain.excelVo.OrderInfoExcel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author
 * @date
 */
public interface OrderInfoMapper {
    /**
     * 查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 查询订单
     *
     * @param orderInfo
     * @return 订单
     */
    public OrderInfo selectOrderInfo(OrderInfo orderInfo);

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 三方订单号
     * @param preOrderNo 淘宝订单号
     * @return 订单
     */
    public OrderInfo selectByOrderNo(@Param("orderNo") String orderNo, @Param("preOrderNo") String preOrderNo);

    /**
     * 查询订单列表
     *
     * @param orderInfo 订单
     * @return 订单集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 订单数据导出
     *
     * @param orderInfo 订单
     * @return 订单集合
     */
    public List<OrderInfoExcel> selectOrderInfoListForExcel(OrderInfo orderInfo);

    /**
     * 新增订单
     *
     * @param orderInfo 订单
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单
     *
     * @param orderInfo 订单
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 更新三方订单号
     *
     * @param id
     * @param orderNo 订单号
     * @return 结果
     */
    public int updateOrderNo(@Param("orderNo") String orderNo, @Param("id") Long id);

    /**
     * 更新订单状态
     *
     * @param preOrderNo 订单号
     * @param status 订单状态
     * @return 结果
     */
    public int updateStatus(@Param("preOrderNo") String preOrderNo, @Param("status") Integer status);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);

    /**
     * 查询订单金额对应未使用的预支付订单数量
     * @param amount
     * @return
     */
    public int selectPreOrderUnused(String amount);

    /**
     * 查询订单金额对应未使用的预支付订单数量
     * @param amount
     * @return
     */
    public OrderInfo selectPreOrder(Double amount);

}

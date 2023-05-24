package com.backend.project.system.mapper;

import com.backend.project.system.domain.TaskPreOrder;

import java.util.List;

/**
 * 预支付订单任务Mapper接口
 *
 * @author
 * @date
 */
public interface TaskPreOrderMapper {
    /**
     * 查询预支付订单任务
     *
     * @param id 预支付订单任务ID
     * @return 预支付订单任务
     */
    public TaskPreOrder selectTaskPreOrderById(Long id);

    /**
     * 查询预支付订单任务
     *
     * @param mobile
     * @return 预支付订单任务
     */
    public TaskPreOrder selectTaskPreOrderByDevice(String mobile);

    /**
     * 查询预支付订单任务列表
     *
     * @param taskPreOrder 预支付订单任务
     * @return 预支付订单任务集合
     */
    public List<TaskPreOrder> selectTaskPreOrderList(TaskPreOrder taskPreOrder);

    /**
     * 新增预支付订单任务
     *
     * @param taskPreOrder 预支付订单任务
     * @return 结果
     */
    public int insertTaskPreOrder(TaskPreOrder taskPreOrder);

    /**
     * 修改预支付订单任务
     *
     * @param taskPreOrder 预支付订单任务
     * @return 结果
     */
    public int updateTaskPreOrder(TaskPreOrder taskPreOrder);

}

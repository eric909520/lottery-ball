package com.backend.project.system.service;

import com.backend.project.system.domain.AppConfig;

import java.util.List;
import java.util.Map;

/**
 * 系统配置Service接口
 *
 * @author
 * @date 2020-06-24
 */
public interface ILotteryService {

    /**
     * 近期x小时内数据预测
     * @param hour
     * @return
     * @throws Exception
     */
    public List<Map.Entry<String, Integer>> getNumbers(int hour) throws Exception;

    /**
     * 近期x注数据预测 - 横向
     * @param limit
     * @return
     * @throws Exception
     */
    public List<Map.Entry<String, Integer>> getNumbers1(int limit) throws Exception;

    /**
     * 近期x注数据预测 - 竖向
     * @param limit
     * @return
     * @throws Exception
     */
    public List<String> getNumbers2(int limit) throws Exception;

}

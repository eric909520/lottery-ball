package com.backend.project.system.service;

/**
 *  体彩数据获取
 */
public interface ISportsBettingDataService {

    /**
     *  获取体彩足球数据
     */
    void  getSportsBettingFBData();

    /**
     * 获取体彩篮球数据
     */
    void  getSportsBettingBKData();
}

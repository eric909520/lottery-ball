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

    /**
     * 将投注中的记录复制到投注表 并把投注状态设置成投注中
     */
    void insertBetSPMatchInfo(Integer matchNum,String matchDate);

    /**
     *  清理当天已经过期的数据
     */
    void cleanObsoleteData();
}

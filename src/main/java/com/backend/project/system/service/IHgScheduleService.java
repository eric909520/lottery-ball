package com.backend.project.system.service;

public interface IHgScheduleService {

    /**
     * 今日足球数据
     * polling today football data
     */
    public void pollingFootballDataToday();

    /**
     * 早盘足球数据
     * polling early football data
     */
    public void pollingFootballDataEarly();

    /**
     * hg - sp 数据计算
     */
    public void hedge_Hg_SP_data();


}

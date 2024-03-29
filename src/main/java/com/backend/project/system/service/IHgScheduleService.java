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
     * hg - sp 数据计算 - 单关
     */
    public void hedge_Hg_SP_data_single();

    /**
     * hg - sp 数据计算 - 012
     */
    public void hedge_Hg_SP_data_012();

    /**
     * hg - sp 数据计算 - 单关 - zhuang
     */
    public void hedge_Hg_SP_data_single_dealer();

    /**
     * hg - sp 数据计算 - 012 - zhuang
     */
    public void hedge_Hg_SP_data_012_dealer();

    /**
     * 今日足球数据 - AG
     * polling today football data
     */
    public void pollingFootballDataToday_All();

    /**
     * 早盘足球数据 - AG
     * polling early football data
     */
    public void pollingFootballDataEarly_All();

    /**
     * hg - ag 数据计算 - 单关
     */
    public void hedge_Hg_Ag_data_single();

    /**
     * hg - ag 数据计算 - 012
     */
    public void hedge_Hg_Ag_data_012();

}

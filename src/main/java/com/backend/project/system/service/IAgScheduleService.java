package com.backend.project.system.service;

public interface IAgScheduleService {

    /**
     * 今日足球联赛数据
     * polling today football league data
     */
    public void pollingTodayFootballLeagueData();

    /**
     * 早盘足球联赛数据
     * polling early football league data
     */
    public void pollingEarlyFootballLeagueData();

    /**
     * 球赛开始 更新状态 - 滚球
     */
    public void rollStatus();

    /**
     * 球赛结束
     */
    public void finishStatus();

}

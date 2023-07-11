package com.backend.project.system.mapper;

import com.backend.project.system.domain.BetSPMatchInfo;

import java.util.List;

/**
 * 正在投注的比赛信息
 */
public interface BetSPMatchInfoMapper {

    /**
     * 查询当前有没有正在投注中的足球⚽️比赛
     * @return
     */
    List<BetSPMatchInfo> findBettingRecord();

    void insertBetSPMatchInfo(BetSPMatchInfo betSPMatchInfo);

    /**
     * 更新最后通知时间
     * @param id
     */
    void updateNotifyTime(Long id,Long timeStamp);

    int updateBetMatchInfo(BetSPMatchInfo betSPMatchInfo);


}

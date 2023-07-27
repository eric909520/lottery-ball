package com.backend.project.system.mapper;

import com.backend.project.system.domain.AgFbLeagueData;

/**
 *
 * @author
 */
public interface AgFbLeagueDataMapper {

    public Integer selectByMatchId(String matchId);

    public int insertData(AgFbLeagueData agFbLeagueData);

    /**
     * 球赛开始 更新状态 - 滚球
     */
    public int rollStatus(Long currentTime);

    /**
     * 球赛结束
     */
    public int finishStatus(Long currentTime);

}

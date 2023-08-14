package com.backend.project.system.mapper;

import com.backend.project.system.domain.AgLeagueData;

import java.util.List;

/**
 *
 * @author
 */
public interface AgLeagueDataMapper {

    public List<AgLeagueData> selectAll();

    public Integer selectByMatchId(String matchId);

    public List<AgLeagueData> selectNormalList();

    public int insertData(AgLeagueData agLeagueData);

    /**
     * 球赛开始 更新状态 - 滚球
     */
    public int rollStatus(Long currentTime);

    /**
     * 球赛结束
     */
    public int finishStatus(Long currentTime);

}

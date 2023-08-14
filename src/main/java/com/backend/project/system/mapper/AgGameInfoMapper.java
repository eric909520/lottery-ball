package com.backend.project.system.mapper;

import com.backend.project.system.domain.AgGameInfo;

/**
 */
public interface AgGameInfoMapper {

    public Integer selectExisit(String matchId);

    public AgGameInfo selectByMatchId(String matchId);

    public int insertData(AgGameInfo agGameInfo);

    public int updateData(AgGameInfo agGameInfo);

}

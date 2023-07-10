package com.backend.project.system.mapper;

import com.backend.project.system.domain.HgFbLeagueData;
import io.lettuce.core.dynamic.annotation.Param;

/**
 *
 * @author
 */
public interface HgFbLeagueDataMapper {
    public Integer selectExist(@Param("leagueId") String leagueId, @Param("ecid") String ecid);

    public HgFbLeagueData selectBySpId(Long spId);

    /**
     */
    public int insertData(HgFbLeagueData hgFbLeagueData);

}

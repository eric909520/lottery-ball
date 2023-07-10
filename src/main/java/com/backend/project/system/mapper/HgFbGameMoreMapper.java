package com.backend.project.system.mapper;

import com.backend.project.system.domain.HgFbGameMore;
import io.lettuce.core.dynamic.annotation.Param;

/**
 *
 * @author
 */
public interface HgFbGameMoreMapper {

    public Integer selectExist(@Param("leagueId") String leagueId, @Param("ecid") String ecid);

    public HgFbGameMore selectCondition(@Param("leagueId") String leagueId, @Param("ecid") String ecid);

    /**
     * 写入数据
     */
    public int insertData(HgFbGameMore hgFbGameMore);

    public int updateData(HgFbGameMore hgFbGameMore);

}

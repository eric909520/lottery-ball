package com.backend.project.system.domain;

import lombok.Data;

/**
 * football 联赛数据
 * @author
 */
@Data
public class FbLeagueData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String regionName;

    private String regionSortName;

    private String leagueName;

    private String leagueSortName;

    private String leagueId;

    private String ecid;

    private String ecTime;

    private Long ecTimestamp;

    private String teamH;

    private String teamHId;

    private String teamC;

    private String teamCId;

}

package com.backend.project.system.domain;

import lombok.Data;

/**
 * hg football 联赛数据
 * @author
 */
@Data
public class HgFbLeagueData extends BaseEntity {
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

    public HgFbLeagueData() {}

    public HgFbLeagueData(String regionName, String regionSortName, String leagueName, String leagueSortName
            , String leagueId, String ecid, String ecTime, Long ecTimestamp, String teamH, String teamHId, String teamC
            , String teamCId) {
        this.regionName = regionName;
        this.regionSortName = regionSortName;
        this.leagueName = leagueName;
        this.leagueSortName = leagueSortName;
        this.leagueId = leagueId;
        this.ecid = ecid;
        this.ecTime = ecTime;
        this.ecTimestamp = ecTimestamp;
        this.teamH = teamH;
        this.teamHId = teamHId;
        this.teamC = teamC;
        this.teamCId = teamCId;

    }

}

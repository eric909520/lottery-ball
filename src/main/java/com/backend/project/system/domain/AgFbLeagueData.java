package com.backend.project.system.domain;

import lombok.Data;

/**
 * ag football 联赛数据
 * @author
 */
@Data
public class AgFbLeagueData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer sportType;

    private String leagueName;

    private String leagueId;

    private String matchId;

    private String kickoffTime;

    private Long kickoffTimestamp;

    private String teamH;

    private String teamHId;

    private String teamA;

    private String teamAId;

    private Integer status;

    public AgFbLeagueData() {}

    public AgFbLeagueData(Integer sportType, String leagueName, String leagueId, String matchId, String kickoffTime
            , Long kickoffTimestamp, String teamH, String teamHId, String teamA, String teamAId) {
        this.sportType = sportType;
        this.leagueName = leagueName;
        this.leagueId = leagueId;
        this.matchId = matchId;
        this.kickoffTime = kickoffTime;
        this.kickoffTimestamp = kickoffTimestamp;
        this.teamH = teamH;
        this.teamHId = teamHId;
        this.teamA = teamA;
        this.teamAId = teamAId;
    }

}

package com.backend.project.system.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class SubMatchList {
    /** 比赛日期*/
    private String matchDate;

    private String matchWeek;
    /**客队名称 */
    private String awayTeamAbbName;

    private int bettingAllUp;
//    private String homeTeamAllName;
    /**客队胜平负赔率*/
    private Had had;
    /**主队赔率列表*/
    private List<OddsList> oddsList;

    private List<PoolList> poolList;

    /**足球总进球赔率数据*/
    private Ttg ttg;

    private Date businessDate;

    private String matchStatus;
    /**客队 让球/受让球赔率 */
    private Hhad hhad;
//    private int baseAwayTeamId;

    private int leagueId;
//    private String awayRank;
    private String leagueAllName;
//    private int homeTeamId;

    private int bettingSingle;
//    private long matchId;

    private String matchNumStr;
//    private String matchName;
    /**主队名称 */
    private String homeTeamAbbName;
    /**比赛时间*/
    private String matchTime;
    /** 比赛编号  例如 周1 001  1001*/
    private int matchNum;
    /** 比赛状态  Selling:销售中 */
    private int sellStatus;
    /**联赛名称*/
    private String leagueAbbName;

    private int baseHomeTeamId;

    private String lineNum;

    private int isHot;

    /**
     * 篮球数据
     */
    /**让分胜负数据*/
    private Hdc hdc;
    /**大小分数据*/
    private Hilo hilo;
    /**胜负*/
    private Mnl mnl;

}

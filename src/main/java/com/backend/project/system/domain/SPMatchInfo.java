package com.backend.project.system.domain;

import lombok.Data;

import java.io.Serializable;


/**
 * 足球比赛信息尸体 sp_match_info
 */
@Data
public class SPMatchInfo implements Serializable {

    private Integer id;
    // 比赛编号 例 周一001就是1001
    private int matchNum;
    // 联赛名称
    private String leagueAbbName;
    //比赛开始日期
    private String matchDate;
    //比赛开始时间
    private String matchTime;
    //主队名
    private String homeTeamAbbName;
    //客队名
    private String awaTeamAbbName;
    //主胜
    private String win;
    //主平
    private String draw;
    //主负
    private String lose;
    //主让球/受让球数
    private String handicap;
    //主让球胜
    private String handicapWin;
    //主让球平
    private String handicapDraw;
    //主让球负
    private String handicapLose;
    //总进球赔率
    private String s0;

    private String s1;

    private String s2;

    private String s3;

    private String s4;
    //5球赔率
    private String s5;
    //6球赔率
    private String s6;
    //7球赔率
    private String s7;
    //创建时间
    private Long createTime;

    // 精确时间
    private String exactDate;






}

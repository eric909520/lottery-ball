package com.backend.project.system.domain;

import lombok.Data;

import java.io.Serializable;


/**
 * 足球比赛信息尸体 sp_match_info
 */
@Data
public class BetSPMatchInfo implements Serializable {

    private Long id;

    private Long spId;

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
    //是否正在投注状态 0否 1是  默认0
    private String isBetting;

    // 投注组合类型 BetTypeEnum
    private String betType;

    // 皇冠投注金额
    private Double hgAmount;

    // 体彩投注金额 - 单关
    private Double spAmount;

    // 体彩投注金额 - 0球
    private Double spAmount0;

    // 体彩投注金额 - 1球
    private Double spAmount1;

    // 体彩投注金额 - 2球
    private Double spAmount2;

    // 体彩投注金额 - 3球
    private Double spAmount3;

    // 体彩投注金额 - 4球
    private Double spAmount4;

    // 体彩投注金额 - 5球
    private Double spAmount5;

    // 体彩投注金额 - 6球
    private Double spAmount6;

    // 体彩投注金额 - 7+球
    private Double spAmount7;

    // 皇冠赔率1
    private Double hgOdds1;

    // 皇冠赔率2
    private Double hgOdds2;

}

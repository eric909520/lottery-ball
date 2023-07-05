package com.backend.project.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 体彩篮球数据
 */
@Data
public class SPBKMatchInfo implements Serializable {
    //比赛编号
    private Integer matchNum;
    //比赛日期
    private String matchDate;
    //比赛时间
    private String matchTime;
    //主队名
    private String homeTeamAbbName;
    //客队名
    private String awayTeamAbbName;
    //主胜
    private String win;
    //主负
    private String lose;
    //主队让分/被让分数
    private String handicap;
    //主队让分/被让分数 胜
    private String handicapWin;
    //主队让分/被让分数 负
    private String handicapLose;
    //大小分总分
    private String score;
    //大于总分
    private String high;
    //小于总分
    private String low;

    private Long createTime;




}

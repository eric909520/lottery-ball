package com.backend.project.system.domain.vo;

import lombok.Data;

/**
 * 篮球投注vo
 */
@Data
public class BetBasketballParamVo extends BetBKParamVo {

    private Double betBaseAmount;

    // 体彩主胜
    private Double oddsWin;

    // 体彩客胜
    private Double oddsLose;

    // 皇冠主胜
    private Double homeWin;

    // 皇冠客胜
    private Double visitWin;

    //体彩大分
    private Double oddsBig;

    //体彩小分
    private Double oddsSmall;

    //hg大分 - 体彩基准减1，体彩基准135，皇冠大134
    private Double hgBigCut1;

    //hg小分 - 体彩基准加1，体彩基准135，皇冠小136
    private Double hgSmallAdd1;

}

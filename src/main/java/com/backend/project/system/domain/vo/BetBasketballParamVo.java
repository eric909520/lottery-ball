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

}

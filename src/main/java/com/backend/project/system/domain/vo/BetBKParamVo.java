package com.backend.project.system.domain.vo;

import lombok.Data;

/**
 * bk 让分vo
 *
 */
@Data
public class BetBKParamVo {

    private Double betBaseAmount;
    //主加主胜
    private Double oddsAddWin;

    //主加主负
    private Double oddsAddLose;

    //主加客胜
    private Double visitAddWin;

    //主加客负
    private Double visitAddLose;

    //主减主胜
    private Double homeSubWin;

    //主减主负
    private Double homeSUbLose;

    //主减客胜
    private Double visitorSubWin;

    //主减客负
    private Double visitorSubLose;

}

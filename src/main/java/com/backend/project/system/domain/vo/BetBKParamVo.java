package com.backend.project.system.domain.vo;

import lombok.Data;

/**
 * bk 让分vo
 *
 */
@Data
public class BetBKParamVo {

    private Double betBaseAmount;
    //体彩主胜赔率
    private Double oddsWin;
    //体彩让分/被让分数
    private Double oddsHandicap;
    //体彩主负赔率
    private Double oddsLose;

    //皇冠主胜赔率
    private Double visitWin;
    //皇冠让分/被让分数
    private Double visitHandicap;
    //皇冠主负赔率
    private Double visitLose;


}

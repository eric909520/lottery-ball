package com.backend.project.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 投注参数信息vo类
 */
@Data
public class BetParamVo extends BetHGVo implements Serializable {

    /**
     * 投注金额基数
     */
    private Double betBaseAmount;

    private Double betAmountZero;

    private Double oddsZero = 0d;

    private Double reward0;

    private Double betAmountOne;

    private Double oddsOne;

    private Double reward1;

    private Double betAmountTwo;

    private Double oddsTwo;

    private Double reward2;

    private Double betAmountThree;

    private Double oddsThree;

    private Double reward3;

    private Double betAmountFour;

    private Double oddsFour = 0d;

    private Double reward4;

    private Double betAmountFive;

    private Double oddsFive;

    private Double reward5;

    private Double betAmountSix;

    private Double oddsSix;

    private Double reward6;

    private Double betAmountSeven;

    private Double oddsSeven;

    private Double reward7;

    private Double betAmountHg;

    private Double oddsHg;

    private Double rewardHG;

    /**
     * 单关参数 - 体彩
     */
    // 胜
    private Double betAmountWin;

    private Double oddsWin;

    // 平
    private Double betAmountTie;

    private Double oddsTie;

    // 负
    private Double betAmountLose;

    private Double oddsLose;

    // 让胜 体彩：主队减 胜
    private Double betAmountRangWin;

    private Double oddsRangWin;

    // 让负 体彩：主队减 负
    private Double betAmountRangLose;

    private Double oddsRangLose;

    // 受让胜 体彩：主队加 胜
    private Double betAmountShouWin;

    private Double oddsShouWin;

    // 受让负 体彩：主队加 负
    private Double betAmountShouLose;

    private Double oddsShouLose;

}

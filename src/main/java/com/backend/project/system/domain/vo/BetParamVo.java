package com.backend.project.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 投注参数信息vo类
 */
@Data
public class BetParamVo implements Serializable {

    /**
     * 投注金额基数
     */
    private Double betBaseAmount;

    private Double betAmountZero;

    private Double oddsZero;

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

    private Double oddsFour;

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

    private Double rewardHG3;

    private Double rewardHGOther;

}

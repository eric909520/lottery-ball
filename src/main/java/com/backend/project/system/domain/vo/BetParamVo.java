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
    private Double betBaseAmount = 0D;

    private Double betAmountZero = 0D;

    private Double oddsZero = 0d;

    private Double reward0 = 0D;

    private Double betAmountOne = 0D;

    private Double oddsOne = 0D;

    private Double reward1 = 0D;

    private Double betAmountTwo = 0D;

    private Double oddsTwo = 0D;

    private Double reward2 = 0D;

    private Double betAmountThree = 0D;

    private Double oddsThree = 0D;

    private Double reward3 = 0D;

    private Double betAmountFour = 0D;

    private Double oddsFour = 0d;

    private Double reward4 = 0D;

    private Double betAmountFive = 0D;

    private Double oddsFive = 0D;

    private Double reward5 = 0D;

    private Double betAmountSix = 0D;

    private Double oddsSix = 0D;

    private Double reward6 = 0D;

    private Double betAmountSeven = 0D;

    private Double oddsSeven = 0D;

    private Double reward7 = 0D;

    private Double betAmountHg = 0D;

    private Double oddsHg = 0D;

    private Double rewardHG = 0D;

    /**
     * 单关参数 - 体彩
     */
    // 胜
    private Double betAmountWin = 0D;

    private Double oddsWin = 0D;

    // 平
    private Double betAmountTie = 0D;

    private Double oddsTie = 0D;

    // 负
    private Double betAmountLose = 0D;

    private Double oddsLose = 0D;

    // 让胜 体彩：主队减 胜
    private Double betAmountRangWin = 0D;

    private Double oddsRangWin = 0D;

    // 让负 体彩：主队减 负
    private Double betAmountRangLose = 0D;

    private Double oddsRangLose = 0D;

    // 受让胜 体彩：主队加 胜
    private Double betAmountShouWin = 0D;

    private Double oddsShouWin = 0D;

    // 受让负 体彩：主队加 负
    private Double betAmountShouLose = 0D;

    private Double oddsShouLose = 0D;

    /**
     * 通知消息
     */
    private String msg;

    // 体彩id
    private Long spId;

}

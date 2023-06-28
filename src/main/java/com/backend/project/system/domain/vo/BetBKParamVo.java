package com.backend.project.system.domain.vo;

import lombok.Data;

/**
 * bk 让分vo
 *
 */
@Data
public class BetBKParamVo {

    // 体彩：主加主胜
    private Double oddsAddWin;

    // 体彩：主加客胜
    private Double oddsAddLose;

    // 体彩：主减主胜
    private Double oddsCutWin;

    // 体彩：主减客胜
    private Double oddsCutLose;

    // 皇冠：主加
    private Double homeAdd;

    // 皇冠：客减
    private Double visitCut;

    // 皇冠：主减
    private Double homeCut;

    // 皇冠：客加
    private Double visitAdd;

}

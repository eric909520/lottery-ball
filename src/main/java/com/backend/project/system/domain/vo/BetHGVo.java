package com.backend.project.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 投注接口参数信息vo类
 */
@Data
public class BetHGVo implements Serializable {

    /**
     * 体彩小，皇冠大
     */
    // 大1.5赔率
    private Double 大15;

    private Double 大15Amount;

    // 大2赔率
    private Double 大2;

    private Double 大2Amount;

    // 大2.5赔率
    private Double 大25;

    private Double 大25Amount;

    // 大3赔率
    private Double 大3;

    private Double 大3Amount;

    // 大3.5赔率
    private Double 大35;

    private Double 大35Amount;

    // 大1.5/2赔率
    private Double 大15_2;

    private Double 大15_2Amount;

    // 大2/2.5赔率
    private Double 大2_25;

    private Double 大2_25Amount;

    // 大2.5/3赔率
    private Double 大25_3;

    private Double 大25_3Amount;

    // 大3/3.5赔率
    private Double 大3_35;

    private Double 大3_35Amount;

    /**
     * 体彩大，皇冠小
     */
    // 小3.5赔率
    private Double 小35;

    private Double 小35Amount;

    // 小3.5/4赔率
    private Double 小35_4;

    private Double 小35_4Amount;

    /**
     * 单关参数
     */
    /** 让球赔率 */
    // 主队加
    private Double homeAdd05;

    private Double homeAdd05Amount;

    // 主队减
    private Double homeCut05;

    private Double homeCut05Amount;

    // 客队加
    private Double visitAdd05;

    private Double visitAdd05Amount;

    // 客队减
    private Double visitCut05;

    private Double visitCut05Amount;

    // 主队胜
    private Double home;

    private Double homeAmount;

    // 和局
    private Double tie;

    private Double tieAmount;

    // 客队胜
    private Double visit;

    private Double visitAmount;

}

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
    private Double 大15 = 0D;

    private Double 大15Amount = 0D;

    // 大2赔率
    private Double 大2 = 0D;

    private Double 大2Amount = 0D;

    // 大2.5赔率
    private Double 大25 = 0D;

    private Double 大25Amount = 0D;

    // 大3赔率
    private Double 大3 = 0D;

    private Double 大3Amount = 0D;

    // 大3.5赔率
    private Double 大35 = 0D;

    private Double 大35Amount = 0D;

    // 大1.5/2赔率
    private Double 大15_2 = 0D;

    private Double 大15_2Amount = 0D;

    // 大2/2.5赔率
    private Double 大2_25 = 0D;

    private Double 大2_25Amount = 0D;

    // 大2.5/3赔率
    private Double 大25_3 = 0D;

    private Double 大25_3Amount = 0D;

    // 大3/3.5赔率
    private Double 大3_35 = 0D;

    private Double 大3_35Amount = 0D;

    /**
     * 体彩大，皇冠小
     */
    // 小2.5赔率
    private Double 小25 = 0D;

    private Double 小25Amount = 0D;

    // 小3.5赔率
    private Double 小35 = 0D;

    private Double 小35Amount = 0D;

    // 小2/2.5赔率
    private Double 小2_25 = 0D;

    private Double 小2_25Amount = 0D;

    // 小2.5/3赔率
    private Double 小25_3 = 0D;

    private Double 小25_3Amount = 0D;

    // 小3/3.5赔率
    private Double 小3_35 = 0D;

    private Double 小3_35Amount = 0D;

    // 小3.5/4赔率
    private Double 小35_4 = 0D;

    private Double 小35_4Amount = 0D;

    // 总进球 0-1
    private Double zong0_1 = 0D;

    private Double zong0_1Amount = 0D;

    private Double rewardZong0_1 = 0D;

    // 总进球 2-3
    private Double zong2_3 = 0D;

    private Double zong2_3Amount = 0D;

    private Double rewardZong2_3 = 0D;

    // 总进球 4-6
    private Double zong4_6 = 0D;

    private Double zong4_6Amount = 0D;

    private Double rewardZong4_6 = 0D;

    // 总进球 7+
    private Double zong7 = 0D;

    private Double zong7Amount = 0D;

    private Double rewardZong7 = 0D;

    /**
     * 单关参数
     */
    /** 让球赔率 */
    // 主队加
    private Double homeAdd05 = 0D;

    private Double homeAdd05Amount = 0D;

    // 主队减
    private Double homeCut05 = 0D;

    private Double homeCut05Amount = 0D;

    // 客队加
    private Double visitAdd05 = 0D;

    private Double visitAdd05Amount = 0D;

    // 客队减
    private Double visitCut05 = 0D;

    private Double visitCut05Amount = 0D;

    // 主队胜
    private Double home = 0D;

    private Double homeAmount = 0D;

    // 和局
    private Double tie = 0D;

    private Double tieAmount = 0D;

    // 客队胜
    private Double visit = 0D;

    private Double visitAmount = 0D;

}

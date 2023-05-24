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
    // 大2赔率
    private Double 大2;

    // 大2.5赔率
    private Double 大25;

    // 大3.5赔率
    private Double 大35;

    // 大2/2.5赔率
    private Double 大2_25;

    // 大2.5/4赔率
    private Double 大25_3;

    // 大3/3.5赔率
    private Double 大3_35;

    /**
     * 体彩大，皇冠小
     */
    // 小3.5赔率
    private Double 小35;

    // 小3.5/4赔率
    private Double 小35_4;

}

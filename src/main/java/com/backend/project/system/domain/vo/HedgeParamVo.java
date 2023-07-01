package com.backend.project.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 参数信息vo类
 */
@Data
public class HedgeParamVo implements Serializable {

    private Double baseAmount;

    private Double oddsHg;

    private Double oddsBet365;

}

package com.backend.project.system.domain;

import lombok.Data;

/**
 * predict_number
 *
 * @author
 */
@Data
public class PredictNumber extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 期数
     */
    private String period;

    private String ten;

    private Integer tenCheck;

    private String twenty;

    private Integer twentyCheck;

    private String thirty;

    private Integer thirtyCheck;

    private String forty;

    private Integer fortyCheck;

    private String fifty;

    private Integer fiftyCheck;

    private String sixty;

    private Integer sixtyCheck;

    private String seventy;

    private Integer seventyCheck;

    private String eighty;

    private Integer eightyCheck;

    private String ninety;

    private Integer ninetyCheck;

    private String oneHundred;

    private Integer oneHundredCheck;

    private String tenCheckSingle;

    private String twentyCheckSingle;

    private String thirtyCheckSingle;

    private String fortyCheckSingle;

    private String fiftyCheckSingle;

    private String sixtyCheckSingle;

    private String seventyCheckSingle;

    private String eightyCheckSingle;

    private String ninetyCheckSingle;

    private String oneHundredCheckSingle;

}

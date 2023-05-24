package com.backend.project.system.domain;

import lombok.Data;

/**
 * number_analyze
 *
 * @author
 */
@Data
public class NumberAnalyze extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 日期
     */
    private String createDate;

    /**
     * 0
     */
    private Integer zero;

    /**
     * 1
     */
    private Integer one;

    /**
     * 2
     */
    private Integer two;

    /**
     * 3
     */
    private Integer three;

    /**
     * 4
     */
    private Integer four;

    /**
     * 5
     */
    private Integer five;

    /**
     * 6
     */
    private Integer six;

    /**
     * 7
     */
    private Integer seven;

    /**
     * 8
     */
    private Integer eight;

    /**
     * 9
     */
    private Integer nine;

    public NumberAnalyze() {

    }

    public NumberAnalyze(Integer zero, Integer one, Integer two, Integer three, Integer four, Integer five, Integer six
            , Integer seven, Integer eight, Integer nine) {
        this.zero = zero;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
    }
}

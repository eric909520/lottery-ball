package com.backend.project.system.domain;

import com.alibaba.fastjson.JSONObject;
import com.backend.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

/**
 * lottery_data
 *
 * @author
 */
@Data
public class LotteryData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 期数
     */
    private Integer preDrawIssue;

    /**
     * 时间
     */
    private String preDrawTime;

    /**
     * 号码
     */
    private String preDrawCode;

    /**
     * 总和
     */
    private Integer sumNum;

    /**
     * 总单双
     */
    private Integer sumSingleDouble;

    /**
     * 总大小
     */
    private Integer sumBigSmall;

    /**
     * 龙虎
     */
    private Integer dragonTiger;

    /**
     * 第一球大小
     */
    private Integer firstBigSmall;

    /**
     * 第一球单双
     */
    private Integer firstSingleDouble;

    /**
     * 第二球大小
     */
    private Integer secondBigSmall;

    /**
     * 第二球单双
     */
    private Integer secondSingleDouble;

    /**
     * 第三球大小
     */
    private Integer thirdBigSmall;

    /**
     * 第三球单双
     */
    private Integer thirdSingleDouble;

    /**
     * 第四球大小
     */
    private Integer fourthBigSmall;

    /**
     * 第四球单双
     */
    private Integer fourthSingleDouble;

    /**
     * 第五球大小
     */
    private Integer fifthBigSmall;

    /**
     * 第五球单双
     */
    private Integer fifthSingleDouble;

    /**
     * 前三
     */
    private Integer behindThree;

    /**
     * 中三
     */
    private Integer betweenThree;

    /**
     * 后三
     */
    private Integer lastThree;

    /**
     * 组合
     */
    private Integer groupCode;

    public LotteryData() {

    }

    public LotteryData (Integer preDrawIssue, String preDrawTime, String preDrawCode, Integer sumNum
            , Integer sumSingleDouble, Integer sumBigSmall, Integer dragonTiger, Integer firstBigSmall
            , Integer firstSingleDouble, Integer secondBigSmall, Integer secondSingleDouble, Integer thirdBigSmall
            , Integer thirdSingleDouble, Integer fourthBigSmall, Integer fourthSingleDouble, Integer fifthBigSmall
            , Integer fifthSingleDouble, Integer behindThree, Integer betweenThree, Integer lastThree
            , Integer groupCode) {
        this.preDrawIssue = preDrawIssue;
        this.preDrawTime = preDrawTime;
        this.preDrawCode = preDrawCode;
        this.sumNum = sumNum;
        this.sumSingleDouble = sumSingleDouble;
        this.sumBigSmall = sumBigSmall;
        this.dragonTiger = dragonTiger;
        this.firstBigSmall = firstBigSmall;
        this.firstSingleDouble = firstSingleDouble;
        this.secondBigSmall = secondBigSmall;
        this.secondSingleDouble = secondSingleDouble;
        this.thirdBigSmall = thirdBigSmall;
        this.thirdSingleDouble = thirdSingleDouble;
        this.fourthBigSmall = fourthBigSmall;
        this.fourthSingleDouble = fourthSingleDouble;
        this.fifthBigSmall = fifthBigSmall;
        this.fifthSingleDouble = fifthSingleDouble;
        this.behindThree = behindThree;
        this.betweenThree = betweenThree;
        this.lastThree = lastThree;
        this.groupCode = groupCode;
    }
}

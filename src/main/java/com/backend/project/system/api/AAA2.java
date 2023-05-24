package com.backend.project.system.api;

import com.backend.common.utils.CalcUtil;

public class AAA2 {
    /**
     * 体彩小 012  皇冠大 大2.5/3
     */
    public static void main(String[] args) {
        // 体彩参数
        // 体彩返水比例
        double rebateSP = 0.12;

        double betAmountZero = 2760;
        double oddsZero = 17;

        double betAmountOne = 7230;
        double oddsOne = 6.5;

        double betAmountTwo = 12360;
        double oddsTwo = 3.8;

        // 皇冠参数
        // 皇冠返水比例
        double rebateHG = 0.024;

        double betAmountLarge = 26700;
        double oddsLarge = 0.76;

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        /** 体彩中球 */
        // 皇冠出奖
        Double rewardHG = CalcUtil.div(betAmountLarge, 2);
        // 皇冠返水
        double rebateHGAmountHalf = CalcUtil.mul(rewardHG, rebateHG);

        double rebateHGAmountAll = CalcUtil.mul(betAmountLarge, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        System.out.println("0球数据++++++++++");
        System.out.println("奖金：" + bonusZero);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward0);
        System.out.println("-----------------------------------------------------------------");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        System.out.println("1球数据++++++++++");
        System.out.println("奖金：" + bonusOne);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward1);
        System.out.println("-----------------------------------------------------------------");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        System.out.println("2球数据++++++++++");
        System.out.println("奖金：" + bonusTwo);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountHalf);
        System.out.println("收益：" + reward2);
        System.out.println("-----------------------------------------------------------------");

        /** 皇冠中球 */
        double bonusLargeHalf = CalcUtil.div(CalcUtil.mul(betAmountLarge, oddsLarge), 2); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount2 = CalcUtil.mul(bonusLargeHalf, rebateHG);
        Double rewardLarge2 = CalcUtil.sub(CalcUtil.add(bonusLargeHalf, rebateSPAmount, rebateHGAmount2), betAmountZero, betAmountOne, betAmountTwo);
        System.out.println("皇冠3数据++++++++++");
        System.out.println("奖金：" + bonusLargeHalf);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount2);
        System.out.println("收益：" + rewardLarge2);
        System.out.println("-----------------------------------------------------------------");

        double bonusLarge = CalcUtil.mul(betAmountLarge, oddsLarge); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusLarge, rebateHG);
        Double rewardLarge = CalcUtil.sub(CalcUtil.add(bonusLarge, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        System.out.println("皇冠4，5，6，7+数据++++++++++");
        System.out.println("奖金：" + bonusLarge);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount1);
        System.out.println("收益：" + rewardLarge);
    }
}

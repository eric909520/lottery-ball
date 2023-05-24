package com.backend.project.system.api;

import com.backend.common.utils.CalcUtil;

public class DDD {
    /**
     * 体彩小 皇冠大 0123 皇冠全输
     */
    public static void main(String[] args) {
        // 体彩参数
        // 体彩返水比例
        double rebateSP = 0.12;

        double betAmountZero = 3294;
        double oddsZero = 17;

        double betAmountOne = 8615;
        double oddsOne = 6.5;

        double betAmountTwo = 14545;
        double oddsTwo = 3.8;

        double betAmountThree = 15342;
        double oddsThree = 3.65;

        // 皇冠参数
        // 皇冠返水比例
        double rebateHG = 0.024;

        double betAmountLarge = 26540;
        double oddsLarge = 1.11;

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountLarge, rebateHG);


        /** 0球数据 */
        double bonusZero = betAmountZero * oddsZero; // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        System.out.println("0球数据++++++++++");
        System.out.println("奖金：" + bonusZero);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward0);
        System.out.println("-----------------------------------------------------------------");

        /** 1球数据 */
        double bonusOne = betAmountOne * oddsOne; // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        System.out.println("1球数据++++++++++");
        System.out.println("奖金：" + bonusOne);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward1);
        System.out.println("-----------------------------------------------------------------");

        /** 2球数据 */
        double bonusTwo = betAmountTwo * oddsTwo; // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        System.out.println("2球数据++++++++++");
        System.out.println("奖金：" + bonusTwo);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward2);
        System.out.println("-----------------------------------------------------------------");

        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        System.out.println("3球数据++++++++++");
        System.out.println("奖金：" + bonusThree);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward3);
        System.out.println("-----------------------------------------------------------------");

        /** 皇冠中球 */
        double bonusLarge = CalcUtil.mul(betAmountLarge, oddsLarge); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = bonusLarge * rebateHG;
        Double rewardLarge = CalcUtil.sub(CalcUtil.add(bonusLarge, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        System.out.println("皇冠中球数据++++++++++");
        System.out.println("奖金：" + bonusLarge);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount1);
        System.out.println("收益：" + rewardLarge);
    }
}

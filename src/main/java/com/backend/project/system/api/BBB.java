package com.backend.project.system.api;

import com.backend.common.utils.CalcUtil;

public class BBB {

    /**
     * 体彩小 0123， 皇冠大 大3/3.5
     */
    public static void main(String[] args) {
        // 体彩参数
        // 体彩返水比例
        double rebateSP = 0.12;

        double betAmountZero = 2545;
        double oddsZero = 22;

        double betAmountOne = 7777;
        double oddsOne = 7.2;

        double betAmountTwo = 13023;
        double oddsTwo = 4.3;

        double betAmountThree = 10135;
        double oddsThree = 3.7;

        // 皇冠参数
        // 皇冠返水比例
        double rebateHG = 0.024;

        double betAmountLarge = 28282;
        double oddsLarge = 0.98;

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        /** 体彩中球 */
        // 皇冠出奖
        Double rewardHG = CalcUtil.div(betAmountLarge, 2);
        // 皇冠返水
        double rebateHGAmountHalf = CalcUtil.mul(rewardHG, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountLarge, rebateHG);


        /** 0球数据 */
        double bonusZero = betAmountZero * oddsZero; // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        System.out.println("0球数据++++++++++");
        System.out.println("奖金：" + bonusZero);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward0);
        System.out.println("-----------------------------------------------------------------");

        /** 1球数据 */
        double bonusOne = betAmountOne * oddsOne; // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        System.out.println("1球数据++++++++++");
        System.out.println("奖金：" + bonusOne);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward1);
        System.out.println("-----------------------------------------------------------------");

        /** 2球数据 */
        double bonusTwo = betAmountTwo * oddsTwo; // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        System.out.println("2球数据++++++++++");
        System.out.println("奖金：" + bonusTwo);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward2);
        System.out.println("-----------------------------------------------------------------");

        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, rewardHG);
        System.out.println("3球数据++++++++++");
        System.out.println("奖金：" + bonusThree);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountHalf);
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

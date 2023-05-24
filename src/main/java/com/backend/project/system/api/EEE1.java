package com.backend.project.system.api;

import com.backend.common.utils.CalcUtil;

public class EEE1 {

    /**
     * 体彩大 皇冠小 3.5/4
     */
    public static void main(String[] args) {
        // 体彩参数
        // 体彩返水比例
        double rebateSP = 0.12;

        double betAmountFour = 9676;
        double oddsFour = 4.25;

        double betAmountFive = 9333;
        double oddsFive = 6;

        double betAmountSix = 5714;
        double oddsSix = 9.8;

        double betAmountSeven = 5090;
        double oddsSeven = 11;

        // 皇冠参数
        // 皇冠返水比例
        double rebateHG = 0.024;

        double betAmountSmall = 29473;
        double oddsSmall = 0.9;

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        /** 体彩中球 */
        // 皇冠返水
        Double rewardHGHalf = CalcUtil.div(betAmountSmall, 2);
        double rebateHGAmountHalf = CalcUtil.mul(rewardHGHalf, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountSmall, rebateHG);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountHalf), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, rewardHGHalf);
        System.out.println("4球数据++++++++++");
        System.out.println("奖金：" + bonusFour);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountHalf);
        System.out.println("收益：" + reward4);
        System.out.println("-----------------------------------------------------------------");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        System.out.println("5球数据++++++++++");
        System.out.println("奖金：" + bonusFive);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward5);
        System.out.println("-----------------------------------------------------------------");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        System.out.println("6球数据++++++++++");
        System.out.println("奖金：" + bonusSix);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward6);
        System.out.println("-----------------------------------------------------------------");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        System.out.println("7球+数据++++++++++");
        System.out.println("奖金：" + bonusSeven);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmountAll);
        System.out.println("收益：" + reward7);
        System.out.println("-----------------------------------------------------------------");

        /** 皇冠中球 */
        double bonusSmall = CalcUtil.mul(betAmountSmall, oddsSmall); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount2 = CalcUtil.mul(bonusSmall, rebateHG);

        Double rewardSmall2 = CalcUtil.sub(CalcUtil.add(bonusSmall, rebateSPAmount, rebateHGAmount2), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        System.out.println("皇冠中球数据++++++++++");
        System.out.println("奖金：" + bonusSmall);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount2);
        System.out.println("收益：" + rewardSmall2);
    }
}

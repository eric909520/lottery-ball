package com.backend.project.system.api;

import com.backend.common.utils.CalcUtil;

public class FFF {
    /**
     * 体彩大 皇冠小 全输
     */
    public static void main(String[] args) {
        // 体彩参数
        // 体彩返水比例
        double rebateSP = 0.12;

        double betAmountFour = 9782;
        double oddsFour = 4.6;

        double betAmountFive = 6000;
        double oddsFive = 7.25;

        double betAmountSix = 3400;
        double oddsSix = 13;

        double betAmountSeven = 2600;
        double oddsSeven = 17;

        // 皇冠参数
        // 皇冠返水比例
        double rebateHG = 0.024;

        double betAmountSmall = 24400;
        double oddsSmall = 1.13;

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountSmall, rebateHG);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        System.out.println("4球数据++++++++++");
        System.out.println("奖金：" + bonusFour);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward4);
        System.out.println("-----------------------------------------------------------------");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        System.out.println("5球数据++++++++++");
        System.out.println("奖金：" + bonusFive);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward5);
        System.out.println("-----------------------------------------------------------------");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        System.out.println("6球数据++++++++++");
        System.out.println("奖金：" + bonusSix);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward6);
        System.out.println("-----------------------------------------------------------------");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        System.out.println("7球+数据++++++++++");
        System.out.println("奖金：" + bonusSeven);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount);
        System.out.println("收益：" + reward7);
        System.out.println("-----------------------------------------------------------------");

        /** 皇冠中球 */
        double bonusSmall = CalcUtil.mul(betAmountSmall, oddsSmall); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusSmall, rebateHG);

        Double rewardSmall = CalcUtil.sub(CalcUtil.add(bonusSmall, rebateSPAmount, rebateHGAmount1), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        System.out.println("皇冠中球数据++++++++++");
        System.out.println("奖金：" + bonusSmall);
        System.out.println("体彩返水：" + rebateSPAmount);
        System.out.println("皇冠返水：" + rebateHGAmount1);
        System.out.println("收益：" + rewardSmall);
    }
}

package com.backend.project.system.service.impl;

import com.backend.common.utils.AdaptationAmount;
import com.backend.common.utils.CalcUtil;
import com.backend.project.system.domain.vo.BetParamVo;
import com.backend.project.system.service.IBallService;
import com.backend.project.system.service.IBallTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author
 */
@Slf4j
@Service
public class BallTempServiceImpl implements IBallTempService {

    // 体彩固定返水比例
    private final static double rebateSP = 0.12;

    // 皇冠固定返水比例
    private final static double rebateHG = 0.024;

    @Override
    public void betCheck(BetParamVo betParamVo) {
        /** 1、大2, 体彩小 012,皇冠 大2, 全输全赢 */
        Double 大2 = betParamVo.get大2();
        if (大2 != null && 大2 != 0) {
            log.info("体彩小 012, 皇冠大2 ------------------------------------------------------");
            betParamVo.setOddsHg(大2);
            betParamVo.setBetAmountHg(betParamVo.get大2Amount());
            SP012_HG大2(betParamVo);
        }

        /** 2、大2.5, 体彩小 012,皇冠 大2.5, 全输全赢 */
        Double 大25 = betParamVo.get大25();
        if (大25 != null && 大25 != 0) {
            log.info("体彩小 012, 皇冠大2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大25);
            betParamVo.setBetAmountHg(betParamVo.get大25Amount());
            SP012_HG大2(betParamVo);
        }

        /** 3、大3.5, 体彩小 0123,皇冠 大3.5, 全输全赢 */
        Double 大35 = betParamVo.get大35();
        if (大35 != null && 大35 != 0) {
            log.info("体彩小 0123, 皇冠大3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大35);
            betParamVo.setBetAmountHg(betParamVo.get大35Amount());
            SP012_HG大35(betParamVo);
        }

        /** 4、大2/2.5, 体彩小 012,皇冠 大2/2.5, 2球皇冠输一半 */
        Double 大2_25 = betParamVo.get大2_25();
        if (大2_25 != null && 大2_25 != 0) {
            log.info("体彩小 012, 皇冠 大2/2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大2_25);
            betParamVo.setBetAmountHg(betParamVo.get大2_25Amount());
            SP012_HG大2_25(betParamVo);
        }

        /** 5、大2.5/3, 体彩小 0123,皇冠 大2.5/3, 3球体彩赢，皇冠赢一半 */
        Double 大25_3 = betParamVo.get大25_3();
        if (大25_3 != null && 大25_3 != 0) {
            log.info("体彩小 0123, 皇冠大2.5/3 ------------------------------------------------------");
            betParamVo.setOddsHg(大25_3);
            betParamVo.setBetAmountHg(betParamVo.get大25_3Amount());
            SP012_HG大25_3(betParamVo);
        }

        /** 6、大3/3.5, 体彩小 0123,皇冠 大3/3.5 ,3球体彩赢，皇冠输一半*/
        Double 大3_35 = betParamVo.get大3_35();
        if (大3_35 != null && 大3_35 != 0) {
            log.info("体彩小 0123, 皇冠大3/3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大3_35);
            betParamVo.setBetAmountHg(betParamVo.get大3_35Amount());
            SP012_HG大3_35(betParamVo);
        }

        /** 7、小3.5, 体彩大 4567+,皇冠 小3.5 */
        Double 小35 = betParamVo.get小35();
        if (小35 != null && 小35 != 0) {
            log.info("体彩大 4567+, 皇冠小3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(小35);
            betParamVo.setBetAmountHg(betParamVo.get小35Amount());
            SP4567_HG小35(betParamVo);
        }

        /** 8、小3.5/4, 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半 */
        Double 小35_4 = betParamVo.get小35_4();
        if (小35_4 != null && 小35_4 != 0) {
            log.info("体彩大 4567+, 皇冠小3.5/4 ------------------------------------------------------");
            betParamVo.setOddsHg(小35_4);
            betParamVo.setBetAmountHg(betParamVo.get小35_4Amount());
            SP4567_HG小35_4(betParamVo);
        }

    }

    /**
     * 大2 || 大2.5
     * 体彩小 012,皇冠 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大2(BetParamVo betParamVo) {
        BetParamVo betParamBase;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamBase = AdaptationAmount.adaptation(betParamVo);
        } else {
            betParamBase = betParamVo;
        }
        // 体彩参数
        double betAmountZero = betParamBase.getBetAmountZero();
        double oddsZero = betParamBase.getOddsZero();

        double betAmountOne = betParamBase.getBetAmountOne();
        double oddsOne = betParamBase.getOddsOne();

        double betAmountTwo = betParamBase.getBetAmountTwo();
        double oddsTwo = betParamBase.getOddsTwo();

        // 皇冠参数
        // 皇冠返水比例
        double betAmountLarge = betParamBase.getBetAmountHg();
        double oddsLarge = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩赔率：0球：" + betParamBase.getOddsZero() + ", 1球：" + betParamBase.getOddsOne()
                + ", 2球：" + betParamBase.getOddsTwo() + ", 皇冠赔率：" + betParamBase.getOddsHg());
        log.info("投注额：0球：" + betParamBase.getBetAmountZero() + ", 1球：" + betParamBase.getBetAmountOne()
                + ", 2球：" + betParamBase.getBetAmountTwo() + ", 皇冠：" + betParamBase.getBetAmountHg());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo())
                + ", 皇冠总投注：" + betParamBase.getBetAmountHg());

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountLarge, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        log.info("0球数据++++++++++");
        log.info("奖金：" + bonusZero);
        log.info("体彩返水：" + rebateSPAmount + ", 皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward0 + ", 收益率：" + CalcUtil.divide(reward0, betAmountAll, 1));

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        log.info("1球数据++++++++++");
        log.info("奖金：" + bonusOne);
        log.info("体彩返水：" + rebateSPAmount + ", 皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward1 + ", 收益率：" + CalcUtil.divide(reward1, betAmountAll, 1));

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        log.info("2球数据++++++++++");
        log.info("奖金：" + bonusTwo);
        log.info("体彩返水：" + rebateSPAmount + ", 皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward2 + ", 收益率：" + CalcUtil.divide(reward2, betAmountAll, 1));

        /** 皇冠中球 */
        double bonusLarge = CalcUtil.mul(betAmountLarge, oddsLarge); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusLarge, rebateHG);
        Double rewardLarge = CalcUtil.sub(CalcUtil.add(bonusLarge, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        log.info("皇冠中球数据++++++++++");
        log.info("奖金：" + bonusLarge);
        log.info("体彩返水：" + rebateSPAmount + ", 皇冠返水：" + rebateHGAmount1);
        log.info("收益：" + rewardLarge + ", 收益率：" + CalcUtil.divide(rewardLarge, betAmountAll, 1));
    }

    /**
     * 大3.5
     * 体彩小 0123,皇冠 大3.5 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大35(BetParamVo betParamVo) {
        BetParamVo betParamBase;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamBase = AdaptationAmount.adaptation(betParamVo);
        } else {
            betParamBase = betParamVo;
        }

        // 体彩参数
        // 体彩返水比例
        double betAmountZero = betParamBase.getBetAmountZero();
        double oddsZero = betParamBase.getOddsZero();

        double betAmountOne = betParamBase.getBetAmountOne();
        double oddsOne = betParamBase.getOddsOne();

        double betAmountTwo = betParamBase.getBetAmountTwo();
        double oddsTwo = betParamBase.getOddsTwo();

        double betAmountThree = betParamBase.getBetAmountThree();
        double oddsThree = betParamBase.getOddsThree();

        // 皇冠参数
        double betAmountLarge = betParamBase.getBetAmountHg();
        double oddsLarge = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩赔率：0球：" + betParamBase.getOddsZero() + ", 1球：" + betParamBase.getOddsOne()
                + ", 2球：" + betParamBase.getOddsTwo() + ", 3球：" + betParamBase.getOddsThree() + ", 皇冠赔率：" + betParamBase.getOddsHg());
        log.info("投注额：0球：" + betParamBase.getBetAmountZero() + ", 1球：" + betParamBase.getBetAmountOne()
                + ", 2球：" + betParamBase.getBetAmountTwo() + ", 3球：" + betParamBase.getBetAmountThree() + ", 皇冠：" + betParamBase.getBetAmountHg());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountThree())
                + ", 皇冠总投注：" + betParamBase.getBetAmountHg());

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountThree(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountLarge, rebateHG);

        /** 0球数据 */
        double bonusZero = betAmountZero * oddsZero; // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward0(reward0);
        log.info("0球数据++++++++++");
        log.info("奖金：" + bonusZero);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward0 + ", 收益率：" + CalcUtil.divide(reward0, betAmountAll, 3));

        /** 1球数据 */
        double bonusOne = betAmountOne * oddsOne; // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward1(reward1);
        log.info("1球数据++++++++++");
        log.info("奖金：" + bonusOne);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward1 + ", 收益率：" + CalcUtil.divide(reward1, betAmountAll, 3));

        /** 2球数据 */
        double bonusTwo = betAmountTwo * oddsTwo; // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward2(reward2);
        log.info("2球数据++++++++++");
        log.info("奖金：" + bonusTwo);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward2 + ", 收益率：" + CalcUtil.divide(reward2, betAmountAll, 3));

        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward3(reward3);
        log.info("3球数据++++++++++");
        log.info("奖金：" + bonusThree);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward3 + ", 收益率：" + CalcUtil.divide(reward3, betAmountAll, 3));

        /** 皇冠中球 */
        double bonusLarge = CalcUtil.mul(betAmountLarge, oddsLarge); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusLarge, rebateHG);
        Double rewardLarge = CalcUtil.sub(CalcUtil.add(bonusLarge, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamBase.setRewardHG(rewardLarge);
        log.info("皇冠中球数据++++++++++");
        log.info("奖金：" + bonusLarge);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount1);
        log.info("收益：" + rewardLarge + ", 收益率：" + CalcUtil.divide(rewardLarge, betAmountAll, 3));
    }

    /**
     * 大3/3.5
     * 体彩小 0123,皇冠 大3/3.5 3球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP012_HG大3_35(BetParamVo betParamVo) {
        BetParamVo betParamBase;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamBase = AdaptationAmount.adaptation(betParamVo);
        } else {
            betParamBase = betParamVo;
        }
        // 体彩参数
        // 体彩返水比例
        double betAmountZero = betParamBase.getBetAmountZero();
        double oddsZero = betParamBase.getOddsZero();

        double betAmountOne = betParamBase.getBetAmountOne();
        double oddsOne = betParamBase.getOddsOne();

        double betAmountTwo = betParamBase.getBetAmountTwo();
        double oddsTwo = betParamBase.getOddsTwo();

        double betAmountThree = betParamBase.getBetAmountThree();
        double oddsThree = betParamBase.getOddsThree();

        // 皇冠参数
        double betAmountLarge = betParamBase.getBetAmountHg();
        double oddsLarge = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩赔率：0球：" + betParamBase.getOddsZero() + ", 1球：" + betParamBase.getOddsOne()
                + ", 2球：" + betParamBase.getOddsTwo() + ", 3球：" + betParamBase.getOddsThree() + ", 皇冠赔率：" + betParamBase.getOddsHg());
        log.info("投注额：0球：" + betParamBase.getBetAmountZero() + ", 1球：" + betParamBase.getBetAmountOne()
                + ", 2球：" + betParamBase.getBetAmountTwo() + ", 3球：" + betParamBase.getBetAmountThree() + ", 皇冠：" + betParamBase.getBetAmountHg());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountThree())
                + ", 皇冠总投注：" + betParamBase.getBetAmountHg());

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountThree(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        Double rewardHg = CalcUtil.div(betAmountLarge, 2);
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountLarge, rebateHG);
        Double rebateHGAmountHalf = CalcUtil.div(rebateHGAmount, 2);


        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward0(reward0);
        log.info("0球数据++++++++++");
        log.info("奖金：" + bonusZero);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward0 + ", 收益率：" + CalcUtil.divide(reward0, betAmountAll, 3));

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward1(reward1);
        log.info("1球数据++++++++++");
        log.info("奖金：" + bonusOne);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward1 + ", 收益率：" + CalcUtil.divide(reward1, betAmountAll, 3));

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward2(reward2);
        log.info("2球数据++++++++++");
        log.info("奖金：" + bonusTwo);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward2 + ", 收益率：" + CalcUtil.divide(reward2, betAmountAll, 3));

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, rewardHg);
        betParamBase.setReward3(reward3);
        log.info("3球数据++++++++++");
        log.info("奖金：" + bonusThree);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward3 + ", 收益率：" + CalcUtil.divide(reward3, betAmountAll, 3));

        /** 皇冠中球 */
        double bonusLarge = CalcUtil.mul(betAmountLarge, oddsLarge); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusLarge, rebateHG);
        Double rewardLarge = CalcUtil.sub(CalcUtil.add(bonusLarge, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamBase.setRewardHG(rewardLarge);
        log.info("皇冠中球数据++++++++++");
        log.info("奖金：" + bonusLarge);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount1);
        log.info("收益：" + rewardLarge + ", 收益率：" + CalcUtil.divide(rewardLarge, betAmountAll, 3));
    }

    /**
     * 大2/2。5
     * 体彩小 012,皇冠 大2/2。5, 2球皇冠输一半
     * @param betParamVo
     */
    public void SP012_HG大2_25(BetParamVo betParamVo) {
        BetParamVo betParamBase;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamBase = AdaptationAmount.adaptation(betParamVo);
        } else {
            betParamBase = betParamVo;
        }
        // 体彩参数
        double betAmountZero = betParamBase.getBetAmountZero();
        double oddsZero = betParamBase.getOddsZero();

        double betAmountOne = betParamBase.getBetAmountOne();
        double oddsOne = betParamBase.getOddsOne();

        double betAmountTwo = betParamBase.getBetAmountTwo();
        double oddsTwo = betParamBase.getOddsTwo();

        // 皇冠参数
        double betAmountLarge = betParamBase.getBetAmountHg();
        double oddsLarge = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩赔率：0球：" + betParamBase.getOddsZero() + ", 1球：" + betParamBase.getOddsOne()
                + ", 2球：" + betParamBase.getOddsTwo() + ", 皇冠赔率：" + betParamBase.getOddsHg());
        log.info("投注额：0球：" + betParamBase.getBetAmountZero() + ", 1球：" + betParamBase.getBetAmountOne()
                + ", 2球：" + betParamBase.getBetAmountTwo() + ", 皇冠：" + betParamBase.getBetAmountHg());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo())
                + ", 皇冠总投注：" + betParamBase.getBetAmountHg());

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠出奖
        Double rewardHG = CalcUtil.div(betAmountLarge, 2);
        // 皇冠返水
        double rebateHGAmountHalf = CalcUtil.mul(rewardHG, rebateHG);

        double rebateHGAmountAll = CalcUtil.mul(betAmountLarge, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        betParamBase.setReward0(reward0);
        log.info("0球数据++++++++++");
        log.info("奖金：" + bonusZero);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward0 + ", 收益率：" + CalcUtil.divide(reward0, betAmountAll, 3));

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        betParamBase.setReward1(reward1);
        log.info("1球数据++++++++++");
        log.info("奖金：" + bonusOne);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward1 + ", 收益率：" + CalcUtil.divide(reward1, betAmountAll, 3));
        
        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, rewardHG);
        betParamBase.setReward2(reward2);
        log.info("2球数据++++++++++");
        log.info("奖金：" + bonusTwo);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountHalf);
        log.info("收益：" + reward2 + ", 收益率：" + CalcUtil.divide(reward2, betAmountAll, 3));
        
        double bonusLarge = CalcUtil.mul(betAmountLarge, oddsLarge); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusLarge, rebateHG);
        Double rewardLarge = CalcUtil.sub(CalcUtil.add(bonusLarge, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        betParamBase.setRewardHG(rewardHG);
        log.info("皇冠3，4，5，6，7+数据++++++++++");
        log.info("奖金：" + bonusLarge);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount1);
        log.info("收益：" + rewardLarge + ", 收益率：" + CalcUtil.divide(rewardLarge, betAmountAll, 3));
    }

    /**
     * 大2。5/3
     * 体彩小 0123,皇冠 大2。5/3, 3球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    public void SP012_HG大25_3(BetParamVo betParamVo) {
        BetParamVo betParamBase;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamBase = AdaptationAmount.adaptation(betParamVo);
        } else {
            betParamBase = betParamVo;
        }
        // 体彩参数
        double betAmountZero = betParamBase.getBetAmountZero();
        double oddsZero = betParamBase.getOddsZero();

        double betAmountOne = betParamBase.getBetAmountOne();
        double oddsOne = betParamBase.getOddsOne();

        double betAmountTwo = betParamBase.getBetAmountTwo();
        double oddsTwo = betParamBase.getOddsTwo();

        double betAmountThree = betParamBase.getBetAmountThree();
        double oddsThree = betParamBase.getOddsThree();

        // 皇冠参数
        double betAmountLarge = betParamBase.getBetAmountHg();
        double oddsLarge = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩赔率：0球：" + betParamBase.getOddsZero() + ", 1球：" + betParamBase.getOddsOne()
                + ", 2球：" + betParamBase.getOddsTwo() + ", 3球：" + betParamBase.getOddsThree() + ", 皇冠赔率：" + betParamBase.getOddsHg());
        log.info("投注额：0球：" + betParamBase.getBetAmountZero() + ", 1球：" + betParamBase.getBetAmountOne()
                + ", 2球：" + betParamBase.getBetAmountTwo() + ", 3球：" + betParamBase.getBetAmountThree() + ", 皇冠：" + betParamBase.getBetAmountHg());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountThree())
                + ", 皇冠总投注：" + betParamBase.getBetAmountHg());

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountThree(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠出奖
        Double rewardHG = CalcUtil.div(CalcUtil.mul(betAmountLarge, oddsLarge), 2);
        // 皇冠返水
        double rebateHGAmountHalf = CalcUtil.mul(rewardHG, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountLarge, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward0(reward0);
        log.info("0球数据++++++++++");
        log.info("奖金：" + bonusZero);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward0 + ", 收益率：" + CalcUtil.divide(reward0, betAmountAll, 3));
        
        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward1(reward1);
        log.info("1球数据++++++++++");
        log.info("奖金：" + bonusOne);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward1 + ", 收益率：" + CalcUtil.divide(reward1, betAmountAll, 3));

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountLarge);
        betParamBase.setReward2(reward2);
        log.info("2球数据++++++++++");
        log.info("奖金：" + bonusTwo);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward2 + ", 收益率：" + CalcUtil.divide(reward2, betAmountAll, 3));
        
        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf, rewardHG), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamBase.setReward3(reward3);
        log.info("3球数据++++++++++");
        log.info("奖金：" + bonusThree);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountHalf);
        log.info("收益：" + reward3 + ", 收益率：" + CalcUtil.divide(reward3, betAmountAll, 3));
        
        /** 皇冠中球 */
        double bonusLarge = CalcUtil.mul(betAmountLarge, oddsLarge); // 奖金
        betParamBase.setRewardHG(bonusLarge);
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusLarge, rebateHG);
        Double rewardLarge = CalcUtil.sub(CalcUtil.add(bonusLarge, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        log.info("皇冠4，5，6，7+数据++++++++++");
        log.info("奖金：" + bonusLarge);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount1);
        log.info("收益：" + rewardLarge + ", 收益率：" + CalcUtil.divide(rewardLarge, betAmountAll, 3));
    }

    /**
     * 小3.5
     * 体彩大 4567+,皇冠 小3.5 全输全赢
     * @param betParamVo
     */
    public void SP4567_HG小35(BetParamVo betParamVo) {
        BetParamVo betParamBase;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamBase = AdaptationAmount.adaptation(betParamVo);
        } else {
            betParamBase = betParamVo;
        }
        // 体彩参数
        double betAmountFour = betParamBase.getBetAmountFour();
        double oddsFour = betParamBase.getOddsFour();

        double betAmountFive = betParamBase.getBetAmountFive();
        double oddsFive = betParamBase.getOddsFive();

        double betAmountSix = betParamBase.getBetAmountSix();
        double oddsSix = betParamBase.getOddsSix();

        double betAmountSeven = betParamBase.getBetAmountSeven();
        double oddsSeven = betParamBase.getOddsSeven();

        // 皇冠参数
        double betAmountSmall = betParamBase.getBetAmountHg();
        double oddsSmall = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩赔率：4球：" + betParamBase.getOddsFour() + ", 5球：" + betParamBase.getOddsFive()
                + ", 6球：" + betParamBase.getOddsSix() + ", 7+球：" + betParamBase.getOddsSeven() + ", 皇冠赔率：" + betParamBase.getOddsHg());
        log.info("投注额：4球：" + betParamBase.getBetAmountFour() + ", 5球：" + betParamBase.getBetAmountFive()
                + ", 6球：" + betParamBase.getBetAmountSix() + ", 7+球：" + betParamBase.getBetAmountSeven() + ", 皇冠：" + betParamBase.getBetAmountHg());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountFour(), betParamBase.getBetAmountFive(), betParamBase.getBetAmountSix(), betParamBase.getBetAmountSeven())
                + ", 皇冠总投注：" + betParamBase.getBetAmountHg());

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountFour(), betParamBase.getBetAmountFive(), betParamBase.getBetAmountSix(), betParamBase.getBetAmountSeven(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountSmall, rebateHG);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        betParamBase.setReward4(reward4);
        log.info("4球数据++++++++++");
        log.info("奖金：" + bonusFour);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward4 + ", 收益率：" + CalcUtil.divide(reward4, betAmountAll, 3));

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        betParamBase.setReward5(reward5);
        log.info("5球数据++++++++++");
        log.info("奖金：" + bonusFive);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward5 + ", 收益率：" + CalcUtil.divide(reward5, betAmountAll, 3));

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        betParamBase.setReward6(reward6);
        log.info("6球数据++++++++++");
        log.info("奖金：" + bonusSix);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward6 + ", 收益率：" + CalcUtil.divide(reward6, betAmountAll, 3));

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        betParamBase.setReward7(reward7);
        log.info("7球+数据++++++++++");
        log.info("奖金：" + bonusSeven);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount);
        log.info("收益：" + reward7 + ", 收益率：" + CalcUtil.divide(reward7, betAmountAll, 3));

        /** 皇冠中球 */
        double bonusSmall = CalcUtil.mul(betAmountSmall, oddsSmall); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusSmall, rebateHG);

        Double rewardSmall = CalcUtil.sub(CalcUtil.add(bonusSmall, rebateSPAmount, rebateHGAmount1), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamBase.setRewardHG(rewardSmall);
        log.info("皇冠中球数据++++++++++");
        log.info("奖金：" + bonusSmall);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount1);
        log.info("收益：" + rewardSmall + ", 收益率：" + CalcUtil.divide(rewardSmall, betAmountAll, 3));
    }

    /**
     * 小3.5/4
     * 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP4567_HG小35_4(BetParamVo betParamVo) {
        BetParamVo betParamBase;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamBase = AdaptationAmount.adaptation(betParamVo);
        } else {
            betParamBase = betParamVo;
        }
        // 体彩参数
        double betAmountFour = betParamBase.getBetAmountFour();
        double oddsFour = betParamBase.getOddsFour();

        double betAmountFive = betParamBase.getBetAmountFive();
        double oddsFive = betParamBase.getOddsFive();

        double betAmountSix = betParamBase.getBetAmountSix();
        double oddsSix = betParamBase.getOddsSix();

        double betAmountSeven = betParamBase.getBetAmountSeven();
        double oddsSeven = betParamBase.getOddsSeven();

        // 皇冠参数
        double betAmountSmall = betParamBase.getBetAmountHg();
        double oddsSmall = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩赔率：4球：" + betParamBase.getOddsFour() + ", 5球：" + betParamBase.getOddsFive()
                + ", 6球：" + betParamBase.getOddsSix() + ", 7+球：" + betParamBase.getOddsSeven() + ", 皇冠赔率：" + betParamBase.getOddsHg());
        log.info("投注额：4球：" + betParamBase.getBetAmountFour() + ", 5球：" + betParamBase.getBetAmountFive()
                + ", 6球：" + betParamBase.getBetAmountSix() + ", 7+球：" + betParamBase.getBetAmountSeven() + ", 皇冠：" + betParamBase.getBetAmountHg());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountFour(), betParamBase.getBetAmountFive(), betParamBase.getBetAmountSix(), betParamBase.getBetAmountSeven())
                + ", 皇冠总投注：" + betParamBase.getBetAmountHg());

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountFour(), betParamBase.getBetAmountFive(), betParamBase.getBetAmountSix(), betParamBase.getBetAmountSeven(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠返水
        Double rewardHGHalf = CalcUtil.div(betAmountSmall, 2);
        double rebateHGAmountHalf = CalcUtil.mul(rewardHGHalf, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountSmall, rebateHG);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountHalf), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, rewardHGHalf);
        betParamBase.setReward4(reward4);
        log.info("4球数据++++++++++");
        log.info("奖金：" + bonusFour);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountHalf);
        log.info("收益：" + reward4 + ", 收益率：" + CalcUtil.divide(reward4, betAmountAll, 3));

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        betParamBase.setReward5(reward5);
        log.info("5球数据++++++++++");
        log.info("奖金：" + bonusFive);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward5 + ", 收益率：" + CalcUtil.divide(reward5, betAmountAll, 3));

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        betParamBase.setReward6(reward6);
        log.info("6球数据++++++++++");
        log.info("奖金：" + bonusSix);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward6 + ", 收益率：" + CalcUtil.divide(reward6, betAmountAll, 3));

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountSmall);
        betParamBase.setReward7(reward7);
        log.info("7球+数据++++++++++");
        log.info("奖金：" + bonusSeven);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmountAll);
        log.info("收益：" + reward7 + ", 收益率：" + CalcUtil.divide(reward7, betAmountAll, 3));

        /** 皇冠中球 */
        double bonusSmall = CalcUtil.mul(betAmountSmall, oddsSmall); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount2 = CalcUtil.mul(bonusSmall, rebateHG);

        Double rewardSmall = CalcUtil.sub(CalcUtil.add(bonusSmall, rebateSPAmount, rebateHGAmount2), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamBase.setRewardHG(rewardSmall);
        log.info("皇冠中0123球数据++++++++++");
        log.info("奖金：" + bonusSmall);
        log.info("体彩返水：" + rebateSPAmount);
        log.info("皇冠返水：" + rebateHGAmount2);
        log.info("收益：" + rewardSmall + ", 收益率：" + CalcUtil.divide(rewardSmall, betAmountAll, 3));
    }

}

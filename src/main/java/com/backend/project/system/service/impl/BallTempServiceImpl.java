package com.backend.project.system.service.impl;

import com.alibaba.fastjson.JSONObject;
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
            log.info("体彩 012, 皇冠 大2 ------------------------------------------------------");
            betParamVo.setOddsHg(大2);
            betParamVo.setBetAmountHg(betParamVo.get大2Amount());
            SP012_HG大2(betParamVo);
        }

        /** 2、大2.5, 体彩小 012,皇冠 大2.5, 全输全赢 */
        Double 大25 = betParamVo.get大25();
        if (大25 != null && 大25 != 0) {
            log.info("体彩 012, 皇冠 大2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大25);
            betParamVo.setBetAmountHg(betParamVo.get大25Amount());
            SP012_HG大25(betParamVo);
        }

        /** 3、大3.5, 体彩小 0123,皇冠 大3.5, 全输全赢 */
        Double 大35 = betParamVo.get大35();
        if (大35 != null && 大35 != 0) {
            log.info("体彩 0123, 皇冠 大3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大35);
            betParamVo.setBetAmountHg(betParamVo.get大35Amount());
            SP012_HG大35(betParamVo);
        }

        /** 4、大2/2.5, 体彩小 012,皇冠 大2/2.5, 2球皇冠输一半 */
        Double 大2_25 = betParamVo.get大2_25();
        if (大2_25 != null && 大2_25 != 0) {
            log.info("体彩 012, 皇冠 大2/2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大2_25);
            betParamVo.setBetAmountHg(betParamVo.get大2_25Amount());
            SP012_HG大2_25(betParamVo);
        }

        /** 5、大2.5/3, 体彩小 0123,皇冠 大2.5/3, 3球体彩赢，皇冠赢一半 */
        Double 大25_3 = betParamVo.get大25_3();
        if (大25_3 != null && 大25_3 != 0) {
            log.info("体彩 0123, 皇冠 大2.5/3 ------------------------------------------------------");
            betParamVo.setOddsHg(大25_3);
            betParamVo.setBetAmountHg(betParamVo.get大25_3Amount());
            SP012_HG大25_3(betParamVo);
        }

        /** 6、大3/3.5, 体彩小 0123,皇冠 大3/3.5 ,3球体彩赢，皇冠输一半*/
        Double 大3_35 = betParamVo.get大3_35();
        if (大3_35 != null && 大3_35 != 0) {
            log.info("体彩 0123, 皇冠 大3/3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大3_35);
            betParamVo.setBetAmountHg(betParamVo.get大3_35Amount());
            SP012_HG大3_35(betParamVo);
        }

        /** 7、小3.5, 体彩大 4567+,皇冠 小3.5 */
        Double 小35 = betParamVo.get小35();
        if (小35 != null && 小35 != 0) {
            log.info("体彩 4567+, 皇冠 小3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(小35);
            betParamVo.setBetAmountHg(betParamVo.get小35Amount());
            SP4567_HG小35(betParamVo);
        }

        /** 8、小3.5/4, 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半 */
        Double 小35_4 = betParamVo.get小35_4();
        if (小35_4 != null && 小35_4 != 0) {
            log.info("体彩 4567+, 皇冠 小3.5/4 ------------------------------------------------------");
            betParamVo.setOddsHg(小35_4);
            betParamVo.setBetAmountHg(betParamVo.get小35_4Amount());
            SP4567_HG小35_4(betParamVo);
        }

    }

    /**
     * 大2
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
        double oddsHg = betParamBase.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩投注：0球 " + betParamBase.getBetAmountZero().intValue() + ", 1球 " + betParamBase.getBetAmountOne().intValue() + ", 2球 " + betParamBase.getBetAmountTwo().intValue());
        log.info("皇冠投注：大2 @" + oddsHg + ", " + betParamBase.getBetAmountHg().intValue());
        log.info("体彩总投注：" + CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo()).intValue() + ", 皇冠总投注：" + betParamBase.getBetAmountHg().intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betParamBase.getBetAmountZero(), betParamBase.getBetAmountOne(), betParamBase.getBetAmountTwo(), betParamBase.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountLarge, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountLarge);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountLarge, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 大2.5
     * 体彩小 012,皇冠 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大25(BetParamVo betParamVo) {
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountTwo;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountTwo = betParamTemp.getBetAmountTwo();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountTwo = betParamVo.getBetAmountTwo();
            betAmountHg = betParamVo.getBetAmountHg();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsTwo = betParamVo.getOddsTwo();
        // 皇冠参数
        Double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue());
        log.info("皇冠投注：大2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 大3.5
     * 体彩小 0123,皇冠 大3.5 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大35(BetParamVo betParamVo) {
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountTwo;
        Double betAmountThree;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountTwo = betParamTemp.getBetAmountTwo();
            betAmountThree = betParamTemp.getBetAmountThree();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountTwo = betParamVo.getBetAmountTwo();
            betAmountThree = betParamVo.getBetAmountThree();
            betAmountHg = betParamVo.getBetAmountHg();
        }

        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsTwo = betParamVo.getOddsTwo();
        double oddsThree = betParamVo.getOddsThree();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue() + ", 3球 " + betAmountThree.intValue());
        log.info("皇冠投注：大3.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        double bonusZero = betAmountZero * oddsZero; // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = betAmountOne * oddsOne; // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = betAmountTwo * oddsTwo; // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 大3/3.5
     * 体彩小 0123,皇冠 大3/3.5 3球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP012_HG大3_35(BetParamVo betParamVo) {
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountTwo;
        Double betAmountThree;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountTwo = betParamTemp.getBetAmountTwo();
            betAmountThree = betParamTemp.getBetAmountThree();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountTwo = betParamVo.getBetAmountTwo();
            betAmountThree = betParamVo.getBetAmountThree();
            betAmountHg = betParamVo.getBetAmountHg();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsTwo = betParamVo.getOddsTwo();
        double oddsThree = betParamVo.getOddsThree();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue() + ", 3球 " + betAmountThree.intValue());
        log.info("皇冠投注：大3/3.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);

        /** 体彩输一半 */
        Double rewardAmountHalf = CalcUtil.div(betAmountHg, 2);
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);
        Double rebateHGAmountHalf = CalcUtil.div(rebateHGAmount, 2);


        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, rewardAmountHalf);
        betParamVo.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 大2/2.5
     * 体彩小 012,皇冠 大2/2.5, 2球皇冠输一半
     * @param betParamVo
     */
    public void SP012_HG大2_25(BetParamVo betParamVo) {
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountTwo;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountTwo = betParamTemp.getBetAmountTwo();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountTwo = betParamVo.getBetAmountTwo();
            betAmountHg = betParamVo.getBetAmountHg();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsTwo = betParamVo.getOddsTwo();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue());
        log.info("皇冠投注：大2/2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountHg);

        /** 体彩中球 */
        // 皇冠输一半
        Double betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        // 皇冠返水
        double rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);

        double rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamVo.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");
        
        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountHgHalf);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");
        
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        betParamVo.setRewardHG(betAmountHgHalf);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 大2。5/3
     * 体彩小 0123,皇冠 大2.5/3, 3球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    public void SP012_HG大25_3(BetParamVo betParamVo) {
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountTwo;
        Double betAmountThree;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountTwo = betParamTemp.getBetAmountTwo();
            betAmountThree = betParamTemp.getBetAmountThree();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountTwo = betParamVo.getBetAmountTwo();
            betAmountThree = betParamVo.getBetAmountThree();
            betAmountHg = betParamVo.getBetAmountHg();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsTwo = betParamVo.getOddsTwo();
        double oddsThree = betParamVo.getOddsThree();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue() + ", 3球：" + betAmountThree.intValue());
        log.info("皇冠投注：大2.5/3 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);

        /** 体彩中球 */
        // 皇冠出奖
        Double rewardHGHalf = CalcUtil.div(CalcUtil.mul(betAmountHg, oddsHg), 2);
        // 皇冠返水
        double rebateHGAmountHalf = CalcUtil.mul(rewardHGHalf, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");
        
        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");
        
        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf, rewardHGHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamVo.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");
        
        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        betParamVo.setRewardHG(bonusHg);
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 小3.5
     * 体彩大 4567+,皇冠 小3.5 全输全赢
     * @param betParamVo
     */
    public void SP4567_HG小35(BetParamVo betParamVo) {
        Double betAmountFour;
        Double betAmountFive;
        Double betAmountSix;
        Double betAmountSeven;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountFour = betParamTemp.getBetAmountFour();
            betAmountFive = betParamTemp.getBetAmountFive();
            betAmountSix = betParamTemp.getBetAmountSix();
            betAmountSeven = betParamTemp.getBetAmountSeven();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountFour = betParamVo.getBetAmountFour();
            betAmountFive = betParamVo.getBetAmountFive();
            betAmountSix = betParamVo.getBetAmountSix();
            betAmountSeven = betParamVo.getBetAmountSeven();
            betAmountHg = betParamVo.getBetAmountHg();
        }
        // 体彩参数
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        double oddsSeven = betParamVo.getOddsSeven();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小3.5 @" + oddsHg + ", " + betParamVo.getBetAmountHg().intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betParamVo.getBetAmountHg().intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betParamVo.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 小3.5/4
     * 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP4567_HG小35_4(BetParamVo betParamVo) {
        Double betAmountFour;
        Double betAmountFive;
        Double betAmountSix;
        Double betAmountSeven;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountFour = betParamTemp.getBetAmountFour();
            betAmountFive = betParamTemp.getBetAmountFive();
            betAmountSix = betParamTemp.getBetAmountSix();
            betAmountSeven = betParamTemp.getBetAmountSeven();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountFour = betParamVo.getBetAmountFour();
            betAmountFive = betParamVo.getBetAmountFive();
            betAmountSix = betParamVo.getBetAmountSix();
            betAmountSeven = betParamVo.getBetAmountSeven();
            betAmountHg = betParamVo.getBetAmountHg();
        }
        // 体彩参数
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        double oddsSeven = betParamVo.getOddsSeven();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：4球 " + betParamVo.getBetAmountFour().intValue() + ", 5球 " + betParamVo.getBetAmountFive().intValue() + ", 6球 " + betParamVo.getBetAmountSix().intValue() + ", 7+球 " + betParamVo.getBetAmountSeven().intValue());
        log.info("皇冠投注：小3.5/4 @" + oddsHg + ", " + betParamVo.getBetAmountHg().intValue());
        log.info("体彩总投注：" + CalcUtil.add(betParamVo.getBetAmountFour(), betParamVo.getBetAmountFive(), betParamVo.getBetAmountSix(), betParamVo.getBetAmountSeven()).intValue()
                + ", 皇冠总投注：" + betParamVo.getBetAmountHg().intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betParamVo.getBetAmountFour(), betParamVo.getBetAmountFive(), betParamVo.getBetAmountSix(), betParamVo.getBetAmountSeven(), betParamVo.getBetAmountHg());

        /** 体彩中球 */
        // 皇冠返水
        Double betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        double rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountHalf), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        betParamVo.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount2 = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount2), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

}

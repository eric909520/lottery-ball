package com.backend.common.utils;

import com.backend.project.system.domain.vo.BetParamVo;
import lombok.extern.slf4j.Slf4j;

/**
 * 调整投注适配金额
 */
@Slf4j
public class AdaptationAmount {

    public static BetParamVo adaptation(BetParamVo betParamVo){
        BetParamVo betParamTemp = new BetParamVo();
        if (betParamVo.getOddsZero() != 0) {
            Double betZeroAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsZero());
            betParamTemp.setBetAmountZero(Rounding(betZeroAmount));
        }
        if (betParamVo.getOddsOne() != 0) {
            Double betOneAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsOne());
            betParamTemp.setBetAmountOne(Rounding(betOneAmount));
        }
        if (betParamVo.getOddsTwo() != 0) {
            Double betTwoAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsTwo());
            betParamTemp.setBetAmountTwo(Rounding(betTwoAmount));
        }
        if (betParamVo.getOddsThree() != 0) {
            Double betThreeAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsThree());
            betParamTemp.setBetAmountThree(Rounding(betThreeAmount));
        }
        if (betParamVo.getOddsFour() != 0) {
            Double betFourAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsFour());
            betParamTemp.setBetAmountFour(Rounding(betFourAmount));
        }
        if (betParamVo.getOddsFive() != 0) {
            Double betFiveAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsFive());
            betParamTemp.setBetAmountFive(Rounding(betFiveAmount));
        }
        if (betParamVo.getOddsSix() != 0) {
            Double betSixAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsSix());
            betParamTemp.setBetAmountSix(Rounding(betSixAmount));
        }
        if (betParamVo.getOddsSeven() != 0) {
            Double betSevenAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsSeven());
            betParamTemp.setBetAmountSeven(Rounding(betSevenAmount));
        }
        if (betParamVo.getOddsHg()!=null && betParamVo.getOddsHg() != 0) {
            Double HGBet = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsHg()+1);
            betParamTemp.setBetAmountHg(Rounding(HGBet));
        }
        if (betParamVo.getZong0_1() != 0) {
            Double amount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getZong0_1());
            betParamTemp.setZong0_1Amount(Rounding(amount));
        }
        if (betParamVo.getZong2_3() != 0) {
            Double amount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getZong2_3());
            betParamTemp.setZong2_3Amount(Rounding(amount));
        }
        if (betParamVo.getZong4_6() != 0) {
            Double amount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getZong4_6());
            betParamTemp.setZong4_6Amount(Rounding(amount));
        }
        if (betParamVo.getZong7() != 0) {
            Double amount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getZong7());
            betParamTemp.setZong7Amount(Rounding(amount));
        }
        return betParamTemp;
    }

    /**
     * 两个赔率金额调配
     * @param oddsHg
     * @param betAmountHg
     * @param rewardSp
     * @param rewardHg
     * @return
     */
    public static Double amountDeployment(Double oddsHg, Double betAmountHg, Double rewardSp, Double rewardHg) {
        if (rewardSp <0 && rewardHg <0) {
            return null;
        } else if (rewardSp<0 || rewardHg<0) { // 单边收益负
            Double rewardAll = CalcUtil.add(rewardSp, rewardHg);
            if (rewardAll < 50) {
                return null;
            }
            // 收益差
            Double rewardSub = CalcUtil.add(Math.abs(rewardSp), Math.abs(rewardHg));
            if (rewardSub < 0) {
                // 两边收益和小于0
                return null;
            } else {
                Double hgBetAmountSub = CalcUtil.mul(rewardSub, oddsHg);
                if (rewardSp < 0) {
                    // 体彩收益小于0，皇冠投注额减少
                    betAmountHg = CalcUtil.sub(betAmountHg, hgBetAmountSub);
                } else if (rewardHg < 0) {
                    // 皇冠收益小于0，皇冠投注额增加
                    betAmountHg = CalcUtil.add(betAmountHg, hgBetAmountSub);
                }
            }
        } else if (rewardSp>0 && rewardHg>0) {
            // 皇冠收益大于体彩收益，皇冠投注额减少
            if (rewardSp < rewardHg) {
                // 收益差
                Double rewardSub = CalcUtil.sub(rewardHg, rewardSp);
                Double hgBetAmountSub = CalcUtil.div(rewardSub, oddsHg);
                betAmountHg = CalcUtil.sub(betAmountHg, hgBetAmountSub);
            }
            // 体彩收益大于皇冠收益，皇冠投注额增加
            if (rewardSp > rewardHg) {
                // 收益差
                Double rewardSub = CalcUtil.sub(rewardSp, rewardHg);
                Double hgBetAmountSub = CalcUtil.div(rewardSub, oddsHg);
                betAmountHg = CalcUtil.add(betAmountHg, hgBetAmountSub);
            }
        }
        return betAmountHg;
    }

    /**
     * 总进球金额调配
     * @return
     */
    public static BetParamVo adaptationZong(BetParamVo betParamVo) {
        Double reward0 = betParamVo.getReward0();
        Double reward1 = betParamVo.getReward1();
        Double reward2 = betParamVo.getReward2();
        Double reward3 = betParamVo.getReward3();
        Double reward4 = betParamVo.getReward4();
        Double reward5 = betParamVo.getReward5();
        Double reward6 = betParamVo.getReward6();
        Double reward7 = betParamVo.getReward7();
        Double rewardZong0_1 = betParamVo.getRewardZong0_1();
        Double rewardZong2_3 = betParamVo.getRewardZong2_3();
        Double rewardZong4_6 = betParamVo.getRewardZong4_6();
        Double rewardZong7 = betParamVo.getRewardZong7();
        if (reward0<=10 && reward1<=10 && reward2<=10 && reward3<=10 && reward4<=10 && reward5<=10 && reward6<=10
                && reward7<=10 && rewardZong0_1<=10 && rewardZong2_3<=10 && rewardZong4_6<=10 && rewardZong7<=10) {
            return null;
        }
        return betParamVo;
    }

    /**
     * 调整投注金额
     * @param betParamVo
     * @return
     */
    public static  BetParamVo AdaptationBet (BetParamVo betParamVo){

        Double rewardZero = betParamVo.getReward0();
        Double rewardOne = betParamVo.getReward1();
        Double rewardTwo = betParamVo.getReward2();
        Double rewardThree = betParamVo.getReward3();
        Double rewardHG = betParamVo.getRewardHG();
        Double rewardFour = betParamVo.getReward4();
        Double rewardFive = betParamVo.getReward5();
        Double rewardSix = betParamVo.getReward6();
        Double rewardSeven = betParamVo.getReward7();
        /**
         * 0 1 2
         */
        if(betParamVo.getOddsZero() > 0 && betParamVo.getOddsThree() == 0){
            if(rewardZero < 0 && rewardOne < 0 && rewardTwo < 0 && rewardHG < 0) {
                log.info("收益均为负数 无法投注：0球："+rewardZero +" 1球："+rewardOne +" 2球："+ rewardTwo + " HG:" + rewardHG);
                return null;
            }
            if(rewardZero > 0 && rewardOne > 0 && rewardTwo > 0 && rewardHG > 0) {
                log.info("收益均为正 无需调整：0球："+rewardZero +" 1球："+rewardOne +" 2球："+ rewardTwo + " HG:" + rewardHG);
                return betParamVo;
            }
            // 0球单负
            if(rewardZero < 0 && rewardOne > 0 && rewardTwo > 0 && rewardHG > 0){
                log.info("0球单负 增加0球 2%投注额" + rewardZero);
                betParamVo.setBetAmountZero(CalcUtil.add(betParamVo.getBetAmountZero(),CalcUtil.mul(betParamVo.getBetAmountZero(),0.02)));
                return betParamVo;
            }
            // 1球单负
            if(rewardZero > 0 && rewardOne < 0 && rewardTwo > 0 && rewardHG > 0){
                log.info("1球单负 增加1球 2%投注额" + rewardOne);
                betParamVo.setBetAmountOne(CalcUtil.add(betParamVo.getBetAmountOne(),CalcUtil.mul(betParamVo.getBetAmountOne(),0.02)));
                return betParamVo;
            }
            // 2球单负
            if(rewardZero > 0 && rewardOne > 0 && rewardTwo < 0 && rewardHG > 0){
                //如果2球负太多 无法投注
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardZero, rewardOne), rewardTwo), rewardHG);
                if(total < 0){
                    log.info("2球负太多  无法投注: "+ total);
                    return null;
                }
                //如果hg盈利大于1000 减少2%hg投注额
                if(rewardHG > 1000){
                    log.info("2球单负 HG盈利过1000 减少HG2%投注额" + rewardTwo);
                    betParamVo.setBetAmountHg(CalcUtil.sub(betParamVo.getBetAmountHg(),CalcUtil.mul(betParamVo.getBetAmountHg(),0.02)));
                }
                log.info("2球单负 增加2球 2%投注额" + rewardTwo);
                betParamVo.setBetAmountTwo(CalcUtil.add(betParamVo.getBetAmountTwo(),CalcUtil.mul(betParamVo.getBetAmountTwo(),0.02)));
                return betParamVo;
            }

            // HG单负
            if(rewardZero > 0 && rewardOne > 0 && rewardTwo > 0 && rewardHG < 0){
                //如果HG负太多 无法投注
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardZero, rewardOne), rewardTwo), rewardHG);
                if(total < 0){
                    log.info("HG负太多  无法投注: "+ total);
                    return null;
                }
                if(rewardTwo > 1000){
                    log.info("HG单负 2球盈利过1000 减少2球2%投注额" + rewardTwo);
                    betParamVo.setBetAmountTwo(CalcUtil.sub(betParamVo.getBetAmountTwo(),CalcUtil.mul(betParamVo.getBetAmountTwo(),0.02)));
                }
                log.info("HG单负 增加HG 2%投注额" + rewardHG);
                betParamVo.setBetAmountHg(CalcUtil.add(betParamVo.getBetAmountHg(),CalcUtil.mul(betParamVo.getBetAmountHg(),0.02)));
                return betParamVo;
            }
        }
        /**
         * 0 1 2 3
         */
        if(betParamVo.getOddsZero() > 0 && betParamVo.getOddsThree() > 0){
            if(rewardZero < 0 && rewardOne < 0 && rewardTwo < 0 && rewardThree < 0 && rewardHG < 0) {
                log.info("收益均为负数 无法投注：0球："+rewardZero +" 1球："+rewardOne +" 2球："+ rewardTwo + " 3球："+ rewardThree +" HG:" + rewardHG);
                return null;
            }
            if(rewardZero > 0 && rewardOne > 0 && rewardTwo > 0 && rewardThree > 0 && rewardHG > 0) {
                log.info("收益均为正 无需调整：0球："+rewardZero +" 1球："+rewardOne +" 2球："+ rewardTwo + " 3球："+ rewardThree + " HG:" + rewardHG);
                return betParamVo;
            }
            // 0球负
            if(rewardZero < 0 && rewardOne > 0 && rewardTwo > 0 && rewardThree >0 && rewardHG > 0){
                betParamVo.setBetAmountZero(CalcUtil.add(betParamVo.getBetAmountZero(),CalcUtil.mul(betParamVo.getBetAmountZero(),0.02)));
                return betParamVo;
            }
            // 1球负
            if(rewardZero > 0 && rewardOne < 0 && rewardTwo > 0 && rewardThree >0 && rewardHG > 0){
                betParamVo.setBetAmountOne(CalcUtil.add(betParamVo.getBetAmountOne(),CalcUtil.mul(betParamVo.getBetAmountOne(),0.02)));
                return betParamVo;
            }
            // 2球负
            if(rewardZero > 0 && rewardOne > 0 && rewardTwo < 0 && rewardThree >0 && rewardHG > 0){
                //如果2球负太多 无法投注
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardZero, rewardOne), rewardTwo), rewardHG),rewardThree);
                if(total < 0){
                    log.info("2球负太多  无法投注: "+ total);
                    return null;
                }
                betParamVo.setBetAmountTwo(CalcUtil.add(betParamVo.getBetAmountTwo(),CalcUtil.mul(betParamVo.getBetAmountTwo(),0.02)));
                return betParamVo;
            }
            //2球3球都负
            if(rewardTwo < 0 && rewardThree < 0 && rewardZero > 0 && rewardOne > 0 && rewardHG > 0){
                Double sum = CalcUtil.add(rewardZero, rewardOne,rewardHG);
                Double sub = CalcUtil.sub(CalcUtil.sub(sum, rewardTwo), rewardThree);
                if(sub < 0){
                    log.info("2球和3球都负 总盈利为负 无法投注: "+ sub);
                    return null;
                }
            }
            //1球2球3球都负 均负不多
            if(rewardZero > 0 && rewardOne < 0 && rewardTwo < 0 && rewardThree < 0 && rewardHG > 0){
                if(CalcUtil.sub(600,rewardOne) > 0 && CalcUtil.sub(600,rewardTwo) > 0 && CalcUtil.sub(600,rewardThree) > 0 && rewardHG > 1500 && rewardSeven > 3000){
                    log.info(" 1 2 3 均负不多 且 0 HG 盈利可调 hg投注降低 2%   0球降低 2%" );
                    betParamVo.setBetAmountHg(CalcUtil.sub(betParamVo.getBetAmountHg(),CalcUtil.mul(betParamVo.getBetAmountHg(),0.02)));
                    betParamVo.setBetAmountZero(CalcUtil.sub(betParamVo.getBetAmountZero(),CalcUtil.mul(betParamVo.getBetAmountZero(),0.02)));
                    return  betParamVo;

                }

                Double sum = CalcUtil.add(rewardZero, rewardOne,rewardHG);
                Double sub = CalcUtil.sub(CalcUtil.sub(sum, rewardTwo), rewardThree);
                if(sub < 0){
                    log.info("2球和3球都负 总盈利为负 无法投注: "+ sub);
                    return null;
                }
            }
            //1球2球3球 hg都负
            if(rewardZero > 0 && rewardOne < 0 && rewardTwo < 0 && rewardThree < 0 && rewardHG < 0){
                log.info(" 1 2 3 均负不多 且 0 HG 盈利可调 hg投注降低 2%   0球降低 2%" );
                return  null;
            }

            // 3球单负
            if(rewardZero > 0 && rewardOne > 0 && rewardTwo > 0 && rewardThree < 0 && rewardHG > 0){
                //如果3球负太多 无法投注
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardZero, rewardOne), rewardTwo), rewardHG);
                if(total < 0){
                    log.info("3球负太多  无法投注: "+ total);
                    return null;
                }
                if(rewardTwo > 1000){
                    log.info("3球单负 2球盈利过1000 减少2球2%投注额" + rewardTwo);
                    betParamVo.setBetAmountTwo(CalcUtil.sub(betParamVo.getBetAmountTwo(),CalcUtil.mul(betParamVo.getBetAmountTwo(),0.02)));
                }
                log.info("3球单负 增加2%投注额" + rewardThree);
                betParamVo.setBetAmountThree(CalcUtil.add(betParamVo.getBetAmountThree(),CalcUtil.mul(betParamVo.getBetAmountThree(),0.02)));
                return betParamVo;
            }

            // HG单负
            if(rewardZero > 0 && rewardOne > 0 && rewardTwo > 0 && rewardHG < 0){
                //如果HG负太多 无法投注
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardZero, rewardOne), rewardTwo), rewardHG);
                if(total < 0){
                    log.info("HG负太多  无法投注: "+ total);
                    return null;
                }
                if(rewardTwo > 1000){
                    log.info("HG单负 2球盈利过1000 减少2球2%投注额" + rewardTwo);
                    betParamVo.setBetAmountTwo(CalcUtil.sub(betParamVo.getBetAmountTwo(),CalcUtil.mul(betParamVo.getBetAmountTwo(),0.02)));
                }
                if(rewardThree > 1000){
                    log.info("HG单负 3球盈利过1000 减少3球2%投注额" + rewardThree);
                    betParamVo.setBetAmountThree(CalcUtil.sub(betParamVo.getBetAmountThree(),CalcUtil.mul(betParamVo.getBetAmountThree(),0.02)));
                }
                log.info("HG单负 增加HG 2%投注额" + rewardHG);
                betParamVo.setBetAmountHg(CalcUtil.add(betParamVo.getBetAmountHg(),CalcUtil.mul(betParamVo.getBetAmountHg(),0.02)));
                return betParamVo;
            }

        }
        /**
         *  4 5 6 7+
         */
        if(betParamVo.getOddsFour() > 0){
            if(rewardFour < 0 && rewardFive < 0 && rewardSix < 0 && rewardSeven < 0 && rewardHG < 0 && rewardHG < 0) {
                log.info("收益均为负数 无法投注：4球："+rewardFour +" 5球："+rewardFive +" 6球："+ rewardSix + " 7球："+ rewardSeven +" HG:" + rewardHG);
                return null;
            }
            if(rewardFour > 0 && rewardFive > 0 && rewardSix > 0 && rewardSeven > 0 && rewardHG > 0 && rewardHG > 0) {
                log.info("收益均为正 无需调配：4球："+rewardFour +" 5球："+rewardFive +" 6球："+ rewardSix + " 7球："+ rewardSeven +" HG:" + rewardHG);
                return betParamVo;
            }
            // 4球负
            if(rewardFour < 0 && rewardFive > 0 && rewardSix > 0 && rewardSeven > 0 && rewardHG > 0){
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardFour, rewardFive), rewardSix), rewardSeven),rewardHG) ;
                if(total < 0){
                    log.info("4球负太多  无法投注: "+ total);
                    return null;
                }
                if(rewardFive > 1000){
                    log.info("4球单负 5球盈利过1000 减少5球2%投注额" + rewardFive);
                    betParamVo.setBetAmountFive(CalcUtil.sub(betParamVo.getBetAmountFive(),CalcUtil.mul(betParamVo.getBetAmountFive(),0.02)));
                }
                if(rewardSix > 1000){
                    log.info("4球单负 6球盈利过1000 减少6球2%投注额" + rewardSix);
                    betParamVo.setBetAmountSix(CalcUtil.sub(betParamVo.getBetAmountSix(),CalcUtil.mul(betParamVo.getBetAmountSix(),0.02)));
                }
                betParamVo.setBetAmountFour(CalcUtil.add(betParamVo.getBetAmountFour(),CalcUtil.mul(betParamVo.getBetAmountFour(),0.02)));
                return betParamVo;
            }
            // 4球和 HG都负
            if(rewardFour < 0 && rewardFive > 0 && rewardSix > 0 && rewardSeven > 0 && rewardHG < 0){
                log.info("4球和 HG都负   无法投注:  4球"+ rewardFour +" HG:"+ rewardHG);
                return null;
            }
            //4球5球都负
            if(rewardFour < 0 && rewardFive < 0 && rewardSix > 0 && rewardSeven > 0 && rewardHG > 0){
                Double sum = CalcUtil.add(rewardSeven, rewardSix,rewardHG);
                Double sub = CalcUtil.sub(CalcUtil.sub(sum, rewardFour), rewardFive);
                if(sub < 0){
                    log.info("4球和5球都负 总盈利为负 无法投注: "+ sub);
                    return null;
                }
            }
            // 5球单负
            if(rewardFour > 0 && rewardFive < 0 && rewardSix > 0 && rewardSeven > 0 && rewardHG > 0){
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardFour, rewardFive), rewardSix), rewardSeven),rewardHG) ;
                if(total < 0){
                    log.info("5球负太多  无法投注: "+ total);
                    return null;
                }
                betParamVo.setBetAmountFive(CalcUtil.add(betParamVo.getBetAmountFive(),CalcUtil.mul(betParamVo.getBetAmountFive(),0.02)));
                return betParamVo;
            }
            //4球5球6球都负 hg 7球为盈
            if(rewardFour < 0 && rewardFive < 0 && rewardSix < 0 && rewardSeven > 0 && rewardHG > 0){
                if(CalcUtil.sub(600,rewardFour) > 0 && CalcUtil.sub(600,rewardFive) > 0 && CalcUtil.sub(600,rewardSix) > 0 && rewardHG > 1500 && rewardSeven > 3000){
                    log.info(" 4 5 6 均负不多 且 7HG 盈利可调 hg投注降低 2%   7球降低 2%" );
                    betParamVo.setBetAmountHg(CalcUtil.sub(betParamVo.getBetAmountHg(),CalcUtil.mul(betParamVo.getBetAmountHg(),0.02)));
                    betParamVo.setBetAmountSeven(CalcUtil.sub(betParamVo.getBetAmountSeven(),CalcUtil.mul(betParamVo.getBetAmountSeven(),0.02)));
                    return betParamVo;
                }

                Double sum = CalcUtil.add(rewardSeven, rewardSix,rewardHG);
                Double sub = CalcUtil.sub(CalcUtil.sub(sum, rewardFour),rewardFive);
                if(sub < 0){
                    log.info("4球和5球都负 总盈利为负 无法投注: "+ sub);
                    return null;
                }
            }

            // 6球负
            if(rewardFour > 0 && rewardFive > 0 && rewardSix < 0 && rewardSeven > 0 && rewardHG > 0){
                //如果6球负太多 无法投注
                Double total = CalcUtil.add(CalcUtil.add(CalcUtil.add(CalcUtil.add(rewardFour, rewardFive), rewardSix), rewardHG),rewardSeven);
                if(total < 0){
                    log.info("6球负太多  无法投注: "+ total);
                    return null;
                }
                betParamVo.setBetAmountSix(CalcUtil.add(betParamVo.getBetAmountSix(),CalcUtil.mul(betParamVo.getBetAmountSix(),0.02)));
                return betParamVo;
            }
            // 7球负
            if(rewardFour > 0 && rewardFive > 0 && rewardSix > 0 && rewardSeven < 0 && rewardHG > 0){
                log.info("7球负  无法投注 增加7球投注: "+ rewardSeven);
                betParamVo.setBetAmountSeven(CalcUtil.add(betParamVo.getBetAmountSeven(),CalcUtil.mul(betParamVo.getBetAmountSeven(),0.02)));
                return betParamVo;
            }
            // HG负
            if(rewardFour > 0 && rewardFive > 0 && rewardSix > 0 && rewardSeven > 0 && rewardHG < 0){
                //如果HG负 且 4球盈利过1000 则减去 4球2%的投注额
                if(rewardFour > 1000){
                    log.info("HG负  4球盈利过1000 减去 4球2%的投注额  "+ rewardHG);
                    betParamVo.setBetAmountFour(CalcUtil.sub(betParamVo.getBetAmountFour(),CalcUtil.mul(betParamVo.getBetAmountFour(),0.02)));
                }
                //如果HG负 且 5球盈利过1000 则减去 5球2%的投注额
                if(rewardFour > 1000){
                    log.info("HG负  5球盈利过1000 减去 5球2%的投注额  "+ rewardHG);
                    betParamVo.setBetAmountFour(CalcUtil.sub(betParamVo.getBetAmountFive(),CalcUtil.mul(betParamVo.getBetAmountFive(),0.02)));
                }
                betParamVo.setBetAmountHg(CalcUtil.add(betParamVo.getBetAmountHg(),CalcUtil.mul(betParamVo.getBetAmountHg(),0.02)));
                return betParamVo;
            }
        }

        return null;
    }


         public static  Double Rounding(Double d){
             Integer round = Math.round(d.intValue() / 10) * 10;
             return round.doubleValue();
         }





    public static void main(String[] args) {
        BetParamVo betParamVo =  new BetParamVo();
        betParamVo.setBetBaseAmount(53000d);
        betParamVo.setOddsHg(0.9);
        betParamVo.setOddsFour(6.8);
        betParamVo.setOddsFive(4.8);
        betParamVo.setOddsSix(4.8);
        betParamVo.setOddsSeven(4.8);
    }
}

package com.backend.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.backend.project.system.domain.vo.BetParamVo;

/**
 * 调整投注适配金额
 */
public class AdaptationAmount {

    public static BetParamVo adaptation(BetParamVo betParamVo){
        /**
         * 0 1 2 3
         */
        if(betParamVo.getOddsZero() != 0 && betParamVo.getOddsZero() != null){
            Double betZeroAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsZero());
            betParamVo.setBetAmountZero(betZeroAmount);

            Double betOneAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsOne());
            betParamVo.setBetAmountOne(betOneAmount);

            Double betTwoAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsTwo());
            betParamVo.setBetAmountTwo(betTwoAmount);

            Double HGBet = CalcUtil.div(betZeroAmount, betParamVo.getOddsHg()+1);
            betParamVo.setBetAmountHg(HGBet);

            if(betParamVo.getOddsThree() != null){
                Double betThreeAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsThree());
                betParamVo.setBetAmountThree(betThreeAmount);
            }
            return betParamVo;
        }
        /**
         * 4 5 6 7+
         */
        if(betParamVo.getOddsFour()!= 0 && betParamVo.getOddsFour() != null){
            Double betFourAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsFour());
            betParamVo.setBetAmountFour(betFourAmount);

            Double betFiveAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsFive());
            betParamVo.setBetAmountFour(betFiveAmount);

            Double betSixAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsSix());
            betParamVo.setBetAmountSix(betSixAmount);

            Double betSevenAmount = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsSeven());
            betParamVo.setBetAmountFour(betSevenAmount);

            Double HGBet = CalcUtil.div(betParamVo.getBetBaseAmount(), betParamVo.getOddsHg()+1);
            betParamVo.setBetAmountHg(HGBet);
            return  betParamVo;
        }
        return null;
    }


    public static void main(String[] args) {
        BetParamVo betParamVo =  new BetParamVo();
        betParamVo.setBetBaseAmount(53000d);
        betParamVo.setOddsHg(0.9);
        betParamVo.setOddsFour(6.8);
        betParamVo.setOddsFive(4.8);
        betParamVo.setOddsSix(4.8);
        betParamVo.setOddsSeven(4.8);
        BetParamVo adaptation = AdaptationAmount.adaptation(betParamVo);

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(adaptation);
        System.out.println(jsonObject);
    }
}

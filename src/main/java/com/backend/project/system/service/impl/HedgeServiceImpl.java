package com.backend.project.system.service.impl;

import com.backend.common.utils.AdaptationAmount;
import com.backend.common.utils.CalcUtil;
import com.backend.project.system.domain.vo.HedgeParamVo;
import com.backend.project.system.service.IHedgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author
 */
@Slf4j
@Service
public class HedgeServiceImpl implements IHedgeService {

    // hg固定返水比例
    private final static double rebateHG = 0.026;

    @Override
    public void betCheck(HedgeParamVo hedgeParamVo) {
        Double betHgAmount = hedgeParamVo.getBaseAmount();
        Double oddsHg = hedgeParamVo.getOddsHg();
        Double odds365 = hedgeParamVo.getOddsBet365();
        Double bet365Amount = calcHedgeBetAmount(betHgAmount, oddsHg, odds365);

        // hg全输水
        Double rebateHgAll = CalcUtil.mul(betHgAmount, rebateHG);
        // 计算365收益
        Double reward365 = CalcUtil.add(CalcUtil.sub(CalcUtil.mul(bet365Amount, CalcUtil.sub(odds365, 1)), betHgAmount), rebateHgAll);
        // 计算皇冠出奖
        Double hgBonus = CalcUtil.mul(betHgAmount, CalcUtil.sub(oddsHg, 1));
        // 计算皇冠出奖返水
        Double hgBonusRebate = CalcUtil.mul(hgBonus, rebateHG);
        Double rewardHg = CalcUtil.add(CalcUtil.sub(hgBonus, bet365Amount), hgBonusRebate);

        log.info("  皇冠投注 @" + oddsHg + ", " + betHgAmount.intValue() + ", 收益:" + rewardHg.intValue() + ", 收益率:"+ CalcUtil.mul(CalcUtil.div(rewardHg, CalcUtil.add(bet365Amount, betHgAmount), 4), 100) + "%");
        log.info("  365投注 @" + odds365 + ", " + bet365Amount.intValue() + ", 收益:"+ reward365.intValue() + ", 收益率:"+ CalcUtil.mul(CalcUtil.div(reward365, CalcUtil.add(bet365Amount, betHgAmount), 4), 100) + "%");

        // 调配金额
        bet365Amount = AdaptationAmount.amountDeployment(odds365, bet365Amount, rewardHg, reward365);
        if (bet365Amount == null) {
            return;
        }

        // 计算365收益
        reward365 = CalcUtil.add(CalcUtil.sub(CalcUtil.mul(bet365Amount, CalcUtil.sub(odds365, 1)), betHgAmount), rebateHgAll);
        // 计算皇冠出奖返水
        hgBonusRebate = CalcUtil.mul(hgBonus,rebateHG);
        rewardHg = CalcUtil.add(CalcUtil.sub(hgBonus, bet365Amount), hgBonusRebate);

        log.info("  皇冠投注 @" + oddsHg + ", " + betHgAmount.intValue() + ", 收益:" + rewardHg.intValue() + ", 收益率:"+ CalcUtil.mul(CalcUtil.div(rewardHg, CalcUtil.add(bet365Amount, betHgAmount), 4), 100) + "%");
        log.info("  365投注 @" + odds365 + ", " + bet365Amount.intValue() + ", 收益:"+ reward365.intValue() + ", 收益率:"+ CalcUtil.mul(CalcUtil.div(reward365, CalcUtil.add(bet365Amount, betHgAmount), 4), 100) + "%");

    }

    /**
     * 计算对冲赔率初始投注金额
     * @param baseAmount
     * @param oddsHg
     * @return
     */
    private Double calcHedgeBetAmount(Double baseAmount,Double oddsHg,Double oddsBet365){
        Double bet365Amount = CalcUtil.div(CalcUtil.mul(baseAmount, oddsHg), oddsBet365);
        return bet365Amount;
    }

}

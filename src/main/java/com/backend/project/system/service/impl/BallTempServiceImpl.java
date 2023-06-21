package com.backend.project.system.service.impl;

import com.backend.common.utils.AdaptationAmount;
import com.backend.common.utils.CalcUtil;
import com.backend.project.system.domain.vo.BetParamVo;
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
    private final static double rebateHG = 0.026;

    /**
     * 012
     * @param betParamVo
     */
    @Override
    public void betCheck(BetParamVo betParamVo) {
        /** 大1.5, 体彩小 01,皇冠 大1.5, 全输全赢 */
        Double 大15 = betParamVo.get大15();
        if (大15 != null && 大15 != 0) {
            log.info("      体彩 01, 皇冠 大1.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大15);
            betParamVo.setBetAmountHg(betParamVo.get大15Amount());
            SP01_HG大15(betParamVo);
        }

        /** 1、大2, 体彩小 012,皇冠 大2, 全输全赢 */
        Double 大2 = betParamVo.get大2();
        if (大2 != null && 大2 != 0) {
            log.info("      体彩 012, 皇冠 大2 ------------------------------------------------------");
            betParamVo.setOddsHg(大2);
            betParamVo.setBetAmountHg(betParamVo.get大2Amount());
            SP012_HG大2(betParamVo);
        }

        /** 2、大2.5, 体彩小 012,皇冠 大2.5, 全输全赢 */
        Double 大25 = betParamVo.get大25();
        if (大25 != null && 大25 != 0) {
            log.info("      体彩 012, 皇冠 大2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大25);
            betParamVo.setBetAmountHg(betParamVo.get大25Amount());
            SP012_HG大25(betParamVo);
        }

        /** 大3, 体彩小 0123,皇冠 大3, 全输全赢 */
        Double 大3 = betParamVo.get大3();
        if (大3 != null && 大3 != 0) {
            log.info("      体彩 0123, 皇冠 大3 ------------------------------------------------------");
            betParamVo.setOddsHg(大3);
            betParamVo.setBetAmountHg(betParamVo.get大3Amount());
            SP012_HG大3(betParamVo);
        }

        /** 3、大3.5, 体彩小 0123,皇冠 大3.5, 全输全赢 */
        Double 大35 = betParamVo.get大35();
        if (大35 != null && 大35 != 0) {
            log.info("      体彩 0123, 皇冠 大3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大35);
            betParamVo.setBetAmountHg(betParamVo.get大35Amount());
            SP012_HG大35(betParamVo);
        }

        /** 大1.5/2, 体彩小 012,皇冠 大1.5/2, 2球体彩赢，皇冠赢一半 */
        Double 大15_2 = betParamVo.get大15_2();
        if (大15_2 != null && 大15_2 != 0) {
            log.info("      体彩 012, 皇冠 大1.5/2 ------------------------------------------------------");
            betParamVo.setOddsHg(大15_2);
            betParamVo.setBetAmountHg(betParamVo.get大15_2Amount());
            SP012_HG大15_2(betParamVo);
        }

        /** 4、大2/2.5, 体彩小 012,皇冠 大2/2.5, 2球皇冠输一半 */
        Double 大2_25 = betParamVo.get大2_25();
        if (大2_25 != null && 大2_25 != 0) {
            log.info("      体彩 012, 皇冠 大2/2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大2_25);
            betParamVo.setBetAmountHg(betParamVo.get大2_25Amount());
            SP012_HG大2_25(betParamVo);
        }

        /** 5、大2.5/3, 体彩小 0123,皇冠 大2.5/3, 3球体彩赢，皇冠赢一半 */
        Double 大25_3 = betParamVo.get大25_3();
        if (大25_3 != null && 大25_3 != 0) {
            log.info("      体彩 0123, 皇冠 大2.5/3 ------------------------------------------------------");
            betParamVo.setOddsHg(大25_3);
            betParamVo.setBetAmountHg(betParamVo.get大25_3Amount());
            SP012_HG大25_3(betParamVo);
        }

        /** 6、大3/3.5, 体彩小 0123,皇冠 大3/3.5 ,3球体彩赢，皇冠输一半*/
        Double 大3_35 = betParamVo.get大3_35();
        if (大3_35 != null && 大3_35 != 0) {
            log.info("      体彩 0123, 皇冠 大3/3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大3_35);
            betParamVo.setBetAmountHg(betParamVo.get大3_35Amount());
            SP012_HG大3_35(betParamVo);
        }

        /** 7、小3.5, 体彩大 4567+,皇冠 小3.5 */
        Double 小35 = betParamVo.get小35();
        if (小35 != null && 小35 != 0) {
            log.info("      体彩 4567+, 皇冠 小3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(小35);
            betParamVo.setBetAmountHg(betParamVo.get小35Amount());
            SP4567_HG小35(betParamVo);
        }

        /** 8、小3.5/4, 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半 */
        Double 小35_4 = betParamVo.get小35_4();
        if (小35_4 != null && 小35_4 != 0) {
            log.info("      体彩 4567+, 皇冠 小3.5/4 ------------------------------------------------------");
            betParamVo.setOddsHg(小35_4);
            betParamVo.setBetAmountHg(betParamVo.get小35_4Amount());
            SP4567_HG小35_4(betParamVo);
        }
    }

    /**
     * 单关
     * @param betParamVo
     */
    public void betCheckSingle(BetParamVo betParamVo) {
        /** 体彩主队胜，皇冠客队加 */
        Double visitAdd05 = betParamVo.getVisitAdd05();
        Double oddsWin = betParamVo.getOddsWin();
        if (oddsWin != 0 && visitAdd05 != 0) {
            log.info("    体彩 @主队胜, 皇冠 @客队加05 -----------------------------");
            SPWin_HGVisitAdd05(betParamVo);
            log.info("");
        }
        /** 体彩主队胜，皇冠客队减 */ // 要补平
        /*Double visitCut05 = betParamVo.getVisitCut05();
        if (visitCut05 != null && visitCut05 != 0) {
            log.info("    体彩 @主队胜, 皇冠 @客队减05 -----------------------------");
            SPWin_HGVisitCut05(betParamVo);
            log.info("");
        }*/
        /** 体彩主队负，皇冠主队加 */
        Double homeAdd05 = betParamVo.getHomeAdd05();
        Double oddsLose = betParamVo.getOddsLose();
        if (oddsLose != 0 && homeAdd05 != 0) {
            log.info("    体彩 @主队负, 皇冠 @主队加05 -----------------------------");
            SPLose_HGHomeAdd05(betParamVo);
            log.info("");
        }
        /** 体彩主队负，皇冠主队减 */ // 要补平
        /*Double homeCut05 = betParamVo.getHomeCut05();
        if (homeCut05 != null && homeCut05 != 0) {
            log.info("    体彩 @主队负, 皇冠 @主队减05 -----------------------------");
            SPLose_HGHomeCut05(betParamVo);
            log.info("");
        }*/

        /** 体彩主队胜，皇冠（和局、客队胜） */
        Double home = betParamVo.getHome();
        Double tie = betParamVo.getTie();
        Double visit = betParamVo.getVisit();
        if (home!=0 && tie!=0 && visit!=0) {
            log.info("    体彩 @主队胜, 皇冠 @和局 @客胜 -----------------------------");
            SPWin_HGTieAndLose(betParamVo);
            log.info("");
            /** 体彩平，皇冠（主队胜、客队胜） */
            log.info("    体彩 @平, 皇冠 @主胜 @客胜 -----------------------------");
            SPTie_HGWinAndLose(betParamVo);
            log.info("");
            /** 体彩主队负，皇冠（和局、主队胜） */
            log.info("    体彩 @主负, 皇冠 @平 @主胜 -----------------------------");
            SPLose_HGWinAndTie(betParamVo);
            log.info("");
        }


        Double homeCut05 = betParamVo.getHomeCut05();
        Double oddsRangLose = betParamVo.getOddsRangLose();
        /** 体彩主队让球负(体彩主队-)，皇冠（主队减05胜） */
        if (oddsRangLose!=0 && homeCut05 != 0) {
            log.info("    体彩 @主队让球负, 皇冠 @主队减05 -----------------------------");
            SPRangLose_HGHomeCut05(betParamVo);
            log.info("");
        }

//        Double oddsRangWin = betParamVo.getOddsRangWin();
        /** 体彩主队让球胜(体彩主队-)，皇冠（客队加05胜） */ // 要补平
        /*if (oddsRangWin != 0 && visitAdd05 != 0) {
            log.info("    体彩 @主队让球胜, 皇冠 @客队加05 -----------------------------");
            SPRangWin_HGVisitAdd05(betParamVo);
            log.info("");
        }*/

        /** 体彩主队受球胜(体彩主队+)，皇冠（客队减05胜） */
        Double oddsShouWin = betParamVo.getOddsShouWin();
        Double visitCut05 = betParamVo.getVisitCut05();
        if (oddsShouWin != 0 && visitCut05 != 0) {
            log.info("    体彩 @主队受球胜, 皇冠 @客队减05 -----------------------------");
            SPShouWin_HGHomeCut05(betParamVo);
            log.info("");
        }

    }

    /**
     * 体彩主队胜，皇冠（和局、客队胜）
     */
    public void SPWin_HGTieAndLose(BetParamVo betParamVo) {
        Double oddsWin = betParamVo.getOddsWin();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHgTie = betParamVo.getTie();
        Double oddsHgVisit = betParamVo.getVisit();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsWin, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHgTie = CalcUtil.div(baseAmount, oddsHgTie);
        Double betAmountHgVisit = CalcUtil.div(baseAmount, oddsHgVisit);

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgTie, betAmountHgVisit), rebateHG);
        // 体彩收益
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHgTie, betAmountHgVisit);

        /** 皇冠和局收益:皇冠和局奖金 - 皇冠客队胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHgTie = CalcUtil.mul(CalcUtil.sub(oddsHgTie, 1), betAmountHgTie);
        // 皇冠奖金返水 = 和局奖金 + 客胜本金
        Double rebateHgBonusTie = CalcUtil.mul(CalcUtil.add(bonusHgTie, betAmountHgVisit), rebateHG);
        Double rewardHgTie = CalcUtil.sub(CalcUtil.add(bonusHgTie, rebateSpAmount, rebateHgBonusTie), betAmountSp, betAmountHgVisit);

        /** 皇冠客胜收益:皇冠客胜奖金 - 皇冠和局本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHgVisit = CalcUtil.mul(CalcUtil.sub(oddsHgVisit, 1), betAmountHgVisit);
        // 皇冠奖金返水 = 客胜奖金 + 和局本金
        Double rebateHgBonusVisit = CalcUtil.mul(CalcUtil.add(bonusHgVisit, betAmountHgTie), rebateHG);
        Double rewardHgVisit = CalcUtil.sub(CalcUtil.add(bonusHgVisit, rebateSpAmount, rebateHgBonusVisit), betAmountSp, betAmountHgTie);


        // TODO 调配金额
//        if(rewardSp > 0 && rewardHgTie > 0 && rewardHgVisit >0 ){
        //如果皇冠和收益大于体彩收益20+
        if(rewardHgTie - rewardSp > 20){
            Double differ = rewardHgTie - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getTie());
            betAmountHgTie = betAmountHgTie - revision;
        }
        if(rewardSp - rewardHgTie > 20){
            Double differ = rewardSp - rewardHgTie;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getTie());
            betAmountHgTie = betAmountHgTie + revision;
        }
        //如果皇冠客队胜收益大于体彩收益20+
        if(rewardHgVisit - rewardSp > 20){
            Double differ = rewardHgVisit - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getVisit());
            betAmountHgVisit= betAmountHgVisit - revision;
        }
        if(rewardSp - rewardHgVisit > 20){
            Double differ = rewardHgVisit - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getVisit());
            betAmountHgVisit= betAmountHgVisit + revision;
        }
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgTie, betAmountHgVisit), rebateHG);
        // 体彩收益
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHgTie, betAmountHgVisit);

        /** 皇冠和局收益:皇冠和局奖金 - 皇冠客队胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        bonusHgTie = CalcUtil.mul(CalcUtil.sub(oddsHgTie, 1), betAmountHgTie);
        // 皇冠奖金返水 = 和局奖金 + 客胜本金
        rebateHgBonusTie = CalcUtil.mul(CalcUtil.add(bonusHgTie, betAmountHgVisit), rebateHG);
        rewardHgTie = CalcUtil.sub(CalcUtil.add(bonusHgTie, rebateSpAmount, rebateHgBonusTie), betAmountSp, betAmountHgVisit);

        /** 皇冠客胜收益:皇冠客胜奖金 - 皇冠和局本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        bonusHgVisit = CalcUtil.mul(CalcUtil.sub(oddsHgVisit, 1), betAmountHgVisit);
        // 皇冠奖金返水 = 客胜奖金 + 和局本金
        rebateHgBonusVisit = CalcUtil.mul(CalcUtil.add(bonusHgVisit, betAmountHgTie), rebateHG);
        rewardHgVisit = CalcUtil.sub(CalcUtil.add(bonusHgVisit, rebateSpAmount, rebateHgBonusVisit), betAmountSp, betAmountHgTie);
        if(rewardSp > 0 && rewardHgTie > 0 && rewardHgVisit > 0) {
            log.info("调整后 体彩投注：主胜 @" + oddsWin + ", 投 " + betAmountSp.intValue() + ", 收益：" + rewardSp              + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
            log.info("调整后 皇冠投注：和局 @" + oddsHgTie + ", 投" + betAmountHgTie.intValue() + ", 收益：" + rewardHgTie       + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHgTie, betAmountHgTie, 4), 100) + "％");
            log.info("调整后 皇冠投注：客胜 @" + oddsHgVisit + ", 投" + betAmountHgVisit.intValue() + ", 收益：" + rewardHgVisit + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHgVisit, betAmountHgVisit, 4), 100) + "％");
        }

//        }else {
//            log.info("体彩主队胜，皇冠（和局、客队胜）收益为负 不可打");
//        }

    }

    /** 体彩平，皇冠（主队胜、客队胜） */
    public void SPTie_HGWinAndLose(BetParamVo betParamVo) {
        Double oddsTie = betParamVo.getOddsTie();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHgHome = betParamVo.getHome();
        Double oddsHgVisit = betParamVo.getVisit();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsTie, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHgHome = CalcUtil.div(baseAmount, oddsHgHome);
        Double betAmountHgVisit = CalcUtil.div(baseAmount, oddsHgVisit);

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsTie, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgHome, betAmountHgVisit), rebateHG);
        // 体彩收益
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHgHome, betAmountHgVisit);

        /** 皇冠和局收益:皇冠和局奖金 - 皇冠客队胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHgHome = CalcUtil.mul(CalcUtil.sub(oddsHgHome, 1), betAmountHgHome);
        // 皇冠奖金返水 = 和局奖金 + 客胜本金
        Double rebateHgBonusTie = CalcUtil.mul(CalcUtil.add(bonusHgHome, betAmountHgVisit), rebateHG);
        Double rewardHgHome = CalcUtil.sub(CalcUtil.add(bonusHgHome, rebateSpAmount, rebateHgBonusTie), betAmountSp, betAmountHgVisit);

        /** 皇冠客胜收益:皇冠客胜奖金 - 皇冠主胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHgVisit = CalcUtil.mul(CalcUtil.sub(oddsHgVisit, 1), betAmountHgVisit);
        // 皇冠奖金返水 = 客胜奖金 + 和局本金
        Double rebateHgBonusVisit = CalcUtil.mul(CalcUtil.add(bonusHgVisit, betAmountHgHome), rebateHG);
        Double rewardHgVisit = CalcUtil.sub(CalcUtil.add(bonusHgVisit, rebateSpAmount, rebateHgBonusVisit), betAmountSp, betAmountHgHome);



        // TODO 调配金额
//        if(rewardSp > 0 && rewardHgHome > 0 && rewardHgVisit >0 ){
        //如果皇冠和收益大于体彩收益20+
        if(rewardHgHome - rewardSp > 20){
            Double differ = rewardHgHome - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getTie());
            betAmountHgHome = betAmountHgHome - revision;
        }
        if(rewardSp - rewardHgHome > 20){
            Double differ = rewardHgHome - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getTie());
            betAmountHgHome = betAmountHgHome + revision;
        }
        //如果皇冠客队胜收益大于体彩收益20+
        if(rewardHgVisit - rewardSp > 20){
            Double differ = rewardHgVisit - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getHome());
            betAmountHgVisit= betAmountHgVisit - revision;
        }
        if(rewardSp - rewardHgVisit > 20){
            Double differ = rewardHgVisit - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getHome());
            betAmountHgVisit= betAmountHgVisit + revision;
        }
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgHome, betAmountHgVisit), rebateHG);
        // 体彩收益
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHgHome, betAmountHgVisit);

        /** 皇冠和局收益:皇冠和局奖金 - 皇冠客队胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        bonusHgHome = CalcUtil.mul(CalcUtil.sub(oddsHgHome, 1), betAmountHgHome);
        // 皇冠奖金返水 = 和局奖金 + 客胜本金
        rebateHgBonusTie = CalcUtil.mul(CalcUtil.add(bonusHgHome, betAmountHgVisit), rebateHG);
        rewardHgHome = CalcUtil.sub(CalcUtil.add(bonusHgHome, rebateSpAmount, rebateHgBonusTie), betAmountSp, betAmountHgVisit);

        /** 皇冠客胜收益:皇冠客胜奖金 - 皇冠主胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        bonusHgVisit = CalcUtil.mul(CalcUtil.sub(oddsHgVisit, 1), betAmountHgVisit);
        // 皇冠奖金返水 = 客胜奖金 + 主胜本金
        rebateHgBonusVisit = CalcUtil.mul(CalcUtil.add(bonusHgVisit, betAmountHgHome), rebateHG);
        rewardHgVisit = CalcUtil.sub(CalcUtil.add(bonusHgVisit, rebateSpAmount, rebateHgBonusVisit), betAmountSp, betAmountHgHome);
        if(rewardSp > 0 && rewardHgHome > 0 && rewardHgVisit > 0){
            log.info(" 体彩投注：和局 @" + oddsTie + ", 投 " + betAmountSp.intValue() + ", 收益：" + rewardSp              + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
            log.info(" 皇冠投注：主胜 @" + oddsHgHome + ", 投" + betAmountHgHome.intValue() + ", 收益：" + rewardHgHome    + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHgHome, betAmountHgHome, 4), 100) + "％");
            log.info(" 皇冠投注：客胜 @" + oddsHgVisit + ", 投" + betAmountHgVisit.intValue() + ", 收益：" + rewardHgVisit + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHgVisit, betAmountHgVisit, 4), 100) + "％");
        }

//        }else {
//            log.info("体彩平，皇冠（主队胜、客队胜） 收益为负 不可打");
//        }

    }
    /** 体彩主队负，皇冠（和局、主队胜） */
    public void SPLose_HGWinAndTie(BetParamVo betParamVo) {
        Double oddsLose = betParamVo.getOddsLose();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHgTie = betParamVo.getTie();
        Double oddsHgHome = betParamVo.getHome();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsLose, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHgTie = CalcUtil.div(baseAmount, oddsHgTie);
        Double betAmountHgHome = CalcUtil.div(baseAmount, oddsHgHome);

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgTie, betAmountHgHome), rebateHG);
        // 体彩收益
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHgTie, betAmountHgHome);

        /** 皇冠和局收益:皇冠和局奖金 - 皇冠客队胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHgTie = CalcUtil.mul(CalcUtil.sub(oddsHgTie, 1), betAmountHgTie);
        // 皇冠奖金返水 = 和局奖金 + 客胜本金
        Double rebateHgBonusTie = CalcUtil.mul(CalcUtil.add(bonusHgTie, betAmountHgHome), rebateHG);
        Double rewardHgTie = CalcUtil.sub(CalcUtil.add(bonusHgTie, rebateSpAmount, rebateHgBonusTie), betAmountSp, betAmountHgHome);

        /** 皇冠主胜收益:皇冠客胜奖金 - 皇冠主胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHgHome = CalcUtil.mul(CalcUtil.sub(oddsHgHome, 1), betAmountHgHome);
        // 皇冠奖金返水 = 客胜奖金 + 和局本金
        Double rebateHgBonusHome = CalcUtil.mul(CalcUtil.add(bonusHgHome, betAmountHgTie), rebateHG);
        Double rewardHgHome = CalcUtil.sub(CalcUtil.add(bonusHgHome, rebateSpAmount, rebateHgBonusHome), betAmountSp, betAmountHgTie);


        // TODO 调配金额
        revision(betParamVo, oddsLose, betAmountSp, oddsHgTie, oddsHgHome, betAmountHgTie, betAmountHgHome, rebateSpAmount, bonusSp, rewardSp, rewardHgTie, rewardHgHome);

    }

    private static void revision(BetParamVo betParamVo, Double oddsLose, Double betAmountSp, Double oddsHgTie, Double oddsHgHome, Double betAmountHgTie, Double betAmountHgHome, Double rebateSpAmount, Double bonusSp, Double rewardSp, Double rewardHgTie, Double rewardHgHome) {
        Double rebateHgAmount;
        Double bonusHgHome;
        Double rebateHgBonusTie;
        Double rebateHgBonusHome;
        Double bonusHgTie;
//        if(rewardSp > 0 && rewardHgTie > 0 && rewardHgHome >0 ){
        //如果皇冠和收益大于体彩收益20+
        if(rewardHgTie - rewardSp > 20){
            Double differ = rewardHgTie - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getTie());
            betAmountHgTie = betAmountHgTie - revision;
        }
        if(rewardSp - rewardHgTie > 20){
            Double differ = rewardHgTie - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getTie());
            betAmountHgTie = betAmountHgTie + revision;
        }
        //如果皇冠主队胜收益大于体彩收益20+
        if(rewardHgHome - rewardSp > 20){
            Double differ = rewardHgHome - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getHome());
            betAmountHgHome = betAmountHgHome - revision;
        }
        if(rewardSp - rewardHgHome > 20){
            Double differ = rewardSp - rewardHgHome;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getHome());
            betAmountHgHome = betAmountHgHome + revision;
        }
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgTie, betAmountHgHome), rebateHG);
        // 体彩收益
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHgTie, betAmountHgHome);

        /** 皇冠和局收益:皇冠和局奖金 - 皇冠客队胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        bonusHgTie = CalcUtil.mul(CalcUtil.sub(oddsHgTie, 1), betAmountHgTie);
        // 皇冠奖金返水 = 和局奖金 + 客胜本金
        rebateHgBonusTie = CalcUtil.mul(CalcUtil.add(bonusHgTie, betAmountHgHome), rebateHG);
        rewardHgTie = CalcUtil.sub(CalcUtil.add(bonusHgTie, rebateSpAmount, rebateHgBonusTie), betAmountSp, betAmountHgHome);

        /** 皇冠客胜收益:皇冠客胜奖金 - 皇冠主胜本金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        bonusHgHome = CalcUtil.mul(CalcUtil.sub(oddsHgHome, 1), betAmountHgHome);
        // 皇冠奖金返水 = 客胜奖金 + 主胜本金
        rebateHgBonusHome = CalcUtil.mul(CalcUtil.add(bonusHgHome, betAmountHgTie), rebateHG);
        rewardHgHome = CalcUtil.sub(CalcUtil.add(bonusHgHome, rebateSpAmount, rebateHgBonusHome), betAmountSp, betAmountHgTie);


        if(rewardSp > 0 && rewardHgTie > 0 && rewardHgHome > 0){
            log.info(" 体彩投注：主负 @" + oddsLose + ", 投 " + betAmountSp.intValue() + ", 收益：" + rewardSp.intValue()  + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
            log.info(" 皇冠投注：和局 @" + oddsHgTie + ", 投" + betAmountHgTie.intValue() + ", 收益：" + rewardHgTie.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHgTie, betAmountHgTie, 4), 100) + "％");
            log.info(" 皇冠投注：主胜 @" + oddsHgHome + ", 投" + betAmountHgHome.intValue() + ", 收益：" + rewardHgHome.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHgHome, betAmountHgHome, 4), 100) + "％");
        }

//        }else {
//            log.info("体彩主负，皇冠（平、主队胜） 收益为负 不可打");
//        }
    }




    /**
     * 体彩主队胜，皇冠客队加
     * @param betParamVo
     */
    public void SPWin_HGVisitAdd05(BetParamVo betParamVo) {
        Double oddsWin = betParamVo.getOddsWin();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHg = betParamVo.getVisitAdd05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsWin, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHg = CalcUtil.div(baseAmount, CalcUtil.add(oddsHg, 1));

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        /** 皇冠中奖收益:皇冠奖金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHg = CalcUtil.mul(oddsHg, betAmountHg);
        // 皇冠奖金返水
        Double rebateHgBonus = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSpAmount, rebateHgBonus), betAmountSp);

        // 调配金额
        betAmountHg = AdaptationAmount.amountDeployment(betAmountSp, oddsHg, betAmountHg, rewardSp, rewardHg);
        if (betAmountHg == null) {
            return;
        }

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        log.info("体彩投注：主 胜 @" + oddsWin + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：客 +05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
    }

    /**
     * 体彩主队让球胜，皇冠客队加
     * @param betParamVo
     */
    public void SPRangWin_HGVisitAdd05(BetParamVo betParamVo) {
        Double oddsWin = betParamVo.getOddsRangWin();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHg = betParamVo.getVisitAdd05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsWin, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHg = CalcUtil.div(baseAmount, CalcUtil.add(oddsHg, 1));

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        /** 皇冠中奖收益:皇冠奖金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHg = CalcUtil.mul(oddsHg, betAmountHg);
        // 皇冠奖金返水
        Double rebateHgBonus = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSpAmount, rebateHgBonus), betAmountSp);

        // 调配金额
        betAmountHg = AdaptationAmount.amountDeployment(betAmountSp, oddsHg, betAmountHg, rewardSp, rewardHg);
        if (betAmountHg == null) {
            return;
        }

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        log.info("体彩投注：主 让球胜 @" + oddsWin + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：客 +05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
    }

    /**
     * 体彩主队受球胜(体彩主队+)，皇冠（客队减05胜
     * @param betParamVo
     */
    public void SPShouWin_HGHomeCut05(BetParamVo betParamVo) {
        Double oddsWin = betParamVo.getOddsShouWin();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHg = betParamVo.getVisitCut05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsWin, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHg = CalcUtil.div(baseAmount, CalcUtil.add(oddsHg, 1));

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        /** 皇冠中奖收益:皇冠奖金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHg = CalcUtil.mul(oddsHg, betAmountHg);
        // 皇冠奖金返水
        Double rebateHgBonus = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSpAmount, rebateHgBonus), betAmountSp);

        // 调配金额
        betAmountHg = AdaptationAmount.amountDeployment(betAmountSp, oddsHg, betAmountHg, rewardSp, rewardHg);
        if (betAmountHg == null) {
            return;
        }

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        log.info("体彩投注：主 受球胜 @" + oddsWin + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：客 -05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
    }

    /**
     * 体彩主队负，皇冠主队加
     * @param betParamVo
     */
    public void SPLose_HGHomeAdd05(BetParamVo betParamVo) {
        Double oddsLose = betParamVo.getOddsLose();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHg = betParamVo.getHomeAdd05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsLose, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHg = CalcUtil.div(baseAmount, CalcUtil.add(oddsHg, 1));

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        /** 皇冠中奖收益:皇冠奖金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHg = CalcUtil.mul(oddsHg, betAmountHg);
        // 皇冠奖金返水
        Double rebateHgBonus = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSpAmount, rebateHgBonus), betAmountSp);

        // 调配金额
        betAmountHg = AdaptationAmount.amountDeployment(betAmountSp, oddsHg, betAmountHg, rewardSp, rewardHg);
        if (betAmountHg == null) {
            return;
        }

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        log.info("体彩投注：主负 @" + oddsLose + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：主 +05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
    }

    /**
     * 体彩主队让球负，皇冠主队减05
     * @param betParamVo
     */
    public void SPRangLose_HGHomeCut05(BetParamVo betParamVo) {
        Double oddsLose = betParamVo.getOddsRangLose();
        Double betAmountSp = betParamVo.getBetBaseAmount();
        Double oddsHg = betParamVo.getHomeCut05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(oddsLose, betAmountSp);
        // 计算皇冠投注金额
        Double betAmountHg = CalcUtil.div(baseAmount, CalcUtil.add(oddsHg, 1));

        // 体彩返水
        Double rebateSpAmount = CalcUtil.mul(betAmountSp, rebateSP);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        Double bonusSp = CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp);
        // 皇冠全输返水
        Double rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        Double rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        /** 皇冠中奖收益:皇冠奖金 - 体彩本金 + 体彩返水 + 皇冠返水 */
        // 皇冠奖金
        Double bonusHg = CalcUtil.mul(oddsHg, betAmountHg);
        // 皇冠奖金返水
        Double rebateHgBonus = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSpAmount, rebateHgBonus), betAmountSp);

        // 调配金额
        betAmountHg = AdaptationAmount.amountDeployment(betAmountSp, oddsHg, betAmountHg, rewardSp, rewardHg);
        if (betAmountHg == null) {
            return;
        }

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);

        log.info("体彩投注：主 让球负 @" + oddsLose + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：主 -05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, betAmountSp, 4), 100) + "％");
    }

    /**
     * 大15
     * 体彩小 01,皇冠 全输全赢
     * @param betParamVo
     */
    public void SP01_HG大15(BetParamVo betParamVo) {
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountHg;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountHg = betParamTemp.getBetAmountHg();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountHg = betParamVo.getBetAmountHg();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();

        double oddsOne = betParamVo.getOddsOne();

        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();

        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue());
        log.info("皇冠投注：大1.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne).intValue() + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountHg);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountHg);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

    /**
     * 大2
     * 体彩小 012,皇冠 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大2(BetParamVo betParamVo) {
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
        log.info("皇冠投注：大2 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo).intValue() + ", 皇冠总投注：" + betAmountHg.intValue());
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
     * 大3
     * 体彩小 0123,皇冠 大3 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大3(BetParamVo betParamVo) {
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
        log.info("皇冠投注：大3 @" + oddsHg + ", " + betAmountHg.intValue());
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
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = betAmountOne * oddsOne; // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = betAmountTwo * oddsTwo; // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");
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
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = betAmountOne * oddsOne; // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = betAmountTwo * oddsTwo; // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");
    }

    /**
     * 大1.5/2
     * 体彩小 012,皇冠 大1.5/2, 2球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    public void SP012_HG大15_2(BetParamVo betParamVo) {
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
        log.info("皇冠投注：大2.5/3 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountHg);

        /** 体彩中球 */
        // 皇冠出奖
        Double rewardHGHalf = CalcUtil.div(CalcUtil.mul(betAmountHg, oddsHg), 2);
        // 皇冠返水
        double rebateHGAmountHalf = CalcUtil.mul(rewardHGHalf, rebateHG);
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
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        betParamVo.setRewardHG(bonusHg);
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
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
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, rewardAmountHalf);
        betParamVo.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");
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
     * 大2.5/3
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
        double rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
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
        log.info("皇冠投注：小3.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

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

        log.info("体彩投注：4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小3.5/4 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

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
        double rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamVo.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
    }

}

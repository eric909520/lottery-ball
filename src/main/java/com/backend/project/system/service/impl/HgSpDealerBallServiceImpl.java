package com.backend.project.system.service.impl;

import com.backend.common.utils.AdaptationAmount;
import com.backend.common.utils.CalcUtil;
import com.backend.common.utils.http.HttpUtils;
import com.backend.project.system.domain.NotifyMsg;
import com.backend.project.system.domain.vo.BetBasketballParamVo;
import com.backend.project.system.domain.vo.BetParamVo;
import com.backend.project.system.enums.BetTypeEnum;
import com.backend.project.system.enums.HandicapEnum;
import com.backend.project.system.mapper.NotifyMsgMapper;
import com.backend.project.system.service.IHgSPDealerBallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author
 */
@Slf4j
@Service
public class HgSpDealerBallServiceImpl implements IHgSPDealerBallService {

    @Resource
    private NotifyMsgMapper notifyMsgMapper;

    @Value("${tgApi.url}")
    private String tgUrl;

    @Resource
    private HgSpBallServiceImpl hgSpBallService;

    // 体彩固定返水比例
    private final static double rebateSP = 0.12;

    //体彩篮球返水
    private final static double rebateSPBK = 0.15;

    // 皇冠固定返水比例
    private final static double rebateHG = 0.026;

    private static double baseAmount = 50000;

    /**
     * 012
     * @param betParamVo
     */
    @Override
    public void betCheck012(BetParamVo betParamVo) {
        /** 大1.5, 体彩小 01,皇冠 大1.5, 全输全赢 */
        Double 大15 = betParamVo.get大15();
        if (大15 != null && 大15 != 0) {
            log.info("      @@足球, 体彩 01, 皇冠 大1.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大15);
            SP01_HG大15(betParamVo);
            log.info("");
        }

        /** 1、大2, 体彩小 012,皇冠 大2, 全输全赢 */
        /*Double 大2 = betParamVo.get大2();
        if (大2 != null && 大2 != 0) {
            log.info("      @@足球, 体彩 012, 皇冠 大2 ------------------------------------------------------");
            betParamVo.setOddsHg(大2);
            SP012_HG大2(betParamVo);
            log.info("");
        }*/

        /** 2、大2.5, 体彩小 012,皇冠 大2.5, 全输全赢 */
        Double 大25 = betParamVo.get大25();
        if (大25 != null && 大25 != 0) {
            log.info("      @@足球, 体彩 012, 皇冠 大2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大25);
            SP012_HG大25(betParamVo);
            log.info("");
        }

        /** 大3, 体彩小 0123,皇冠 大3, 全输全赢 */
        /*Double 大3 = betParamVo.get大3();
        if (大3 != null && 大3 != 0) {
            log.info("      @@足球, 体彩 0123, 皇冠 大3 ------------------------------------------------------");
            betParamVo.setOddsHg(大3);
            SP012_HG大3(betParamVo);
            log.info("");
        }*/

        /** 3、大3.5, 体彩小 0123,皇冠 大3.5, 全输全赢 */
        Double 大35 = betParamVo.get大35();
        if (大35 != null && 大35 != 0) {
            log.info("      @@足球, 体彩 0123, 皇冠 大3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大35);
            SP012_HG大35(betParamVo);
            log.info("");
        }

        /** 大1.5/2, 体彩小 012,皇冠 大1.5/2, 2球体彩赢，皇冠赢一半 */
        Double 大15_2 = betParamVo.get大15_2();
        if (大15_2 != null && 大15_2 != 0) {
            log.info("      @@足球, 体彩 012, 皇冠 大1.5/2 ------------------------------------------------------");
            betParamVo.setOddsHg(大15_2);
            SP012_HG大15_2(betParamVo);
            log.info("");
        }

        /** 4、大2/2.5, 体彩小 012,皇冠 大2/2.5, 2球皇冠输一半 */
        Double 大2_25 = betParamVo.get大2_25();
        if (大2_25 != null && 大2_25 != 0) {
            log.info("      @@足球, 体彩 012, 皇冠 大2/2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大2_25);
            SP012_HG大2_25(betParamVo);
            log.info("");
        }

        /** 5、大2.5/3, 体彩小 0123,皇冠 大2.5/3, 3球体彩赢，皇冠赢一半 */
        Double 大25_3 = betParamVo.get大25_3();
        if (大25_3 != null && 大25_3 != 0) {
            log.info("      @@足球, 体彩 0123, 皇冠 大2.5/3 ------------------------------------------------------");
            betParamVo.setOddsHg(大25_3);
            SP012_HG大25_3(betParamVo);
            log.info("");
        }

        /** 6、大3/3.5, 体彩小 0123,皇冠 大3/3.5 ,3球体彩赢，皇冠输一半*/
        Double 大3_35 = betParamVo.get大3_35();
        if (大3_35 != null && 大3_35 != 0) {
            log.info("      @@足球, 体彩 0123, 皇冠 大3/3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(大3_35);
            SP012_HG大3_35(betParamVo);
            log.info("");
        }

        /** 小2.5, 体彩大 34567+,皇冠 小2.5 */
        Double 小25 = betParamVo.get小25();
        if (小25 != null && 小25 != 0) {
            log.info("      @@足球, 体彩 34567+, 皇冠 小2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(小25);
            SP34567_HG小25(betParamVo);
            log.info("");
        }

        /** 7、小3.5, 体彩大 4567+,皇冠 小3.5 */
        Double 小35 = betParamVo.get小35();
        if (小35 != null && 小35 != 0) {
            log.info("      @@足球, 体彩 4567+, 皇冠 小3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(小35);
            SP4567_HG小35(betParamVo);
            log.info("");
        }

        /** 小2.5/3, 体彩大 34567+,皇冠 小2.5/3 */
        Double 小25_3 = betParamVo.get小25_3();
        if (小25_3 != null && 小25_3 != 0) {
            log.info("      @@足球, 体彩 34567+, 皇冠 小2.5/3 ------------------------------------------------------");
            betParamVo.setOddsHg(小25_3);
            SP34567_HG小25_3(betParamVo);
            log.info("");
        }

        /** 小2/2.5, 体彩大 234567+,皇冠 小2/2.5 */
        Double 小2_25 = betParamVo.get小2_25();
        if (小2_25 != null && 小2_25 != 0) {
            log.info("      @@足球, 体彩 234567+, 皇冠 小2/2.5 ------------------------------------------------------");
            betParamVo.setOddsHg(小2_25);
            SP234567_HG小2_25(betParamVo);
            log.info("");
        }

        /** 小3/3.5, 体彩大 34567+,皇冠 小3/3.5 */
        Double 小3_35 = betParamVo.get小3_35();
        if (小3_35 != null && 小3_35 != 0) {
            log.info("      @@足球, 体彩 34567+, 皇冠 小3/3.5 ------------------------------------------------------");
            betParamVo.setOddsHg(小3_35);
            SP34567_HG小3_35(betParamVo);
            log.info("");
        }

        /** 8、小3.5/4, 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半 */
        Double 小35_4 = betParamVo.get小35_4();
        if (小35_4 != null && 小35_4 != 0) {
            log.info("      @@足球, 体彩 4567+, 皇冠 小3.5/4 ------------------------------------------------------");
            betParamVo.setOddsHg(小35_4);
            SP4567_HG小35_4(betParamVo);
            log.info("");
        }

        /** 皇冠总进球 */
        /*Double zong0_1 = betParamVo.getZong0_1();
        Double zong2_3 = betParamVo.getZong2_3();
        Double zong4_6 = betParamVo.getZong4_6();
        Double zong7 = betParamVo.getZong7();
        if (zong0_1!=0 && zong2_3!=0 && zong4_6!=0 && zong7!=0) {
            *//** 体彩0123，皇冠 1）总进球4-6,  2）总进球7+ *//*
            log.info("      @@足球, 体彩 0123, 皇冠 ①总进球4-6,  ②总进球7+ ------------------------------------------------------");
            SP0123_HGZong4_6_7(betParamVo);
            log.info("");

            *//** 体彩01456，皇冠 1）总进球2-3,  2）总进球7+ *//*
            log.info("      @@足球, 体彩 01456, 皇冠 ①总进球2-3,  ②总进球7+ ------------------------------------------------------");
            SP01456_HGZong2_3_7(betParamVo);
            log.info("");

            *//** 体彩017+，皇冠 1）总进球2-3,  2）总进球4-6 *//*
            log.info("      @@足球, 体彩 017+, 皇冠 ①总进球2-3,  ②总进球4-6 ------------------------------------------------------");
            SP017_HGZong2_3_4_6(betParamVo);
            log.info("");

            *//** 体彩4567+，皇冠 1）总进球0-1,  2）总进球2-3 *//*
            log.info("      @@足球, 体彩 4567+, 皇冠 ①总进球0-1,  ②总进球2-3 ------------------------------------------------------");
            SP4567_HGZong0_1_2_3(betParamVo);
            log.info("");

        }*/
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
            log.info("       @@足球, 体彩 @主队胜, 皇冠 @客队加05 -----------------------------");
            SPWin_HGVisitAdd05(betParamVo);
            log.info("");
        }
        /** 体彩主队胜，皇冠客队减 */ // 要补平
        /*Double visitCut05 = betParamVo.getVisitCut05();
        if (visitCut05 != null && visitCut05 != 0) {
            log.info("       @@足球, 体彩 @主队胜, 皇冠 @客队减05 -----------------------------");
            SPWin_HGVisitCut05(betParamVo);
            log.info("");
        }*/
        /** 体彩主队负，皇冠主队加 */
        Double homeAdd05 = betParamVo.getHomeAdd05();
        Double oddsLose = betParamVo.getOddsLose();
        if (oddsLose != 0 && homeAdd05 != 0) {
            log.info("    @@足球, 体彩 @主队负, 皇冠 @主队加05 -----------------------------");
            SPLose_HGHomeAdd05(betParamVo);
            log.info("");
        }
        /** 体彩主队负，皇冠主队减 */ // 要补平
        /*Double homeCut05 = betParamVo.getHomeCut05();
        if (homeCut05 != null && homeCut05 != 0) {
            log.info("       @@足球, 体彩 @主队负, 皇冠 @主队减05 -----------------------------");
            SPLose_HGHomeCut05(betParamVo);
            log.info("");
        }*/

        /** 体彩主队胜，皇冠（和局、客队胜） */
        /*Double home = betParamVo.getHome();
        Double tie = betParamVo.getTie();
        Double visit = betParamVo.getVisit();
        if (home!=0 && tie!=0 && visit!=0) {
            log.info("       @@足球, 体彩 @主队胜, 皇冠 @和局 @客胜 -----------------------------");
            SPWin_HGTieAndLose(betParamVo);
            log.info("");
            *//** 体彩平，皇冠（主队胜、客队胜） *//*
            log.info("       @@足球, 体彩 @平, 皇冠 @主胜 @客胜 -----------------------------");
            SPTie_HGWinAndLose(betParamVo);
            log.info("");
            *//** 体彩主队负，皇冠（和局、主队胜） *//*
            log.info("       @@足球, 体彩 @主负, 皇冠 @平 @主胜 -----------------------------");
            SPLose_HGWinAndTie(betParamVo);
            log.info("");
        }*/

        Double homeCut05 = betParamVo.getHomeCut05();
        Double oddsRangLose = betParamVo.getOddsRangLose();
        /** 体彩主队让球负(体彩主队-)，皇冠（主队减05胜） */
        if (oddsRangLose!=0 && homeCut05 != 0) {
            log.info("       @@足球, 体彩 @主队让球负, 皇冠 @主队减05 -----------------------------");
            SPRangLose_HGHomeCut05(betParamVo);
            log.info("");
        }

//        Double oddsRangWin = betParamVo.getOddsRangWin();
        /** 体彩主队让球胜(体彩主队-)，皇冠（客队加05胜） */ // 要补平
        /*if (oddsRangWin != 0 && visitAdd05 != 0) {
            log.info("       @@足球, 体彩 @主队让球胜, 皇冠 @客队加05 -----------------------------");
            SPRangWin_HGVisitAdd05(betParamVo);
            log.info("");
        }*/

        /** 体彩主队受球胜(体彩主队+)，皇冠（客队减05胜） */
        Double oddsShouWin = betParamVo.getOddsShouWin();
        Double visitCut05 = betParamVo.getVisitCut05();
        if (oddsShouWin != 0 && visitCut05 != 0) {
            log.info("       @@足球, 体彩 @主队受球胜, 皇冠 @客队减05 -----------------------------");
            SPShouWin_HGVisitCut05(betParamVo);
            log.info("");
        }

    }

    /**
     * 体彩主队胜，皇冠（和局、客队胜）
     */
    public void SPWin_HGTieAndLose(BetParamVo betParamVo) {
        Double oddsWin = betParamVo.getOddsWin();
        if (oddsWin == null || oddsWin == 0) {
            return;
        }
        Double oddsHgTie = betParamVo.getTie();
        Double oddsHgVisit = betParamVo.getVisit();
        //基础投注额为hg平
        Double betAmountHgTie = betParamVo.getBetBaseAmount();
        //计算hg客胜投注额
        Double betAmountHgVisit = calcBetAmount(betParamVo.getTie(),betAmountHgTie,betParamVo.getVisit());
        //计算体彩投注额
        Double betAmountSp = calcBetAmount(betParamVo.getTie(), betAmountHgTie, oddsWin);

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

        //如果皇冠和收益大于体彩收益20+
        if(rewardHgTie - rewardSp > 20){
            Double differ = rewardHgTie - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getOddsWin());
            betAmountSp = betAmountSp + revision;
        }
        if(rewardSp - rewardHgTie > 20){
            Double differ = rewardSp - rewardHgTie;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getOddsWin());
            betAmountSp = betAmountSp - revision;
        }
        //如果皇冠客队胜收益大于体彩收益20+
        if(rewardHgVisit - rewardHgTie > 20){
            Double differ = rewardHgVisit - rewardHgTie;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getVisit());
            betAmountHgVisit= betAmountHgVisit - revision;
        }
        if(rewardHgTie - rewardHgVisit > 20){
            Double differ = rewardHgTie - rewardHgVisit;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getVisit());
            betAmountHgVisit= betAmountHgVisit + revision;
        }
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgTie, betAmountHgVisit), rebateHG);
        // 体彩收益
        rewardSp = CalcUtil.sub(CalcUtil.add(CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp), rebateSpAmount, rebateHgAmount), betAmountHgTie, betAmountHgVisit);

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

            String msg = betParamVo.getMsg() + ",  体彩投注：主 胜 @" + oddsWin + ", 金额 " + betAmountSp.intValue()
                    + ",  皇冠投注：① 和局 @" + oddsHgTie + ", 金额 " + betAmountHgTie.intValue()
                    + ",  ② 客胜 @" + oddsHgVisit + ", 金额 " + betAmountHgVisit.intValue()
                    + ",  收益：" + rewardSp.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHgTie, betAmountHgVisit), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.hedge_SPHomeWin_HGVisitWinAndTie);
        }
    }

    /**
     *
     * @param x 皇冠基础投注倍数
     * @param baseAmount 基础投注金额
     * @param y 需要算出投注额的倍数
     * @return
     */
    private  Double calcBetAmount(double x,double baseAmount,double y){
        //应出奖金额
        Double award = CalcUtil.mul(baseAmount, x);
        Double betAmount = CalcUtil.div(award, y, 2);
        return  betAmount;
    }
    /** 体彩平，皇冠（主队胜、客队胜） */
    public void SPTie_HGWinAndLose(BetParamVo betParamVo) {
        Double oddsTie = betParamVo.getOddsTie();
        if (oddsTie == null || oddsTie == 0) {
            return;
        }
        Double oddsHgHome = betParamVo.getHome();
        Double oddsHgVisit = betParamVo.getVisit();
        //hg主胜投注额为基础投注额
        Double betAmountHgHome =betParamVo.getBetBaseAmount();
        //hg计算hg客胜投注额
        Double betAmountHgVisit = calcBetAmount(oddsHgHome, betAmountHgHome, oddsHgVisit);
        //计算体彩投注额
        Double betAmountSp =  calcBetAmount(oddsHgHome, betAmountHgHome, betParamVo.getOddsTie());

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

        //如果皇冠和收益大于体彩收益20+
        if(rewardHgHome - rewardSp > 20){
            Double differ = rewardHgHome - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getOddsTie());
            betAmountSp = betAmountSp + revision;
        }
        if(rewardSp - rewardHgHome > 20){
            Double differ = rewardSp - rewardHgHome;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getOddsTie());
            betAmountSp = betAmountSp - revision;
        }
        //如果皇冠客队胜收益大于体彩收益20+
        if(rewardHgVisit - rewardHgHome > 20){
            Double differ = rewardHgVisit - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getVisit());
            betAmountHgVisit = betAmountHgVisit - revision;
        }
        if(rewardHgHome - rewardHgVisit > 20){
            Double differ = rewardHgVisit - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getVisit());
            betAmountHgVisit= betAmountHgVisit + revision;
        }
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgHome, betAmountHgVisit), rebateHG);
        // 体彩收益
        rewardSp = CalcUtil.sub(CalcUtil.add(CalcUtil.mul(CalcUtil.sub(oddsTie, 1), betAmountSp), rebateSpAmount, rebateHgAmount), betAmountHgHome, betAmountHgVisit);

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

            String msg = betParamVo.getMsg() + ",  体彩投注：平 @" + oddsTie + ", 金额 " + betAmountSp.intValue()
                    + ",  皇冠投注：① 主胜 @" + oddsHgHome + ", 金额 " + betAmountHgHome.intValue()
                    + ",  ② 客胜 @" + oddsHgVisit + ", 金额 " + betAmountHgVisit.intValue()
                    + ",  收益：" + rewardSp.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHgHome, betAmountHgVisit), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.hedge_SPTie_HGHomeWinAndVisitWin);
        }
    }
    /** 体彩主队负，皇冠（和局、主队胜） */
    public void SPLose_HGWinAndTie(BetParamVo betParamVo) {
        Double oddsLose = betParamVo.getOddsLose();
        if (oddsLose == null || oddsLose == 0) {
            return;
        }
        Double oddsHgTie = betParamVo.getTie();
        Double oddsHgHome = betParamVo.getHome();
        //hg平为基础投注额
        Double betAmountHgTie = betParamVo.getBetBaseAmount();
        //计算hg主胜投注额
        Double betAmountHgHome = calcBetAmount(oddsHgTie,betAmountHgTie,oddsHgHome);
        //计算体彩投注额
        Double betAmountSp = calcBetAmount(oddsHgTie,betAmountHgTie,oddsLose);

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

        revision(betParamVo, oddsLose, betAmountSp, oddsHgTie, oddsHgHome, betAmountHgTie, betAmountHgHome, rebateSpAmount, bonusSp, rewardSp, rewardHgTie, rewardHgHome);

    }

    private void revision(BetParamVo betParamVo, Double oddsLose, Double betAmountSp, Double oddsHgTie, Double oddsHgHome, Double betAmountHgTie, Double betAmountHgHome, Double rebateSpAmount, Double bonusSp, Double rewardSp, Double rewardHgTie, Double rewardHgHome) {
        Double rebateHgAmount;
        Double bonusHgHome;
        Double rebateHgBonusTie;
        Double rebateHgBonusHome;
        Double bonusHgTie;
        //如果皇冠和收益大于体彩收益20+
        if(rewardHgTie - rewardSp > 20){
            Double differ = rewardHgTie - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getOddsLose());
            betAmountSp = betAmountSp + revision;
        }
        if(rewardSp - rewardHgTie > 20){
            Double differ = rewardHgTie - rewardSp;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getOddsLose());
            betAmountSp = betAmountSp - revision;
        }
        //如果皇冠主队胜收益大于皇冠平收益20+
        if(rewardHgHome - rewardHgTie > 20){
            Double differ = rewardHgHome - rewardHgTie;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getHome());
            betAmountHgHome = betAmountHgHome - revision;
        }
        if(rewardHgTie - rewardHgHome > 20){
            Double differ = rewardHgTie - rewardHgHome;
            //需要调整的金额
            Double revision = CalcUtil.div(differ, betParamVo.getHome());
            betAmountHgHome = betAmountHgHome + revision;
        }
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(CalcUtil.add(betAmountHgTie, betAmountHgHome), rebateHG);
        // 体彩收益
        rewardSp = CalcUtil.sub(CalcUtil.add(CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp), rebateSpAmount, rebateHgAmount), betAmountHgTie, betAmountHgHome);

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

            String msg = betParamVo.getMsg() + ",  体彩投注：客胜 @" + oddsLose + ", 金额 " + betAmountSp.intValue()
                    + ",  皇冠投注：① 和局 @" + oddsHgTie + ", 金额 " + betAmountHgTie.intValue()
                    + ",  ② 主胜 @" + oddsHgHome + ", 金额 " + betAmountHgHome.intValue()
                    + ",  收益：" + rewardSp.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHgTie, betAmountHgHome), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.hedge_SPVisitWin_HGHomeWinAndTie);
        }
    }

    /**
     * 体彩主队胜，皇冠客队加
     * @param betParamVo
     */
    @Override
    public BetParamVo SPWin_HGVisitAdd05(BetParamVo betParamVo) {
        Double oddsWin = betParamVo.getOddsWin();
        Double betAmountHg = betParamVo.getBetAmountHg();
        Double oddsHg = betParamVo.getVisitAdd05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(CalcUtil.add(oddsHg, 1), betAmountHg);
        // 计算体彩投注金额
        Double betAmountSp = CalcUtil.div(baseAmount, oddsWin);

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
        betAmountSp = AdaptationAmount.amountDeployment1(oddsWin, betAmountSp, rewardHg, rewardSp);
        if (betAmountSp == null) {
            return null;
        }
        betParamVo.setBetAmountWin(betAmountSp);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);
        if (rewardSp < CalcUtil.mul(CalcUtil.add(betAmountSp, betAmountHg), 0.01)) {
            return null;
        }

        log.info("体彩投注：主 胜 @" + oddsWin + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：客 +05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％");

        if (betParamVo.getNotifyFlag() == 1) {
            String msg = betParamVo.getMsg() + ",  体彩投注：主 胜 @" + oddsWin + ", 金额 " + betAmountSp.intValue()
                    + ",  皇冠投注：客 +05 @" + oddsHg + ", 金额 " + betAmountHg.intValue()
                    + ",  收益：" + rewardSp.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.hedge_SPHomeWin_HGVisitAdd05);
        }
        return betParamVo;
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
        betAmountHg = AdaptationAmount.amountDeployment1(CalcUtil.add(oddsHg, 1), betAmountHg, rewardSp, rewardHg);
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
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％");
    }

    /**
     * 体彩主队受球胜(体彩主队+)，皇冠（客队减05胜
     * @param betParamVo
     */
    @Override
    public BetParamVo SPShouWin_HGVisitCut05(BetParamVo betParamVo) {
        Double oddsWin = betParamVo.getOddsShouWin();
        Double betAmountHg = betParamVo.getBetAmountHg();
        Double oddsHg = betParamVo.getVisitCut05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(CalcUtil.add(oddsHg, 1), betAmountHg);
        // 计算体彩投注金额
        Double betAmountSp = CalcUtil.div(baseAmount, oddsWin);

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
        betAmountSp = AdaptationAmount.amountDeployment1(oddsWin, betAmountSp, rewardHg, rewardSp);
        if (betAmountSp == null) {
            return null;
        }
        betParamVo.setBetAmountShouWin(betAmountSp);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsWin, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);
        if (rewardSp < CalcUtil.mul(CalcUtil.add(betAmountSp, betAmountHg), 0.01)) {
            return null;
        }

        log.info("体彩投注：主 受球胜 @" + oddsWin + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：客 -05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％");

        if (betParamVo.getNotifyFlag() == 1) {
            String msg = betParamVo.getMsg() + ",  体彩投注：主 受球胜 @" + oddsWin + ", 金额 " + betAmountSp.intValue()
                    + ",  皇冠投注：客 -05 @" + oddsHg + ", 金额 " + betAmountHg.intValue()
                    + ",  收益：" + rewardSp.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.hedge_SPShouWin_HGVisitCut05);
        }
        return betParamVo;
    }

    /**
     * 体彩主队负，皇冠主队加
     * @param betParamVo
     */
    @Override
    public BetParamVo SPLose_HGHomeAdd05(BetParamVo betParamVo) {
        Double oddsLose = betParamVo.getOddsLose();
        Double betAmountHg = betParamVo.getBetAmountHg();
        Double oddsHg = betParamVo.getHomeAdd05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(CalcUtil.add(oddsHg, 1), betAmountHg);
        // 计算体彩投注金额
        Double betAmountSp = CalcUtil.div(baseAmount, oddsLose);

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
        betAmountSp = AdaptationAmount.amountDeployment1(oddsLose, betAmountSp, rewardHg, rewardSp);
        if (betAmountSp == null) {
            return null;
        }
        betParamVo.setBetAmountLose(betAmountSp);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);
        if (rewardSp < CalcUtil.mul(CalcUtil.add(betAmountSp, betAmountHg), 0.01)) {
            return null;
        }

        log.info("体彩投注：主 负 @" + oddsLose + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：主 +05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％");

        if (betParamVo.getNotifyFlag() == 1) {
            String msg = betParamVo.getMsg() + ",  体彩投注：主 负 @" + oddsLose + ", 金额 " + betAmountSp.intValue()
                    + ",  皇冠投注：主 +05 @" + oddsHg + ", 金额 " + betAmountHg.intValue()
                    + ",  收益：" + rewardSp.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.hedge_SPVisitWin_HGHomeAdd05);
        }
        return betParamVo;
    }

    /**
     * 体彩主队让球负，皇冠主队减05
     * @param betParamVo
     */
    public BetParamVo SPRangLose_HGHomeCut05(BetParamVo betParamVo) {
        Double oddsLose = betParamVo.getOddsRangLose();
        Double betAmountHg = betParamVo.getBetAmountHg();
        Double oddsHg = betParamVo.getHomeCut05();
        // 计算投注金额基数
        Double baseAmount = CalcUtil.mul(CalcUtil.add(oddsHg, 1), betAmountHg);
        // 计算体彩投注金额
        Double betAmountSp = CalcUtil.div(baseAmount, oddsLose);

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
        betAmountSp = AdaptationAmount.amountDeployment1(oddsLose, betAmountSp, rewardHg, rewardSp);
        if (betAmountSp == null) {
            return null;
        }
        betParamVo.setBetAmountRangLose(betAmountSp);

        /** 体彩中奖收益:体彩奖金 - 皇冠本金 + 体彩返水 + 皇冠返水 */
        // 体彩奖金
        bonusSp = CalcUtil.mul(CalcUtil.sub(oddsLose, 1), betAmountSp);
        // 皇冠全输返水
        rebateHgAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rewardSp = CalcUtil.sub(CalcUtil.add(bonusSp, rebateSpAmount, rebateHgAmount), betAmountHg);
        if (rewardSp < CalcUtil.mul(CalcUtil.add(betAmountSp, betAmountHg), 0.01)) {
            return null;
        }

        log.info("体彩投注：主 让球负 @" + oddsLose + ", 投 " + betAmountSp.intValue());
        log.info("皇冠投注：主 -05 @" + oddsHg + ", 投" + betAmountHg.intValue());
        log.info("收益：" + rewardSp.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％");

        if (betParamVo.getNotifyFlag() == 1) {
            String msg = betParamVo.getMsg() + ",  体彩投注：主 让球负 @" + oddsLose + ", 金额 " + betAmountSp.intValue()
                    + ",  皇冠投注：主 -05 @" + oddsHg + ", 金额 " + betAmountHg.intValue()
                    + ",  收益：" + rewardSp.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.hedge_SPRangLose_HGHomeCut5);
        }
        return betParamVo;
    }

    /**
     * 大15
     * 体彩小 01,皇冠 全输全赢
     * @param betParamVo
     */
    public void SP01_HG大15(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        betParamTemp.setOddsZero(oddsZero);
        betParamTemp.setOddsOne(oddsOne);
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
        betParamTemp.setReward0(reward0);

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountHg);
        betParamTemp.setReward1(reward1);

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne);
        betParamTemp.setRewardHG(rewardHg);

        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);
        if (betParamTemp == null) {
            return;
        }
        betAmountZero = betParamTemp.getBetAmountZero();
        betAmountOne = betParamTemp.getBetAmountOne();

        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue());
        log.info("皇冠投注：大1.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne).intValue() + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountHg);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountHg);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        if(rewardHg > 0) {
            String msg = betParamVo.getMsg() + ",  体彩投注：0球  @" + betParamVo.getOddsZero() + ", 金额 " + betParamTemp.getBetAmountZero()
                    + "1球  @" + betParamVo.getOddsOne() + ", 金额 " + betParamTemp.getBetAmountOne()
                    + ",  皇冠投注：大1.5 @" + oddsHg + ", 金额 " + betParamTemp.getBetAmountHg()
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, CalcUtil.add(betAmountZero, betAmountOne, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.big_15);
        }
    }

    /**
     * 大2
     * 体彩小 012,皇冠 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大2(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
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
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsTwo = betParamVo.getOddsTwo();
        betParamTemp.setOddsZero(oddsZero);
        betParamTemp.setOddsOne(oddsOne);
        betParamTemp.setOddsTwo(oddsTwo);
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
        betParamTemp.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamTemp.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamTemp.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);
        betAmountZero = betParamTemp.getBetAmountZero();
        betAmountOne = betParamTemp.getBetAmountOne();
        betAmountTwo = betParamTemp.getBetAmountTwo();


        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue());
        log.info("皇冠投注：大2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountHg);

        /** 0球数据 */
        bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamTemp.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");


        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：0球  @" + oddsZero + ", 金额 " + betAmountZero
                    + "1球  @" + oddsOne + ", 金额 " + betAmountOne
                    + "2球  @" + oddsTwo + ", 金额 " + betAmountTwo
                    + ",  皇冠投注：大2.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, CalcUtil.add(betAmountZero, betAmountOne, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.big_25);
        }
    }

    /**
     * 大3
     * 体彩小 0123,皇冠 大3 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大3(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
        Double betAmountThree = betParamTemp.getBetAmountThree();
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
     * 大3.5
     * 体彩小 0123,皇冠 大3.5 全输全赢
     * @param betParamVo
     */
    public void SP012_HG大35(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
        Double betAmountThree = betParamTemp.getBetAmountThree();

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
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);
        betAmountZero = betParamTemp.getBetAmountZero();
        betAmountOne = betParamTemp.getBetAmountOne();
        betAmountTwo = betParamTemp.getBetAmountTwo();
        betAmountThree = betParamTemp.getBetAmountThree();

        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);

        /** 0球数据 */
        bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：0球  @" + oddsZero + ", 金额 " + betAmountZero
                    + "1球  @" + oddsOne + ", 金额 " + betAmountOne
                    + "2球  @" + oddsTwo + ", 金额 " + betAmountTwo
                    + "3球  @" + oddsThree + ", 金额 " + betAmountThree
                    + ",  皇冠投注：大3.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, CalcUtil.add(betAmountZero, betAmountOne,betAmountTwo,betAmountThree, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.big_35);
        }

    }

    /**
     * 大1.5/2
     * 体彩小 012,皇冠 大1.5/2, 2球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    public void SP012_HG大15_2(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
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
        // 皇冠返水
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


        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountZero = betParamTemp.getBetAmountZero();
        betAmountOne = betParamTemp.getBetAmountOne();
        betAmountTwo = betParamTemp.getBetAmountTwo();
        //体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue());
        log.info("皇冠投注：大2.5/3 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountHg);

        /** 0球数据 */
        bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：0球  @" + oddsZero + ", 金额 " + betAmountZero
                    + "1球  @" + oddsOne + ", 金额 " + betAmountOne
                    + "2球  @" + oddsTwo + ", 金额 " + betAmountTwo
                    + ",  皇冠投注：大1.5/2 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, CalcUtil.add(betAmountZero, betAmountOne,betAmountTwo, betAmountHg), 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.big_15_2);
        }
    }

    /**
     * 大3/3.5
     * 体彩小 0123,皇冠 大3/3.5 3球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP012_HG大3_35(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
        Double betAmountThree = betParamTemp.getBetAmountThree();
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
        betParamTemp.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, rewardAmountHalf);
        betParamTemp.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");
        /**
         * 调整金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountZero = betParamTemp.getBetAmountZero();
        betAmountOne = betParamTemp.getBetAmountOne();
        betAmountTwo = betParamTemp.getBetAmountTwo();
        betAmountThree = betParamTemp.getBetAmountThree();

        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue() + ", 3球 " + betAmountThree.intValue());
        log.info("皇冠投注：大3/3.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);

        /** 体彩输一半 */
        rewardAmountHalf = CalcUtil.div(betAmountHg, 2);
        // 皇冠返水
        rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);
        rebateHGAmountHalf = CalcUtil.div(rebateHGAmount, 2);


        /** 0球数据 */
        bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 1球数据 */
        bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 2球数据 */
        bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 3球数据 */
        bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, rewardAmountHalf);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betParamVo.getBetBaseAmount(), 4), 1000) + "‰");
        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：0球  @" + oddsZero + ", 金额 " + betAmountZero
                    + "1球  @" + oddsOne + ", 金额 " + betAmountOne
                    + "2球  @" + oddsTwo + ", 金额 " + betAmountTwo
                    + "3球  @" + oddsThree + ", 金额 " + betAmountThree
                    + ",  皇冠投注：大3/3.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.big_3_35);
        }

    }

    /**
     * 大2/2.5
     * 体彩小 012,皇冠 大2/2.5, 2球皇冠输一半
     * @param betParamVo
     */
    public void SP012_HG大2_25(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
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
        betParamTemp.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamTemp.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountHgHalf);
        betParamTemp.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        betParamTemp.setRewardHG(betAmountHgHalf);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        /**
         * 调整投注金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountZero = betParamTemp.getBetAmountZero();
        betAmountOne = betParamTemp.getBetAmountOne();
        betAmountTwo = betParamTemp.getBetAmountTwo();

        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue());
        log.info("皇冠投注：大2/2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountHg);

        /** 体彩中球 */
        // 皇冠输一半
        betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        // 皇冠返水
        rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);

        rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamVo.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountHgHalf);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountZero, betAmountOne, betAmountTwo);
        betParamVo.setRewardHG(betAmountHgHalf);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：0球  @" + oddsZero + ", 金额 " + betAmountZero
                    + "1球  @" + oddsOne + ", 金额 " + betAmountOne
                    + "2球  @" + oddsTwo + ", 金额 " + betAmountTwo
                    + ",  皇冠投注：大2/2.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.big_2_25);
        }


    }

    /**
     * 大2.5/3
     * 体彩小 0123,皇冠 大2.5/3, 3球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    public void SP012_HG大25_3(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountZero = betParamTemp.getBetAmountZero();
        Double betAmountOne = betParamTemp.getBetAmountOne();
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
        Double betAmountThree = betParamTemp.getBetAmountThree();
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
        betParamTemp.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamTemp.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf, rewardHGHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamTemp.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        betParamVo.setRewardHG(bonusHg);
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);
        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        /**
         * 调增投注金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountZero = betParamTemp.getBetAmountZero();
        betAmountOne = betParamTemp.getBetAmountOne();
        betAmountTwo = betParamTemp.getBetAmountTwo();
        betAmountThree = betParamTemp.getBetAmountThree();

        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue() + ", 3球 " + betAmountThree.intValue());
        log.info("皇冠投注：大2.5/3 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);

        /** 体彩中球 */
        // 皇冠出奖
        rewardHGHalf = CalcUtil.div(CalcUtil.mul(betAmountHg, oddsHg), 2);
        // 皇冠返水
        rebateHGAmountHalf = CalcUtil.mul(rewardHGHalf, rebateHG);
        rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 0球数据 */
        bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward0(reward0);
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");

        /** 1球数据 */
        bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward1(reward1);
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");

        /** 2球数据 */
        bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountZero, betAmountOne, betAmountTwo, betAmountThree, betAmountHg);
        betParamVo.setReward2(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf, rewardHGHalf), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        betParamVo.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金
        betParamVo.setRewardHG(bonusHg);
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountZero, betAmountOne, betAmountTwo, betAmountThree);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：0球  @" + oddsZero + ", 金额 " + betAmountZero
                    + "1球  @" + oddsOne + ", 金额 " + betAmountOne
                    + "2球  @" + oddsTwo + ", 金额 " + betAmountTwo
                    + "3球  @" + oddsThree + ", 金额 " + betAmountThree
                    + ",  皇冠投注：大2.5/3 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.big_25_3);
        }
    }

    /**
     * 小3.5
     * 体彩大 4567+,皇冠 小3.5 全输全赢
     * @param betParamVo
     */
    public void SP4567_HG小35(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountFour = betParamTemp.getBetAmountFour();
        Double betAmountFive = betParamTemp.getBetAmountFive();
        Double betAmountSix = betParamTemp.getBetAmountSix();
        Double betAmountSeven = betParamTemp.getBetAmountSeven();
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
        betParamTemp.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        /**
         * 调整投注金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountFour = betParamTemp.getBetAmountFour();
        betAmountFive = betParamTemp.getBetAmountFive();
        betAmountSix = betParamTemp.getBetAmountSix();
        betAmountSeven = betParamTemp.getBetAmountSeven();
        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小3.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);

        /** 4球数据 */
        bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");


        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：4球  @" + oddsFour + ", 金额 " + betAmountFour
                    + "5球  @" + oddsFive + ", 金额 " + betAmountFive
                    + "6球  @" + oddsSix + ", 金额 " + betAmountSix
                    + "7+球  @" + oddsSeven + ", 金额 " + betAmountSeven
                    + ",  皇冠投注：小3.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.small_35);
        }
    }

    /**
     * 小2.5
     * 体彩大 34567+,皇冠 小2.5 全输全赢
     * @param betParamVo
     */
    public void SP34567_HG小25(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountThree = betParamTemp.getBetAmountThree();
        Double betAmountFour = betParamTemp.getBetAmountFour();
        Double betAmountFive = betParamTemp.getBetAmountFive();
        Double betAmountSix = betParamTemp.getBetAmountSix();
        Double betAmountSeven = betParamTemp.getBetAmountSeven();
        // 体彩参数
        double oddsThree = betParamVo.getOddsThree();
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        double oddsSeven = betParamVo.getOddsSeven();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：3球 " + betAmountThree.intValue() + ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        double rebateHGAmount = CalcUtil.mul(betAmountHg, rebateHG);

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward4(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount1 = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        /**
         * 调整投注金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountThree = betParamTemp.getBetAmountThree();
        betAmountFour = betParamTemp.getBetAmountFour();
        betAmountFive = betParamTemp.getBetAmountFive();
        betAmountSix = betParamTemp.getBetAmountSix();
        betAmountSeven = betParamTemp.getBetAmountSeven();

        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：3球 " + betAmountThree.intValue() + ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 3球数据 */
        bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount1), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：3球  @" + oddsThree + ", 金额 " + betAmountThree
                    +"4球  @" + oddsFour + ", 金额 " + betAmountFour
                    + "5球  @" + oddsFive + ", 金额 " + betAmountFive
                    + "6球  @" + oddsSix + ", 金额 " + betAmountSix
                    + "7+球  @" + oddsSeven + ", 金额 " + betAmountSeven
                    + ",  皇冠投注：小2.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.small_25);
        }
    }

    /**
     * 小2/2.5
     * 体彩大 234567+,皇冠 小2/2.5, 2球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP234567_HG小2_25(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountTwo = betParamTemp.getBetAmountTwo();
        Double betAmountThree = betParamTemp.getBetAmountThree();
        Double betAmountFour = betParamTemp.getBetAmountFour();
        Double betAmountFive = betParamTemp.getBetAmountFive();
        Double betAmountSix = betParamTemp.getBetAmountSix();
        Double betAmountSeven = betParamTemp.getBetAmountSeven();
        // 体彩参数
        double oddsTwo = betParamVo.getOddsTwo();
        double oddsThree = betParamVo.getOddsThree();
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        double oddsSeven = betParamVo.getOddsSeven();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：2球 " + betAmountTwo.intValue()+ ", 3球 " + betAmountThree.intValue() + ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小2/2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        Double betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        double rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountHalf), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        betParamTemp.setReward3(reward2);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        betParamTemp.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        /**
         * 调整投注金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountTwo = betParamTemp.getBetAmountTwo();
        betAmountThree = betParamTemp.getBetAmountThree();
        betAmountFour = betParamTemp.getBetAmountFour();
        betAmountFive = betParamTemp.getBetAmountFive();
        betAmountSix = betParamTemp.getBetAmountSix();
        betAmountSeven = betParamTemp.getBetAmountSeven();

        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：2球 " + betAmountTwo.intValue()+ ", 3球 " + betAmountThree.intValue() + ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小2/2.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);
        rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 2球数据 */
        bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountHalf), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");

        /** 3球数据 */
        bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountTwo, betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：3球  @" + oddsThree + ", 金额 " + betAmountThree
                    +"4球  @" + oddsFour + ", 金额 " + betAmountFour
                    + "5球  @" + oddsFive + ", 金额 " + betAmountFive
                    + "6球  @" + oddsSix + ", 金额 " + betAmountSix
                    + "7+球  @" + oddsSeven + ", 金额 " + betAmountSeven
                    + ",  皇冠投注：小2/2.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.small_2_25);
        }
    }

    /**
     * 小2.5/3
     * 体彩大 34567+,皇冠 小2.5/3, 3球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP34567_HG小25_3(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountThree = betParamTemp.getBetAmountThree();
        Double betAmountFour = betParamTemp.getBetAmountFour();
        Double betAmountFive = betParamTemp.getBetAmountFive();
        Double betAmountSix = betParamTemp.getBetAmountSix();
        Double betAmountSeven = betParamTemp.getBetAmountSeven();
        // 体彩参数
        double oddsThree = betParamVo.getOddsThree();
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        double oddsSeven = betParamVo.getOddsSeven();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：3球 " + betAmountThree.intValue()+ ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小2.5/3 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        Double betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        double rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);
        double rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        betParamTemp.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        /**
         * 调整投注金额
         */
        betParamTemp =  AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountThree = betParamTemp.getBetAmountThree();
        betAmountFour = betParamTemp.getBetAmountFour();
        betAmountFive = betParamTemp.getBetAmountFive();
        betAmountSix = betParamTemp.getBetAmountSix();
        betAmountSeven = betParamTemp.getBetAmountSeven();

        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：3球 " + betAmountThree.intValue()+ ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小2.5/3 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);
        rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 3球数据 */
        bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamVo.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);
        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：3球  @" + oddsThree + ", 金额 " + betAmountThree
                    +"4球  @" + oddsFour + ", 金额 " + betAmountFour
                    + "5球  @" + oddsFive + ", 金额 " + betAmountFive
                    + "6球  @" + oddsSix + ", 金额 " + betAmountSix
                    + "7+球  @" + oddsSeven + ", 金额 " + betAmountSeven
                    + ",  皇冠投注：小2.5/3 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";

            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.small_25_3);
        }
    }

    /**
     * 小3/3.5
     * 体彩大 34567+,皇冠 小3/3.5, 3球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    public void SP34567_HG小3_35(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountThree = betParamTemp.getBetAmountThree();
        Double betAmountFour = betParamTemp.getBetAmountFour();
        Double betAmountFive = betParamTemp.getBetAmountFive();
        Double betAmountSix = betParamTemp.getBetAmountSix();
        Double betAmountSeven = betParamTemp.getBetAmountSeven();
        // 体彩参数
        double oddsThree = betParamVo.getOddsThree();
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        double oddsSeven = betParamVo.getOddsSeven();
        // 皇冠参数
        double oddsHg = betParamVo.getOddsHg();
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：3球 " + betAmountThree.intValue()+ ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小3/3.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        Double betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠半奖
        Double bonusHgHalf = CalcUtil.div(CalcUtil.mul(betAmountHg, oddsHg), 2);
        // 皇冠半奖返水
        double rebateHGAmountHalf = CalcUtil.mul(bonusHgHalf, rebateHG);
        // 皇冠全输返水
        double rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 3球数据 */
        double bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf, bonusHgHalf), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamTemp.setReward3(reward3);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        /**
         * 调整投注金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);

        betAmountThree = betParamTemp.getBetAmountThree();
        betAmountFour = betParamTemp.getBetAmountFour();
        betAmountFive = betParamTemp.getBetAmountFive();
        betAmountSix = betParamTemp.getBetAmountSix();
        betAmountSeven = betParamTemp.getBetAmountSeven();
        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：3球 " + betAmountThree.intValue()+ ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小3/3.5 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠半奖
        bonusHgHalf = CalcUtil.div(CalcUtil.mul(betAmountHg, oddsHg), 2);
        // 皇冠半奖返水
        rebateHGAmountHalf = CalcUtil.mul(bonusHgHalf, rebateHG);
        // 皇冠全输返水
        rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 3球数据 */
        bonusThree = CalcUtil.mul(betAmountThree, oddsThree); // 奖金
        reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountHalf, bonusHgHalf), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");

        /** 4球数据 */
        bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);

        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountThree, betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注：3球  @" + oddsThree + ", 金额 " + betAmountThree
                    +"4球  @" + oddsFour + ", 金额 " + betAmountFour
                    + "5球  @" + oddsFive + ", 金额 " + betAmountFive
                    + "6球  @" + oddsSix + ", 金额 " + betAmountSix
                    + "7+球  @" + oddsSeven + ", 金额 " + betAmountSeven
                    + ",  皇冠投注：小3/3.5 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";
            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.small_3_35);
        }
    }

    /**
     * 小3.5/4
     * 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半
     * @param betParamVo
     */
    public void SP4567_HG小35_4(BetParamVo betParamVo) {
        Double betAmountHg = betParamVo.getBetAmountHg();
        betParamVo.setBetBaseAmount(betAmountHg);
        // 计算体彩初始投注金额
        BetParamVo betParamTemp = AdaptationAmount.adaptation(betParamVo);
        Double betAmountFour = betParamTemp.getBetAmountFour();
        Double betAmountFive = betParamTemp.getBetAmountFive();
        Double betAmountSix = betParamTemp.getBetAmountSix();
        Double betAmountSeven = betParamTemp.getBetAmountSeven();
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
        betParamTemp.setReward4(reward4);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward5(reward5);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward6(reward6);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        betParamTemp.setReward7(reward7);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        double bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);

        Double rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        betParamTemp.setRewardHG(rewardHg);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");
        /**
         * 调整投注金额
         */
        betParamTemp = AdaptationAmount.adaptationLastAmount(betParamTemp);


        betAmountFour = betParamTemp.getBetAmountFour();
        betAmountFive = betParamTemp.getBetAmountFive();
        betAmountSix = betParamTemp.getBetAmountSix();
        betAmountSeven = betParamTemp.getBetAmountSeven();

        // 体彩返水
        rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        log.info("体彩投注：4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：小3.5/4 @" + oddsHg + ", " + betAmountHg.intValue());
        log.info("体彩总投注：" + CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + betAmountHg.intValue());
        log.info("");

        betAmountAll = CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);

        /** 体彩中球 */
        // 皇冠返水
        betAmountHgHalf = CalcUtil.div(betAmountHg, 2);
        rebateHGAmountHalf = CalcUtil.mul(betAmountHgHalf, rebateHG);
        rebateHGAmountAll = CalcUtil.mul(betAmountHg, rebateHG);

        /** 4球数据 */
        bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountHalf), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHgHalf);
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");

        /** 5球数据 */
        bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");

        /** 6球数据 */
        bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");

        /** 7球+数据 */
        bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountFour, betAmountFive, betAmountSix, betAmountSeven, betAmountHg);
        log.info("7球+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");

        /** 皇冠中球 */
        bonusHg = CalcUtil.mul(betAmountHg, oddsHg); // 奖金

        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        rebateHGAmount = CalcUtil.mul(bonusHg, rebateHG);

        rewardHg = CalcUtil.sub(CalcUtil.add(bonusHg, rebateSPAmount, rebateHGAmount), betAmountFour, betAmountFive, betAmountSix, betAmountSeven);
        log.info("皇冠收益：" + rewardHg.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 1000) + "‰");

        if(rewardHg > 0){
            String msg = betParamVo.getMsg() + ",  体彩投注："
                    +"4球  @" + oddsFour + ", 金额 " + betAmountFour
                    + "5球  @" + oddsFive + ", 金额 " + betAmountFive
                    + "6球  @" + oddsSix + ", 金额 " + betAmountSix
                    + "7+球  @" + oddsSeven + ", 金额 " + betAmountSeven
                    + ",  皇冠投注：小3.5/4 @" + oddsHg + ", 金额 " + betAmountHg
                    + ",  收益：" + rewardHg.intValue() + ",  收益率：" + CalcUtil.mul(CalcUtil.div(rewardHg, betAmountAll, 4), 100) + "％";
            // 消息通知
            notifyMsg(betParamVo.getSpId(), msg, BetTypeEnum.small_35_4);
        }
    }

    /**
     * 总进球
     * 体彩0123，皇冠 1）总进球4-6,  2）总进球7+
     * @param betParamVo
     */
    public void SP0123_HGZong4_6_7(BetParamVo betParamVo) {
        BetParamVo betParamTemp = new BetParamVo();
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountTwo;
        Double betAmountThree;
        Double zong4_6Amount;
        Double zong7Amount;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountTwo = betParamTemp.getBetAmountTwo();
            betAmountThree = betParamTemp.getBetAmountThree();
            zong4_6Amount = betParamTemp.getZong4_6Amount();
            zong7Amount = betParamTemp.getZong7Amount();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountTwo = betParamVo.getBetAmountTwo();
            betAmountThree = betParamVo.getBetAmountThree();
            zong4_6Amount = betParamVo.getZong4_6Amount();
            zong7Amount = betParamVo.getZong7Amount();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsTwo = betParamVo.getOddsTwo();
        double oddsThree = betParamVo.getOddsThree();
        betParamTemp.setOddsZero(oddsZero);
        betParamTemp.setOddsOne(oddsOne);
        betParamTemp.setOddsTwo(oddsTwo);
        betParamTemp.setOddsThree(oddsThree);
        // 皇冠参数
        Double zong4_6 = betParamVo.getZong4_6();
        Double zong7 = betParamVo.getZong7();
        betParamTemp.setZong4_6(zong4_6);
        betParamTemp.setZong7(zong7);
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree), rebateSP);

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree, zong4_6Amount, zong7Amount);

        /** 体彩中球 */
        // 皇冠全输返水
        double rebateHGAmountAll = CalcUtil.mul(CalcUtil.add(zong4_6Amount, zong7Amount), rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward0(reward0);

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward1(reward1);

        /** 2球数据 */
        double bonusTwo = CalcUtil.mul(betAmountTwo, oddsTwo); // 奖金
        Double reward2 = CalcUtil.sub(CalcUtil.add(bonusTwo, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward2(reward2);

        /** 3球数据 */
        double bonusThree = betAmountThree * oddsThree; // 奖金
        Double reward3 = CalcUtil.sub(CalcUtil.add(bonusThree, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward3(reward3);

        /** 皇冠中球 */
        /** 总进球4-6数据 */
        double bonus4_6 = CalcUtil.mul(zong4_6Amount, zong4_6); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate4_6 = CalcUtil.mul(bonus4_6, rebateHG);
        Double reward4_6 = CalcUtil.sub(CalcUtil.add(bonus4_6, rebateSPAmount, rebate4_6), betAmountAll);
        betParamTemp.setRewardZong4_6(reward4_6);

        /** 总进球7+数据 */
        double bonus7 = CalcUtil.mul(zong7Amount, zong7); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate7 = CalcUtil.mul(bonus7, rebateHG);
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonus7, rebateSPAmount, rebate7), betAmountAll);
        betParamTemp.setRewardZong7(reward7);

        BetParamVo adaptation = AdaptationAmount.adaptationZong(betParamTemp);
        if (adaptation == null) {
            return;
        }
        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");
        log.info("2球收益：" + reward2.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2, betAmountAll, 4), 1000) + "‰");
        log.info("3球收益：" + reward3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward3, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球4-6收益：" + reward4_6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4_6, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球7+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");
        log.info("");
        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 2球 " + betAmountTwo.intValue() + ", 3球 " + betAmountThree.intValue());
        log.info("皇冠投注：总进球 ①4-6@" + zong4_6 + "  ￥" + zong4_6Amount.intValue() + ",  ②7+@" + zong7 + "  ￥" + zong7Amount);
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountTwo, betAmountThree).intValue()
                + ", 皇冠总投注：" + CalcUtil.add(zong4_6Amount, zong7Amount).intValue());
        log.info("");
    }

    /**
     * 总进球
     * 体彩01456，皇冠 1）总进球2-3,  2）总进球7+
     * @param betParamVo
     */
    public void SP01456_HGZong2_3_7(BetParamVo betParamVo) {
        BetParamVo betParamTemp = new BetParamVo();
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountFour;
        Double betAmountFive;
        Double betAmountSix;
        Double zong2_3Amount;
        Double zong7Amount;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountFour = betParamTemp.getBetAmountFour();
            betAmountFive = betParamTemp.getBetAmountFive();
            betAmountSix = betParamTemp.getBetAmountSix();
            zong2_3Amount = betParamTemp.getZong2_3Amount();
            zong7Amount = betParamTemp.getZong7Amount();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountFour = betParamVo.getBetAmountFour();
            betAmountFive = betParamVo.getBetAmountFive();
            betAmountSix = betParamVo.getBetAmountSix();
            zong2_3Amount = betParamVo.getZong2_3Amount();
            zong7Amount = betParamVo.getZong7Amount();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        betParamTemp.setOddsZero(oddsZero);
        betParamTemp.setOddsOne(oddsOne);
        betParamTemp.setOddsFour(oddsFour);
        betParamTemp.setOddsFive(oddsFive);
        betParamTemp.setOddsSix(oddsSix);
        // 皇冠参数
        Double zong2_3 = betParamVo.getZong2_3();
        Double zong7 = betParamVo.getZong7();
        betParamTemp.setZong2_3(zong2_3);
        betParamTemp.setZong7(zong7);
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountFour, betAmountFive, betAmountSix), rebateSP);

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountFour, betAmountFive, betAmountSix, zong2_3Amount, zong7Amount);

        /** 体彩中球 */
        // 皇冠全输返水
        double rebateHGAmountAll = CalcUtil.mul(CalcUtil.add(zong2_3Amount, zong7Amount), rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward0(reward0);

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward1(reward1);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward4(reward4);

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward5(reward5);

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward6(reward6);

        /** 皇冠中球 */
        /** 总进球2-3数据 */
        double bonus2_3 = CalcUtil.mul(zong2_3Amount, zong2_3); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate2_3 = CalcUtil.mul(bonus2_3, rebateHG);
        Double reward2_3 = CalcUtil.sub(CalcUtil.add(bonus2_3, rebateSPAmount, rebate2_3), betAmountAll);
        betParamTemp.setRewardZong2_3(reward2_3);

        /** 总进球7+数据 */
        double bonus7 = CalcUtil.mul(zong7Amount, zong7); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate7 = CalcUtil.mul(bonus7, rebateHG);
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonus7, rebateSPAmount, rebate7), betAmountAll);
        betParamTemp.setRewardZong7(reward7);

        BetParamVo adaptation = AdaptationAmount.adaptationZong(betParamTemp);
        if (adaptation == null) {
            return;
        }

        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");
        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球2-3收益：" + reward2_3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2_3, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球7+收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");
        log.info("");
        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue());
        log.info("皇冠投注：总进球 ①2-3@" + zong2_3 + "  ￥" + zong2_3Amount.intValue() + ",  ②7+@" + zong7 + "  ￥" + zong7Amount);
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountFour, betAmountFive, betAmountSix).intValue()
                + ", 皇冠总投注：" + CalcUtil.add(zong2_3Amount, zong7Amount).intValue());
        log.info("");
    }

    /**
     * 总进球
     * 体彩017+，皇冠 1）总进球2-3,  2）总进球4-6
     * @param betParamVo
     */
    public void SP017_HGZong2_3_4_6(BetParamVo betParamVo) {
        BetParamVo betParamTemp = new BetParamVo();
        Double betAmountZero;
        Double betAmountOne;
        Double betAmountSeven;
        Double zong2_3Amount;
        Double zong4_6Amount;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountZero = betParamTemp.getBetAmountZero();
            betAmountOne = betParamTemp.getBetAmountOne();
            betAmountSeven = betParamTemp.getBetAmountSeven();
            zong2_3Amount = betParamTemp.getZong2_3Amount();
            zong4_6Amount = betParamTemp.getZong4_6Amount();
        } else {
            betAmountZero = betParamVo.getBetAmountZero();
            betAmountOne = betParamVo.getBetAmountOne();
            betAmountSeven = betParamVo.getBetAmountSeven();
            zong2_3Amount = betParamVo.getZong2_3Amount();
            zong4_6Amount = betParamVo.getZong4_6Amount();
        }
        // 体彩参数
        double oddsZero = betParamVo.getOddsZero();
        double oddsOne = betParamVo.getOddsOne();
        double oddsSeven = betParamVo.getOddsSeven();
        betParamTemp.setOddsZero(oddsZero);
        betParamTemp.setOddsOne(oddsOne);
        betParamTemp.setOddsSeven(oddsSeven);
        // 皇冠参数
        Double zong2_3 = betParamVo.getZong2_3();
        Double zong4_6 = betParamVo.getZong4_6();
        betParamTemp.setZong2_3(zong2_3);
        betParamTemp.setZong4_6(zong4_6);
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountZero, betAmountOne, betAmountSeven), rebateSP);

        Double betAmountAll = CalcUtil.add(betAmountZero, betAmountOne, betAmountSeven, zong2_3Amount, zong4_6Amount);

        /** 体彩中球 */
        // 皇冠全输返水
        double rebateHGAmountAll = CalcUtil.mul(CalcUtil.add(zong2_3Amount, zong4_6Amount), rebateHG);

        /** 0球数据 */
        double bonusZero = CalcUtil.mul(betAmountZero, oddsZero); // 奖金
        Double reward0 = CalcUtil.sub(CalcUtil.add(bonusZero, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward0(reward0);

        /** 1球数据 */
        double bonusOne = CalcUtil.mul(betAmountOne, oddsOne); // 奖金
        Double reward1 = CalcUtil.sub(CalcUtil.add(bonusOne, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward1(reward1);

        /** 7+球数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward7(reward7);

        /** 皇冠中球 */
        /** 总进球2-3数据 */
        double bonus2_3 = CalcUtil.mul(zong2_3Amount, zong2_3); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate2_3 = CalcUtil.mul(bonus2_3, rebateHG);
        Double reward2_3 = CalcUtil.sub(CalcUtil.add(bonus2_3, rebateSPAmount, rebate2_3), betAmountAll);
        betParamTemp.setRewardZong2_3(reward2_3);

        /** 总进球4-6数据 */
        double bonus4_6 = CalcUtil.mul(zong4_6Amount, zong4_6); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate4_6 = CalcUtil.mul(bonus4_6, rebateHG);
        Double reward4_6 = CalcUtil.sub(CalcUtil.add(bonus4_6, rebateSPAmount, rebate4_6), betAmountAll);
        betParamTemp.setRewardZong4_6(zong4_6);

        BetParamVo adaptation = AdaptationAmount.adaptationZong(betParamTemp);
        if (adaptation == null) {
            return;
        }

        log.info("0球收益：" + reward0.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0, betAmountAll, 4), 1000) + "‰");
        log.info("1球收益：" + reward1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward1, betAmountAll, 4), 1000) + "‰");
        log.info("7+球收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球2-3收益：" + reward2_3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2_3, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球4-6收益：" + reward4_6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4_6, betAmountAll, 4), 1000) + "‰");
        log.info("");
        log.info("体彩投注：0球 " + betAmountZero.intValue() + ", 1球 " + betAmountOne.intValue() + ", 7球 " + betAmountSeven.intValue());
        log.info("皇冠投注：总进球 ①2-3@" + zong2_3 + "  ￥" + zong2_3Amount.intValue() + ",  ②4-6@" + zong4_6 + "  ￥" + zong4_6Amount);
        log.info("体彩总投注：" + CalcUtil.add(betAmountZero, betAmountOne, betAmountSeven).intValue()
                + ", 皇冠总投注：" + CalcUtil.add(zong2_3Amount, zong4_6Amount).intValue());
        log.info("");
    }

    /**
     * 总进球
     * 体彩4567+，皇冠 1）总进球0-1,  2）总进球2-3
     * @param betParamVo
     */
    public void SP4567_HGZong0_1_2_3(BetParamVo betParamVo) {
        BetParamVo betParamTemp = new BetParamVo();
        Double betAmountFour;
        Double betAmountFive;
        Double betAmountSix;
        Double betAmountSeven;
        Double zong0_1Amount;
        Double zong2_3Amount;
        if (betParamVo.getBetAmountZero()==0.0 && betParamVo.getBetAmountFour()==0.0) {
            betParamTemp = AdaptationAmount.adaptation(betParamVo);
            betAmountFour = betParamTemp.getBetAmountFour();
            betAmountFive = betParamTemp.getBetAmountFive();
            betAmountSix = betParamTemp.getBetAmountSix();
            betAmountSeven = betParamTemp.getBetAmountSeven();
            zong0_1Amount = betParamTemp.getZong0_1Amount();
            zong2_3Amount = betParamTemp.getZong2_3Amount();
        } else {
            betAmountFour = betParamVo.getBetAmountFour();
            betAmountFive = betParamVo.getBetAmountFive();
            betAmountSix = betParamVo.getBetAmountSix();
            betAmountSeven = betParamVo.getBetAmountSeven();
            zong0_1Amount = betParamVo.getZong0_1Amount();
            zong2_3Amount = betParamVo.getZong2_3Amount();
        }
        // 体彩参数
        double oddsFour = betParamVo.getOddsFour();
        double oddsFive = betParamVo.getOddsFive();
        double oddsSix = betParamVo.getOddsSix();
        double oddsSeven = betParamVo.getOddsSeven();
        betParamTemp.setOddsFour(oddsFour);
        betParamTemp.setOddsFive(oddsFive);
        betParamTemp.setOddsSix(oddsSix);
        betParamTemp.setOddsSeven(oddsSeven);
        // 皇冠参数
        Double zong0_1 = betParamVo.getZong0_1();
        Double zong2_3 = betParamVo.getZong2_3();
        betParamTemp.setZong0_1(zong0_1);
        betParamTemp.setZong2_3(zong2_3);
        // 体彩返水
        double rebateSPAmount = CalcUtil.mul(CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven), rebateSP);

        Double betAmountAll = CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven, zong0_1Amount, zong2_3Amount);

        /** 体彩中球 */
        // 皇冠全输返水
        double rebateHGAmountAll = CalcUtil.mul(CalcUtil.add(zong0_1Amount, zong2_3Amount), rebateHG);

        /** 4球数据 */
        double bonusFour = CalcUtil.mul(betAmountFour, oddsFour); // 奖金
        Double reward4 = CalcUtil.sub(CalcUtil.add(bonusFour, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward4(reward4);

        /** 5球数据 */
        double bonusFive = CalcUtil.mul(betAmountFive, oddsFive); // 奖金
        Double reward5 = CalcUtil.sub(CalcUtil.add(bonusFive, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward5(reward5);

        /** 6球数据 */
        double bonusSix = CalcUtil.mul(betAmountSix, oddsSix); // 奖金
        Double reward6 = CalcUtil.sub(CalcUtil.add(bonusSix, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward6(reward6);

        /** 7+球数据 */
        double bonusSeven = CalcUtil.mul(betAmountSeven, oddsSeven); // 奖金
        Double reward7 = CalcUtil.sub(CalcUtil.add(bonusSeven, rebateSPAmount, rebateHGAmountAll), betAmountAll);
        betParamTemp.setReward7(reward7);

        /** 总进球0-1数据 */
        double bonus0_1 = CalcUtil.mul(zong0_1Amount, zong0_1); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate0_1 = CalcUtil.mul(bonus0_1, rebateHG);
        Double reward0_1 = CalcUtil.sub(CalcUtil.add(bonus0_1, rebateSPAmount, rebate0_1), betAmountAll);
        betParamTemp.setRewardZong0_1(reward0_1);

        /** 皇冠中球 */
        /** 总进球2-3数据 */
        double bonus2_3 = CalcUtil.mul(zong2_3Amount, zong2_3); // 奖金
        // 皇冠返水 - 皇冠中球返水 按出奖金额作为基础金额
        double rebate2_3 = CalcUtil.mul(bonus2_3, rebateHG);
        Double reward2_3 = CalcUtil.sub(CalcUtil.add(bonus2_3, rebateSPAmount, rebate2_3), betAmountAll);
        betParamTemp.setRewardZong2_3(reward2_3);

        BetParamVo adaptation = AdaptationAmount.adaptationZong(betParamTemp);
        if (adaptation == null) {
            return;
        }

        log.info("4球收益：" + reward4.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward4, betAmountAll, 4), 1000) + "‰");
        log.info("5球收益：" + reward5.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward5, betAmountAll, 4), 1000) + "‰");
        log.info("6球收益：" + reward6.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward6, betAmountAll, 4), 1000) + "‰");
        log.info("7+球收益：" + reward7.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward7, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球0-1收益：" + reward0_1.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward0_1, betAmountAll, 4), 1000) + "‰");
        log.info("皇冠总进球2-3收益：" + reward2_3.intValue() + ", 收益率：" + CalcUtil.mul(CalcUtil.div(reward2_3, betAmountAll, 4), 1000) + "‰");
        log.info("");
        log.info("体彩投注：4球 " + betAmountFour.intValue() + ", 5球 " + betAmountFive.intValue() + ", 6球 " + betAmountSix.intValue() + ", 7+球 " + betAmountSeven.intValue());
        log.info("皇冠投注：总进球 ①0-1@" + zong0_1 + "  ￥" + zong0_1Amount.intValue() + ",  ②2-3@" + zong2_3 + "  ￥" + zong2_3Amount);
        log.info("体彩总投注：" + CalcUtil.add(betAmountFour, betAmountFive, betAmountSix, betAmountSeven).intValue()
                + ", 皇冠总投注：" + CalcUtil.add(zong0_1Amount, zong2_3Amount).intValue());
        log.info("");

    }

    /**
     * 篮球
     * @param basketballParamVo
     */
   public void betBasketball(BetBasketballParamVo basketballParamVo){
       Double betAmountSp = basketballParamVo.getBetBaseAmount();
       /**
        * 胜负
        */
       /** 体彩主胜，皇冠客胜 */
       Double oddsWin = basketballParamVo.getOddsWin();
       Double visitWin = basketballParamVo.getVisitWin();
       if (oddsWin != 0 && visitWin != 0) {
           log.info("       @@篮球@胜负, 体彩 @主胜, 皇冠 @客胜 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsWin, visitWin, 1);
           log.info("");
       }

       /** 体彩客胜，皇冠主胜 */
       Double oddsLose = basketballParamVo.getOddsLose();
       Double homeWin = basketballParamVo.getHomeWin();
       if (oddsLose != 0 && homeWin != 0) {
           log.info("       @@篮球@胜负, 体彩 @客胜, 皇冠 @主胜 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsLose, homeWin, 1);
           log.info("");
       }

       /**
        * 让分
        */
       /** 体彩 主减胜，皇冠 客加胜 */
       Double oddsCutWin = basketballParamVo.getOddsCutWin();
       Double visitAdd = basketballParamVo.getVisitAdd();
       if (oddsCutWin != 0 && visitAdd != 0) {
           log.info("       @@篮球@让分胜负, 体彩 @主减胜, 皇冠 @客加胜 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsCutWin, visitAdd, 0);
           log.info("");
       }

       /** 体彩 主减客胜，皇冠 主胜 */
       Double oddsCutLose = basketballParamVo.getOddsCutLose();
       if (oddsCutLose != 0 && homeWin != 0) {
           log.info("       @@篮球@让分胜负, 体彩 @主减客胜, 皇冠 @主胜 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsCutLose, homeWin, 1);
           log.info("");
       }

       /** 体彩 主减客胜，皇冠 主加胜（加任意分/最高赔率） */
       Double homeAdd = basketballParamVo.getHomeAdd();
       if (oddsCutLose != 0 && homeAdd != 0) {
           log.info("       @@篮球@让分胜负, 体彩 @主减客胜, 皇冠 @主加胜 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsCutLose, homeAdd, 0);
           log.info("");
       }

       /** 体彩 主加胜，皇冠 客减胜 */
       Double oddsAddWin = basketballParamVo.getOddsAddWin();
       Double visitCut = basketballParamVo.getVisitCut();
       if (oddsAddWin != 0 && visitCut != 0) {
           log.info("       @@篮球@让分胜负, 体彩 @主加胜, 皇冠 @客减胜 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsAddWin, visitCut, 0);
           log.info("");
       }

       /** 体彩 主加客胜，皇冠 主加胜 */
       Double oddsAddLose = basketballParamVo.getOddsAddLose();
       if (oddsAddLose != 0 && homeAdd != 0) {
           log.info("       @@篮球@让分胜负, 体彩 @主加胜, 皇冠 @客减胜 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsAddLose, homeAdd, 0);
           log.info("");
       }
       /** 体彩 大，皇冠 小  , hg小分 - 体彩基准加1，体彩基准135 （体彩大135,皇冠小136）*/
       Double oddsBig = basketballParamVo.getOddsBig();
       Double hgSmallAdd1 = basketballParamVo.getHgSmallAdd1();
       if (oddsBig != 0 && hgSmallAdd1 != 0) {
           log.info("       @@篮球@大小分, 体彩 @大, 皇冠 @小 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsBig, hgSmallAdd1, 0);
           log.info("");
       }
       /** 体彩 小，皇冠 大  , hg大分 - 体彩基准减1，体彩基准135 （体彩小135,皇冠大134）*/
       Double oddsSmall = basketballParamVo.getOddsSmall();
       Double hgBigCut1 = basketballParamVo.getHgBigCut1();
       if (oddsSmall != 0 && hgBigCut1 != 0) {
           log.info("       @@篮球@大小分, 体彩 @小, 皇冠 @大 ------------------------------------------------------");
           BK_winLose_rangFen(betAmountSp, oddsBig, hgSmallAdd1, 0);
           log.info("");
       }
   }

    /**
     * 篮球胜负
     * 篮球让分
     * 篮球大小分
     * @param flag  0:非篮球胜负场, 1:篮球胜负场
     */
    private void BK_winLose_rangFen(Double betAmountSp, Double oddsSp, Double oddsHg, Integer flag) {
        //计算hg投注额
        Double betAmountHg = calcBet(betAmountSp, oddsSp, flag==0?CalcUtil.add(oddsHg, 1):oddsHg);
        //hg全输水
        Double rebateHgAll = CalcUtil.mul(betAmountHg, rebateHG);
        //计算体彩水
        Double rebateSp = CalcUtil.mul(betAmountSp, rebateSPBK);
        //计算体彩收益
        Double rewardSp = CalcUtil.add(CalcUtil.sub(CalcUtil.mul(betAmountSp, CalcUtil.sub(oddsSp, 1)), betAmountHg), rebateSp,rebateHgAll);
        //计算皇冠出奖
        Double hgBonus = CalcUtil.mul(betAmountHg, CalcUtil.sub(oddsHg, 1));
        //计算皇冠出奖返水
        Double hgBonusRebate = CalcUtil.mul(hgBonus,rebateHG);
        Double rewardHg = CalcUtil.add(CalcUtil.sub(hgBonus, betAmountSp), rebateSp, hgBonusRebate);

        // 调配金额
        betAmountHg = AdaptationAmount.amountDeployment1(flag==0?CalcUtil.add(oddsHg, 1):oddsHg, betAmountHg, rewardSp, rewardHg);
        if (betAmountHg == null) {
            return;
        }

        //hg全输水
        rebateHgAll = CalcUtil.mul(betAmountHg, rebateHG);
        //计算体彩收益
        rewardSp = CalcUtil.add(CalcUtil.sub(CalcUtil.mul(betAmountSp, CalcUtil.sub(oddsSp, 1)), betAmountHg), rebateSp,rebateHgAll);
        //计算皇冠出奖
        hgBonus = CalcUtil.mul(betAmountHg, CalcUtil.sub(oddsHg, 1));
        //计算皇冠出奖返水
        hgBonusRebate = CalcUtil.mul(hgBonus,rebateHG);
        rewardHg = CalcUtil.add(CalcUtil.sub(hgBonus, betAmountSp), rebateSp, hgBonusRebate);

        log.info("  体彩投注 @"+ oddsSp +", "+betAmountSp.intValue() +", 收益:"+ rewardSp.intValue() +", 收益率:"+ CalcUtil.mul(CalcUtil.div(rewardSp, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "%");
        log.info("  皇冠投注 @"+ oddsHg +", "+betAmountHg.intValue() +", 收益:"+ rewardHg.intValue() +", 收益率:"+ CalcUtil.mul(CalcUtil.div(rewardHg, CalcUtil.add(betAmountSp, betAmountHg), 4), 100) + "%");
    }

    /**
     * 计算篮球投注金额
     * @param betBaseAmount
     * @param odds
     * @return
     */
    private Double calcBet(Double betBaseAmount,Double odds,Double visit){
        Double visitBet = CalcUtil.div(CalcUtil.mul(betBaseAmount, odds), visit);
        return visitBet;
    }

    /**
     * 消息通知
     * @param spId
     * @param msg
     */
    private void notifyMsg(Long spId, String msg, BetTypeEnum betTypeEnum) {
        NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(betTypeEnum.getValue(), spId, HandicapEnum.dealer.getValue());
        long currentTime = System.currentTimeMillis();
        if (msgByCondition == null || currentTime - msgByCondition.getNotifyTime() > 600000) {
            HttpUtils.sendPost(tgUrl, msg);
            NotifyMsg notifyMsg = new NotifyMsg();
            notifyMsg.setMsgType(betTypeEnum.getValue());
            notifyMsg.setNotifyTime(currentTime);
            notifyMsg.setBetId(spId);
            notifyMsg.setHandicap(HandicapEnum.dealer.getValue());
            notifyMsgMapper.insertNotifyMsg(notifyMsg);
        }
    }

}

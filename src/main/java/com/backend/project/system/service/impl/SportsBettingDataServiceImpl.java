package com.backend.project.system.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.DateUtils;
import com.backend.common.utils.bean.BeanUtils;
import com.backend.common.utils.http.HttpUtils;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.BetSPMatchInfo;
import com.backend.project.system.domain.NotifyMsg;
import com.backend.project.system.domain.SPBKMatchInfo;
import com.backend.project.system.domain.SPMatchInfo;
import com.backend.project.system.domain.vo.*;
import com.backend.project.system.enums.BetTypeEnum;
import com.backend.project.system.mapper.BetSPMatchInfoMapper;
import com.backend.project.system.mapper.NotifyMsgMapper;
import com.backend.project.system.mapper.SPMatchInfoMapper;
import com.backend.project.system.service.IHgSPBallService;
import com.backend.project.system.service.ISportsBettingDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 获取体彩数据入库
 */

@Service
public class SportsBettingDataServiceImpl implements ISportsBettingDataService {

    @Resource
    private SPMatchInfoMapper spMatchInfoMapper;
    @Resource
    private BetSPMatchInfoMapper betSPMatchInfoMapper;

    @Resource
    private NotifyMsgMapper notifyMsgMapper;

    @Resource
    private IHgSPBallService hgSPBallService;

    @org.springframework.beans.factory.annotation.Value("${tgApi.url}")
    private String tgUrl;


    @Override
    public void getSportsBettingFBData() {
        String s = HttpUtils.commonGet("https://webapi.sporttery.cn/gateway/jc/football/getMatchCalculatorV1.qry");
        JSONObject obj = JSONObject.parseObject(s);
        Root root = JSON.toJavaObject(obj, Root.class);
        //批量插入集合
        List<SPMatchInfo> spMatchInfos = new ArrayList<>();
        if (root != null) {
            Value value = root.getValue();

            if (value != null) {
                List<MatchInfoList> matchInfoList = value.getMatchInfoList();

                if (matchInfoList != null && matchInfoList.size() > 0) {
                    for (int i = 0; i < matchInfoList.size(); i++) {
                        MatchInfoList m = matchInfoList.get(i);
                        List<SubMatchList> subMatchList = m.getSubMatchList();
                        // 转换精确时间
                        String exactDate = "";
                        if (i == 0) {
                            exactDate = DateUtils.getDate();
                        } else if(i > 0) {
                            exactDate = DateUtils.addDaysYYYYMMDD(DateUtils.getDate(), i);
                        }

                        if (subMatchList != null && subMatchList.size() > 0) {
                            for (int j = 0; j < subMatchList.size(); j++) {
                                SubMatchList sl = subMatchList.get(j);
                                SPMatchInfo smi = new SPMatchInfo();
                                smi.setCreateTime(System.currentTimeMillis());
                                smi.setExactDate(exactDate);
                                smi.setHomeTeamAbbName(sl.getHomeTeamAbbName());
                                smi.setAwaTeamAbbName(sl.getAwayTeamAbbName());
                                smi.setMatchDate(sl.getMatchDate());
                                smi.setLeagueAbbName(sl.getLeagueAbbName());
                                smi.setMatchNum(sl.getMatchNum());
                                smi.setMatchTime(sl.getMatchTime());
                                Had had = sl.getHad();
                                smi.setLose(had.getA());
                                smi.setDraw(had.getD());
                                smi.setWin(had.getH());
                                Hhad hhad = sl.getHhad();
                                smi.setHandicapLose(hhad.getA());
                                smi.setHandicapDraw(hhad.getD());
                                smi.setHandicapWin(hhad.getH());
                                smi.setHandicap(hhad.getGoalLine());
                                Ttg ttg = sl.getTtg();
                                smi.setS0(ttg.getS0());
                                smi.setS1(ttg.getS1());
                                smi.setS2(ttg.getS2());
                                smi.setS3(ttg.getS3());
                                smi.setS4(ttg.getS4());
                                smi.setS5(ttg.getS5());
                                smi.setS6(ttg.getS6());
                                smi.setS7(ttg.getS7());
                                spMatchInfos.add(smi);
                            }
                        }
                    }
                }
            }
        }

        if(spMatchInfos != null && spMatchInfos.size() > 0){
            try {
                //查询有没有投注中状态的记录
                List<BetSPMatchInfo> bettingRecordList = betSPMatchInfoMapper.findBettingRecord();
                if(bettingRecordList != null && bettingRecordList.size() > 0){

                    for(BetSPMatchInfo bsmi : bettingRecordList){

                        for(SPMatchInfo smi: spMatchInfos){
                            //如果比赛编号和比赛日期相等 则比较赔率有没有变化
                            if(bsmi.getMatchNum() == smi.getMatchNum() && bsmi.getMatchDate().equals(smi.getMatchDate())){

                                if(!smi.getWin().equals(bsmi.getWin())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.win.getValue(), bsmi.getId());
                                    if (msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️体彩水位变动⚠️⚠,  ️比赛编号:" + smi.getMatchNum() + ",  @@ 主胜,  原始赔率 @" + bsmi.getWin() + ",  最新赔率 @" + smi.getWin());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.win.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getDraw().equals(bsmi.getDraw())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.draw.getValue(), bsmi.getId());
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️体彩水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@ 平,  原始赔率 @"+bsmi.getDraw() + ",  最新赔率 @" + smi.getDraw());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.draw.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getLose().equals(bsmi.getLose())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.lose.getValue(), bsmi.getId());
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️体彩水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@ 客胜,  原始赔率 @"+bsmi.getLose() + ",  最新赔率 @" + smi.getLose());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.lose.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getHandicapWin().equals(bsmi.getHandicapWin())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.handicapWin.getValue(), bsmi.getId());
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@主让胜 / 主受让胜,  原始赔率 @"+bsmi.getHandicapWin() + ",  最新赔率 @" + smi.getHandicapWin());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.handicapWin.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getHandicapLose().equals(bsmi.getHandicapLose())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.handicapWin.getValue(), bsmi.getId());
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@主让客胜 / 主受让客胜,  原始赔率 @"+bsmi.getHandicapLose() + ",  最新赔率 @" + smi.getHandicapLose());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.handicapLose.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                            }
                        }
                    }
                }

                for(SPMatchInfo smi : spMatchInfos){
                    SPMatchInfo MatchInfo = spMatchInfoMapper.findSPMatchInfo(smi.getMatchNum(), smi.getMatchDate());
                    if(MatchInfo == null){
                        //插入
                        spMatchInfoMapper.insertSPMatchInfo(smi);
                    }else {
                        //更新
                        spMatchInfoMapper.updateMatchInfo(smi);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    @Override
    public void getSportsBettingBKData() {
        String s = HttpUtils.commonGet("https://webapi.sporttery.cn/gateway/jc/basketball/getMatchCalculatorV1.qry");
        JSONObject obj = JSONObject.parseObject(s);
        Root root = JSON.toJavaObject(obj, Root.class);
        //批量插入集合
        List<SPBKMatchInfo> spBKMatchInfos = new ArrayList<>();
        if (root != null) {
            Value value = root.getValue();

            if (value != null) {
                List<MatchInfoList> matchInfoList = value.getMatchInfoList();

                if (matchInfoList != null && matchInfoList.size() > 0) {
                    for (MatchInfoList m : matchInfoList) {
                        List<SubMatchList> subMatchList = m.getSubMatchList();

                        if (subMatchList != null && subMatchList.size() > 0) {
                            for (SubMatchList sl : subMatchList){

                                SPBKMatchInfo smi = new SPBKMatchInfo();
                                smi.setCreateTime(System.currentTimeMillis());
                                smi.setHomeTeamAbbName(sl.getHomeTeamAbbName());
                                smi.setAwayTeamAbbName(sl.getAwayTeamAbbName());
                                smi.setMatchDate(sl.getMatchDate());
                                smi.setMatchNum(sl.getMatchNum());
                                smi.setMatchTime(sl.getMatchTime());
                                Mnl mnl = sl.getMnl();
                                smi.setLose(mnl.getA());
                                smi.setWin(mnl.getH());
                                Hdc hdc = sl.getHdc();
                                smi.setHandicap(hdc.getGoalLine());
                                smi.setHandicapWin(hdc.getA());
                                smi.setHandicapLose(hdc.getH());
                                Hilo hilo = sl.getHilo();
                                smi.setHigh(hilo.getH());
                                smi.setLow(hilo.getL());
                                smi.setScore(hilo.getGoalLine());

                                spBKMatchInfos.add(smi);
                            }
                        }
                    }
                }
            }
        }

        if(spBKMatchInfos != null && spBKMatchInfos.size() >0){
            try {
                spMatchInfoMapper.deleteBKMatchInfo();
                spMatchInfoMapper.insertSPBKMatchInfos(spBKMatchInfos);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 将投注中的记录复制到投注表(bet_sp_match_info) 并把投注状态设置成投注中
     */
    @Override
    public void betStart(BetSPMatchInfo betParam) {
        SPMatchInfo spMatchInfo =  spMatchInfoMapper.findSPMatchInfo(betParam.getMatchNum(), betParam.getMatchDate());
        BetSPMatchInfo betSPMatchInfo = new BetSPMatchInfo();
        BeanUtils.copyBeanProp(betSPMatchInfo, spMatchInfo);
        betSPMatchInfo.setSpId(spMatchInfo.getId());
        betSPMatchInfoMapper.insertBetSPMatchInfo(betSPMatchInfo);
    }

    @Override
    public void cleanObsoleteData() {
        String weekOfDate = DateUtils.getWeekOfDate(new Date());
        int num = Integer.valueOf(weekOfDate) * 1000;
        spMatchInfoMapper.cleanObsoleteData(num,num+1000);
    }

    /**
     * 录入皇冠投注数据，计算体彩投注金额
     * @param betSPMatchInfo
     * @return
     */
    @Override
    public AjaxResult betInfoInput(BetSPMatchInfo betSPMatchInfo) {
        String betType = betSPMatchInfo.getBetType();
        Long spId = betSPMatchInfo.getSpId();
        Double hgOdds1 = betSPMatchInfo.getHgOdds1();
        Double hgAmount = betSPMatchInfo.getHgAmount();
        SPMatchInfo spMatchInfo = spMatchInfoMapper.selectById(spId);
        if (betType.equals(BetTypeEnum.hedge_SPHomeWin_HGVisitAdd05.getValue())) {
            String win = spMatchInfo.getWin();
            BetParamVo betParamVo = new BetParamVo();
            betParamVo.setBetAmountHg(hgAmount);
            betParamVo.setOddsWin(Double.valueOf(win));
            betParamVo.setVisitAdd05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPBallService.SPWin_HGVisitAdd05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountWin());
            }
        } else if (betType.equals(BetTypeEnum.hedge_SPVisitWin_HGHomeAdd05.getValue())) {
            String lose = spMatchInfo.getLose();
            BetParamVo betParamVo = new BetParamVo();
            betParamVo.setBetAmountHg(hgAmount);
            betParamVo.setOddsLose(Double.valueOf(lose));
            betParamVo.setHomeAdd05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPBallService.SPLose_HGHomeAdd05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountLose());
            }
        } else if (betType.equals(BetTypeEnum.hedge_SPRangLose_HGHomeCut5.getValue())) {
            String handicapLose = spMatchInfo.getHandicapLose();
            BetParamVo betParamVo = new BetParamVo();
            betParamVo.setBetAmountHg(hgAmount);
            betParamVo.setOddsRangLose(Double.valueOf(handicapLose));
            betParamVo.setHomeCut05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPBallService.SPRangLose_HGHomeCut05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountRangLose());
            }
        } else if (betType.equals(BetTypeEnum.hedge_SPShouWin_HGVisitCut05.getValue())) {
            String handicapWin = spMatchInfo.getHandicapWin();
            BetParamVo betParamVo = new BetParamVo();
            betParamVo.setBetAmountHg(hgAmount);
            betParamVo.setOddsShouWin(Double.valueOf(handicapWin));
            betParamVo.setVisitCut05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPBallService.SPShouWin_HGVisitCut05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountShouWin());
            }
        }
        int result = betSPMatchInfoMapper.updateBetMatchInfo(betSPMatchInfo);
        if (result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

}

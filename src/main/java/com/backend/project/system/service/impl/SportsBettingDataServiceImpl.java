package com.backend.project.system.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.CalcUtil;
import com.backend.common.utils.DateUtils;
import com.backend.common.utils.bean.BeanUtils;
import com.backend.common.utils.http.HttpUtils;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.*;
import com.backend.project.system.domain.vo.*;
import com.backend.project.system.enums.BetTypeEnum;
import com.backend.project.system.mapper.BetSPMatchInfoMapper;
import com.backend.project.system.mapper.NotifyMsgMapper;
import com.backend.project.system.mapper.SPBetAmountMapper;
import com.backend.project.system.mapper.SPMatchInfoMapper;
import com.backend.project.system.service.IHgSPBallService;
import com.backend.project.system.service.IHgSPDealerBallService;
import com.backend.project.system.service.ISportsBettingDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    @Resource
    private IHgSPDealerBallService hgSPDealerBallService;
    @Resource
    private SPBetAmountMapper spBetAmountMapper;
    @org.springframework.beans.factory.annotation.Value("${tgApi.url}")
    private String tgUrl;
    private Double limitBet = 10000d;


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
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.win.getValue(), bsmi.getId(), null);
                                    if (msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️体彩水位变动⚠️⚠,  ️比赛编号:" + smi.getMatchNum() + ",  @@ 主胜,  原始赔率 @" + bsmi.getWin() + ",  最新赔率 @" + smi.getWin());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️体彩水位变动⚠️⚠,  ️比赛编号:" + smi.getMatchNum() + ",  @@ 主胜,  原始赔率 @" + bsmi.getWin() + ",  最新赔率 @" + smi.getWin());

                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.win.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getDraw().equals(bsmi.getDraw())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.draw.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️体彩水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@ 平,  原始赔率 @"+bsmi.getDraw() + ",  最新赔率 @" + smi.getDraw());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️体彩水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@ 平,  原始赔率 @"+bsmi.getDraw() + ",  最新赔率 @" + smi.getDraw());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.draw.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getLose().equals(bsmi.getLose())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.lose.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️体彩水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@ 客胜,  原始赔率 @"+bsmi.getLose() + ",  最新赔率 @" + smi.getLose());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️体彩水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@ 客胜,  原始赔率 @"+bsmi.getLose() + ",  最新赔率 @" + smi.getLose());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.lose.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getHandicapWin().equals(bsmi.getHandicapWin())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.handicapWin.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@主让胜 / 主受让胜,  原始赔率 @"+bsmi.getHandicapWin() + ",  最新赔率 @" + smi.getHandicapWin());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@主让胜 / 主受让胜,  原始赔率 @"+bsmi.getHandicapWin() + ",  最新赔率 @" + smi.getHandicapWin());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.handicapWin.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getHandicapLose().equals(bsmi.getHandicapLose())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.handicapWin.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@主让客胜 / 主受让客胜,  原始赔率 @"+bsmi.getHandicapLose() + ",  最新赔率 @" + smi.getHandicapLose());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠️,  比赛编号:" + smi.getMatchNum() + ",  @@主让客胜 / 主受让客胜,  原始赔率 @"+bsmi.getHandicapLose() + ",  最新赔率 @" + smi.getHandicapLose());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.handicapLose.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS0().equals(bsmi.getS0())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_zero.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 0球,  原始赔率 @"+bsmi.getS0() + ",  最新赔率 @" + smi.getS0());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 0球,  原始赔率 @"+bsmi.getS0() + ",  最新赔率 @" + smi.getS0());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_zero.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS1().equals(bsmi.getS1())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_one.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 1球,  原始赔率 @"+bsmi.getS1() + ",  最新赔率 @" + smi.getS1());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 1球,  原始赔率 @"+bsmi.getS1() + ",  最新赔率 @" + smi.getS1());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_one.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS2().equals(bsmi.getS2())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_two.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 2球,  原始赔率 @"+bsmi.getS2() + ",  最新赔率 @" + smi.getS2());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 2球,  原始赔率 @"+bsmi.getS2() + ",  最新赔率 @" + smi.getS2());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_two.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS3().equals(bsmi.getS3())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_three.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 3球,  原始赔率 @"+bsmi.getS3() + ",  最新赔率 @" + smi.getS3());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 3球,  原始赔率 @"+bsmi.getS3() + ",  最新赔率 @" + smi.getS3());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_three.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS4().equals(bsmi.getS4())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_four.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 4球,  原始赔率 @"+bsmi.getS4() + ",  最新赔率 @" + smi.getS4());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 4球,  原始赔率 @"+bsmi.getS4() + ",  最新赔率 @" + smi.getS4());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_four.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS5().equals(bsmi.getS5())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_five.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 5球,  原始赔率 @"+bsmi.getS5() + ",  最新赔率 @" + smi.getS5());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 5球,  原始赔率 @"+bsmi.getS5() + ",  最新赔率 @" + smi.getS5());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_five.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS6().equals(bsmi.getS6())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_six.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 6球,  原始赔率 @"+bsmi.getS6() + ",  最新赔率 @" + smi.getS6());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 6球,  原始赔率 @"+bsmi.getS6() + ",  最新赔率 @" + smi.getS6());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_six.getValue());
                                        msg.setNotifyTime(System.currentTimeMillis());
                                        msg.setBetId(bsmi.getId());
                                        //通知后更新通知时间
                                        notifyMsgMapper.insertNotifyMsg(msg);
                                    }
                                }
                                if(!smi.getS7().equals(bsmi.getS7())){
                                    NotifyMsg msgByCondition = notifyMsgMapper.findMsgByCondition(BetTypeEnum.total_ball_seven.getValue(), bsmi.getId(), null);
                                    if(msgByCondition == null || System.currentTimeMillis() - msgByCondition.getNotifyTime() > 600000) {
                                        //通知 胜平负赔率变化
                                        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 7球,  原始赔率 @"+bsmi.getS7() + ",  最新赔率 @" + smi.getS7());
                                        HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=⚠️⚠️水位变动⚠️⚠,  比赛编号:" + smi.getMatchNum() + ",  @@ 7球,  原始赔率 @"+bsmi.getS7() + ",  最新赔率 @" + smi.getS7());
                                        NotifyMsg msg = new NotifyMsg();
                                        msg.setMsgType(BetTypeEnum.total_ball_seven.getValue());
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
     * 录入皇冠投注数据，计算体彩投注金额 - 体彩
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
        if (betType.equals(BetTypeEnum.spHomeWin_hgVisitAdd05.getValue())) {
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
        } else if (betType.equals(BetTypeEnum.spVisitWin_hgHomeAdd05.getValue())) {
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
        } else if (betType.equals(BetTypeEnum.spRangLose_hgHomeCut05.getValue())) {
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
        } else if (betType.equals(BetTypeEnum.spShouWin_hgVisitCut05.getValue())) {
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

    /**
     * 录入皇冠投注数据，计算体彩投注金额 - zhuang
     * @param betSPMatchInfo
     * @return
     */
    @Override
    public AjaxResult betInfoInputDealer(BetSPMatchInfo betSPMatchInfo) {
        String betType = betSPMatchInfo.getBetType();
        Long spId = betSPMatchInfo.getSpId();
        Double hgOdds1 = betSPMatchInfo.getHgOdds1();
        Double hgAmount = betSPMatchInfo.getHgAmount();
        SPMatchInfo spMatchInfo = spMatchInfoMapper.selectById(spId);
        BetParamVo betParamVo = new BetParamVo();
        betParamVo.setBetAmountHg(hgAmount);
        if (betType.equals(BetTypeEnum.spHomeWin_hgVisitAdd05.getValue())) {
            String win = spMatchInfo.getWin();
            betParamVo.setOddsWin(Double.valueOf(win));
            betParamVo.setVisitAdd05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SPWin_HGVisitAdd05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountWin());
            }
        } else if (betType.equals(BetTypeEnum.spVisitWin_hgHomeAdd05.getValue())) {
            String lose = spMatchInfo.getLose();
            betParamVo.setOddsLose(Double.valueOf(lose));
            betParamVo.setHomeAdd05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SPLose_HGHomeAdd05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountLose());
            }
        } else if (betType.equals(BetTypeEnum.spRangLose_hgHomeCut05.getValue())) {
            String handicapLose = spMatchInfo.getHandicapLose();
            betParamVo.setOddsRangLose(Double.valueOf(handicapLose));
            betParamVo.setHomeCut05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SPRangLose_HGHomeCut05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountRangLose());
            }
        } else if (betType.equals(BetTypeEnum.spShouWin_hgVisitCut05.getValue())) {
            String handicapWin = spMatchInfo.getHandicapWin();
            betParamVo.setOddsShouWin(Double.valueOf(handicapWin));
            betParamVo.setVisitCut05(hgOdds1);
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SPShouWin_HGVisitCut05(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount(betParamResult.getBetAmountShouWin());
            }
        } else if (betType.equals(BetTypeEnum.big_15.getValue())) {
            betParamVo.set大15(hgOdds1);
            betParamVo.setOddsZero(Double.valueOf(spMatchInfo.getS0()));
            betParamVo.setOddsOne(Double.valueOf(spMatchInfo.getS1()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP01_HG大15(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount0(betParamResult.getBetAmountZero());
                betSPMatchInfo.setSpAmount1(betParamResult.getBetAmountOne());
            }
        } else if (betType.equals(BetTypeEnum.big_15_2.getValue())) {
            betParamVo.set大15_2(hgOdds1);
            betParamVo.setOddsZero(Double.valueOf(spMatchInfo.getS0()));
            betParamVo.setOddsOne(Double.valueOf(spMatchInfo.getS1()));
            betParamVo.setOddsTwo(Double.valueOf(spMatchInfo.getS2()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP012_HG大15_2(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount0(betParamResult.getBetAmountZero());
                betSPMatchInfo.setSpAmount1(betParamResult.getBetAmountOne());
                betSPMatchInfo.setSpAmount2(betParamResult.getBetAmountTwo());
            }
        } else if (betType.equals(BetTypeEnum.big_25.getValue())) {
            betParamVo.set大25(hgOdds1);
            betParamVo.setOddsZero(Double.valueOf(spMatchInfo.getS0()));
            betParamVo.setOddsOne(Double.valueOf(spMatchInfo.getS1()));
            betParamVo.setOddsTwo(Double.valueOf(spMatchInfo.getS2()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP012_HG大25(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount0(betParamResult.getBetAmountZero());
                betSPMatchInfo.setSpAmount1(betParamResult.getBetAmountOne());
                betSPMatchInfo.setSpAmount2(betParamResult.getBetAmountTwo());
            }
        } else if (betType.equals(BetTypeEnum.big_2_25.getValue())) {
            betParamVo.set大2_25(hgOdds1);
            betParamVo.setOddsZero(Double.valueOf(spMatchInfo.getS0()));
            betParamVo.setOddsOne(Double.valueOf(spMatchInfo.getS1()));
            betParamVo.setOddsTwo(Double.valueOf(spMatchInfo.getS2()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP012_HG大2_25(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount0(betParamResult.getBetAmountZero());
                betSPMatchInfo.setSpAmount1(betParamResult.getBetAmountOne());
                betSPMatchInfo.setSpAmount2(betParamResult.getBetAmountTwo());
            }
        } else if (betType.equals(BetTypeEnum.big_25_3.getValue())) {
            betParamVo.set大25_3(hgOdds1);
            betParamVo.setOddsZero(Double.valueOf(spMatchInfo.getS0()));
            betParamVo.setOddsOne(Double.valueOf(spMatchInfo.getS1()));
            betParamVo.setOddsTwo(Double.valueOf(spMatchInfo.getS2()));
            betParamVo.setOddsThree(Double.valueOf(spMatchInfo.getS3()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP012_HG大25_3(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount0(betParamResult.getBetAmountZero());
                betSPMatchInfo.setSpAmount1(betParamResult.getBetAmountOne());
                betSPMatchInfo.setSpAmount2(betParamResult.getBetAmountTwo());
                betSPMatchInfo.setSpAmount3(betParamResult.getBetAmountThree());
            }
        } else if (betType.equals(BetTypeEnum.big_35.getValue())) {
            betParamVo.set大35(hgOdds1);
            betParamVo.setOddsZero(Double.valueOf(spMatchInfo.getS0()));
            betParamVo.setOddsOne(Double.valueOf(spMatchInfo.getS1()));
            betParamVo.setOddsTwo(Double.valueOf(spMatchInfo.getS2()));
            betParamVo.setOddsThree(Double.valueOf(spMatchInfo.getS3()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP012_HG大35(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount0(betParamResult.getBetAmountZero());
                betSPMatchInfo.setSpAmount1(betParamResult.getBetAmountOne());
                betSPMatchInfo.setSpAmount2(betParamResult.getBetAmountTwo());
                betSPMatchInfo.setSpAmount3(betParamResult.getBetAmountThree());
            }
        } else if (betType.equals(BetTypeEnum.big_3_35.getValue())) {
            betParamVo.set大3_35(hgOdds1);
            betParamVo.setOddsZero(Double.valueOf(spMatchInfo.getS0()));
            betParamVo.setOddsOne(Double.valueOf(spMatchInfo.getS1()));
            betParamVo.setOddsTwo(Double.valueOf(spMatchInfo.getS2()));
            betParamVo.setOddsThree(Double.valueOf(spMatchInfo.getS3()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP012_HG大3_35(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount0(betParamResult.getBetAmountZero());
                betSPMatchInfo.setSpAmount1(betParamResult.getBetAmountOne());
                betSPMatchInfo.setSpAmount2(betParamResult.getBetAmountTwo());
                betSPMatchInfo.setSpAmount3(betParamResult.getBetAmountThree());
            }
        } else if (betType.equals(BetTypeEnum.small_25.getValue())) {
            betParamVo.set小25(hgOdds1);
            betParamVo.setOddsThree(Double.valueOf(spMatchInfo.getS3()));
            betParamVo.setOddsFour(Double.valueOf(spMatchInfo.getS4()));
            betParamVo.setOddsFive(Double.valueOf(spMatchInfo.getS5()));
            betParamVo.setOddsSix(Double.valueOf(spMatchInfo.getS6()));
            betParamVo.setOddsSeven(Double.valueOf(spMatchInfo.getS7()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP34567_HG小25(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount3(betParamResult.getBetAmountThree());
                betSPMatchInfo.setSpAmount4(betParamResult.getBetAmountFour());
                betSPMatchInfo.setSpAmount5(betParamResult.getBetAmountFive());
                betSPMatchInfo.setSpAmount6(betParamResult.getBetAmountSix());
                betSPMatchInfo.setSpAmount7(betParamResult.getBetAmountSeven());
            }
        } else if (betType.equals(BetTypeEnum.small_2_25.getValue())) {
            betParamVo.set小2_25(hgOdds1);
            betParamVo.setOddsTwo(Double.valueOf(spMatchInfo.getS2()));
            betParamVo.setOddsThree(Double.valueOf(spMatchInfo.getS3()));
            betParamVo.setOddsFour(Double.valueOf(spMatchInfo.getS4()));
            betParamVo.setOddsFive(Double.valueOf(spMatchInfo.getS5()));
            betParamVo.setOddsSix(Double.valueOf(spMatchInfo.getS6()));
            betParamVo.setOddsSeven(Double.valueOf(spMatchInfo.getS7()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP234567_HG小2_25(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount2(betParamResult.getBetAmountTwo());
                betSPMatchInfo.setSpAmount3(betParamResult.getBetAmountThree());
                betSPMatchInfo.setSpAmount4(betParamResult.getBetAmountFour());
                betSPMatchInfo.setSpAmount5(betParamResult.getBetAmountFive());
                betSPMatchInfo.setSpAmount6(betParamResult.getBetAmountSix());
                betSPMatchInfo.setSpAmount7(betParamResult.getBetAmountSeven());
            }
        } else if (betType.equals(BetTypeEnum.small_25_3.getValue())) {
            betParamVo.set小25_3(hgOdds1);
            betParamVo.setOddsThree(Double.valueOf(spMatchInfo.getS3()));
            betParamVo.setOddsFour(Double.valueOf(spMatchInfo.getS4()));
            betParamVo.setOddsFive(Double.valueOf(spMatchInfo.getS5()));
            betParamVo.setOddsSix(Double.valueOf(spMatchInfo.getS6()));
            betParamVo.setOddsSeven(Double.valueOf(spMatchInfo.getS7()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP34567_HG小25_3(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount3(betParamResult.getBetAmountThree());
                betSPMatchInfo.setSpAmount4(betParamResult.getBetAmountFour());
                betSPMatchInfo.setSpAmount5(betParamResult.getBetAmountFive());
                betSPMatchInfo.setSpAmount6(betParamResult.getBetAmountSix());
                betSPMatchInfo.setSpAmount7(betParamResult.getBetAmountSeven());
            }
        } else if (betType.equals(BetTypeEnum.small_35.getValue())) {
            betParamVo.set小35(hgOdds1);
            betParamVo.setOddsFour(Double.valueOf(spMatchInfo.getS4()));
            betParamVo.setOddsFive(Double.valueOf(spMatchInfo.getS5()));
            betParamVo.setOddsSix(Double.valueOf(spMatchInfo.getS6()));
            betParamVo.setOddsSeven(Double.valueOf(spMatchInfo.getS7()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP4567_HG小35(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount4(betParamResult.getBetAmountFour());
                betSPMatchInfo.setSpAmount5(betParamResult.getBetAmountFive());
                betSPMatchInfo.setSpAmount6(betParamResult.getBetAmountSix());
                betSPMatchInfo.setSpAmount7(betParamResult.getBetAmountSeven());
            }
        } else if (betType.equals(BetTypeEnum.small_3_35.getValue())) {
            betParamVo.set小3_35(hgOdds1);
            betParamVo.setOddsThree(Double.valueOf(spMatchInfo.getS3()));
            betParamVo.setOddsFour(Double.valueOf(spMatchInfo.getS4()));
            betParamVo.setOddsFive(Double.valueOf(spMatchInfo.getS5()));
            betParamVo.setOddsSix(Double.valueOf(spMatchInfo.getS6()));
            betParamVo.setOddsSeven(Double.valueOf(spMatchInfo.getS7()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP34567_HG小3_35(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount3(betParamResult.getBetAmountThree());
                betSPMatchInfo.setSpAmount4(betParamResult.getBetAmountFour());
                betSPMatchInfo.setSpAmount5(betParamResult.getBetAmountFive());
                betSPMatchInfo.setSpAmount6(betParamResult.getBetAmountSix());
                betSPMatchInfo.setSpAmount7(betParamResult.getBetAmountSeven());
            }
        } else if (betType.equals(BetTypeEnum.small_35_4.getValue())) {
            betParamVo.set小35_4(hgOdds1);
            betParamVo.setOddsFour(Double.valueOf(spMatchInfo.getS4()));
            betParamVo.setOddsFive(Double.valueOf(spMatchInfo.getS5()));
            betParamVo.setOddsSix(Double.valueOf(spMatchInfo.getS6()));
            betParamVo.setOddsSeven(Double.valueOf(spMatchInfo.getS7()));
            betParamVo.setNotifyFlag(0);
            BetParamVo betParamResult = hgSPDealerBallService.SP4567_HG小35_4(betParamVo);
            if (betParamResult != null) {
                betSPMatchInfo.setSpAmount4(betParamResult.getBetAmountFour());
                betSPMatchInfo.setSpAmount5(betParamResult.getBetAmountFive());
                betSPMatchInfo.setSpAmount6(betParamResult.getBetAmountSix());
                betSPMatchInfo.setSpAmount7(betParamResult.getBetAmountSeven());
            }
        }
        int result = betSPMatchInfoMapper.updateBetMatchInfo(betSPMatchInfo);
        if (result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 拆分金额
     * @param betId
     * @return
     */
    @Override
    public AjaxResult splitAmount(Long betId) {
        BetSPMatchInfo betSPMatchInfo = betSPMatchInfoMapper.findBetInfoById(betId);
        try {

        if(betSPMatchInfo != null){
            /**体彩主胜*/
            if(BetTypeEnum.win.getValue().equals(betSPMatchInfo.getBetType())  //体彩主胜
               || BetTypeEnum.spHomeWin_hgVisitAdd05.getValue().equals(betSPMatchInfo.getBetType()) //体彩主胜，皇冠客+05
               || BetTypeEnum.spHomeWin_hgVisitWinAndTie.getValue().equals(betSPMatchInfo.getBetType())){//体彩主胜，皇冠客胜&平
                String winStr = betSPMatchInfo.getWin();
                generateAmount(betSPMatchInfo, winStr);

                //保存投注额列表
                /**体彩主平*/
            }else if(BetTypeEnum.draw.getValue().equals(betSPMatchInfo.getBetType())//体彩平
                || BetTypeEnum.spTie_hgHomeWinAndVisitWin.getValue().equals(betSPMatchInfo.getBetType())){//体彩平，皇冠主胜&客胜"

                String drawStr = betSPMatchInfo.getDraw();
                generateAmount(betSPMatchInfo, drawStr);

                /**体彩主负*/
            }else if(BetTypeEnum.lose.getValue().equals(betSPMatchInfo.getBetType())//体彩主负
                || BetTypeEnum.spVisitWin_hgHomeAdd05.getValue().equals(betSPMatchInfo.getBetType()) //体彩主负，皇冠主+05胜
                || BetTypeEnum.spVisitWin_hgHomeWinAndTie.getValue().equals(betSPMatchInfo.getBetType())){//体彩主负，皇冠主胜&平
                String loseStr = betSPMatchInfo.getLose();
                generateAmount(betSPMatchInfo, loseStr);

                /**主让胜/受球胜*/
            } else if (BetTypeEnum.handicapWin.getValue().equals(betSPMatchInfo.getBetType())//体彩主让胜
                || BetTypeEnum.spShouWin_hgVisitCut05.getValue().equals(betSPMatchInfo.getBetType())){ //主受球胜，皇冠客-05
                String handicapWinStr = betSPMatchInfo.getHandicapWin();
                generateAmount(betSPMatchInfo, handicapWinStr);

                /**主让负/受球负*/
            }else if(BetTypeEnum.handicapLose.getValue().equals(betSPMatchInfo.getBetType())//体彩主让负
                || BetTypeEnum.spRangLose_hgHomeCut05.getValue().equals(betSPMatchInfo.getBetType())){//体彩主让球负，皇冠主-05
                String getHandicapLoseStr = betSPMatchInfo.getHandicapLose();
                generateAmount(betSPMatchInfo, getHandicapLoseStr);
            }
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        return AjaxResult.success();
    }

    private void generateAmount(BetSPMatchInfo betSPMatchInfo, String drawStr) {
        Double odds = Double.valueOf(drawStr);
        Double bigBet = CalcUtil.div(limitBet, odds);
        Double spAmount = betSPMatchInfo.getSpAmount();
        Double betSpAmount = 0d;
        List betList = new ArrayList();
        //如果总投注额 - 已累计投注额 < 单注最大投注额  循环结束
        while (spAmount - betSpAmount > bigBet) {
            double v = randomAmount(bigBet.intValue());
            long l = Math.round(v / 10) * 10;
            betSpAmount = CalcUtil.add(betSpAmount, l);
            System.out.println("已累计金额:"+betSpAmount);
            betList.add(l);
        }
        Double sub = CalcUtil.sub(spAmount, betSpAmount);
        System.out.println("已累计金额:"+betSpAmount);
        betList.add(sub.intValue());
        SPBetAmount spBetAmount = new SPBetAmount();
        spBetAmount.setAmount(betList);
        spBetAmount.setBetId(betSPMatchInfo.getId());
        spBetAmountMapper.insertSPBetAmount(spBetAmount);
    }

    /**
     * 随机生成
     */
    private  static double randomAmount(int max){
        Random random = new Random();
        double i = random.nextInt(max);
        return i ;

    }

    public static void main(String[] args) {
        double v = randomAmount(500);
        System.out.println(v*10);
    }

}

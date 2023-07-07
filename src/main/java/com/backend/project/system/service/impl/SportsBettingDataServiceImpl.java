package com.backend.project.system.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.DateUtils;
import com.backend.common.utils.http.HttpUtils;
import com.backend.project.system.domain.SPBKMatchInfo;
import com.backend.project.system.domain.SPMatchInfo;
import com.backend.project.system.domain.vo.*;
import com.backend.project.system.mapper.SPMatchInfoMapper;
import com.backend.project.system.service.ISportsBettingDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取体彩数据入库
 */

@Service
public class SportsBettingDataServiceImpl implements ISportsBettingDataService {

    @Resource
    private SPMatchInfoMapper spMatchInfoMapper;


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
                Integer integer = null;
                List<MatchInfoList> matchInfoList = value.getMatchInfoList();

                if (matchInfoList != null && matchInfoList.size() > 0) {
                    for (MatchInfoList m : matchInfoList) {
                        List<SubMatchList> subMatchList = m.getSubMatchList();

                        if (subMatchList != null && subMatchList.size() > 0) {
                            for (int i = 0; i < subMatchList.size(); i++) {
                                SubMatchList sl = subMatchList.get(i);
                                SPMatchInfo smi = new SPMatchInfo();
                                smi.setCreateTime(System.currentTimeMillis());
                                smi.setHomeTeamAbbName(sl.getHomeTeamAbbName());
                                smi.setAwaTeamAbbName(sl.getAwayTeamAbbName());
                                smi.setMatchDate(sl.getMatchDate());
                                smi.setLeagueAbbName(sl.getLeagueAbbName());
                                int matchNum = sl.getMatchNum();
                                String exactDate = "";
                                // 转换精确时间
                                String substring = String.valueOf(matchNum).substring(0, 1);
                                if (i == 0) {
                                    integer = Integer.valueOf(substring);
                                }
                                Integer num = Integer.valueOf(substring);
                                if (num == integer) {
                                    exactDate = DateUtils.getDate();
                                } else if(num > integer) {
                                    int substarct = num - integer;
                                    exactDate = DateUtils.addDaysYYYYMMDD(DateUtils.getDate(), substarct);
                                }
                                smi.setExactDate(exactDate);
                                smi.setMatchNum(matchNum);
                                smi.setMatchTime(sl.getMatchTime());
                                Had had = sl.getHad();
                                smi.setLose(had.getA());
                                smi.setDraw(had.getD());
                                smi.setWin(had.getH());
                                Hhad hhad = sl.getHhad();
                                smi.setHandicapLose(hhad.getA());
                                smi.setHandicapDraw(hhad.getD());
                                smi.setHandicapWin(hhad.getH());
                                Ttg ttg = sl.getTtg();
                                smi.setS0(ttg.getS0());
                                smi.setS1(ttg.getS1());
                                smi.setS2(ttg.getS2());
                                smi.setS3(ttg.getS3());
                                smi.setS4(ttg.getS4());
                                smi.setS5(ttg.getS5());
                                smi.setS6(ttg.getS6());
                                smi.setS7(ttg.getS7());
//                                spMatchInfoMapper.insertSPMatchInfo(smi);
                                spMatchInfos.add(smi);
                            }
                        }
                    }
                }
            }
        }

        if(spMatchInfos != null && spMatchInfos.size() >0){
            try {
                spMatchInfoMapper.deleteMatchInfo();
                spMatchInfoMapper.insertSPMatchInfos(spMatchInfos);
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
}

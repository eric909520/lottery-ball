package com.backend.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.AgApiUtils;
import com.backend.project.system.domain.AgApi;
import com.backend.project.system.domain.AgLeagueData;
import com.backend.project.system.enums.AgApiEnum;
import com.backend.project.system.enums.AgMarketTypeEnum;
import com.backend.project.system.enums.AgSportTypeEnum;
import com.backend.project.system.mapper.AgApiMapper;
import com.backend.project.system.mapper.AgLeagueDataMapper;
import com.backend.project.system.service.IAgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;

@Service
@Slf4j
public class AgScheduleServiceImpl implements IAgScheduleService {

    @Resource
    private AgApiMapper agApiMapper;
    @Resource
    private AgLeagueDataMapper agLeagueDataMapper;

    /**
     * polling today football league data
     */
    @Override
    public void pollingTodayFootballLeagueData() {
        try {
            AgApi agApi = agApiMapper.selectByApi(AgApiEnum.get_match_list.getApi());
            agApi.setSportType(AgSportTypeEnum.football.getType());
            agApi.setMarketType(AgMarketTypeEnum.today.getType());
            agApi.setPageIndex(1);
            JSONObject jsonObject = getFootballLeagueData(agApi);
            if (jsonObject == null) return;
            int totalPage = jsonObject.getIntValue("TotalPage");
            if (totalPage < 2) {
                return;
            }
            for (int i = 2; i <= totalPage; i++) {
                agApi.setPageIndex(i);
                getFootballLeagueData(agApi);
            }
        } catch (Exception e) {
            log.info("ag - pollingTodayFootballLeagueData exception ----->>>>", e);
        }
    }

    public JSONObject getFootballLeagueData(AgApi agApi) {
        String match_list = AgApiUtils.get_match_list(agApi);
        JSONObject jsonObject =  JSON.parseObject(match_list);
        int ret = jsonObject.getIntValue("Ret");
        if (ret != 0) {
            return null;
        }
        JSONArray matches = jsonObject.getJSONArray("Matches");
        Iterator<Object> iterator = matches.stream().iterator();
        while (iterator.hasNext()) {
            JSONObject match = JSON.parseObject(iterator.next().toString());
            String leagueName = match.getString("LeagueName");
            String matchID = match.getString("MatchID");
            Integer exist = agLeagueDataMapper.selectByMatchId(matchID);
            if (exist != null) {
                continue;
            }
            String leagueID = match.getString("LeagueID");
            String kickoffTime = match.getString("KickoffTime");
            Long kickoffTimeStamp = match.getLong("KickoffTimeStamp");
            String homeTeamName = match.getString("HomeTeamName");
            String homeTeamID = match.getString("HomeTeamID");
            String awayTeamName = match.getString("AwayTeamName");
            String awayTeamID = match.getString("AwayTeamID");
            AgLeagueData agLeagueData = new AgLeagueData(AgSportTypeEnum.football.getType(), leagueName, leagueID
                    , matchID, kickoffTime, kickoffTimeStamp, homeTeamName, homeTeamID, awayTeamName, awayTeamID);
            agLeagueDataMapper.insertData(agLeagueData);
        }
        return jsonObject;
    }

    /**
     * 早盘足球联赛数据
     * polling early football league data
     */
    public void pollingEarlyFootballLeagueData() {
        try {
            AgApi agApi = agApiMapper.selectByApi(AgApiEnum.get_match_list.getApi());
            agApi.setSportType(AgSportTypeEnum.football.getType());
            agApi.setMarketType(AgMarketTypeEnum.early.getType());
            agApi.setPageIndex(1);
            JSONObject jsonObject = getFootballLeagueData(agApi);
            if (jsonObject == null) return;
            int totalPage = jsonObject.getIntValue("TotalPage");
            if (totalPage < 2) {
                return;
            }
            for (int i = 2; i <= totalPage; i++) {
                agApi.setPageIndex(i);
                getFootballLeagueData(agApi);
            }
        } catch (Exception e) {
            log.info("ag - pollingEarlyFootballLeagueData exception ----->>>>", e);
        }
    }

    /**
     * 球赛开始 更新状态 - 滚球
     */
    public void rollStatus() {
        long currentTime = System.currentTimeMillis() / 1000;
        agLeagueDataMapper.rollStatus(currentTime);
    }

    /**
     * 球赛结束
     */
    public void finishStatus() {
        long currentTime = System.currentTimeMillis() / 1000;
        agLeagueDataMapper.finishStatus(currentTime);
    }

}

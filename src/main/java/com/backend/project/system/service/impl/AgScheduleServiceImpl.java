package com.backend.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.AgApiUtils;
import com.backend.project.system.domain.AgApi;
import com.backend.project.system.domain.AgGameInfo;
import com.backend.project.system.domain.AgLeagueData;
import com.backend.project.system.enums.AgApiEnum;
import com.backend.project.system.enums.AgMarketTypeEnum;
import com.backend.project.system.enums.AgSportTypeEnum;
import com.backend.project.system.mapper.AgApiMapper;
import com.backend.project.system.mapper.AgGameInfoMapper;
import com.backend.project.system.mapper.AgLeagueDataMapper;
import com.backend.project.system.service.IAgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class AgScheduleServiceImpl implements IAgScheduleService {

    @Resource
    private AgApiMapper agApiMapper;
    @Resource
    private AgLeagueDataMapper agLeagueDataMapper;
    @Resource
    private AgGameInfoMapper agGameInfoMapper;

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
        JSONObject jsonObject = JSON.parseObject(match_list);
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
     * 比赛信息
     */
    @Override
    public void getGameInfo() {
        AgApi agApi = agApiMapper.selectByApi(AgApiEnum.get_match_info.getApi());
        List<AgLeagueData> leagueDataList = agLeagueDataMapper.selectNormalList();
        for (AgLeagueData leagueData : leagueDataList) {
            String matchId = leagueData.getMatchId();
            agApi.setMatchId(matchId);
            String match_info = AgApiUtils.get_match_info(agApi);
            log.info(match_info);
            JSONObject jsonObject = JSON.parseObject(match_info);
            int ret = jsonObject.getIntValue("Ret");
            if (ret != 0) {
                continue;
            }
            AgGameInfo agGameInfo = new AgGameInfo();;
            agGameInfo.setMatchId(matchId);
            JSONObject match = jsonObject.getJSONObject("Match");
            JSONArray markets = match.getJSONArray("Markets");
            Iterator<Object> iterator = markets.stream().iterator();
            while (iterator.hasNext()) {
                JSONObject market = JSON.parseObject(iterator.next().toString());
                int betType = market.getIntValue("BetType");
                // 让球胜平负
                if (betType == 28) {
                    JSONArray selections = market.getJSONArray("Selections");
                    Iterator<Object> it = selections.stream().iterator();
                    while (it.hasNext()) {
                        JSONObject selection = JSON.parseObject(it.next().toString());
                        String key = selection.getString("Key"); // 赔率归属：让球胜负
                        String point = selection.getString("Point"); // 让球数：1(代表加1) / -1
                        String price = selection.getString("Price"); // 赔率
                        if ("1".equals(key)) { // 主队赔率
                            if ("1".equals(point)) { // 主+1
                                agGameInfo.setHomeAdd(price);
                            } else if ("-1".equals(point)) { // 主-1
                                agGameInfo.setHomeCut(price);
                            }
                        } else if ("2".equals(key)) {
                            if ("1".equals(point)) { // 客+1
                                agGameInfo.setAwayAdd(price);
                            } else if ("-1".equals(point)) { // 客-1
                                agGameInfo.setAwayCut(price);
                            }
                        }
                    }
                }

                // 胜平负
                if (betType == 5) {
                    JSONArray selections = market.getJSONArray("Selections");
                    Iterator<Object> it = selections.stream().iterator();
                    while (it.hasNext()) {
                        JSONObject selection = JSON.parseObject(it.next().toString());
                        String key = selection.getString("Key"); // 赔率归属：胜平负
                        String price = selection.getString("Price"); // 赔率
                        if ("1".equals(key)) { // 主胜赔率
                            agGameInfo.setMyselfH(price);
                        } else if ("2".equals(key)) { // 客胜赔率
                            agGameInfo.setMyselfA(price);
                        } else { // 和局赔率
                            agGameInfo.setMyselfN(price);
                        }
                    }
                }

                // 精准进球数
                if (betType == 406) {
                    JSONArray selections = market.getJSONArray("Selections");
                    Iterator<Object> it = selections.stream().iterator();
                    while (it.hasNext()) {
                        JSONObject selection = JSON.parseObject(it.next().toString());
                        String key = selection.getString("Key");
                        String price = selection.getString("Price"); // 赔率
                        if ("g0".equals(key)) { // 总进球0
                            agGameInfo.setS0(price);
                        } else if ("g1".equals(key)) { // 总进球1
                            agGameInfo.setS1(price);
                        } else if ("g2".equals(key)) { // 总进球2
                            agGameInfo.setS2(price);
                        } else if ("g3".equals(key)) { // 总进球3
                            agGameInfo.setS3(price);
                        } else if ("g4".equals(key)) { // 总进球4
                            agGameInfo.setS4(price);
                        } else if ("g5".equals(key)) { // 总进球5
                            agGameInfo.setS5(price);
                        } else if ("g6".equals(key)) { // 总进球6
                            agGameInfo.setS6(price);
                        }
                    }
                }
            }
            Integer exist = agGameInfoMapper.selectExisit(matchId);
            if (exist == null) {
                agGameInfoMapper.insertData(agGameInfo);
            } else {
                agGameInfoMapper.updateData(agGameInfo);
            }
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

package com.backend.project.system.schedule;

import com.backend.common.utils.DateUtils;
import com.backend.common.utils.HgApiUtils;
import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.domain.FbLeagueData;
import com.backend.project.system.domain.HgApi;
import com.backend.project.system.enums.HgApiEnum;
import com.backend.project.system.mapper.FbLeagueDataMapper;
import com.backend.project.system.mapper.HgApiMapper;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 */
@Configuration
@EnableScheduling
@Slf4j
public class HgSchedule {

    @Resource
    private ThreadPoolConfig threadPoolConfig;
    @Resource
    private HgApiMapper hgApiMapper;

    @Resource
    private FbLeagueDataMapper fbLeagueDataMapper;

    /**
     * task - polling today football data
     * 每天中午12点拿今日足球比赛数据
     */
    @Scheduled(cron="0 0/1 * * * ?")
    private void pollingFootballDataToday() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            try {
                HgApi hgApi = hgApiMapper.selectByP(HgApiEnum.get_league_list_All.getApi());
                String league_list_all = HgApiUtils.get_league_list_All(hgApi);
                Document doc = DocumentHelper.parseText(league_list_all);
                Element rootElt = doc.getRootElement();
                Element classifier = rootElt.element("classifier");
                Iterator regionIt = classifier.elementIterator("region");
                while (regionIt.hasNext()) {
                    Element region = (Element)regionIt.next();
                    String regionName = region.attributeValue("name"); // 地区名称
                    String regionSortName = region.attributeValue("sort_name"); // 地区排序标记
                    Iterator leagueIt = region.elementIterator("league");
                    while (leagueIt.hasNext()) {
                        Element league = (Element)leagueIt.next();
                        String leagueName = league.attributeValue("name"); // 联赛名称
                        String leagueSortName = league.attributeValue("sort_name"); // 联赛排序标记
                        String leagueId = league.attributeValue("id"); // 联赛id
                        // 设置参数，获取联赛下属比赛列表
                        hgApi.setLid(leagueId);
                        String game_list = HgApiUtils.get_game_list(hgApi);
                        Document docGameList = DocumentHelper.parseText(game_list);
                        Element rootEltGameList = docGameList.getRootElement();
                        Iterator ecIt = rootEltGameList.elementIterator("ec");
                        while (ecIt.hasNext()) {
                            Element ec = (Element)ecIt.next();
                            Element game = ec.element("game");
                            String dateTime = game.elementTextTrim("DATETIME");
                            String team_h = game.elementTextTrim("TEAM_H");
                            String team_h_id = game.elementTextTrim("TEAM_H_ID");
                            String team_c = game.elementTextTrim("TEAM_C");
                            String team_c_id = game.elementTextTrim("TEAM_C_ID");
                            String ecid = game.elementTextTrim("ECID");
                            // 处理比赛时间，+12H
                            String ecTime = DateUtils.transformDateHg(dateTime);
                            Long ecTimestamp = DateUtils.getLeagueDate(dateTime);

                            FbLeagueData fbLeagueData = new FbLeagueData();
                            fbLeagueData.setRegionName(regionName);
                            fbLeagueData.setRegionSortName(regionSortName);
                            fbLeagueData.setLeagueName(leagueName);
                            fbLeagueData.setLeagueSortName(leagueSortName);
                            fbLeagueData.setLeagueId(leagueId);
                            fbLeagueData.setEcid(ecid);
                            fbLeagueData.setEcTime(ecTime);
                            fbLeagueData.setEcTimestamp(ecTimestamp);
                            fbLeagueData.setTeamH(team_h);
                            fbLeagueData.setTeamHId(team_h_id);
                            fbLeagueData.setTeamC(team_c);
                            fbLeagueData.setTeamCId(team_c_id);
                            fbLeagueDataMapper.insertData(fbLeagueData);
                        }
                    }
                }
            } catch (Exception e) {
                log.info("pollingFootballDataToday exception ----->>>>", e);
            }
        });
    }



}

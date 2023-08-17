package com.backend.project.system.service.impl;

import com.backend.common.constant.UserConstants;
import com.backend.common.utils.CalcUtil;
import com.backend.common.utils.DateUtils;
import com.backend.common.utils.HgApiUtils;
import com.backend.common.utils.http.HttpUtils;
import com.backend.project.system.domain.*;
import com.backend.project.system.domain.vo.BetParamVo;
import com.backend.project.system.enums.HgApiEnum;
import com.backend.project.system.enums.HgChooseTeamEnum;
import com.backend.project.system.enums.HgWTypeEnum;
import com.backend.project.system.mapper.*;
import com.backend.project.system.service.IHgAgBallService;
import com.backend.project.system.service.IHgSPBallService;
import com.backend.project.system.service.IHgSPDealerBallService;
import com.backend.project.system.service.IHgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class HgScheduleServiceImpl implements IHgScheduleService {

    @Resource
    private HgApiMapper hgApiMapper;
    @Resource
    private HgFbLeagueDataMapper hgFbLeagueDataMapper;
    @Resource
    private DictLeagueMapper dictLeagueMapper;
    @Resource
    private DictTeamMapper dictTeamMapper;
    @Resource
    private SPMatchInfoMapper spMatchInfoMapper;
    @Resource
    private HgFbGameMoreMapper hgFbGameMoreMapper;
    @Resource
    private AgLeagueDataMapper agLeagueDataMapper;
    @Resource
    private AgGameInfoMapper agGameInfoMapper;
    @Resource
    private IHgSPBallService hgSPBallService;
    @Resource
    private IHgAgBallService hgAgBallService;
    @Resource
    private IHgSPDealerBallService hgSPDealerBallService;
    @Resource
    private DictLeagueHgAgMapper dictLeagueHgAgMapper;
    @Resource
    private DictTeamHgAgMapper dictTeamHgAgMapper;

    private static String[] da_qiu = new String[]{"1.5", "2.5", "3.5", "1.5 / 2", "2 / 2.5", "2.5 / 3", "3 / 3.5"};
    private static String[] xiao_qiu = new String[]{"2.5", "3.5", "2 / 2.5", "2.5 / 3", "3 / 3.5", "3.5 / 4"};

    @Value("${tgApi.url}")
    private String tgUrl;

    /**
     * polling today football data
     */
    @Override
    public void pollingFootballDataToday() {
        try {
            String currenDate = DateUtils.getDate();
            /** 获取体彩今日足球联赛列表 */
            List<String> todayLeague = spMatchInfoMapper.getTodayLeague(currenDate);
            List<String> todayHomeTeam = spMatchInfoMapper.getTodayHomeTeam(currenDate);
            List<String> todayAwayTeam = spMatchInfoMapper.getTodayAwayTeam(currenDate);
            HgApi hgApi1 = hgApiMapper.selectByP(HgApiEnum.get_league_list_All.getApi());
            HgApi hgApi2 = hgApiMapper.selectByP(HgApiEnum.get_game_list.getApi());
            HgApi hgApi3 = hgApiMapper.selectByP(HgApiEnum.get_game_more.getApi());
            HgApi hgApi4 = hgApiMapper.selectByP(HgApiEnum.ft_order_view.getApi());

            String league_list_all = HgApiUtils.today_get_league_list_All(hgApi1);
            Document doc = DocumentHelper.parseText(league_list_all);
            Element rootElt = doc.getRootElement();
            Element classifier = rootElt.element("classifier");
            systemExit(classifier);
            Iterator regionIt = classifier.elementIterator("region");
            while (regionIt.hasNext()) {
                Element region = (Element)regionIt.next();
                String regionName = region.attributeValue("name"); // 地区名称
                String regionSortName = region.attributeValue("sort_name"); // 地区排序标记
                Iterator leagueIt = region.elementIterator("league");
                while (leagueIt.hasNext()) {
                    Element league = (Element)leagueIt.next();
                    String leagueName = league.attributeValue("name"); // 联赛名称
                    String spLeagueName = dictLeagueMapper.selectByHg(leagueName);
                    if (StringUtils.isBlank(spLeagueName) || !todayLeague.contains(spLeagueName)) {
                        continue;
                    }
                    String leagueSortName = league.attributeValue("sort_name"); // 联赛排序标记
                    String leagueId = league.attributeValue("id"); // 联赛id

                    /** 获取联赛下属比赛列表 */
                    hgApi2.setLid(leagueId);
                    String game_list = HgApiUtils.today_get_game_list(hgApi2);
                    Document docGameList = DocumentHelper.parseText(game_list);
                    Element rootEltGameList = docGameList.getRootElement();
                    Iterator ecIt = rootEltGameList.elementIterator("ec");
                    systemExit(ecIt);
                    while (ecIt.hasNext()) {
                        Element ec = (Element)ecIt.next();
                        Element game = ec.element("game");
                        String team_h = game.elementTextTrim("TEAM_H");
                        String team_c = game.elementTextTrim("TEAM_C");
                        String spTeamH = dictTeamMapper.selectByHg(team_h);
                        String spTeamC = dictTeamMapper.selectByHg(team_c);
                        if ((StringUtils.isBlank(spTeamH) || StringUtils.isBlank(spTeamC)) || (!todayHomeTeam.contains(spTeamH)
                                && !todayHomeTeam.contains(spTeamH) && !todayAwayTeam.contains(spTeamC) && !todayAwayTeam.contains(spTeamC))) {
                            continue;
                        }
                        // 获取体彩让球数
                        SPMatchInfo spMatchInfo = spMatchInfoMapper.selectCondition(spLeagueName, spTeamH, spTeamC);
                        String handicap = spMatchInfo.getHandicap();
                        handicap = handicap.substring(1);

                        String dateTime = game.elementTextTrim("DATETIME");
                        String team_h_id = game.elementTextTrim("TEAM_H_ID");
                        String team_c_id = game.elementTextTrim("TEAM_C_ID");
                        String ecid = game.elementTextTrim("ECID");
                        // 处理比赛时间，+12H
                        String ecTime = DateUtils.transformDateHg(dateTime);
                        Long ecTimestamp = DateUtils.getLeagueDate(dateTime);

                        // 写入联赛球队数据
                        HgFbLeagueData hgFbLeagueData = new HgFbLeagueData(spMatchInfo.getId(), regionName, regionSortName, leagueName
                                , leagueSortName, leagueId, ecid, ecTime, ecTimestamp, team_h, team_h_id, team_c, team_c_id);
                        Integer exist = hgFbLeagueDataMapper.selectExist(leagueId, ecid);
                        if (null == exist) {
                            hgFbLeagueDataMapper.insertData(hgFbLeagueData);
                        }

                        /** 赔率明细 */
                        // 存储赔率数据
                        HgFbGameMore hgFbGameMore = new HgFbGameMore();
                        hgFbGameMore.setLid(leagueId);
                        hgFbGameMore.setEcid(ecid);
                        hgApi3.setLid(leagueId);
                        hgApi3.setEcid(ecid);
                        String game_more = HgApiUtils.today_get_game_more(hgApi3);
                        Document docGameMore = DocumentHelper.parseText(game_more);
                        Element rootEltGameMore = docGameMore.getRootElement();
                        Iterator gameIt = rootEltGameMore.elementIterator("game");
                        systemExit(gameIt);
                        while (gameIt.hasNext()) {
                            Element gameGameMore = (Element) gameIt.next();
                            String gidGameMore = gameGameMore.elementTextTrim("gid"); // 获取准确赔率参数
                            hgApi4.setGid(gidGameMore); // 接口参数
                            hgApi4.setLid(leagueId);
                            hgApi4.setEcid(ecid);
                            String strong = gameGameMore.elementTextTrim("strong"); // 强队标识，H：主队强（主减客加），C：客队强（主加客减）
                            String mode = gameGameMore.attributeValue("mode");
                            /** 让球 */
                            String sw_r = gameGameMore.elementTextTrim("sw_R"); // 让球赔率开关 （sw_ 赔率开关，Y:开，N:关）
                            if (UserConstants.YES.equals(sw_r) && "FT".equals(mode)) {
                                sw_rang(hgApi4, handicap, hgFbGameMore, gameGameMore, strong);
                            }
                            /** 大小球 */
                            String sw_ou = gameGameMore.elementTextTrim("sw_OU");
                            if (UserConstants.YES.equals(sw_ou)) {
                                sw_ou(hgApi4, hgFbGameMore);
                            }

                            /** master参数组 */
                            String master = gameGameMore.attributeValue("master");
                            if (UserConstants.YES.equals(master) && "FT".equals(mode)) {
                                /** 独赢 */
                                sw_monopoly(hgApi4, hgFbGameMore);

                                /** 总进球数 */
                                sw_total(hgApi4, hgFbGameMore);
                            }
                        }
                        Integer exist1 = hgFbGameMoreMapper.selectExist(leagueId, ecid);
                        if (null == exist1) {
                            // 写入赔率数据
                            hgFbGameMoreMapper.insertData(hgFbGameMore);
                        } else {
                            hgFbGameMoreMapper.updateData(hgFbGameMore);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("pollingFootballDataToday exception ----->>>>", e);
        }
    }

    /**
     * 早盘数据
     * polling early football data
     */
    @Override
    public void pollingFootballDataEarly() {
        try {
            String currentDate;
            int currentHour = DateUtils.getCurrentHour();
            if (currentHour < 12) {
                currentDate = DateUtils.getDateSubDays(-1);
            } else {
                currentDate = DateUtils.getDate();
            }
            List<String> earlyLeague = spMatchInfoMapper.getEarlyLeague(currentDate);
            List<String> earlyHomeTeam = spMatchInfoMapper.getEarlyHomeTeam(currentDate);
            List<String> earlyAwayTeam = spMatchInfoMapper.getEarlyAwayTeam(currentDate);
            HgApi hgApi1 = hgApiMapper.selectByP(HgApiEnum.get_league_list_All.getApi());
            HgApi hgApi2 = hgApiMapper.selectByP(HgApiEnum.get_game_list.getApi());
            HgApi hgApi3 = hgApiMapper.selectByP(HgApiEnum.get_game_more.getApi());
            HgApi hgApi4 = hgApiMapper.selectByP(HgApiEnum.ft_order_view.getApi());

            /** 获取体彩早盘足球联赛列表 */
            String league_list_all = HgApiUtils.early_get_league_list_All(hgApi1);
            Document doc = DocumentHelper.parseText(league_list_all);
            Element rootElt = doc.getRootElement();
            Element classifier = rootElt.element("classifier");
            systemExit(classifier);
            Iterator regionIt = classifier.elementIterator("region");
            while (regionIt.hasNext()) {
                Element region = (Element)regionIt.next();
                String regionName = region.attributeValue("name"); // 地区名称
                String regionSortName = region.attributeValue("sort_name"); // 地区排序标记
                Iterator leagueIt = region.elementIterator("league");
                while (leagueIt.hasNext()) {
                    Element league = (Element)leagueIt.next();
                    String leagueName = league.attributeValue("name"); // 联赛名称
                    String spLeagueName = dictLeagueMapper.selectByHg(leagueName);
                    if (StringUtils.isBlank(spLeagueName) || !earlyLeague.contains(spLeagueName)) {
                        continue;
                    }
                    String leagueSortName = league.attributeValue("sort_name"); // 联赛排序标记
                    String leagueId = league.attributeValue("id"); // 联赛id

                    /** 获取联赛下属比赛列表 */
                    hgApi2.setLid(leagueId);
                    String game_list = HgApiUtils.early_get_game_list(hgApi2);
                    Document docGameList = DocumentHelper.parseText(game_list);
                    Element rootEltGameList = docGameList.getRootElement();
                    Iterator ecIt = rootEltGameList.elementIterator("ec");
                    systemExit(ecIt);
                    while (ecIt.hasNext()) {
                        Element ec = (Element)ecIt.next();
                        Element game = ec.element("game");
                        String team_h = game.elementTextTrim("TEAM_H");
                        String team_c = game.elementTextTrim("TEAM_C");
                        String spTeamH = dictTeamMapper.selectByHg(team_h);
                        String spTeamC = dictTeamMapper.selectByHg(team_c);
                        if ((StringUtils.isBlank(spTeamH) || StringUtils.isBlank(spTeamC)) || (!earlyHomeTeam.contains(spTeamH)
                                && !earlyHomeTeam.contains(spTeamH) && !earlyAwayTeam.contains(spTeamC) && !earlyAwayTeam.contains(spTeamC))) {
                            continue;
                        }
                        // 获取体彩让球数
                        SPMatchInfo spMatchInfo = spMatchInfoMapper.selectCondition(spLeagueName, spTeamH, spTeamC);
                        String handicap = spMatchInfo.getHandicap();
                        handicap = handicap.substring(1);

                        String dateTime = game.elementTextTrim("DATETIME");
                        String team_h_id = game.elementTextTrim("TEAM_H_ID");
                        String team_c_id = game.elementTextTrim("TEAM_C_ID");
                        String ecid = game.elementTextTrim("ECID");
                        // 处理比赛时间，+12H
                        String ecTime = DateUtils.transformDateHg(dateTime);
                        Long ecTimestamp = DateUtils.getLeagueDate(dateTime);

                        // 写入联赛球队数据
                        HgFbLeagueData hgFbLeagueData = new HgFbLeagueData(spMatchInfo.getId(), regionName, regionSortName, leagueName
                                , leagueSortName, leagueId, ecid, ecTime, ecTimestamp, team_h, team_h_id, team_c, team_c_id);
                        Integer exist = hgFbLeagueDataMapper.selectExist(leagueId, ecid);
                        if (null == exist) {
                            hgFbLeagueDataMapper.insertData(hgFbLeagueData);
                        }

                        /** 赔率明细 */
                        // 存储赔率数据
                        HgFbGameMore hgFbGameMore = new HgFbGameMore();
                        hgFbGameMore.setLid(leagueId);
                        hgFbGameMore.setEcid(ecid);
                        hgApi3.setLid(leagueId);
                        hgApi3.setEcid(ecid);
                        String game_more = HgApiUtils.early_get_game_more(hgApi3);
                        Document docGameMore = DocumentHelper.parseText(game_more);
                        Element rootEltGameMore = docGameMore.getRootElement();
                        Iterator gameIt = rootEltGameMore.elementIterator("game");
                        systemExit(gameIt);
                        while (gameIt.hasNext()) {
                            Element gameGameMore = (Element) gameIt.next();
                            String gidGameMore = gameGameMore.elementTextTrim("gid"); // 获取准确赔率参数
                            hgApi4.setGid(gidGameMore); // 接口参数
                            hgApi4.setLid(leagueId);
                            hgApi4.setEcid(ecid);
                            String strong = gameGameMore.elementTextTrim("strong"); // 强队标识，H：主队强（主减客加），C：客队强（主加客减）
                            String mode = gameGameMore.attributeValue("mode");
                            /** 让球 */
                            String sw_r = gameGameMore.elementTextTrim("sw_R"); // 让球赔率开关 （sw_ 赔率开关，Y:开，N:关）
                            if (UserConstants.YES.equals(sw_r) && "FT".equals(mode)) {
                                sw_rang(hgApi4, handicap, hgFbGameMore, gameGameMore, strong);
                            }
                            /** 大小球 */
                            String sw_ou = gameGameMore.elementTextTrim("sw_OU");
                            if (UserConstants.YES.equals(sw_ou)) {
                                sw_ou(hgApi4, hgFbGameMore);
                            }

                            /** master参数组 */
                            String master = gameGameMore.attributeValue("master");
                            if (UserConstants.YES.equals(master) && "FT".equals(mode)) {
                                /** 独赢 */
                                sw_monopoly(hgApi4, hgFbGameMore);

                                /** 总进球数 */
                                sw_total(hgApi4, hgFbGameMore);
                            }
                        }
                        Integer exist1 = hgFbGameMoreMapper.selectExist(leagueId, ecid);
                        if (null == exist1) {
                            // 写入赔率数据
                            hgFbGameMoreMapper.insertData(hgFbGameMore);
                        } else {
                            hgFbGameMoreMapper.updateData(hgFbGameMore);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("pollingFootballDataEarly exception ----->>>>", e);
        }
    }

    /**
     * hg - sp 数据对冲计算 - 单关
     */
    @Override
    public void hedge_Hg_SP_data_single() {
        try {
            List<SPMatchInfo> spInfos = spMatchInfoMapper.findSPObsoleteNot();
            for (SPMatchInfo spInfo : spInfos) {
                BetParamVo betParamVo = new BetParamVo();
                Long spId = spInfo.getId();
                HgFbLeagueData hgFbLeagueData = hgFbLeagueDataMapper.selectBySpId(spId);
                if (hgFbLeagueData == null) {
                    continue;
                }
                HgFbGameMore fbGameMore = hgFbGameMoreMapper.selectCondition(hgFbLeagueData.getLeagueId(), hgFbLeagueData.getEcid());
                if (fbGameMore == null) {
                    continue;
                }
                betParamVo.setBetAmountHg(fbGameMore.getBetAmount());
                if (spInfo.getWin() != null) {
                    betParamVo.setOddsWin(Double.valueOf(spInfo.getWin())); // 体彩主胜
                    betParamVo.setOddsTie(Double.valueOf(spInfo.getDraw())); // 体彩平
                    betParamVo.setOddsLose(Double.valueOf(spInfo.getLose())); // 体彩客胜
                }
                if (fbGameMore.getMyselfH() != null) {
                    betParamVo.setHome(Double.valueOf(fbGameMore.getMyselfH())); // 皇冠主胜
                    betParamVo.setTie(Double.valueOf(fbGameMore.getMyselfN())); // 皇冠平
                    betParamVo.setVisit(Double.valueOf(fbGameMore.getMyselfC())); // 皇冠客胜
                }
                // 体彩让球数据
                String handicap = spInfo.getHandicap();
                if (StringUtils.isNotBlank(handicap)) {
                    if (handicap.indexOf("+") > -1) { // 主加，主队受球
                        betParamVo.setOddsShouWin(Double.valueOf(spInfo.getHandicapWin())); // 体彩主队受球胜
                        betParamVo.setOddsShouLose(Double.valueOf(spInfo.getHandicapLose())); // 体彩主队受球客胜
                    } else if (handicap.indexOf("-") > -1) { //主减，主队让球
                        betParamVo.setOddsRangWin(Double.valueOf(spInfo.getHandicapWin())); // 体彩主队让球胜
                        betParamVo.setOddsRangLose(Double.valueOf(spInfo.getHandicapLose())); // 体彩主队让球客胜
                    }
                }
                // 皇冠让球数据
                if (StringUtils.isNotBlank(fbGameMore.getHAdd05())) {
                    betParamVo.setHomeAdd05(Double.valueOf(fbGameMore.getHAdd05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getHCut05())) {
                    betParamVo.setHomeCut05(Double.valueOf(fbGameMore.getHCut05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getCAdd05())) {
                    betParamVo.setVisitAdd05(Double.valueOf(fbGameMore.getCAdd05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getCCut05())) {
                    betParamVo.setVisitCut05(Double.valueOf(fbGameMore.getCCut05()));
                }
                String msg = "chat_id=-905019287&text=⚽⚽球赛监测⚽单关⚽, 比赛编号:" + spInfo.getMatchNum();
                betParamVo.setMsg(msg);
                betParamVo.setSpId(spInfo.getId());
                betParamVo.setNotifyFlag(1);
                hgSPBallService.betCheckSingle(betParamVo);
            }
        } catch (Exception e) {
            log.info("hedge_Hg_SP_data_single exception ----->>>>", e);
        }
    }

    /**
     * hg - sp 数据对冲计算 - 012
     */
    @Override
    public void hedge_Hg_SP_data_012() {
        try {
            List<SPMatchInfo> spInfos = spMatchInfoMapper.findSPObsoleteNot();
            for (SPMatchInfo spInfo : spInfos) {
                BetParamVo betParamVo = new BetParamVo();
                Long spId = spInfo.getId();
                HgFbLeagueData hgFbLeagueData = hgFbLeagueDataMapper.selectBySpId(spId);
                if (hgFbLeagueData == null) {
                    continue;
                }
                HgFbGameMore fbGameMore = hgFbGameMoreMapper.selectCondition(hgFbLeagueData.getLeagueId(), hgFbLeagueData.getEcid());
                if (fbGameMore == null) {
                    continue;
                }
                betParamVo.setBetAmountHg(fbGameMore.getBetAmount()); // 皇冠投注金额，基础投注额

                betParamVo.setOddsZero(Double.valueOf(spInfo.getS0())); // 体彩总进球0
                betParamVo.setOddsOne(Double.valueOf(spInfo.getS1())); // 体彩总进球1
                betParamVo.setOddsTwo(Double.valueOf(spInfo.getS2())); // 体彩总进球2
                betParamVo.setOddsThree(Double.valueOf(spInfo.getS3())); // 体彩总进球3
                betParamVo.setOddsFour(Double.valueOf(spInfo.getS4())); // 体彩总进球4
                betParamVo.setOddsFive(Double.valueOf(spInfo.getS5())); // 体彩总进球5
                betParamVo.setOddsSix(Double.valueOf(spInfo.getS6())); // 体彩总进球6
                betParamVo.setOddsSeven(Double.valueOf(spInfo.getS7())); // 体彩总进球7+

                betParamVo.setZong0_1(Double.valueOf(fbGameMore.getTotal01())); // 皇冠总进球0-1
                betParamVo.setZong2_3(Double.valueOf(fbGameMore.getTotal23())); // 皇冠总进球2-3
                betParamVo.setZong4_6(Double.valueOf(fbGameMore.getTotal46())); // 皇冠总进球4-6
                betParamVo.setZong7(Double.valueOf(fbGameMore.getTotal7())); // 皇冠总进球7+

                // 皇冠大球赔率
                betParamVo.set大15(fbGameMore.getBig15()==null ? 0 : Double.valueOf(fbGameMore.getBig15())); // 大1.5
                betParamVo.set大25(fbGameMore.getBig25()==null ? 0 : Double.valueOf(fbGameMore.getBig25())); // 大2.5
                betParamVo.set大35(fbGameMore.getBig35()==null ? 0 : Double.valueOf(fbGameMore.getBig35())); // 大3.5
                betParamVo.set大15_2(fbGameMore.getBig15_2()==null ? 0 : Double.valueOf(fbGameMore.getBig15_2())); // 大1.5/2
                betParamVo.set大2_25(fbGameMore.getBig2_25()==null ? 0 : Double.valueOf(fbGameMore.getBig2_25())); // 大2/2.5
                betParamVo.set大25_3(fbGameMore.getBig25_3()==null ? 0 : Double.valueOf(fbGameMore.getBig25_3())); // 大2.5/3
                betParamVo.set大3_35(fbGameMore.getBig3_35()==null ? 0 : Double.valueOf(fbGameMore.getBig3_35())); // 大3/3.5

                // 皇冠小球赔率
                betParamVo.set小25(fbGameMore.getSmall25()==null ? 0 : Double.valueOf(fbGameMore.getSmall25())); // 小2.5
                betParamVo.set小35(fbGameMore.getSmall35()==null ? 0 : Double.valueOf(fbGameMore.getSmall35())); // 小3.5
                betParamVo.set小2_25(fbGameMore.getSmall2_25()==null ? 0 : Double.valueOf(fbGameMore.getSmall2_25())); // 小2/2.5
                betParamVo.set小25_3(fbGameMore.getSmall25_3()==null ? 0 : Double.valueOf(fbGameMore.getSmall25_3())); // 小2.5/3
                betParamVo.set小3_35(fbGameMore.getSmall3_35()==null ? 0 : Double.valueOf(fbGameMore.getSmall3_35())); // 小3/3.5
                betParamVo.set小35_4(fbGameMore.getSmall35_4()==null ? 0 : Double.valueOf(fbGameMore.getSmall35_4())); // 小3.5/4

                String msg = "chat_id=-905019287&text=⚽⚽球赛监测⚽012⚽, 比赛编号:" + spInfo.getMatchNum();
                betParamVo.setMsg(msg);
                betParamVo.setSpId(spInfo.getId());
                betParamVo.setNotifyFlag(1);
                hgSPBallService.betCheck012(betParamVo);
            }
        } catch (Exception e) {
            log.info("hedge_Hg_SP_data_012 exception ----->>>>", e);
        }
    }

    /**
     * hg - sp 数据对冲计算 - 单关
     */
    @Override
    public void hedge_Hg_SP_data_single_dealer() {
        try {
            List<SPMatchInfo> spInfos = spMatchInfoMapper.findSPObsoleteNot();
            for (SPMatchInfo spInfo : spInfos) {
                BetParamVo betParamVo = new BetParamVo();
                Long spId = spInfo.getId();
                HgFbLeagueData hgFbLeagueData = hgFbLeagueDataMapper.selectBySpId(spId);
                if (hgFbLeagueData == null) {
                    continue;
                }
                HgFbGameMore fbGameMore = hgFbGameMoreMapper.selectCondition(hgFbLeagueData.getLeagueId(), hgFbLeagueData.getEcid());
                if (fbGameMore == null) {
                    continue;
                }
                betParamVo.setBetAmountHg(fbGameMore.getBetAmount());
                if (spInfo.getWin() != null) {
                    betParamVo.setOddsWin(Double.valueOf(spInfo.getWin())); // 体彩主胜
                    betParamVo.setOddsTie(Double.valueOf(spInfo.getDraw())); // 体彩平
                    betParamVo.setOddsLose(Double.valueOf(spInfo.getLose())); // 体彩客胜
                }
                if (fbGameMore.getMyselfH() != null) {
                    betParamVo.setHome(Double.valueOf(fbGameMore.getMyselfH())); // 皇冠主胜
                    betParamVo.setTie(Double.valueOf(fbGameMore.getMyselfN())); // 皇冠平
                    betParamVo.setVisit(Double.valueOf(fbGameMore.getMyselfC())); // 皇冠客胜
                }
                // 体彩让球数据
                String handicap = spInfo.getHandicap();
                if (StringUtils.isNotBlank(handicap)) {
                    if (handicap.indexOf("+") > -1) { // 主加，主队受球
                        betParamVo.setOddsShouWin(Double.valueOf(spInfo.getHandicapWin())); // 体彩主队受球胜
                        betParamVo.setOddsShouLose(Double.valueOf(spInfo.getHandicapLose())); // 体彩主队受球客胜
                    } else if (handicap.indexOf("-") > -1) { //主减，主队让球
                        betParamVo.setOddsRangWin(Double.valueOf(spInfo.getHandicapWin())); // 体彩主队让球胜
                        betParamVo.setOddsRangLose(Double.valueOf(spInfo.getHandicapLose())); // 体彩主队让球客胜
                    }
                }
                // 皇冠让球数据
                if (StringUtils.isNotBlank(fbGameMore.getHAdd05())) {
                    betParamVo.setHomeAdd05(Double.valueOf(fbGameMore.getHAdd05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getHCut05())) {
                    betParamVo.setHomeCut05(Double.valueOf(fbGameMore.getHCut05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getCAdd05())) {
                    betParamVo.setVisitAdd05(Double.valueOf(fbGameMore.getCAdd05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getCCut05())) {
                    betParamVo.setVisitCut05(Double.valueOf(fbGameMore.getCCut05()));
                }
                String msg = "chat_id=-749764025&text=⚽⚽球赛监测⚽单关⚽, 比赛编号:" + spInfo.getMatchNum();
                betParamVo.setMsg(msg);
                betParamVo.setSpId(spInfo.getId());
                betParamVo.setNotifyFlag(1);
                hgSPDealerBallService.betCheckSingle(betParamVo);
            }
        } catch (Exception e) {
            log.info("hedge_Hg_SP_data_single_dealer exception ----->>>>", e);
        }
    }

    /**
     * hg - sp 数据对冲计算 - 012
     */
    @Override
    public void hedge_Hg_SP_data_012_dealer() {
        try {
            List<SPMatchInfo> spInfos = spMatchInfoMapper.findSPObsoleteNot();
            for (SPMatchInfo spInfo : spInfos) {
                BetParamVo betParamVo = new BetParamVo();
                Long spId = spInfo.getId();
                HgFbLeagueData hgFbLeagueData = hgFbLeagueDataMapper.selectBySpId(spId);
                if (hgFbLeagueData == null) {
                    continue;
                }
                HgFbGameMore fbGameMore = hgFbGameMoreMapper.selectCondition(hgFbLeagueData.getLeagueId(), hgFbLeagueData.getEcid());
                if (fbGameMore == null) {
                    continue;
                }
                betParamVo.setBetAmountHg(fbGameMore.getBetAmount()); // 皇冠投注金额，基础投注额

                betParamVo.setOddsZero(Double.valueOf(spInfo.getS0())); // 体彩总进球0
                betParamVo.setOddsOne(Double.valueOf(spInfo.getS1())); // 体彩总进球1
                betParamVo.setOddsTwo(Double.valueOf(spInfo.getS2())); // 体彩总进球2
                betParamVo.setOddsThree(Double.valueOf(spInfo.getS3())); // 体彩总进球3
                betParamVo.setOddsFour(Double.valueOf(spInfo.getS4())); // 体彩总进球4
                betParamVo.setOddsFive(Double.valueOf(spInfo.getS5())); // 体彩总进球5
                betParamVo.setOddsSix(Double.valueOf(spInfo.getS6())); // 体彩总进球6
                betParamVo.setOddsSeven(Double.valueOf(spInfo.getS7())); // 体彩总进球7+

                betParamVo.setZong0_1(Double.valueOf(fbGameMore.getTotal01())); // 皇冠总进球0-1
                betParamVo.setZong2_3(Double.valueOf(fbGameMore.getTotal23())); // 皇冠总进球2-3
                betParamVo.setZong4_6(Double.valueOf(fbGameMore.getTotal46())); // 皇冠总进球4-6
                betParamVo.setZong7(Double.valueOf(fbGameMore.getTotal7())); // 皇冠总进球7+

                // 皇冠大球赔率
                betParamVo.set大15(fbGameMore.getBig15()==null ? 0 : Double.valueOf(fbGameMore.getBig15())); // 大1.5
                betParamVo.set大25(fbGameMore.getBig25()==null ? 0 : Double.valueOf(fbGameMore.getBig25())); // 大2.5
                betParamVo.set大35(fbGameMore.getBig35()==null ? 0 : Double.valueOf(fbGameMore.getBig35())); // 大3.5
                betParamVo.set大15_2(fbGameMore.getBig15_2()==null ? 0 : Double.valueOf(fbGameMore.getBig15_2())); // 大1.5/2
                betParamVo.set大2_25(fbGameMore.getBig2_25()==null ? 0 : Double.valueOf(fbGameMore.getBig2_25())); // 大2/2.5
                betParamVo.set大25_3(fbGameMore.getBig25_3()==null ? 0 : Double.valueOf(fbGameMore.getBig25_3())); // 大2.5/3
                betParamVo.set大3_35(fbGameMore.getBig3_35()==null ? 0 : Double.valueOf(fbGameMore.getBig3_35())); // 大3/3.5

                // 皇冠小球赔率
                betParamVo.set小25(fbGameMore.getSmall25()==null ? 0 : Double.valueOf(fbGameMore.getSmall25())); // 小2.5
                betParamVo.set小35(fbGameMore.getSmall35()==null ? 0 : Double.valueOf(fbGameMore.getSmall35())); // 小3.5
                betParamVo.set小2_25(fbGameMore.getSmall2_25()==null ? 0 : Double.valueOf(fbGameMore.getSmall2_25())); // 小2/2.5
                betParamVo.set小25_3(fbGameMore.getSmall25_3()==null ? 0 : Double.valueOf(fbGameMore.getSmall25_3())); // 小2.5/3
                betParamVo.set小3_35(fbGameMore.getSmall3_35()==null ? 0 : Double.valueOf(fbGameMore.getSmall3_35())); // 小3/3.5
                betParamVo.set小35_4(fbGameMore.getSmall35_4()==null ? 0 : Double.valueOf(fbGameMore.getSmall35_4())); // 小3.5/4

                String msg = "chat_id=-749764025&text=⚽⚽球赛监测⚽012⚽, 比赛编号:" + spInfo.getMatchNum();
                betParamVo.setMsg(msg);
                betParamVo.setSpId(spInfo.getId());
                betParamVo.setNotifyFlag(1);
                hgSPDealerBallService.betCheck012(betParamVo);
            }
        } catch (Exception e) {
            log.info("hedge_Hg_SP_data_012_dealer exception ----->>>>", e);
        }
    }

    /**
     * 总进球数
     * @param hgApi4
     * @param hgFbGameMore
     * @throws DocumentException
     */
    private void sw_total(HgApi hgApi4, HgFbGameMore hgFbGameMore) throws DocumentException {
        hgApi4.setWType(HgWTypeEnum.TOTAL.getType());
        // 0-1
        hgApi4.setChoseTeam(HgChooseTeamEnum.INSIDE0_1.getType());
        String order_view_0_1 = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_0_1 != null) {
            Document orderView0_1 = DocumentHelper.parseText(order_view_0_1);
            Element rootOrderView0_1 = orderView0_1.getRootElement();
            String ioratio0_1 = rootOrderView0_1.elementTextTrim("ioratio"); // 水位
            systemExit(ioratio0_1);
            hgFbGameMore.setTotal01(ioratio0_1);
        }

        // 2-3
        hgApi4.setChoseTeam(HgChooseTeamEnum.INSIDE2_3.getType());
        String order_view_2_3 = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_2_3 != null) {
            Document orderView2_3 = DocumentHelper.parseText(order_view_2_3);
            Element rootOrderView2_3 = orderView2_3.getRootElement();
            String ioratio2_3 = rootOrderView2_3.elementTextTrim("ioratio"); // 水位
            systemExit(ioratio2_3);
            hgFbGameMore.setTotal23(ioratio2_3);
        }

        // 4-6
        hgApi4.setChoseTeam(HgChooseTeamEnum.INSIDE4_6.getType());
        String order_view_4_6 = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_4_6 != null) {
            Document orderView4_6 = DocumentHelper.parseText(order_view_4_6);
            Element rootOrderView4_6 = orderView4_6.getRootElement();
            String ioratio4_6 = rootOrderView4_6.elementTextTrim("ioratio"); // 水位
            systemExit(ioratio4_6);
            hgFbGameMore.setTotal46(ioratio4_6);
        }

        // 7+
        hgApi4.setChoseTeam(HgChooseTeamEnum.OVER7.getType());
        String order_view_7 = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_4_6 != null) {
            Document orderView7 = DocumentHelper.parseText(order_view_7);
            Element rootOrderView7 = orderView7.getRootElement();
            String ioratio7 = rootOrderView7.elementTextTrim("ioratio"); // 水位
            systemExit(ioratio7);
            hgFbGameMore.setTotal7(ioratio7);
        }
    }

    /**
     * 独赢
     * @param hgApi4
     * @param hgFbGameMore
     * @throws DocumentException
     */
    private void sw_monopoly(HgApi hgApi4, HgFbGameMore hgFbGameMore) throws DocumentException {
        hgApi4.setWType(HgWTypeEnum.MYSELF.getType());
        // 独赢 - 主队
        hgApi4.setChoseTeam(HgChooseTeamEnum.HOME.getType());
        String order_view_h = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_h != null) {
            Document orderViewH = DocumentHelper.parseText(order_view_h);
            Element rootOrderViewH = orderViewH.getRootElement();
            String ioratioH = rootOrderViewH.elementTextTrim("ioratio"); // 水位
            systemExit(ioratioH);
            hgFbGameMore.setMyselfH(ioratioH);
        }

        // 独赢 - 客队
        hgApi4.setChoseTeam(HgChooseTeamEnum.CUSTOMER.getType());
        String order_view_c = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_h != null) {
            Document orderViewC = DocumentHelper.parseText(order_view_c);
            Element rootOrderViewC = orderViewC.getRootElement();
            String ioratioC = rootOrderViewC.elementTextTrim("ioratio"); // 水位
            systemExit(ioratioC);
            hgFbGameMore.setMyselfC(ioratioC);
        }

        // 独赢 - 和局
        hgApi4.setChoseTeam(HgChooseTeamEnum.NORMAL.getType());
        String order_view_n = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_h != null) {
            Document orderViewN = DocumentHelper.parseText(order_view_n);
            Element rootOrderViewN = orderViewN.getRootElement();
            String ioratioN = rootOrderViewN.elementTextTrim("ioratio"); // 水位
            systemExit(ioratioN);
            hgFbGameMore.setMyselfN(ioratioN);
        }
    }

    /**
     * 大小球
     * @param hgApi4
     * @param hgFbGameMore
     * @throws DocumentException
     */
    private void sw_ou(HgApi hgApi4, HgFbGameMore hgFbGameMore) throws DocumentException {
        hgApi4.setWType(HgWTypeEnum.OU.getType());
        // 小球赔率 - 小xxx
        hgApi4.setChoseTeam(HgChooseTeamEnum.HOME.getType());
        String order_view_h = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_h != null) {
            Document orderViewH = DocumentHelper.parseText(order_view_h);
            Element rootOrderViewH = orderViewH.getRootElement();
            String spreadH = rootOrderViewH.elementTextTrim("spread"); // 小球个数
            if (Arrays.asList(xiao_qiu).contains(spreadH)) {
                String ioratioH = rootOrderViewH.elementTextTrim("ioratio"); // 水位 - 小球
                systemExit(ioratioH);
                // 小球水位设置
                transformSmall(hgFbGameMore, spreadH, ioratioH);
            }
        }

        // 大球赔率 - 大xxx
        hgApi4.setChoseTeam(HgChooseTeamEnum.CUSTOMER.getType());
        String order_view_c = HgApiUtils.today_ft_order_view(hgApi4);
        if (order_view_c != null) {
            Document orderViewC = DocumentHelper.parseText(order_view_c);
            Element rootOrderViewC = orderViewC.getRootElement();
            String spreadC = rootOrderViewC.elementTextTrim("spread"); // 大球个数
            if (Arrays.asList(da_qiu).contains(spreadC)) {
                String ioratioC = rootOrderViewC.elementTextTrim("ioratio"); // 水位 - 大球
                systemExit(ioratioC);
                // 大球水位设置
                transformBig(hgFbGameMore, spreadC, ioratioC);
            }
        }
    }

    /**
     * 让球
     * @param hgApi4
     * @param handicap
     * @param hgFbGameMore
     * @param gameGameMore
     * @param strong
     * @throws DocumentException
     */
    private void sw_rang(HgApi hgApi4, String handicap, HgFbGameMore hgFbGameMore, Element gameGameMore, String strong) throws DocumentException {
        Double handicapDouble = Double.valueOf(handicap);
        Double handicapSub = CalcUtil.sub(handicapDouble, 0.5);
        String ratio = gameGameMore.elementTextTrim("ratio"); // 让球数
        // 判断是否符合系统所需让球个数 （1-0.5）（2-1.5）
        if (ratio.indexOf("/")<0 && handicapSub.equals(Double.valueOf(ratio))) {
            hgApi4.setWType(HgWTypeEnum.RANG.getType());
            // 主队赔率
            hgApi4.setChoseTeam(HgChooseTeamEnum.HOME.getType());
            /** 准确赔率 */
            String order_view_h = HgApiUtils.today_ft_order_view(hgApi4);
            if (order_view_h != null) {
                Document orderViewH = DocumentHelper.parseText(order_view_h);
                Element rootOrderViewH = orderViewH.getRootElement();
                String ioratioH = rootOrderViewH.elementTextTrim("ioratio"); // 水位
                systemExit(ioratioH);
//                String spreadH = rootOrderViewH.elementTextTrim("spread"); // 让球个数
//                String gold_gmin_h = rootOrderView.elementTextTrim("gold_gmin"); // 投注最小金额
//                String gold_gmax_h = rootOrderView.elementTextTrim("gold_gmax"); // 投注最大金额
                if (HgChooseTeamEnum.HOME.getType().equals(strong)) {
                    // 主减spread
                    hgFbGameMore.setHCut05(ioratioH);
                } else {
                    // 主加spread
                    hgFbGameMore.setHAdd05(ioratioH);
                }
            }

            // 客队赔率
            hgApi4.setChoseTeam(HgChooseTeamEnum.CUSTOMER.getType());
            String order_view_c = HgApiUtils.today_ft_order_view(hgApi4);
            if (order_view_c != null) {
                Document orderViewC = DocumentHelper.parseText(order_view_c);
                Element rootOrderViewC = orderViewC.getRootElement();
                String ioratioC = rootOrderViewC.elementTextTrim("ioratio"); // 水位
                systemExit(ioratioC);
                if (HgChooseTeamEnum.HOME.getType().equals(strong)) {
                    // 客加spread
                    hgFbGameMore.setCAdd05(ioratioC);
                } else {
                    // 客减spread
                    hgFbGameMore.setCCut05(ioratioC);
                }
            }
        }
    }

    private void transformBig(HgFbGameMore hgFbGameMore, String spreadC, String ioratioC) {
        if (da_qiu[0].equals(spreadC)) {
            hgFbGameMore.setBig15(ioratioC);
        } else if (da_qiu[1].equals(spreadC)) {
            hgFbGameMore.setBig25(ioratioC);
        } else if (da_qiu[2].equals(spreadC)) {
            hgFbGameMore.setBig35(ioratioC);
        } else if (da_qiu[3].equals(spreadC)) {
            hgFbGameMore.setBig15_2(ioratioC);
        } else if (da_qiu[4].equals(spreadC)) {
            hgFbGameMore.setBig2_25(ioratioC);
        } else if (da_qiu[5].equals(spreadC)) {
            hgFbGameMore.setBig25_3(ioratioC);
        } else if (da_qiu[6].equals(spreadC)) {
            hgFbGameMore.setBig3_35(ioratioC);
        }
    }

    /**
     * 小球水位设置
     * @param hgFbGameMore
     * @param spreadH
     * @param ioratioH
     */
    private void transformSmall(HgFbGameMore hgFbGameMore, String spreadH, String ioratioH) {
        if (xiao_qiu[0].equals(spreadH)) {
            // 小2.5
            hgFbGameMore.setSmall25(ioratioH);
        } else if (xiao_qiu[1].equals(spreadH)) {
            hgFbGameMore.setSmall35(ioratioH);
        } else if (xiao_qiu[2].equals(spreadH)) {
            hgFbGameMore.setSmall2_25(ioratioH);
        } else if (xiao_qiu[3].equals(spreadH)) {
            hgFbGameMore.setSmall25_3(ioratioH);
        } else if (xiao_qiu[4].equals(spreadH)) {
            hgFbGameMore.setSmall3_35(ioratioH);
        } else if (xiao_qiu[5].equals(spreadH)) {
            hgFbGameMore.setSmall35_4(ioratioH);
        }
    }

    /**
     * 停止程序
     * @param obj
     */
    public void systemExit(Object obj) {
        if (obj == null) {
            HttpUtils.sendPost(tgUrl, "chat_id=-749764025&text=☠☠‼‼程序异常终止，请检查‼‼☠☠");
            HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=☠☠‼‼程序异常终止，请检查‼‼☠☠");
            System.exit(1);
        }
    }

    /**
     * polling today football data AG
     */
    @Override
    public void pollingFootballDataToday_All() {
        try {
            HgApi hgApi1 = hgApiMapper.selectByP(HgApiEnum.get_league_list_All.getApi());
            HgApi hgApi2 = hgApiMapper.selectByP(HgApiEnum.get_game_list.getApi());
            HgApi hgApi3 = hgApiMapper.selectByP(HgApiEnum.get_game_more.getApi());
            HgApi hgApi4 = hgApiMapper.selectByP(HgApiEnum.ft_order_view.getApi());

            String league_list_all = HgApiUtils.today_get_league_list_All(hgApi1);
            Document doc = DocumentHelper.parseText(league_list_all);
            Element rootElt = doc.getRootElement();
            Element classifier = rootElt.element("classifier");
            systemExit(classifier);
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

                    /** 获取联赛下属比赛列表 */
                    hgApi2.setLid(leagueId);
                    String game_list = HgApiUtils.today_get_game_list(hgApi2);
                    Document docGameList = DocumentHelper.parseText(game_list);
                    Element rootEltGameList = docGameList.getRootElement();
                    Iterator ecIt = rootEltGameList.elementIterator("ec");
                    systemExit(ecIt);
                    while (ecIt.hasNext()) {
                        Element ec = (Element)ecIt.next();
                        Element game = ec.element("game");
                        String team_h = game.elementTextTrim("TEAM_H");
                        String team_c = game.elementTextTrim("TEAM_C");
                        String dateTime = game.elementTextTrim("DATETIME");
                        String team_h_id = game.elementTextTrim("TEAM_H_ID");
                        String team_c_id = game.elementTextTrim("TEAM_C_ID");
                        String ecid = game.elementTextTrim("ECID");
                        // 处理比赛时间，+12H
                        String ecTime = DateUtils.transformDateHg(dateTime);
                        Long ecTimestamp = DateUtils.getLeagueDate(dateTime);

                        // 写入联赛球队数据
                        HgFbLeagueData hgFbLeagueData = new HgFbLeagueData(null, regionName, regionSortName, leagueName
                                , leagueSortName, leagueId, ecid, ecTime, ecTimestamp, team_h, team_h_id, team_c, team_c_id);
                        Integer exist = hgFbLeagueDataMapper.selectExist(leagueId, ecid);
                        if (null == exist) {
                            hgFbLeagueDataMapper.insertData(hgFbLeagueData);
                        }

                        /** 赔率明细 */
                        // 存储赔率数据
                        HgFbGameMore hgFbGameMore = new HgFbGameMore();
                        hgFbGameMore.setLid(leagueId);
                        hgFbGameMore.setEcid(ecid);
                        hgApi3.setLid(leagueId);
                        hgApi3.setEcid(ecid);
                        String game_more = HgApiUtils.today_get_game_more(hgApi3);
                        Document docGameMore = DocumentHelper.parseText(game_more);
                        Element rootEltGameMore = docGameMore.getRootElement();
                        Iterator gameIt = rootEltGameMore.elementIterator("game");
                        systemExit(gameIt);
                        while (gameIt.hasNext()) {
                            Element gameGameMore = (Element) gameIt.next();
                            String gidGameMore = gameGameMore.elementTextTrim("gid"); // 获取准确赔率参数
                            hgApi4.setGid(gidGameMore); // 接口参数
                            hgApi4.setLid(leagueId);
                            hgApi4.setEcid(ecid);
                            String strong = gameGameMore.elementTextTrim("strong"); // 强队标识，H：主队强（主减客加），C：客队强（主加客减）
                            String mode = gameGameMore.attributeValue("mode");
                            /** 让球 */
                            String sw_r = gameGameMore.elementTextTrim("sw_R"); // 让球赔率开关 （sw_ 赔率开关，Y:开，N:关）
                            if (UserConstants.YES.equals(sw_r) && "FT".equals(mode)) {
                                sw_rang_AG(hgApi4, hgFbGameMore, gameGameMore, strong);
                            }
                            /** 大小球 */
                            String sw_ou = gameGameMore.elementTextTrim("sw_OU");
                            if (UserConstants.YES.equals(sw_ou)) {
                                sw_ou(hgApi4, hgFbGameMore);
                            }

                            /** master参数组 */
                            String master = gameGameMore.attributeValue("master");
                            if (UserConstants.YES.equals(master) && "FT".equals(mode)) {
                                /** 独赢 */
                                sw_monopoly(hgApi4, hgFbGameMore);

                                /** 总进球数 */
                                sw_total(hgApi4, hgFbGameMore);
                            }
                        }
                        Integer exist1 = hgFbGameMoreMapper.selectExist(leagueId, ecid);
                        if (null == exist1) {
                            // 写入赔率数据
                            hgFbGameMoreMapper.insertData(hgFbGameMore);
                        } else {
                            hgFbGameMoreMapper.updateData(hgFbGameMore);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("pollingFootballDataToday_AG exception ----->>>>", e);
        }
    }

    /**
     * 早盘数据
     * polling early football data
     */
    @Override
    public void pollingFootballDataEarly_All() {
        try {
            HgApi hgApi1 = hgApiMapper.selectByP(HgApiEnum.get_league_list_All.getApi());
            HgApi hgApi2 = hgApiMapper.selectByP(HgApiEnum.get_game_list.getApi());
            HgApi hgApi3 = hgApiMapper.selectByP(HgApiEnum.get_game_more.getApi());
            HgApi hgApi4 = hgApiMapper.selectByP(HgApiEnum.ft_order_view.getApi());

            String league_list_all = HgApiUtils.early_get_league_list_All(hgApi1);
            Document doc = DocumentHelper.parseText(league_list_all);
            Element rootElt = doc.getRootElement();
            Element classifier = rootElt.element("classifier");
            systemExit(classifier);
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

                    /** 获取联赛下属比赛列表 */
                    hgApi2.setLid(leagueId);
                    String game_list = HgApiUtils.early_get_game_list(hgApi2);
                    Document docGameList = DocumentHelper.parseText(game_list);
                    Element rootEltGameList = docGameList.getRootElement();
                    Iterator ecIt = rootEltGameList.elementIterator("ec");
                    systemExit(ecIt);
                    while (ecIt.hasNext()) {
                        Element ec = (Element)ecIt.next();
                        Element game = ec.element("game");
                        String team_h = game.elementTextTrim("TEAM_H");
                        String team_c = game.elementTextTrim("TEAM_C");
                        String dateTime = game.elementTextTrim("DATETIME");
                        String team_h_id = game.elementTextTrim("TEAM_H_ID");
                        String team_c_id = game.elementTextTrim("TEAM_C_ID");
                        String ecid = game.elementTextTrim("ECID");
                        // 处理比赛时间，+12H
                        String ecTime = DateUtils.transformDateHg(dateTime);
                        Long ecTimestamp = DateUtils.getLeagueDate(dateTime);

                        // 写入联赛球队数据
                        HgFbLeagueData hgFbLeagueData = new HgFbLeagueData(null, regionName, regionSortName, leagueName
                                , leagueSortName, leagueId, ecid, ecTime, ecTimestamp, team_h, team_h_id, team_c, team_c_id);
                        Integer exist = hgFbLeagueDataMapper.selectExist(leagueId, ecid);
                        if (null == exist) {
                            hgFbLeagueDataMapper.insertData(hgFbLeagueData);
                        }

                        /** 赔率明细 */
                        // 存储赔率数据
                        HgFbGameMore hgFbGameMore = new HgFbGameMore();
                        hgFbGameMore.setLid(leagueId);
                        hgFbGameMore.setEcid(ecid);
                        hgApi3.setLid(leagueId);
                        hgApi3.setEcid(ecid);
                        String game_more = HgApiUtils.early_get_game_more(hgApi3);
                        Document docGameMore = DocumentHelper.parseText(game_more);
                        Element rootEltGameMore = docGameMore.getRootElement();
                        Iterator gameIt = rootEltGameMore.elementIterator("game");
                        systemExit(gameIt);
                        while (gameIt.hasNext()) {
                            Element gameGameMore = (Element) gameIt.next();
                            String gidGameMore = gameGameMore.elementTextTrim("gid"); // 获取准确赔率参数
                            hgApi4.setGid(gidGameMore); // 接口参数
                            hgApi4.setLid(leagueId);
                            hgApi4.setEcid(ecid);
                            String strong = gameGameMore.elementTextTrim("strong"); // 强队标识，H：主队强（主减客加），C：客队强（主加客减）
                            String mode = gameGameMore.attributeValue("mode");
                            /** 让球 */
                            String sw_r = gameGameMore.elementTextTrim("sw_R"); // 让球赔率开关 （sw_ 赔率开关，Y:开，N:关）
                            if (UserConstants.YES.equals(sw_r) && "FT".equals(mode)) {
                                sw_rang_AG(hgApi4, hgFbGameMore, gameGameMore, strong);
                            }
                            /** 大小球 */
                            String sw_ou = gameGameMore.elementTextTrim("sw_OU");
                            if (UserConstants.YES.equals(sw_ou)) {
                                sw_ou(hgApi4, hgFbGameMore);
                            }

                            /** master参数组 */
                            String master = gameGameMore.attributeValue("master");
                            if (UserConstants.YES.equals(master) && "FT".equals(mode)) {
                                /** 独赢 */
                                sw_monopoly(hgApi4, hgFbGameMore);

                                /** 总进球数 */
                                sw_total(hgApi4, hgFbGameMore);
                            }
                        }
                        Integer exist1 = hgFbGameMoreMapper.selectExist(leagueId, ecid);
                        if (null == exist1) {
                            // 写入赔率数据
                            hgFbGameMoreMapper.insertData(hgFbGameMore);
                        } else {
                            hgFbGameMoreMapper.updateData(hgFbGameMore);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("pollingFootballDataEarly_AG exception ----->>>>", e);
        }
    }

    /**
     * 让球 - AG
     * @param hgApi4
     * @param hgFbGameMore
     * @param gameGameMore
     * @param strong
     * @throws DocumentException
     */
    private void sw_rang_AG(HgApi hgApi4, HgFbGameMore hgFbGameMore, Element gameGameMore, String strong) throws DocumentException {
        String ratio = gameGameMore.elementTextTrim("ratio"); // 让球数
        // 判断是否符合系统所需让球个数 （1-0.5）（2-1.5）
        if (ratio.indexOf("/")<0) {
            hgApi4.setWType(HgWTypeEnum.RANG.getType());
            // 主队赔率
            hgApi4.setChoseTeam(HgChooseTeamEnum.HOME.getType());
            /** 准确赔率 */
            String order_view_h = HgApiUtils.today_ft_order_view(hgApi4);
            if (order_view_h != null) {
                Document orderViewH = DocumentHelper.parseText(order_view_h);
                Element rootOrderViewH = orderViewH.getRootElement();
                String ioratioH = rootOrderViewH.elementTextTrim("ioratio"); // 水位
                systemExit(ioratioH);
//                String spreadH = rootOrderViewH.elementTextTrim("spread"); // 让球个数
//                String gold_gmin_h = rootOrderView.elementTextTrim("gold_gmin"); // 投注最小金额
//                String gold_gmax_h = rootOrderView.elementTextTrim("gold_gmax"); // 投注最大金额
                if (HgChooseTeamEnum.HOME.getType().equals(strong)) {
                    // 主减spread
                    hgFbGameMore.setHCut05(ioratioH);
                } else {
                    // 主加spread
                    hgFbGameMore.setHAdd05(ioratioH);
                }
            }

            // 客队赔率
            hgApi4.setChoseTeam(HgChooseTeamEnum.CUSTOMER.getType());
            String order_view_c = HgApiUtils.today_ft_order_view(hgApi4);
            if (order_view_c != null) {
                Document orderViewC = DocumentHelper.parseText(order_view_c);
                Element rootOrderViewC = orderViewC.getRootElement();
                String ioratioC = rootOrderViewC.elementTextTrim("ioratio"); // 水位
                systemExit(ioratioC);
                if (HgChooseTeamEnum.HOME.getType().equals(strong)) {
                    // 客加spread
                    hgFbGameMore.setCAdd05(ioratioC);
                } else {
                    // 客减spread
                    hgFbGameMore.setCCut05(ioratioC);
                }
            }
        }
    }

    /**
     * hg - ag 数据对冲计算 - 单关
     */
    @Override
    public void hedge_Hg_Ag_data_single() {
        try {
            List<AgLeagueData> leagueDatas = agLeagueDataMapper.selectAll();
            for (AgLeagueData leagueData : leagueDatas) {
                BetParamVo betParamVo = new BetParamVo();
                String matchId = leagueData.getMatchId(); // ag比赛id
                String leagueName = leagueData.getLeagueName(); // 联赛名
                String teamH = leagueData.getTeamH(); // 主队名
                String teamA = leagueData.getTeamA(); // 客队名
                // AG-HG 字典转换
                String leagueNameHg = dictLeagueHgAgMapper.selectByAg(leagueName);
                String teamHHg = dictTeamHgAgMapper.selectByAg(teamH);
                String teamAHg = dictTeamHgAgMapper.selectByAg(teamA);
                HgFbLeagueData hgFbLeagueData = hgFbLeagueDataMapper.selectCondition(leagueNameHg, teamHHg, teamAHg);
                if (hgFbLeagueData == null) {
                    continue;
                }
                HgFbGameMore fbGameMore = hgFbGameMoreMapper.selectCondition(hgFbLeagueData.getLeagueId(), hgFbLeagueData.getEcid());
                if (fbGameMore == null) {
                    continue;
                }
                AgGameInfo agGameInfo = agGameInfoMapper.selectByMatchId(matchId);
                betParamVo.setBetAmountHg(fbGameMore.getBetAmount());
                // 胜平负
                if (agGameInfo.getMyselfH() != null) {
                    betParamVo.setOddsWin(Double.valueOf(agGameInfo.getMyselfH())); // AG主胜
                    betParamVo.setOddsTie(Double.valueOf(agGameInfo.getMyselfN())); // AG平
                    betParamVo.setOddsLose(Double.valueOf(agGameInfo.getMyselfA())); // AG客胜
                }
                if (fbGameMore.getMyselfH() != null) {
                    betParamVo.setHome(Double.valueOf(fbGameMore.getMyselfH())); // 皇冠主胜
                    betParamVo.setTie(Double.valueOf(fbGameMore.getMyselfN())); // 皇冠平
                    betParamVo.setVisit(Double.valueOf(fbGameMore.getMyselfC())); // 皇冠客胜
                }
                // AG让球
                if (agGameInfo.getHomeAdd() != null) { // 主加，客减
                        betParamVo.setOddsShouWin(Double.valueOf(agGameInfo.getHomeAdd())); // AG主队受球胜
                        betParamVo.setOddsShouLose(Double.valueOf(agGameInfo.getAwayCut())); // AG主队受球客胜
                } else if (agGameInfo.getHomeAdd() != null) { // 主减，客加
                    betParamVo.setOddsRangWin(Double.valueOf(agGameInfo.getHomeCut())); // AG主队让球胜
                    betParamVo.setOddsRangLose(Double.valueOf(agGameInfo.getAwayAdd())); // AG主队让球客胜
                }
                // 皇冠让球数据
                if (StringUtils.isNotBlank(fbGameMore.getHAdd05())) {
                    betParamVo.setHomeAdd05(Double.valueOf(fbGameMore.getHAdd05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getHCut05())) {
                    betParamVo.setHomeCut05(Double.valueOf(fbGameMore.getHCut05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getCAdd05())) {
                    betParamVo.setVisitAdd05(Double.valueOf(fbGameMore.getCAdd05()));
                }
                if (StringUtils.isNotBlank(fbGameMore.getCCut05())) {
                    betParamVo.setVisitCut05(Double.valueOf(fbGameMore.getCCut05()));
                }
                String msg = "chat_id=-907633723&text=⚽⚽球赛监测⚽单关⚽, AG -- 联赛名称：" + leagueName + ", 主队：" + teamH + ", 客队：" + teamA
                        + " ||| HG -- 联赛名称：" + leagueNameHg + ", 主队：" + teamHHg + ", 客队：" + teamAHg;
                betParamVo.setMsg(msg);
                betParamVo.setNotifyFlag(1);
                hgAgBallService.betCheckSingle(betParamVo);
            }
        } catch (Exception e) {
            log.info("hedge_Hg_Ag_data_single exception ----->>>>", e);
        }
    }

    /**
     * hg - ag 数据对冲计算 - 012
     */
    @Override
    public void hedge_Hg_Ag_data_012() {
        try {
            List<AgLeagueData> leagueDatas = agLeagueDataMapper.selectAll();
            for (AgLeagueData leagueData : leagueDatas) {
                BetParamVo betParamVo = new BetParamVo();
                String matchId = leagueData.getMatchId(); // ag比赛id
                String leagueName = leagueData.getLeagueName(); // 联赛名
                String teamH = leagueData.getTeamH(); // 主队名
                String teamA = leagueData.getTeamA(); // 客队名
                // AG-HG 字典转换
                String leagueNameHg = dictLeagueHgAgMapper.selectByAg(leagueName);
                String teamHHg = dictTeamHgAgMapper.selectByAg(teamH);
                String teamAHg = dictTeamHgAgMapper.selectByAg(teamA);
                HgFbLeagueData hgFbLeagueData = hgFbLeagueDataMapper.selectCondition(leagueNameHg, teamHHg, teamAHg);
                if (hgFbLeagueData == null) {
                    continue;
                }
                HgFbGameMore fbGameMore = hgFbGameMoreMapper.selectCondition(hgFbLeagueData.getLeagueId(), hgFbLeagueData.getEcid());
                if (fbGameMore == null) {
                    continue;
                }
                AgGameInfo agGameInfo = agGameInfoMapper.selectByMatchId(matchId);

                betParamVo.setBetAmountHg(fbGameMore.getBetAmount()); // 皇冠投注金额，基础投注额
                betParamVo.setOddsZero(Double.valueOf(agGameInfo.getS0())); // AG总进球0
                betParamVo.setOddsOne(Double.valueOf(agGameInfo.getS1())); // AG总进球1
                betParamVo.setOddsTwo(Double.valueOf(agGameInfo.getS2())); // AG总进球2
                betParamVo.setOddsThree(Double.valueOf(agGameInfo.getS3())); // AG总进球3
                betParamVo.setOddsFour(Double.valueOf(agGameInfo.getS4())); // AG总进球4
                betParamVo.setOddsFive(Double.valueOf(agGameInfo.getS5())); // AG总进球5
                betParamVo.setOddsSix(Double.valueOf(agGameInfo.getS6())); // AG总进球6+

                betParamVo.setZong0_1(Double.valueOf(fbGameMore.getTotal01())); // 皇冠总进球0-1
                betParamVo.setZong2_3(Double.valueOf(fbGameMore.getTotal23())); // 皇冠总进球2-3
                betParamVo.setZong4_6(Double.valueOf(fbGameMore.getTotal46())); // 皇冠总进球4-6
                betParamVo.setZong7(Double.valueOf(fbGameMore.getTotal7())); // 皇冠总进球7+

                // 皇冠大球赔率
                betParamVo.set大15(fbGameMore.getBig15()==null ? 0 : Double.valueOf(fbGameMore.getBig15())); // 大1.5
                betParamVo.set大25(fbGameMore.getBig25()==null ? 0 : Double.valueOf(fbGameMore.getBig25())); // 大2.5
                betParamVo.set大35(fbGameMore.getBig35()==null ? 0 : Double.valueOf(fbGameMore.getBig35())); // 大3.5
                betParamVo.set大15_2(fbGameMore.getBig15_2()==null ? 0 : Double.valueOf(fbGameMore.getBig15_2())); // 大1.5/2
                betParamVo.set大2_25(fbGameMore.getBig2_25()==null ? 0 : Double.valueOf(fbGameMore.getBig2_25())); // 大2/2.5
                betParamVo.set大25_3(fbGameMore.getBig25_3()==null ? 0 : Double.valueOf(fbGameMore.getBig25_3())); // 大2.5/3
                betParamVo.set大3_35(fbGameMore.getBig3_35()==null ? 0 : Double.valueOf(fbGameMore.getBig3_35())); // 大3/3.5

                // 皇冠小球赔率
                betParamVo.set小25(fbGameMore.getSmall25()==null ? 0 : Double.valueOf(fbGameMore.getSmall25())); // 小2.5
                betParamVo.set小35(fbGameMore.getSmall35()==null ? 0 : Double.valueOf(fbGameMore.getSmall35())); // 小3.5
                betParamVo.set小2_25(fbGameMore.getSmall2_25()==null ? 0 : Double.valueOf(fbGameMore.getSmall2_25())); // 小2/2.5
                betParamVo.set小25_3(fbGameMore.getSmall25_3()==null ? 0 : Double.valueOf(fbGameMore.getSmall25_3())); // 小2.5/3
                betParamVo.set小3_35(fbGameMore.getSmall3_35()==null ? 0 : Double.valueOf(fbGameMore.getSmall3_35())); // 小3/3.5
                betParamVo.set小35_4(fbGameMore.getSmall35_4()==null ? 0 : Double.valueOf(fbGameMore.getSmall35_4())); // 小3.5/4

                String msg = "chat_id=-907633723&text=⚽⚽球赛监测⚽单关⚽, AG -- 联赛名称：" + leagueName + ", 主队：" + teamH + ", 客队：" + teamA
                        + " ||| HG -- 联赛名称：" + leagueNameHg + ", 主队：" + teamHHg + ", 客队：" + teamAHg;
                betParamVo.setMsg(msg);
                betParamVo.setNotifyFlag(1);
                hgAgBallService.betCheck012(betParamVo);
            }
        } catch (Exception e) {
            log.info("hedge_Hg_Ag_data_012 exception ----->>>>", e);
        }
    }

}

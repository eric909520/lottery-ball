package com.backend.project.system.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.vo.HedgeParamVo;
import com.backend.project.system.service.IHedgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * 对冲 Controller
 *
 * @author
 * @date
 */
@RestController
@RequestMapping("/system/open/api/hedge/")
@Slf4j
public class HedgeController extends BaseController {

    @Resource
    private IHedgeService hedgeService;

    /**
     * 投注测试 - 欧盘赔率
     */
    @PostMapping(value = "/betCheck")
    public AjaxResult betCheck(@RequestBody HedgeParamVo hedgeParamVo) {
        try {
            hedgeService.betCheck(hedgeParamVo);

            return AjaxResult.success();
        } catch (Exception e) {
            log.info("API - betCheck exception ----->>>>", e);
            return AjaxResult.error();
        }
    }

    public static void main(String[] args) {
        String data = "{\n" +
                "    \"dataFrom\": \"\",\n" +
                "    \"emptyFlag\": false,\n" +
                "    \"errorCode\": \"0\",\n" +
                "    \"errorMessage\": \"处理成功\",\n" +
                "    \"success\": true,\n" +
                "    \"value\": {\n" +
                "        \"vtoolsConfig\": {\n" +
                "            \"offLineSaleStatus\": 1,\n" +
                "            \"offLineStopMessage\": \"抱歉，本彩种已停止销售\",\n" +
                "            \"onLineSaleStatus\": 1,\n" +
                "            \"onLineStopMessage\": \"抱歉，本彩种已停止销售\"\n" +
                "        },\n" +
                "        \"matchInfoList\": [\n" +
                "            {\n" +
                "                \"businessDate\": \"2023-07-03\",\n" +
                "                \"subMatchList\": [\n" +
                "                    {\n" +
                "                        \"awayRank\": \"[瑞超5]\",\n" +
                "                        \"awayTeamAbbEnName\": \"KAL\",\n" +
                "                        \"awayTeamAbbName\": \"卡尔马\",\n" +
                "                        \"awayTeamAllName\": \"卡尔马\",\n" +
                "                        \"awayTeamCode\": \"KAL\",\n" +
                "                        \"awayTeamId\": 602,\n" +
                "                        \"backColor\": \"004488\",\n" +
                "                        \"baseAwayTeamId\": 0,\n" +
                "                        \"baseHomeTeamId\": 0,\n" +
                "                        \"bettingAllUp\": 0,\n" +
                "                        \"bettingSingle\": 0,\n" +
                "                        \"businessDate\": \"2023-07-03\",\n" +
                "                        \"crs\": {},\n" +
                "                        \"groupName\": \"\",\n" +
                "                        \"had\": {\n" +
                "                            \"a\": \"2.63\",\n" +
                "                            \"af\": \"0\",\n" +
                "                            \"d\": \"2.75\",\n" +
                "                            \"df\": \"0\",\n" +
                "                            \"goalLine\": \"\",\n" +
                "                            \"h\": \"2.53\",\n" +
                "                            \"hf\": \"0\",\n" +
                "                            \"updateDate\": \"2023-07-03\",\n" +
                "                            \"updateTime\": \"12:00:24\"\n" +
                "                        },\n" +
                "                        \"hafu\": {},\n" +
                "                        \"hhad\": {\n" +
                "                            \"a\": \"1.37\",\n" +
                "                            \"af\": \"0\",\n" +
                "                            \"d\": \"4.00\",\n" +
                "                            \"df\": \"0\",\n" +
                "                            \"goalLine\": \"-1\",\n" +
                "                            \"h\": \"6.25\",\n" +
                "                            \"hf\": \"0\",\n" +
                "                            \"updateDate\": \"2023-07-03\",\n" +
                "                            \"updateTime\": \"12:00:35\"\n" +
                "                        },\n" +
                "                        \"homeRank\": \"[瑞超10]\",\n" +
                "                        \"homeTeamAbbEnName\": \"MJB\",\n" +
                "                        \"homeTeamAbbName\": \"米亚尔比\",\n" +
                "                        \"homeTeamAllName\": \"米亚尔比\",\n" +
                "                        \"homeTeamCode\": \"MJB\",\n" +
                "                        \"homeTeamId\": 974,\n" +
                "                        \"isHide\": 0,\n" +
                "                        \"isHot\": 0,\n" +
                "                        \"leagueAbbName\": \"瑞超\",\n" +
                "                        \"leagueAllName\": \"瑞典超级联赛\",\n" +
                "                        \"leagueCode\": \"SAL\",\n" +
                "                        \"leagueId\": 58,\n" +
                "                        \"lineNum\": \"\",\n" +
                "                        \"matchDate\": \"2023-07-04\",\n" +
                "                        \"matchId\": 1020093,\n" +
                "                        \"matchName\": \"\",\n" +
                "                        \"matchNum\": 1001,\n" +
                "                        \"matchNumStr\": \"周一001\",\n" +
                "                        \"matchStatus\": \"Selling\",\n" +
                "                        \"matchTime\": \"01:00:00\",\n" +
                "                        \"matchWeek\": \"周一\",\n" +
                "                        \"oddsList\": [\n" +
                "                            {\n" +
                "                                \"a\": \"2.630\",\n" +
                "                                \"d\": \"2.750\",\n" +
                "                                \"goalLine\": \"\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"2.530\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1092988,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:00:24\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"1.370\",\n" +
                "                                \"d\": \"4.000\",\n" +
                "                                \"goalLine\": \"-1\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"6.250\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1092988,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:00:35\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"2.630\",\n" +
                "                                \"d\": \"2.750\",\n" +
                "                                \"goalLine\": \"\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"2.530\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1093027,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:00:24\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"1.370\",\n" +
                "                                \"d\": \"4.000\",\n" +
                "                                \"goalLine\": \"-1\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"6.250\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1093027,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:00:35\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"poolList\": [\n" +
                "                            {\n" +
                "                                \"allUp\": 1,\n" +
                "                                \"bettingAllup\": 1,\n" +
                "                                \"bettingSingle\": 0,\n" +
                "                                \"cbtAllUp\": 1,\n" +
                "                                \"cbtSingle\": 0,\n" +
                "                                \"cbtValue\": 1,\n" +
                "                                \"fixedOddsgoalLine\": \"\",\n" +
                "                                \"intAllUp\": 0,\n" +
                "                                \"intSingle\": 0,\n" +
                "                                \"intValue\": 2,\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"poolCloseDate\": \"\",\n" +
                "                                \"poolCloseTime\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1092988,\n" +
                "                                \"poolOddsType\": \"F\",\n" +
                "                                \"poolStatus\": \"Selling\",\n" +
                "                                \"sellInitialDate\": \"\",\n" +
                "                                \"sellInitialTime\": \"\",\n" +
                "                                \"single\": 0,\n" +
                "                                \"updateDate\": \"\",\n" +
                "                                \"updateTime\": \"\",\n" +
                "                                \"vbtAllUp\": 0,\n" +
                "                                \"vbtSingle\": 0,\n" +
                "                                \"vbtValue\": 0\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"allUp\": 1,\n" +
                "                                \"bettingAllup\": 1,\n" +
                "                                \"bettingSingle\": 0,\n" +
                "                                \"cbtAllUp\": 1,\n" +
                "                                \"cbtSingle\": 0,\n" +
                "                                \"cbtValue\": 1,\n" +
                "                                \"fixedOddsgoalLine\": \"\",\n" +
                "                                \"intAllUp\": 0,\n" +
                "                                \"intSingle\": 0,\n" +
                "                                \"intValue\": 2,\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"poolCloseDate\": \"\",\n" +
                "                                \"poolCloseTime\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1093027,\n" +
                "                                \"poolOddsType\": \"F\",\n" +
                "                                \"poolStatus\": \"Selling\",\n" +
                "                                \"sellInitialDate\": \"\",\n" +
                "                                \"sellInitialTime\": \"\",\n" +
                "                                \"single\": 0,\n" +
                "                                \"updateDate\": \"\",\n" +
                "                                \"updateTime\": \"\",\n" +
                "                                \"vbtAllUp\": 0,\n" +
                "                                \"vbtSingle\": 0,\n" +
                "                                \"vbtValue\": 0\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"remark\": \"\",\n" +
                "                        \"sellStatus\": 1,\n" +
                "                        \"ttg\": {},\n" +
                "                        \"vote\": {}\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"awayRank\": \"[瑞超9]\",\n" +
                "                        \"awayTeamAbbEnName\": \"HAM\",\n" +
                "                        \"awayTeamAbbName\": \"哈马比\",\n" +
                "                        \"awayTeamAllName\": \"哈马比\",\n" +
                "                        \"awayTeamCode\": \"HAM\",\n" +
                "                        \"awayTeamId\": 352,\n" +
                "                        \"backColor\": \"004488\",\n" +
                "                        \"baseAwayTeamId\": 0,\n" +
                "                        \"baseHomeTeamId\": 0,\n" +
                "                        \"bettingAllUp\": 0,\n" +
                "                        \"bettingSingle\": 0,\n" +
                "                        \"businessDate\": \"2023-07-03\",\n" +
                "                        \"crs\": {},\n" +
                "                        \"groupName\": \"\",\n" +
                "                        \"had\": {\n" +
                "                            \"a\": \"4.80\",\n" +
                "                            \"af\": \"0\",\n" +
                "                            \"d\": \"4.06\",\n" +
                "                            \"df\": \"0\",\n" +
                "                            \"goalLine\": \"\",\n" +
                "                            \"h\": \"1.46\",\n" +
                "                            \"hf\": \"0\",\n" +
                "                            \"updateDate\": \"2023-07-03\",\n" +
                "                            \"updateTime\": \"12:06:52\"\n" +
                "                        },\n" +
                "                        \"hafu\": {},\n" +
                "                        \"hhad\": {\n" +
                "                            \"a\": \"2.28\",\n" +
                "                            \"af\": \"0\",\n" +
                "                            \"d\": \"3.60\",\n" +
                "                            \"df\": \"0\",\n" +
                "                            \"goalLine\": \"-1\",\n" +
                "                            \"h\": \"2.37\",\n" +
                "                            \"hf\": \"0\",\n" +
                "                            \"updateDate\": \"2023-07-03\",\n" +
                "                            \"updateTime\": \"12:07:03\"\n" +
                "                        },\n" +
                "                        \"homeRank\": \"[瑞超3]\",\n" +
                "                        \"homeTeamAbbEnName\": \"ELF\",\n" +
                "                        \"homeTeamAbbName\": \"埃夫斯堡\",\n" +
                "                        \"homeTeamAllName\": \"埃尔夫斯堡\",\n" +
                "                        \"homeTeamCode\": \"ELF\",\n" +
                "                        \"homeTeamId\": 608,\n" +
                "                        \"isHide\": 0,\n" +
                "                        \"isHot\": 0,\n" +
                "                        \"leagueAbbName\": \"瑞超\",\n" +
                "                        \"leagueAllName\": \"瑞典超级联赛\",\n" +
                "                        \"leagueCode\": \"SAL\",\n" +
                "                        \"leagueId\": 58,\n" +
                "                        \"lineNum\": \"\",\n" +
                "                        \"matchDate\": \"2023-07-04\",\n" +
                "                        \"matchId\": 1020094,\n" +
                "                        \"matchName\": \"\",\n" +
                "                        \"matchNum\": 1002,\n" +
                "                        \"matchNumStr\": \"周一002\",\n" +
                "                        \"matchStatus\": \"Selling\",\n" +
                "                        \"matchTime\": \"01:00:00\",\n" +
                "                        \"matchWeek\": \"周一\",\n" +
                "                        \"oddsList\": [\n" +
                "                            {\n" +
                "                                \"a\": \"4.800\",\n" +
                "                                \"d\": \"4.060\",\n" +
                "                                \"goalLine\": \"\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"1.460\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1093058,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:06:52\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"2.280\",\n" +
                "                                \"d\": \"3.600\",\n" +
                "                                \"goalLine\": \"-1\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"2.370\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1093058,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:07:03\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"4.800\",\n" +
                "                                \"d\": \"4.060\",\n" +
                "                                \"goalLine\": \"\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"1.460\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1092941,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:06:52\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"2.280\",\n" +
                "                                \"d\": \"3.600\",\n" +
                "                                \"goalLine\": \"-1\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"2.370\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1092941,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"12:07:03\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"poolList\": [\n" +
                "                            {\n" +
                "                                \"allUp\": 1,\n" +
                "                                \"bettingAllup\": 1,\n" +
                "                                \"bettingSingle\": 0,\n" +
                "                                \"cbtAllUp\": 1,\n" +
                "                                \"cbtSingle\": 0,\n" +
                "                                \"cbtValue\": 1,\n" +
                "                                \"fixedOddsgoalLine\": \"\",\n" +
                "                                \"intAllUp\": 0,\n" +
                "                                \"intSingle\": 0,\n" +
                "                                \"intValue\": 2,\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"poolCloseDate\": \"\",\n" +
                "                                \"poolCloseTime\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1093058,\n" +
                "                                \"poolOddsType\": \"F\",\n" +
                "                                \"poolStatus\": \"Selling\",\n" +
                "                                \"sellInitialDate\": \"\",\n" +
                "                                \"sellInitialTime\": \"\",\n" +
                "                                \"single\": 0,\n" +
                "                                \"updateDate\": \"\",\n" +
                "                                \"updateTime\": \"\",\n" +
                "                                \"vbtAllUp\": 0,\n" +
                "                                \"vbtSingle\": 0,\n" +
                "                                \"vbtValue\": 0\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"allUp\": 1,\n" +
                "                                \"bettingAllup\": 1,\n" +
                "                                \"bettingSingle\": 0,\n" +
                "                                \"cbtAllUp\": 1,\n" +
                "                                \"cbtSingle\": 0,\n" +
                "                                \"cbtValue\": 1,\n" +
                "                                \"fixedOddsgoalLine\": \"\",\n" +
                "                                \"intAllUp\": 0,\n" +
                "                                \"intSingle\": 0,\n" +
                "                                \"intValue\": 2,\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"poolCloseDate\": \"\",\n" +
                "                                \"poolCloseTime\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1092941,\n" +
                "                                \"poolOddsType\": \"F\",\n" +
                "                                \"poolStatus\": \"Selling\",\n" +
                "                                \"sellInitialDate\": \"\",\n" +
                "                                \"sellInitialTime\": \"\",\n" +
                "                                \"single\": 0,\n" +
                "                                \"updateDate\": \"\",\n" +
                "                                \"updateTime\": \"\",\n" +
                "                                \"vbtAllUp\": 0,\n" +
                "                                \"vbtSingle\": 0,\n" +
                "                                \"vbtValue\": 0\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"remark\": \"\",\n" +
                "                        \"sellStatus\": 1,\n" +
                "                        \"ttg\": {},\n" +
                "                        \"vote\": {}\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"awayRank\": \"[挪超11]\",\n" +
                "                        \"awayTeamAbbEnName\": \"STG\",\n" +
                "                        \"awayTeamAbbName\": \"斯特罗姆\",\n" +
                "                        \"awayTeamAllName\": \"斯特罗姆加斯特\",\n" +
                "                        \"awayTeamCode\": \"STG\",\n" +
                "                        \"awayTeamId\": 887,\n" +
                "                        \"backColor\": \"666666\",\n" +
                "                        \"baseAwayTeamId\": 0,\n" +
                "                        \"baseHomeTeamId\": 0,\n" +
                "                        \"bettingAllUp\": 0,\n" +
                "                        \"bettingSingle\": 0,\n" +
                "                        \"businessDate\": \"2023-07-03\",\n" +
                "                        \"crs\": {},\n" +
                "                        \"groupName\": \"\",\n" +
                "                        \"had\": {\n" +
                "                            \"a\": \"3.12\",\n" +
                "                            \"af\": \"0\",\n" +
                "                            \"d\": \"3.30\",\n" +
                "                            \"df\": \"0\",\n" +
                "                            \"goalLine\": \"\",\n" +
                "                            \"h\": \"1.94\",\n" +
                "                            \"hf\": \"0\",\n" +
                "                            \"updateDate\": \"2023-07-03\",\n" +
                "                            \"updateTime\": \"09:21:48\"\n" +
                "                        },\n" +
                "                        \"hafu\": {},\n" +
                "                        \"hhad\": {\n" +
                "                            \"a\": \"1.63\",\n" +
                "                            \"af\": \"0\",\n" +
                "                            \"d\": \"3.65\",\n" +
                "                            \"df\": \"0\",\n" +
                "                            \"goalLine\": \"-1\",\n" +
                "                            \"h\": \"3.98\",\n" +
                "                            \"hf\": \"0\",\n" +
                "                            \"updateDate\": \"2023-07-03\",\n" +
                "                            \"updateTime\": \"09:22:06\"\n" +
                "                        },\n" +
                "                        \"homeRank\": \"[挪超9]\",\n" +
                "                        \"homeTeamAbbEnName\": \"STA\",\n" +
                "                        \"homeTeamAbbName\": \"斯塔贝克\",\n" +
                "                        \"homeTeamAllName\": \"斯塔贝克\",\n" +
                "                        \"homeTeamCode\": \"STA\",\n" +
                "                        \"homeTeamId\": 351,\n" +
                "                        \"isHide\": 0,\n" +
                "                        \"isHot\": 0,\n" +
                "                        \"leagueAbbName\": \"挪超\",\n" +
                "                        \"leagueAllName\": \"挪威超级联赛\",\n" +
                "                        \"leagueCode\": \"NTL\",\n" +
                "                        \"leagueId\": 51,\n" +
                "                        \"lineNum\": \"\",\n" +
                "                        \"matchDate\": \"2023-07-04\",\n" +
                "                        \"matchId\": 1020095,\n" +
                "                        \"matchName\": \"\",\n" +
                "                        \"matchNum\": 1003,\n" +
                "                        \"matchNumStr\": \"周一003\",\n" +
                "                        \"matchStatus\": \"Selling\",\n" +
                "                        \"matchTime\": \"01:00:00\",\n" +
                "                        \"matchWeek\": \"周一\",\n" +
                "                        \"oddsList\": [\n" +
                "                            {\n" +
                "                                \"a\": \"3.120\",\n" +
                "                                \"d\": \"3.300\",\n" +
                "                                \"goalLine\": \"\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"1.940\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1093003,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"09:21:48\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"1.630\",\n" +
                "                                \"d\": \"3.650\",\n" +
                "                                \"goalLine\": \"-1\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"3.980\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1093003,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"09:22:06\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"3.120\",\n" +
                "                                \"d\": \"3.300\",\n" +
                "                                \"goalLine\": \"\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"1.940\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1092968,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"09:21:48\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"a\": \"1.630\",\n" +
                "                                \"d\": \"3.650\",\n" +
                "                                \"goalLine\": \"-1\",\n" +
                "                                \"goalLineF\": \"\",\n" +
                "                                \"h\": \"3.980\",\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"odds\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1092968,\n" +
                "                                \"updateDate\": \"2023-07-03\",\n" +
                "                                \"updateTime\": \"09:22:06\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"poolList\": [\n" +
                "                            {\n" +
                "                                \"allUp\": 1,\n" +
                "                                \"bettingAllup\": 1,\n" +
                "                                \"bettingSingle\": 0,\n" +
                "                                \"cbtAllUp\": 1,\n" +
                "                                \"cbtSingle\": 0,\n" +
                "                                \"cbtValue\": 1,\n" +
                "                                \"fixedOddsgoalLine\": \"\",\n" +
                "                                \"intAllUp\": 0,\n" +
                "                                \"intSingle\": 0,\n" +
                "                                \"intValue\": 2,\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"poolCloseDate\": \"\",\n" +
                "                                \"poolCloseTime\": \"\",\n" +
                "                                \"poolCode\": \"HAD\",\n" +
                "                                \"poolId\": 1093003,\n" +
                "                                \"poolOddsType\": \"F\",\n" +
                "                                \"poolStatus\": \"Selling\",\n" +
                "                                \"sellInitialDate\": \"\",\n" +
                "                                \"sellInitialTime\": \"\",\n" +
                "                                \"single\": 0,\n" +
                "                                \"updateDate\": \"\",\n" +
                "                                \"updateTime\": \"\",\n" +
                "                                \"vbtAllUp\": 0,\n" +
                "                                \"vbtSingle\": 0,\n" +
                "                                \"vbtValue\": 0\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"allUp\": 1,\n" +
                "                                \"bettingAllup\": 1,\n" +
                "                                \"bettingSingle\": 0,\n" +
                "                                \"cbtAllUp\": 1,\n" +
                "                                \"cbtSingle\": 0,\n" +
                "                                \"cbtValue\": 1,\n" +
                "                                \"fixedOddsgoalLine\": \"\",\n" +
                "                                \"intAllUp\": 0,\n" +
                "                                \"intSingle\": 0,\n" +
                "                                \"intValue\": 2,\n" +
                "                                \"matchId\": 0,\n" +
                "                                \"matchNum\": 0,\n" +
                "                                \"poolCloseDate\": \"\",\n" +
                "                                \"poolCloseTime\": \"\",\n" +
                "                                \"poolCode\": \"HHAD\",\n" +
                "                                \"poolId\": 1092968,\n" +
                "                                \"poolOddsType\": \"F\",\n" +
                "                                \"poolStatus\": \"Selling\",\n" +
                "                                \"sellInitialDate\": \"\",\n" +
                "                                \"sellInitialTime\": \"\",\n" +
                "                                \"single\": 0,\n" +
                "                                \"updateDate\": \"\",\n" +
                "                                \"updateTime\": \"\",\n" +
                "                                \"vbtAllUp\": 0,\n" +
                "                                \"vbtSingle\": 0,\n" +
                "                                \"vbtValue\": 0\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"remark\": \"\",\n" +
                "                        \"sellStatus\": 1,\n" +
                "                        \"ttg\": {},\n" +
                "                        \"vote\": {}\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"weekday\": \"周一\",\n" +
                "                \"matchCount\": 3\n" +
                "            }\n" +
                "        ],\n" +
                "        \"matchDateList\": [\n" +
                "            {\n" +
                "                \"businessDate\": \"2023-07-03\",\n" +
                "                \"businessDateCn\": \"周一\",\n" +
                "                \"matchDate\": \"\",\n" +
                "                \"matchDateCn\": \"\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"allUpList\": {\n" +
                "            \"HHAD\": [\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"2x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x3\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x4\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x4\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x5\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x6\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x11\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x5\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x6\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x10\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x16\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x20\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x26\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x6\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x7\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x15\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x20\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x22\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x35\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x42\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x50\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x57\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x7\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x8\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x21\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x35\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x120\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x8\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x9\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x28\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x56\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x70\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x247\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"Single\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"2x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"Single\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HHAD\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"HAD\": [\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"2x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x3\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x4\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x4\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x5\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x6\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x11\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x5\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x6\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x10\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x16\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x20\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x26\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x6\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x7\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x15\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x20\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x22\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x35\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x42\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x50\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x57\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x7\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x8\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x21\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x35\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x120\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x1\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x8\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x9\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x28\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x56\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x70\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x247\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"Single\",\n" +
                "                    \"formulaType\": 1,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"2x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"3x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"4x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"5x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"6x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"7x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"8x1\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"fValue\": 1,\n" +
                "                    \"formula\": \"Single\",\n" +
                "                    \"formulaType\": 2,\n" +
                "                    \"maxMatchCount\": 0,\n" +
                "                    \"poolCode\": \"HAD\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"leagueList\": [\n" +
                "            {\n" +
                "                \"leagueId\": \"51\",\n" +
                "                \"leagueName\": \"挪威超级联赛\",\n" +
                "                \"leagueNameAbbr\": \"挪超\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"leagueId\": \"58\",\n" +
                "                \"leagueName\": \"瑞典超级联赛\",\n" +
                "                \"leagueNameAbbr\": \"瑞超\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"totalCount\": 3,\n" +
                "        \"lastUpdateTime\": \"2023-07-03 12:07:03\"\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONObject value = jsonObject.getJSONObject("value");
        JSONArray matchInfoList = value.getJSONArray("matchInfoList");
        Iterator<Object> iterator = matchInfoList.stream().iterator();
        while (iterator.hasNext()) {
            JSONObject next = JSONObject.parseObject(iterator.next().toString());
            JSONArray subMatchList = next.getJSONArray("subMatchList");
            Iterator<Object> iterator1 = subMatchList.stream().iterator();
            while (iterator1.hasNext()) {
                JSONObject next1 = JSONObject.parseObject(iterator1.next().toString());
                JSONObject had = next1.getJSONObject("had");
                String string = had.getString("a");
                System.out.println(string);
            }
        }
    }

}

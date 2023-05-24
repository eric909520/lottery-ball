package com.backend.project.system.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.DateUtils;
import com.backend.common.utils.http.HttpUtils;
import com.backend.framework.config.ThreadPoolConfig;
import com.backend.framework.config.websocket.WebSocketServer;
import com.backend.project.system.domain.*;
import com.backend.project.system.domain.websocketVo.WebSocketVo;
import com.backend.project.system.domain.websocketVo.WsPreOrderVo;
import com.backend.project.system.enums.WebsocketTitleEnum;
import com.backend.project.system.mapper.*;
import com.backend.project.system.service.ILotteryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 相关计划
 */
@Configuration
@EnableScheduling
@Slf4j
public class LotterySchedule {

    @Resource
    private ThreadPoolConfig threadPoolConfig;
    @Resource
    private LotteryDataMapper lotteryDataMapper;
    @Resource
    private NumberAnalyzeMapper numberAnalyzeMapper;
    @Resource
    private ILotteryService lotteryService;
    @Resource
    private PredictNumberMapper predictNumberMapper;

    public final static int lengthControl = 4;

    public final static int lengthControlSingle = 5;


    /**
     * task - polling history data
     */
    @Scheduled(cron="0 31 02 20 4 ?")
    private void pollingLotteryHistoryData() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            String baseDate = "2023-04-18";
            String endDate = "2023-04-20";
            while (true) {
                baseDate = DateUtils.addDaysYYYYMMDD(baseDate, 1);
                // https://api.api68.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10036&date=2022-01-01
                String url = "https://api.api68.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10036&date=" + baseDate;
                String lottery = HttpUtils.commonGet(url);
                // System.out.println(data);
                if (StringUtils.isBlank(lottery)) {
                    return;
                }
                JSONObject jsonObject = JSONObject.parseObject(lottery);
                JSONObject result = jsonObject.getJSONObject("result");
                JSONArray data = result.getJSONArray("data");
//                System.out.println(data);
                for (int i = 0; i < data.size(); i++) {
                    JSONObject dataSingle = data.getJSONObject(i);
                    int preDrawIssue = dataSingle.getIntValue("preDrawIssue");
                    String preDrawTime = dataSingle.getString("preDrawTime");
                    String preDrawCode = dataSingle.getString("preDrawCode").replace(",", "");
                    int sumNum = dataSingle.getIntValue("sumNum");
                    int sumSingleDouble = dataSingle.getIntValue("sumSingleDouble");
                    int sumBigSmall = dataSingle.getIntValue("sumBigSmall");
                    int dragonTiger = dataSingle.getIntValue("dragonTiger");
                    int firstBigSmall = dataSingle.getIntValue("firstBigSmall");
                    int firstSingleDouble = dataSingle.getIntValue("firstSingleDouble");
                    int secondBigSmall = dataSingle.getIntValue("secondBigSmall");
                    int secondSingleDouble = dataSingle.getIntValue("secondSingleDouble");
                    int thirdBigSmall = dataSingle.getIntValue("thirdBigSmall");
                    int thirdSingleDouble = dataSingle.getIntValue("thirdSingleDouble");
                    int fourthBigSmall = dataSingle.getIntValue("fourthBigSmall");
                    int fourthSingleDouble = dataSingle.getIntValue("fourthSingleDouble");
                    int fifthBigSmall = dataSingle.getIntValue("fifthBigSmall");
                    int fifthSingleDouble = dataSingle.getIntValue("fifthSingleDouble");
                    int behindThree = dataSingle.getIntValue("behindThree");
                    int betweenThree = dataSingle.getIntValue("betweenThree");
                    int lastThree = dataSingle.getIntValue("lastThree");
                    int groupCode = dataSingle.getIntValue("groupCode");

                    LotteryData lotteryData = new LotteryData(preDrawIssue, preDrawTime, preDrawCode, sumNum, sumSingleDouble
                            , sumBigSmall, dragonTiger, firstBigSmall, firstSingleDouble, secondBigSmall, secondSingleDouble
                            , thirdBigSmall, thirdSingleDouble, fourthBigSmall, fourthSingleDouble, fifthBigSmall
                            , fifthSingleDouble, behindThree, betweenThree, lastThree, groupCode);
                    try {
                        lotteryDataMapper.insertLotteryData(lotteryData);
                    } catch (Exception e) {
                        log.info("----", e);
                        continue;
                    }
                }

                // 控制轮询结束
                if (baseDate.equals(endDate)) {
                    return;
                }
            }
        });
    }

    /**
     * task - create blank record for number analyze
     */
    @Scheduled(cron="5 0 0 * * ?")
//    @Scheduled(cron="0 12 21 * * ?")
    private void createNumberAnalyze() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            try {
                String date = DateUtils.getDate();
                numberAnalyzeMapper.createByDate(date);
            } catch (Exception e) {
                log.info("task - createNumberAnalyze exception ----->>>>", e);
            }
        });
    }

    /**
     * task - repair data
     */
    @Scheduled(cron="0 42 0 15 4 ?")
    private void repairData() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            try {
                String currenDate = DateUtils.getDate();
                NumberAnalyze numberAnalyze = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
                numberAnalyze.setCreateDate(currenDate);
                String data =   "8384278418806227245437672463";
                char[] chars = data.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int number = Integer.valueOf(String.valueOf(chars[i]));
                    numberAnalyze(numberAnalyze, number);
                    continue;
                }

                numberAnalyzeMapper.updateNumberAnalyze(numberAnalyze);

            } catch (Exception e) {
                log.info("task - repairData exception ----->>>>", e);
            }
        });
    }

    /**
     * task - calculate real-time lottery data
     */
    @Scheduled(cron="0/15 * * * * ?")
//    @Scheduled(fixedDelay = 1000L)
//    @Scheduled(cron="0 19 22 13 4 ?")
    private void lotteryDataCalculate() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            try {
                String currenDate = DateUtils.getDate();
                String url = "https://api.api68.com/CQShiCai/getBaseCQShiCai.do?lotCode=10036";
                String lottery = HttpUtils.commonGet(url);
                if (StringUtils.isBlank(lottery)) {
                    return;
                }
                JSONObject jsonObject = JSONObject.parseObject(lottery);
                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject data = result.getJSONObject("data");
                System.out.println(data);
                int preDrawIssue = data.getIntValue("preDrawIssue");

                // determine:whether this data has been analyzed
                LotteryData lotteryDataDB = lotteryDataMapper.selectLotteryDataByPreDrawIssue(preDrawIssue);
                if (lotteryDataDB != null) {
                    return;
                }

                String preDrawTime = data.getString("preDrawTime");
                String preDrawCode = data.getString("preDrawCode").replace(",", "");
                int sumNum = data.getIntValue("sumNum");
                int sumSingleDouble = data.getIntValue("sumSingleDouble");
                int sumBigSmall = data.getIntValue("sumBigSmall");
                int dragonTiger = data.getIntValue("dragonTiger");
                int firstBigSmall = data.getIntValue("firstBigSmall");
                int firstSingleDouble = data.getIntValue("firstSingleDouble");
                int secondBigSmall = data.getIntValue("secondBigSmall");
                int secondSingleDouble = data.getIntValue("secondSingleDouble");
                int thirdBigSmall = data.getIntValue("thirdBigSmall");
                int thirdSingleDouble = data.getIntValue("thirdSingleDouble");
                int fourthBigSmall = data.getIntValue("fourthBigSmall");
                int fourthSingleDouble = data.getIntValue("fourthSingleDouble");
                int fifthBigSmall = data.getIntValue("fifthBigSmall");
                int fifthSingleDouble = data.getIntValue("fifthSingleDouble");
                int behindThree = data.getIntValue("behindThree");
                int betweenThree = data.getIntValue("betweenThree");
                int lastThree = data.getIntValue("lastThree");
                int groupCode = data.getIntValue("groupCode");

                LotteryData lotteryData = new LotteryData(preDrawIssue, preDrawTime, preDrawCode, sumNum, sumSingleDouble
                        , sumBigSmall, dragonTiger, firstBigSmall, firstSingleDouble, secondBigSmall, secondSingleDouble
                        , thirdBigSmall, thirdSingleDouble, fourthBigSmall, fourthSingleDouble, fifthBigSmall
                        , fifthSingleDouble, behindThree, betweenThree, lastThree, groupCode);
                lotteryDataMapper.insertLotteryData(lotteryData);

                NumberAnalyze numberAnalyze = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
                numberAnalyze.setCreateDate(currenDate);
                char[] chars = preDrawCode.toCharArray();
                PredictNumber predictNumber = predictNumberMapper.selectByPeriod(String.valueOf(preDrawIssue));
                for (int i = 0; i < lengthControlSingle; i++) {
                    char var = chars[i];
                    int number = Integer.parseInt(String.valueOf(var));

                    // number analyze
                    numberAnalyze(numberAnalyze, number);

                    if (predictNumber != null) {
                        // check predict number
                        checkPredictNumber(i, var, predictNumber);
                    }
                }

                if (predictNumber != null) {
                    predictNumberMapper.updatePredictNumber(predictNumber);
                }

                int update = numberAnalyzeMapper.updateNumberAnalyze(numberAnalyze);
                if (update > 0) {
                    int preDrawIssueNew = preDrawIssue + 1;
                    // by最近一小时数据预测号码
                    /*List<Map.Entry<String, Integer>> list1 = lotteryService.getNumbers(1);
                    String number1 = list1.get(0).getKey().toString() + list1.get(1).getKey() + list1.get(2).getKey() + list1.get(3).getKey();
                    log.error("一小时数据预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + number1);

                    // by最近两小时数据预测号码
                    List<Map.Entry<String, Integer>> list2 = lotteryService.getNumbers(2);
                    String number2 = list2.get(0).getKey().toString() + list2.get(1).getKey() + list2.get(2).getKey() + list2.get(3).getKey();
                    log.error("两小时数据预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + number2);

                    // by最近三小时数据预测号码
                    List<Map.Entry<String, Integer>> list3 = lotteryService.getNumbers(3);
                    String number3 = list3.get(0).getKey().toString() + list3.get(1).getKey() + list3.get(2).getKey() + list3.get(3).getKey();
                    log.error("三小时数据预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + number3);

                    // by最近四小时数据预测号码
                    List<Map.Entry<String, Integer>> list4 = lotteryService.getNumbers(4);
                    String number4 = list4.get(0).getKey().toString() + list4.get(1).getKey() + list4.get(2).getKey() + list4.get(3).getKey();
                    log.error("四小时数据预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + number4);*/

                    PredictNumber predictNumberNew = new PredictNumber();
                    predictNumberNew.setPeriod(String.valueOf(preDrawIssueNew));

                    // by最近10注数据竖向预测号码
                    List<String> ten = lotteryService.getNumbers2(10);
                    log.error("近10注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + ten);
                    predictNumberNew.setTen(ten.get(0) + "," + ten.get(1) + "," + ten.get(2) + "," + ten.get(3) + "," + ten.get(4));

                    // by最近20注数据竖向预测号码
                    List<String> twenty = lotteryService.getNumbers2(20);
                    log.error("近20注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + twenty);
                    predictNumberNew.setTwenty(twenty.get(0) + "," + twenty.get(1) + "," + twenty.get(2) + "," + twenty.get(3) + "," + twenty.get(4));

                    // by最近30注数据竖向预测号码
                    List<String> thirty = lotteryService.getNumbers2(30);
                    log.error("近30注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + thirty);
                    predictNumberNew.setThirty(thirty.get(0) + "," + thirty.get(1) + "," + thirty.get(2) + "," + thirty.get(3) + "," + thirty.get(4));

                    // by最近40注数据竖向预测号码
                    List<String> forty = lotteryService.getNumbers2(40);
                    log.error("近40注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + forty);
                    predictNumberNew.setForty(forty.get(0) + "," + forty.get(1) + "," + forty.get(2) + "," + forty.get(3) + "," + forty.get(4));

                    // by最近50注数据竖向预测号码
                    List<String> fifty = lotteryService.getNumbers2(50);
                    log.error("近50注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + fifty);
                    predictNumberNew.setFifty(fifty.get(0) + "," + fifty.get(1) + "," + fifty.get(2) + "," + fifty.get(3) + "," + fifty.get(4));

                    // by最近60注数据竖向预测号码
                    List<String> sixty = lotteryService.getNumbers2(60);
                    log.error("近60注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + sixty);
                    predictNumberNew.setSixty(sixty.get(0) + "," + sixty.get(1) + "," + sixty.get(2) + "," + sixty.get(3) + "," + sixty.get(4));

                    // by最近70注数据竖向预测号码
                    List<String> seventy = lotteryService.getNumbers2(70);
                    log.error("近70注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + seventy);
                    predictNumberNew.setSeventy(seventy.get(0) + "," + seventy.get(1) + "," + seventy.get(2) + "," + seventy.get(3) + "," + seventy.get(4));

                    // by最近80注数据竖向预测号码
                    List<String> eighty = lotteryService.getNumbers2(80);
                    log.error("近80注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + eighty);
                    predictNumberNew.setEighty(eighty.get(0) + "," + eighty.get(1) + "," + eighty.get(2) + "," + eighty.get(3) + "," + eighty.get(4));

                    // by最近90注数据竖向预测号码
                    List<String> ninety = lotteryService.getNumbers2(90);
                    log.error("近90注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + ninety);
                    predictNumberNew.setNinety(ninety.get(0) + "," + ninety.get(1) + "," + ninety.get(2) + "," + ninety.get(3) + "," + ninety.get(4));

                    // by最近100注数据竖向预测号码
                    List<String> oneHundred = lotteryService.getNumbers2(100);
                    log.error("近100注数据竖向预测号码 ---- @期数：" + preDrawIssueNew + ", 预测号码：" + oneHundred);
                    predictNumberNew.setOneHundred(oneHundred.get(0) + "," + oneHundred.get(1) + "," + oneHundred.get(2) + "," + oneHundred.get(3) + "," + oneHundred.get(4));

                    predictNumberMapper.insertPredictNumber(predictNumberNew);

                }

            } catch (Exception e) {
                log.info("task - lotteryDataCalculate exception ----->>>>", e);
            }
        });
    }

    /**
     * 结果验证
     * @param i
     * @param var
     * @param predictNumber
     */
    private void checkPredictNumber(int i, char var, PredictNumber predictNumber) {
        // analyze ten predict data
        String ten = predictNumber.getTen();
        // 单赛道验证标识
        boolean tenCheckSingle = false;
        // get all column data
        String[] splitTen = ten.split(",");
        // get column data
        String columnTen = splitTen[i];
        char[] columnCharTen = columnTen.toCharArray();
        for (int k = 0; k < columnCharTen.length; k++) {
            char code = columnCharTen[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setTenCheck(predictNumber.getTenCheck() + 1);
                tenCheckSingle = true;
            }
        }
        if (tenCheckSingle) {
            predictNumber.setTenCheckSingle(predictNumber.getTenCheckSingle() + "是");
        } else {
            predictNumber.setTenCheckSingle(predictNumber.getTenCheckSingle() + "否");
        }

        // analyze twenty predict data
        String twenty = predictNumber.getTwenty();
        boolean twentyCheckSingle = false;
        // get all column data
        String[] splitTwenty = twenty.split(",");
        // get column data
        String columnTwenty = splitTwenty[i];
        char[] columnCharTwenty = columnTwenty.toCharArray();
        for (int k = 0; k < columnCharTwenty.length; k++) {
            char code = columnCharTwenty[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setTwentyCheck(predictNumber.getTwentyCheck() + 1);
                twentyCheckSingle = true;
            }
        }
        if (twentyCheckSingle) {
            predictNumber.setTwentyCheckSingle(predictNumber.getTwentyCheckSingle() + "是");
        } else {
            predictNumber.setTwentyCheckSingle(predictNumber.getTwentyCheckSingle() + "否");
        }

        // analyze twenty predict data
        String thirty = predictNumber.getThirty();
        boolean thirtyCheckSingle = false;
        // get all column data
        String[] splitThirty = thirty.split(",");
        // get column data
        String columnThirty = splitThirty[i];
        char[] columnCharThirty = columnThirty.toCharArray();
        for (int k = 0; k < columnCharThirty.length; k++) {
            char code = columnCharThirty[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setThirtyCheck(predictNumber.getThirtyCheck() + 1);
                thirtyCheckSingle = true;
            }
        }
        if (thirtyCheckSingle) {
            predictNumber.setThirtyCheckSingle(predictNumber.getThirtyCheckSingle() + "是");
        } else {
            predictNumber.setThirtyCheckSingle(predictNumber.getThirtyCheckSingle() + "否");
        }

        // analyze twenty predict data
        String forty = predictNumber.getForty();
        boolean fortyCheckSingle = false;
        // get all column data
        String[] splitForty = forty.split(",");
        // get column data
        String columnForty = splitForty[i];
        char[] columnCharForty = columnForty.toCharArray();
        for (int k = 0; k < columnCharForty.length; k++) {
            char code = columnCharForty[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setFortyCheck(predictNumber.getFortyCheck() + 1);
                fortyCheckSingle = true;
            }
        }
        if (fortyCheckSingle) {
            predictNumber.setFortyCheckSingle(predictNumber.getFortyCheckSingle() + "是");
        } else {
            predictNumber.setFortyCheckSingle(predictNumber.getFortyCheckSingle() + "否");
        }

        // analyze fifty predict data
        String fifty = predictNumber.getFifty();
        boolean fiftyCheckSingle = false;
        // get all column data
        String[] splitFifty = fifty.split(",");
        // get column data
        String columnFifty = splitFifty[i];
        char[] columnCharFifty = columnFifty.toCharArray();
        for (int k = 0; k < columnCharFifty.length; k++) {
            char code = columnCharFifty[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setFiftyCheck(predictNumber.getFiftyCheck() + 1);
                fiftyCheckSingle = true;
            }
        }
        if (fiftyCheckSingle) {
            predictNumber.setFiftyCheckSingle(predictNumber.getFiftyCheckSingle() + "是");
        } else {
            predictNumber.setFiftyCheckSingle(predictNumber.getFiftyCheckSingle() + "否");
        }

        // analyze sixty predict data
        String sixty = predictNumber.getSixty();
        boolean sixtyCheckSingle = false;
        // get all column data
        String[] splitSixty = sixty.split(",");
        // get column data
        String columnSixty = splitSixty[i];
        char[] columnCharSixty = columnSixty.toCharArray();
        for (int k = 0; k < columnCharSixty.length; k++) {
            char code = columnCharSixty[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setSixtyCheck(predictNumber.getSixtyCheck() + 1);
                sixtyCheckSingle = true;
            }
        }
        if (sixtyCheckSingle) {
            predictNumber.setSixtyCheckSingle(predictNumber.getSixtyCheckSingle() + "是");
        } else {
            predictNumber.setSixtyCheckSingle(predictNumber.getSixtyCheckSingle() + "否");
        }

        // analyze seventy predict data
        String seventy = predictNumber.getSeventy();
        boolean seventyCheckSingle = false;
        // get all column data
        String[] splitSeventy = seventy.split(",");
        // get column data
        String columnSeventy = splitSeventy[i];
        char[] columnCharSeventy = columnSeventy.toCharArray();
        for (int k = 0; k < columnCharSeventy.length; k++) {
            char code = columnCharSeventy[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setSeventyCheck(predictNumber.getSeventyCheck() + 1);
                seventyCheckSingle = true;
            }
        }
        if (seventyCheckSingle) {
            predictNumber.setSeventyCheckSingle(predictNumber.getSeventyCheckSingle() + "是");
        } else {
            predictNumber.setSeventyCheckSingle(predictNumber.getSeventyCheckSingle() + "否");
        }

        // analyze eighty predict data
        String eighty = predictNumber.getEighty();
        boolean eightyCheckSingle = false;
        // get all column data
        String[] splitEighty = eighty.split(",");
        // get column data
        String columnEighty = splitEighty[i];
        char[] columnCharEighty = columnEighty.toCharArray();
        for (int k = 0; k < columnCharEighty.length; k++) {
            char code = columnCharEighty[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setEightyCheck(predictNumber.getEightyCheck() + 1);
                eightyCheckSingle = true;
            }
        }
        if (eightyCheckSingle) {
            predictNumber.setEightyCheckSingle(predictNumber.getEightyCheckSingle() + "是");
        } else {
            predictNumber.setEightyCheckSingle(predictNumber.getEightyCheckSingle() + "否");
        }

        // analyze ninety predict data
        String ninety = predictNumber.getNinety();
        boolean ninetyCheckSingle = false;
        // get all column data
        String[] splitNinety = ninety.split(",");
        // get column data
        String columnNinety = splitNinety[i];
        char[] columnCharNinety = columnNinety.toCharArray();
        for (int k = 0; k < columnCharNinety.length; k++) {
            char code = columnCharNinety[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setNinetyCheck(predictNumber.getNinetyCheck() + 1);
                ninetyCheckSingle = true;
            }
        }
        if (ninetyCheckSingle) {
            predictNumber.setNinetyCheckSingle(predictNumber.getNinetyCheckSingle() + "是");
        } else {
            predictNumber.setNinetyCheckSingle(predictNumber.getNinetyCheckSingle() + "否");
        }

        // analyze oneHundred predict data
        String oneHundred = predictNumber.getOneHundred();
        boolean oneHundredCheckSingle = false;
        // get all column data
        String[] splitOneHundred = oneHundred.split(",");
        // get column data
        String columnOneHundred = splitOneHundred[i];
        char[] columnCharOneHundred = columnOneHundred.toCharArray();
        for (int k = 0; k < columnCharOneHundred.length; k++) {
            char code = columnCharOneHundred[k];
            if (String.valueOf(code).equals(String.valueOf(var))) {
                predictNumber.setOneHundredCheck(predictNumber.getOneHundredCheck() + 1);
                oneHundredCheckSingle = true;
            }
        }
        if (oneHundredCheckSingle) {
            predictNumber.setOneHundredCheckSingle(predictNumber.getOneHundredCheckSingle() + "是");
        } else {
            predictNumber.setOneHundredCheckSingle(predictNumber.getOneHundredCheckSingle() + "否");
        }
    }

    private void numberAnalyze(NumberAnalyze numberAnalyze, int number) {
        switch (number) {
            case 0:
                numberAnalyze.setZero(numberAnalyze.getZero() + 1);
                return;
            case 1:
                numberAnalyze.setOne(numberAnalyze.getOne() + 1);
                return;
            case 2:
                numberAnalyze.setTwo(numberAnalyze.getTwo() + 1);
                return;
            case 3:
                numberAnalyze.setThree(numberAnalyze.getThree() + 1);
                return;
            case 4:
                numberAnalyze.setFour(numberAnalyze.getFour() + 1);
                return;
            case 5:
                numberAnalyze.setFive(numberAnalyze.getFive() + 1);
                return;
            case 6:
                numberAnalyze.setSix(numberAnalyze.getSix() + 1);
                return;
            case 7:
                numberAnalyze.setSeven(numberAnalyze.getSeven() + 1);
                return;
            case 8:
                numberAnalyze.setEight(numberAnalyze.getEight() + 1);
                return;
            case 9:
                numberAnalyze.setNine(numberAnalyze.getNine() + 1);
                return;
            default:
                return;
        }
    }

    public static void main(String[] args) {

    }

}

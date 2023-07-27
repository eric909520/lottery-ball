package com.backend.common.utils;

import com.backend.common.utils.http.HttpUtils;
import com.backend.project.system.domain.AgApi;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author
 */
@Slf4j
public class AgApiUtils {

    /**
     * 比赛列表
     * @param agApi
     * @return
     */
    public static String get_match_list(AgApi agApi) {
        try {
            String url = agApi.getApiLink();
            HashMap<String, String> headerMap = new HashMap<>();
            headerMap.put("Token", agApi.getToken());
            headerMap.put("Uid", agApi.getUId());
            String json = "{\n" +
                    "  \"SportType\": " + agApi.getSportType() + "," +
                    "  \"MarketType\": " + agApi.getMarketType() + "," +
                    "  \"PageIndex\": " + agApi.getPageIndex() + "," +
                    "  \"Uid\": " + agApi.getUId() + "," +
                    "  \"Seq\": 10027" +
                    "}";
            String data = HttpUtils.doPostJson(url, headerMap, json);
            return data;
        } catch (Exception e) {
            log.info("get_match_list exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 比赛列表
     * @param agApi
     * @return
     */
    public static String get_match_info(AgApi agApi) {
        try {
            String url = agApi.getApiLink();
            HashMap<String, String> headerMap = new HashMap<>();
            headerMap.put("Token", agApi.getToken());
            headerMap.put("Uid", agApi.getUId());
            String json = "{\n" +
                    "  \"SportType\": " + agApi.getSportType() +
                    "  \"MarketType\": " + agApi.getMarketType() +
                    "  \"PageIndex\": " + agApi.getPageIndex() +
                    "  \"Uid\": " + agApi.getUId() +
                    "  \"Seq\": 10017\n" +
                    "}";
            String data = HttpUtils.doPostJson(url, headerMap, json);
            return data;
        } catch (Exception e) {
            log.info("get_match_info exception ----->>>>", e);
        }
        return null;
    }

}

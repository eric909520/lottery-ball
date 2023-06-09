package com.backend.common.utils;

import com.backend.common.utils.http.HttpUtils;
import com.backend.project.system.domain.HgApi;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author
 */
@Slf4j
public class HgApiUtils {

    /**
     * 今日足球-联赛列表
     * @param hgApi
     * @return
     */
    public static String today_get_league_list_All(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getP());
            paramMap.put("uid", hgApi.getUId());
//            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("gtype", "FT");
            paramMap.put("FS", "N");
            paramMap.put("showtype", "ft");
            paramMap.put("date", "0");
            paramMap.put("ts", hgApi.getTs());
            paramMap.put("nocp", "N");
            String data = HttpUtils.doPostForm(url, paramMap);
            return data;
        } catch (Exception e) {
            log.info("get_league_list_All exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 今日足球-联赛下属比赛列表
     * @param hgApi
     * @return
     */
    public static String today_get_game_list(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getP());
            paramMap.put("uid", hgApi.getUId());
//            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("p3type", "");
            paramMap.put("date", "0");
            paramMap.put("gtype", "ft");
            paramMap.put("showtype", "today");
            paramMap.put("rtype", "r");
            paramMap.put("ltype", "3");
            paramMap.put("lid", hgApi.getLid());
            paramMap.put("action", "click_league");
            paramMap.put("sorttype", "L");
            paramMap.put("specialClick", "");
            paramMap.put("isFantasy", "N");
            paramMap.put("ts", hgApi.getTs());
            String data = HttpUtils.doPostForm(url, paramMap);
            return data;
        } catch (Exception e) {
            log.info("today_get_game_list exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 今日足球-赔率详细
     */
    public static String today_get_game_more(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getP());
            paramMap.put("uid", hgApi.getUId());
//            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("gtype", "ft");
            paramMap.put("showtype", "today");
            paramMap.put("ltype", "3");
            paramMap.put("isRB", "N");
            paramMap.put("lid", hgApi.getLid());
            paramMap.put("specialClick", "");
            paramMap.put("mode", "");
            paramMap.put("filter", "Main");
            paramMap.put("ts", hgApi.getTs());
            paramMap.put("ecid", hgApi.getEcid());
            String data = HttpUtils.doPostForm(url, paramMap);
            return data;
        } catch (Exception e) {
            log.info("today_get_game_more exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 今日足球-准确赔率
     */
    public static String today_ft_order_view(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getP());
            paramMap.put("uid", hgApi.getUId());
//            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("odd_f_type", "H");
            paramMap.put("gid", hgApi.getGid());
            paramMap.put("gtype", "FT");
            paramMap.put("wtype", hgApi.getWType());
            paramMap.put("chose_team", hgApi.getChoseTeam());
            String data = HttpUtils.doPostForm(url, paramMap);
            return data;
        } catch (Exception e) {
            log.info("today_ft_order_view exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 早盘足球-联赛列表
     * @param hgApi
     * @return
     */
    public static String early_get_league_list_All(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getP());
            paramMap.put("uid", hgApi.getUId());
//            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("gtype", "FT");
            paramMap.put("FS", "N");
            paramMap.put("showtype", "fu");
            paramMap.put("date", "all");
            paramMap.put("ts", hgApi.getTs());
            paramMap.put("nocp", "N");
            String data = HttpUtils.doPostForm(url, paramMap);
            return data;
        } catch (Exception e) {
            log.info("early_get_league_list_All exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 早盘足球-联赛下属比赛列表
     * @param hgApi
     * @return
     */
    public static String early_get_game_list(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getP());
            paramMap.put("uid", hgApi.getUId());
//            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("p3type", "");
            paramMap.put("date", "all");
            paramMap.put("gtype", "ft");
            paramMap.put("showtype", "early");
            paramMap.put("rtype", "r");
            paramMap.put("ltype", "3");
            paramMap.put("lid", hgApi.getLid());
            paramMap.put("action", "click_league");
            paramMap.put("sorttype", "L");
            paramMap.put("specialClick", "");
            paramMap.put("isFantasy", "N");
            paramMap.put("ts", hgApi.getTs());
            String data = HttpUtils.doPostForm(url, paramMap);
            return data;
        } catch (Exception e) {
            log.info("early_today_get_game_list exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 早盘足球-赔率详细
     */
    public static String early_get_game_more(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getP());
            paramMap.put("uid", hgApi.getUId());
//            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("gtype", "ft");
            paramMap.put("showtype", "early");
            paramMap.put("ltype", "3");
            paramMap.put("isRB", "N");
            paramMap.put("lid", hgApi.getLid());
            paramMap.put("specialClick", "");
            paramMap.put("mode", "");
            paramMap.put("filter", "Main");
            paramMap.put("ts", hgApi.getTs());
            paramMap.put("ecid", hgApi.getEcid());
            String data = HttpUtils.doPostForm(url, paramMap);
            return data;
        } catch (Exception e) {
            log.info("early_today_get_game_more exception ----->>>>", e);
        }
        return null;
    }

    public static void main6(String[] args) {
//            HashMap<String, String> headerMap = new HashMap<>();
//            headerMap.put("Content-type", "application/x-www-form-urlencoded");
//            headerMap.put("Host", "www.mos022.com");
//            headerMap.put("Origin", "https://www.mos022.com");
//            headerMap.put("Referer", "https://www.mos033.com");
//            headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
//            headerMap.put("Accept", "*/*");
//            headerMap.put("Accept-Encoding", "gzip, deflate, br");
//            headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
//            headerMap.put("Connection", "keep-alive");
//            headerMap.put("Cookie", "CookieChk=WQ; box4pwd_notshow_31216544=MzEyMTY1NDRfTg==; myGameVer_31216544=XzIxMTIyOA==; ft_myGame_31216544=e30=; box4pwd_notshow_31222858=MzEyMjI4NThfTg==; myGameVer_31222858=XzIxMTIyOA==; announcement_31222858_202209=MzEyMjI4NThfTg==; ft_myGame_31222858=e30=; Gen_cookie_31222858=MjE4MzNfMzEyMjI4NTg=; Imp_cookie_31222858=MjI1Nl8zMTIyMjg1OA==; test=aW5pdA; op_myGame_31222858=e30=; bk_myGame_31222858=e30=; announcement_31216544_202209=MzEyMTY1NDRfTg==; protocolstr=aHR0cHM=; odd_f_type_31222858=RQ==; now_passcode=W2RlbF0=; login_31222858=MTY4ODM2MTU3OQ; loginuser=UXVhblNoYW4wMDE=; login_31216544=MTY4ODM2NDM0OQ; cu=Tg==; cuipv6=Tg==; ipv6=Tg==");
//            headerMap.put("sec-ch-ua", "\"Google Chrome\";v=\"107\", \"Chromium\";v=\"107\", \"Not=A?Brand\";v=\"24\"");
//            headerMap.put("sec-ch-ua-mobile", "?0");
//            headerMap.put("sec-ch-ua-platform", "Windows");
//            headerMap.put("Sec-Fetch-Dest", "empty");
//            headerMap.put("Sec-Fetch-Mode", "cors");
//            headerMap.put("Sec-Fetch-Site", "same-origin");
    }
}

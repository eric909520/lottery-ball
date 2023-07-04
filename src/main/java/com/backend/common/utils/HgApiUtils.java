package com.backend.common.utils;

import com.backend.common.utils.http.HttpUtils;
import com.backend.project.system.domain.HgApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author
 */
@Slf4j
public class HgApiUtils {

    private static RequestConfig requestConfig;

    /**
     * 今日足球赛事
     * @param hgApi
     * @return
     */
    public static String get_league_list_All(HgApi hgApi) {
        try {
            String url = hgApi.getApiLink();
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", hgApi.getApiLink());
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
            log.info("doPostForm exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 赛事下属球赛列表
     * @param hgApi
     * @return
     */
    public static String get_game_list(HgApi hgApi) {
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
            log.info("doPostForm exception ----->>>>", e);
        }
        return null;
    }

    /**
     * 球赛详细
     */
    public static String get_game_more(HgApi hgApi) {
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
            paramMap.put("filter", "");
            paramMap.put("ts", hgApi.getTs());
            paramMap.put("ecid", hgApi.getEcid());
            String data = HttpUtils.doPostForm(url, paramMap);

            Document doc = DocumentHelper.parseText(data);
            Element rootElt = doc.getRootElement();
            Iterator gameIt = rootElt.elementIterator("game");
            while (gameIt.hasNext()) {
                Element game = (Element) gameIt.next();
                String gid = game.elementTextTrim("gid");
                if (!gid.equals("6214197")) {
                    continue;
                }
                String team_h = game.elementTextTrim("team_h");
                String team_c = game.elementTextTrim("team_c");
                String ratio_ouho = game.elementTextTrim("ratio_ouho");
                String ior_OUHO = game.elementTextTrim("ior_OUHO");
                System.out.println(gid);
                System.out.println(team_h);
                System.out.println(team_c);
                System.out.println(ratio_ouho);
                System.out.println(ior_OUHO);
            }
            return data;
        } catch (Exception e) {
            log.info("doPostForm exception ----->>>>", e);
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

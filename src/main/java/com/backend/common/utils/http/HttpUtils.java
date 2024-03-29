package com.backend.common.utils.http;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 通用http发送方法
 *
 * @author
 */
@Slf4j
public class HttpUtils {

    private static RequestConfig requestConfig;

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String commonGet(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
//            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url, e);
            Sentry.captureException(e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + e.getMessage());
            Sentry.captureException(e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + url, e);
            Sentry.captureException(e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url, e);
            Sentry.captureException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url, ex);
                Sentry.captureException(ex);
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
            Sentry.captureException(e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param + e.getMessage());
            Sentry.captureException(e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
            Sentry.captureException(e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
            Sentry.captureException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
                Sentry.captureException(ex);
            }
        }
        return result.toString();
    }

    public static String sendGet1(String url, String param) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = null;
        HttpResponse response = null;
        String apiUrl = url + "?" + param;
        log.info("@requestUrl -- " + apiUrl);
        String result = null;
        try {
            HttpEntity entity = null;
            httpGet = new HttpGet(apiUrl);
            httpGet = setHeadersToGet(httpGet, null);
            response = httpClient.execute(httpGet);
            if (response != null) {
                entity = response.getEntity();
            }

            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
        } catch (IOException e) {
            log.info("httpGet Exception ---->>>", e);
            Sentry.captureException(e);
        }
        log.info("@responseData -- " + result);
        return result;
    }

    /**
     * 填充请求头参数至get请求中
     *
     * @param httpPost
     * @param headers
     * @return
     */
    private static HttpGet setHeadersToGet(HttpGet httpPost, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
        }
        return httpPost;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            log.info("sendPost - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Sentry.captureException(ex);
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    public static String sendPostPay(String sendUrl, String xml) throws IOException {
        HttpURLConnection con = null;
        InputStream iStream = null;
        OutputStream oStream = null;
        try {
            URL url = new URL(sendUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            oStream = con.getOutputStream();
            oStream.write(xml.getBytes());

            iStream = con.getInputStream();
            Reader reader = new InputStreamReader(iStream);

            int tempChar;
            String result = new String();
            while ((tempChar = reader.read()) != -1) {
                result += (char) tempChar;
            }
            return result;
        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
        } finally {
            if (iStream != null) iStream.close();
            if (oStream != null) oStream.close();
            if (con != null) con.disconnect();
        }
        return null;
    }

    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try {
            log.info("sendSSLPost - {}", urlNameString);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (ret != null && !ret.trim().equals("")) {
                    result.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
                }
            }
            log.info("recv - {}", result);
            conn.disconnect();
            br.close();
        } catch (ConnectException e) {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param, e);
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String contentType)
    {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try
        {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;
            while ((line = in.readLine()) != null)
            {
                result.append(line);
            }
            log.info("recv - {}", result);
        }
        catch (ConnectException e)
        {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
        }
        catch (SocketTimeoutException e)
        {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        }
        catch (IOException e)
        {
            Sentry.captureException(e);
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        }
        catch (Exception e)
        {
            Sentry.captureException(e);
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception ex)
            {
                Sentry.captureException(ex);
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    public static String doPostForm(String url, HashMap<String, String> paramMap) throws Exception {
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        /*Iterator<Map.Entry<String, String>> headerIt = headerMap.entrySet().iterator();
        while (headerIt.hasNext()) {
            Map.Entry<String, String> header = headerIt.next();
            httpPost.setHeader(header.getKey(), header.getValue());
        }*/

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            builder.addTextBody(entry.getKey(), entry.getValue());
        }

        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception e) {
            log.info("", e);
            throw new RuntimeException("请求异常---" + e);
        }
        return result;
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param url     接口地址
     * @param headers 请求头参数
     * @param content 参数
     * @return
     */
    public static String doPostJson1(String url, HashMap<String, String> headers, String content) throws Exception {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
                    new java.security.SecureRandom());

            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setDoOutput(true);
            // 设置请求头
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
            conn.setRequestMethod("POST");
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(content.getBytes("UTF8"));
            // 刷新、关闭
            out.flush();
            out.close();
            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                return outStream.toString();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("doPostJson 请求异常---" + e);
        }
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param link     接口地址
     * @param headers 请求头参数
     * @param content 参数
     * @return
     */
    public static String doPostJson(String link, HashMap<String, String> headers, String content) throws Exception {
        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            for (String key : headers.keySet()) {
                con.setRequestProperty(key, headers.get(key));
            }
            con.setDoOutput(true);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = content.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("doPostJson 请求异常---" + e);
        }
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static void main(String[] args) {
        String url = "https://svr.hahrsp.com/sport/get_match_list";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTA1Mzk4MjYsImlzcyI6IkJsYWRlTWFzdGVyIiwibmJmIjoxNjkwNDUzNDE2LCJ1aWQiOjk2Mzg4Nywic2VlZCI6IndwcWtycnQ2cDZmYTQwbmcifQ.dIkYs9EfhpZyof4Ii7g4aJgsjcmlHfFtWmCBpOYwjl8");
        headers.put("Uid", "963887");
        String json = "{\n" +
                "  \"SportType\": 1,\n" +
                "  \"MarketType\": 3,\n" +
                "  \"PageIndex\": 5,\n" +
                "  \"Uid\": 963887,\n" +
                "  \"Seq\": 10027\n" +
                "}";
        String s = null;
        try {
            s = doPostJson(url, headers, json);
        } catch (Exception e) {
            log.info("doPostJson exception ----->>>>", e);
        }
        System.out.println(s);
    }

    public static void main1(String[] args) {
        try {
            String url = "https://www.mos022.com/transform.php";

            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", "get_league_list_All");
            paramMap.put("uid", "7qkd16y5m31216544l115184b0");
            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("gtype", "FT");
            paramMap.put("FS", "N");
            paramMap.put("showtype", "ft");
            paramMap.put("date", "0");
            paramMap.put("ts", "1688364421939");
            paramMap.put("nocp", "N");
            String data = doPostForm(url, paramMap);
//            System.out.println(data);


            Document doc = DocumentHelper.parseText(data); // 将字符串转为XML
            Element rootElt = doc.getRootElement();
            Element classifier = rootElt.element("classifier");
            Iterator regionIt = classifier.elementIterator("region");
            while (regionIt.hasNext()) {
                Element region = (Element)regionIt.next();
                String regionName = region.attributeValue("name");
                System.out.println(regionName);
                Iterator leagueIt = region.elementIterator("league");
                while (leagueIt.hasNext()) {
                    Element league = (Element)leagueIt.next();
                    String leagueName = league.attributeValue("name");
                    String leagueId = league.attributeValue("id");
                    String leagueTSort = league.attributeValue("t_sort");
                    System.out.println(leagueName);
                    System.out.println(leagueId);
                    System.out.println(leagueTSort);
                }
            }


        } catch (Exception e) {
            log.info("doPostForm exception ----->>>>", e);
        }
    }

    public static void main2(String[] args) {
        try {
            String url = "https://www.mos022.com/transform.php";

            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", "get_game_list");
            paramMap.put("uid", "7qkd16y5m31216544l115184b0");
            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("p3type", "");
            paramMap.put("date", "0");
            paramMap.put("gtype", "ft");
            paramMap.put("showtype", "today");
            paramMap.put("rtype", "r");
            paramMap.put("ltype", "3");
            paramMap.put("lid", "104841");
            paramMap.put("action", "click_league");
            paramMap.put("sorttype", "L");
            paramMap.put("specialClick", "");
            paramMap.put("isFantasy", "N");
            paramMap.put("ts", "1688405870811");
            String data = doPostForm(url, paramMap);
//            System.out.println(data);


            Document doc = DocumentHelper.parseText(data); // 将字符串转为XML
            Element rootElt = doc.getRootElement();
            Iterator ecIt = rootElt.elementIterator("ec");
            while (ecIt.hasNext()) {
                Element ec = (Element)ecIt.next();
                Element game = ec.element("game");
                String team_h = game.elementTextTrim("TEAM_H");
                String team_c = game.elementTextTrim("TEAM_C");
                String ecid = game.elementTextTrim("ECID");
                System.out.println(team_h);
                System.out.println(team_c);
                System.out.println(ecid);
            }
        } catch (Exception e) {
            log.info("doPostForm exception ----->>>>", e);
        }
    }

    public static void main3(String[] args) {
        try {
            String url = "https://www.mos022.com/transform.php";

            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("p", "get_game_more");
            paramMap.put("uid", "7qkd16y5m31216544l115184b0");
            paramMap.put("ver", "2023-06-26-unbanner-1166");
            paramMap.put("langx", "zh-cn");
            paramMap.put("gtype", "ft");
            paramMap.put("showtype", "today");
            paramMap.put("ltype", "3");
            paramMap.put("isRB", "N");
            paramMap.put("lid", "104841");
            paramMap.put("specialClick", "");
            paramMap.put("mode", "");
            paramMap.put("filter", "");
            paramMap.put("ts", "1688405870811");
            paramMap.put("ecid", "7184623");
            String data = doPostForm(url, paramMap);
//            System.out.println(data);


            Document doc = DocumentHelper.parseText(data); // 将字符串转为XML
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
        } catch (Exception e) {
            log.info("doPostForm exception ----->>>>", e);
        }
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

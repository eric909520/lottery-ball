package com.backend.project.system.fiter;

import com.alibaba.fastjson.JSONObject;
import com.backend.common.utils.StringUtils;
import com.backend.common.utils.security.AesUtil;
import com.backend.common.utils.sign.RSASignature;
import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DecodeFilter implements Filter {

    Logger log = LoggerFactory.getLogger(DecodeFilter.class);

    private String[] checkSignMethod = new String[]{

    };

    private String[] noCheckDir = new String[]{//无需验证路径
            "/genCode/",
            "/failMessageConsume",
            "/download",
            "/system/open/api/auth",
            "/system/open/api/getPreOrder",
            "/system/open/api/orderConfirm",
            "/ws/*",
            "/system/open/api/ball/betCheck",
    };

    private String[] noCheckMethod = new String[]{//无需验证方法
            "avatar",
            "image",
            "treeselect",
            "batchGenCode",
            "genCode",
            "encryption",
            "decryption",
            "upload",
            "download",
            "/system/open/api/auth",
            "/system/open/api/getPreOrder",
            "/system/open/api/orderConfirm",
            "/ws/*",
            "/system/open/api/ball/betCheck",
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("content-type", "application/json;charset=UTF-8");
        String uri = request.getRequestURI().toString().toLowerCase();
        log.info("请求路径:{},请求方式:{}", uri, request.getMethod());
        int length = uri.split("/").length;
        String method = uri.split("/")[length - 1].split("[?]")[0];
        if (!Arrays.asList(noCheckMethod).stream().anyMatch(p -> {
            return p.equalsIgnoreCase(method);
        }) && !Arrays.asList(noCheckDir).stream().anyMatch(p -> uri.contains(p))) {
            if ("OPTIONS".equals(request.getMethod())) {
                return;
            } else if ("POST".equals(request.getMethod())) {
                String sign = request.getParameter("sign");
                String data = request.getParameter("data");
                if (StringUtils.isBlank(data)) {
                    Map paramMap = null;
                    try {
                        paramMap = getDataFromRequest(request);
                    } catch (Exception e) {
                        Sentry.captureException(e);
                        response.getWriter().println("{\n" +
                                "    \"code\": \"400\",\n" +
                                "    \"msg\": \"参数格式错误\"\n" +
                                "}");
                        return;
                    }
                    if (paramMap != null && paramMap.size() > 0) {
                        Object o = paramMap.get("data");
                        if (o != null) {
                            data = o.toString();
                        }
                        Object s = paramMap.get("sign");
                        if (s != null) {
                            sign = s.toString();
                        }
                    }
                }
                log.info("请求参数data:{}", data);
                if (Arrays.asList(checkSignMethod).contains(uri)) {
                    if (StringUtils.isBlank(sign)) {
                        log.info("签名为空 ----->>>>");
                        response.getWriter().println("{\n" +
                                "    \"code\": \"403\",\n" +
                                "    \"msg\": \"验签失败，签名为空\"\n" +
                                "}");
                        return;
                    }
                    // 验签
                    boolean check = RSASignature.doCheck(data, sign, RSASignature.publicKey);
                    if (!check) {
                        response.getWriter().println("{\n" +
                                "    \"code\": \"403\",\n" +
                                "    \"msg\": \"验签失败\"\n" +
                                "}");
                        return;
                    }
                }
                /*if (length > 1 && StringUtils.isNotBlank(data)) {
                    try {
                        data = AesUtil.aesDecryptCbc(data);
                        log.info("解密后的参数：" + data);

                    } catch (Exception e) {
                        Sentry.captureException(e);
                        log.error(e.getMessage());
                        response.getWriter().println("{\n" +
                                "    \"code\": \"400\",\n" +
                                "    \"msg\": \"参数解析错误\"\n" +
                                "}");
                        return;
                    }
                }*/
                if (StringUtils.isBlank(data) || "{}".equals(data.trim()) || "undefined".equals(data.trim())) {
                    //data = "{\"isAsc\": \"asc\",\"orderByColumn\": \"id\"}";
                    //data = "{\"pageNum\": 1,\"pageSize\": 3}";
                    data = "{\"orderByColumn\": \" \"}";
                }
                DecodeParamWrapper wrapper = new DecodeParamWrapper(request, data);
                servletRequest = wrapper;
            }/* else {
                response.getWriter().println("{\n" +
                        "    \"code\": \"400\",\n" +
                        "    \"msg\": \"非POST无效请求\"\n" +
                        "}");
                return;
            }*/
        }
        try {
            filterChain.doFilter(servletRequest, response);
        } catch (Exception e) {
            Sentry.captureException(e);
            response.getWriter().println("{\n" +
                    "    \"code\": \"500\",\n" +
                    "    \"msg\": \"系统错误\"\n" +
                    "}");
        }
    }

    /**
     * 读取request中传过来的字符串
     * 有些调用方不知道用什么方式调用，可能是【application/x-www-form-urlencoded】、【text/plain】、【application/json】
     * 该方法都能处理，但是如果是按【application/x-www-form-urlencoded】
     *
     * @param request
     * @return
     * @throws IOException
     */
    private Map<String, Object> getDataFromRequest(HttpServletRequest request) throws Exception {
        String type = request.getContentType();
        Map<String, Object> receiveMap = new HashMap<String, Object>();
        if ("application/x-www-form-urlencoded".equals(type)) {
            Enumeration<String> enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                String key = String.valueOf(enu.nextElement());
                String value = request.getParameter(key);
                receiveMap.put(key, value);
            }
        } else {    //else是text/plain、application/json这两种情况
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                if (sb != null && sb.length() > 0) {
                    //log.info("application/json数据："+JSONObject.toJSONString(sb.toString()));
                    receiveMap = (Map<String, Object>) JSONObject.parse(sb.toString());
                }
            } catch (IOException e) {
                Sentry.captureException(e);
                e.printStackTrace();
            } finally {
                try {
                    if (null != reader) {
                        reader.close();
                    }
                } catch (IOException e) {
                    Sentry.captureException(e);
                    e.printStackTrace();
                }
            }
        }
        return receiveMap;
    }

}
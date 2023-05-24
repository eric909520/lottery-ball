package com.backend.project.system.callback;

import com.backend.common.utils.MD5Util;
import io.sentry.Sentry;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class CallBackUtil {
    /**
     * Description:MD5工具生成token
     * @param value
     * @return
     */
    public static String getMD5Value(String value){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5ValueByteArray = messageDigest.digest(value.getBytes());
            BigInteger bigInteger = new BigInteger(1 , md5ValueByteArray);
            return bigInteger.toString(16).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 生成签名
     * @param map
     * @return
     */
    public static String getSignToken(Map<String, String> map, String signKey) {
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val);
                    }
                }
            }
            result = sb.toString() + "key="+ signKey;
            //进行MD5加密
            result = MD5Util.MD5(result);
        } catch (Exception e) {
            Sentry.captureException(e);
            return null;
        }
        return result;
    }
}

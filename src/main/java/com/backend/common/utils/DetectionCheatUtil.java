package com.backend.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DetectionCheatUtil {


    /**
     * 读取网络图片区分是否篡改过
     * @param url
     * @return false-图片未P过，true-图片P过
     */
    public static Boolean detection(String url) {
        String host = "https://aips.market.alicloudapi.com";
        String path = "/psdect";
        String method = "POST";
        String appcode = "e933167797044a5c93cfdd521abf45b8";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            bodys.put("src", encodeToString(url));
        } catch (IOException e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String result = EntityUtils.toString(response.getEntity());
            log.info("================"+result+"===============");
            if(result == null || result.length() <= 0 || "".equals(result)){
                return false;
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(200 == jsonObject.getInteger("status")){
                if (jsonObject.getDouble("Threshold") > 0.8){
                    return true;
                }
                return false;
            }else {
                return false;
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            e.printStackTrace();
            return false;
        }
    }

    public static String encodeToString(String imagePath) throws IOException {
        URL url = new URL(imagePath);
        String type = StringUtils.substring(imagePath, imagePath.lastIndexOf(".") + 1);
        BufferedImage image = ImageIO.read(url);
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes);
            bos.close();
        } catch (IOException e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }
        return  imageString;
    }

    public static void main(String[] args) {
        System.out.println(DetectionCheatUtil.detection("https://yixiangbao-hk.oss-cn-hongkong.aliyuncs.com/image/202003/Android/0000004dcd1fc7691917da3a8d7520f21583410677359.jpg"));
    }
}

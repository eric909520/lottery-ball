package com.backend.common.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TencentSMSUtil {

    public static Boolean TencentSendSMS(String mobile,Integer code){
        try{

            Credential cred = new Credential("AKIDmPozweFNM5YlmXtHciY7SXMvGRN3sikX", "iIisqb8TZVJvHVaWJrENK3XtHZdUM7fx");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            String params = "{\"PhoneNumberSet\":[\"+86"+mobile+"\"],\"TemplateID\":\"659727\",\"Sign\":\"支付\",\"TemplateParamSet\":[\""+code+"\"],\"SmsSdkAppid\":\"1400323649\"}";
            SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);

            SendSmsResponse resp = client.SendSms(req);
            log.info(SendSmsRequest.toJsonString(resp));
            SendStatus[] sendStatusSet = resp.getSendStatusSet();
            String status = sendStatusSet[0].getCode();
            if("OK".equals(status.toUpperCase())){
                return true;
            }else {
                return false;
            }
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            Sentry.captureException(e);
        }
        return false;
    }

    /*public static void main(String[] args) {
        TencentSendSMS("",123456);
    }*/

}

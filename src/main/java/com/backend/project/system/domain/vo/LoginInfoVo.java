package com.backend.project.system.domain.vo;

import java.io.Serializable;

/**
 * 登录信息vo类
 */
public class LoginInfoVo implements Serializable {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 图片验证码 */
    private String code;

    /** 登录唯一标识 */
    private String uuid;

    /** 谷歌验证码 */
    private String googleCode;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGoogleCode() {
        return googleCode;
    }

    public void setGoogleCode(String googleCode) {
        this.googleCode = googleCode;
    }

    @Override
    public String toString() {
        return "LoginInfoVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", uuid='" + uuid + '\'' +
                ", googleCode='" + googleCode + '\'' +
                '}';
    }
}

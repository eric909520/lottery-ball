package com.backend.project.system.domain.vo;

import java.io.Serializable;

/**
 * 请求对象
 */
public class ReqBody implements Serializable {

    private String sign;

    private Object data;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReqBody{" +
                "sign='" + sign + '\'' +
                ", data=" + data +
                '}';
    }
}

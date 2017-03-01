package com.mockuai.rainbowcenter.core.service.action.erp.entity;

/**
 * Created by lizg on 2016/6/13.
 */
public class ErpCommon {

    private String appkey;

    private String sessionkey;

    private String method;

    private String sign;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

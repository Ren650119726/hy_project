package com.mockuai.appcenter.common.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class AppInfoQTO extends BaseQTO{
    private Long id;
    private String bizCode;
    private String appKey;
    private String mobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

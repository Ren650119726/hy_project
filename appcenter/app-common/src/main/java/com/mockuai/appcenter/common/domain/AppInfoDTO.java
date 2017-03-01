package com.mockuai.appcenter.common.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class AppInfoDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String bizCode;
    private String appKey;
    private String appPwd;
    /**
     * 1代表ios应用，2代表android应用，3代表手机h5应用，11代表PC端的web应用
     */
    private Integer appType;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 应用版本号
     */
    private String appVersion;
    private String appDesc;
    /**
     * 业务管理员名称/昵称
     */
    private String administrator;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 域名
     */
    private String domainName;
    /**
     * 命名空间id
     */
    private Integer namespaceId;
    private Map<String, AppPropertyDTO> appPropertyMap;

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

    public String getAppPwd() {
        return appPwd;
    }

    public void setAppPwd(String appPwd) {
        this.appPwd = appPwd;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(Integer namespaceId) {
        this.namespaceId = namespaceId;
    }

    public Map<String, AppPropertyDTO> getAppPropertyMap() {
        return appPropertyMap;
    }

    public void setAppPropertyMap(Map<String, AppPropertyDTO> appPropertyMap) {
        this.appPropertyMap = appPropertyMap;
    }
}

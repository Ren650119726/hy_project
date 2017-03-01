package com.mockuai.appcenter.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class AppInfoDO {
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
    private String domainName;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    private Long deleteVersion;
    /**
     * 所属名字空间的id，默认为0，代表为未分类；
     * 后续如果需要支持一个企业下面可以分多个应用目录，则可以新增一张app_namespace表来支持，
     * 这里的namespaceId则外联到app_namespace表
     */
    private Integer namespaceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getDeleteVersion() {
        return deleteVersion;
    }

    public void setDeleteVersion(Long deleteVersion) {
        this.deleteVersion = deleteVersion;
    }

    public Integer getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(Integer namespaceId) {
        this.namespaceId = namespaceId;
    }
}

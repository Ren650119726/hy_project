package com.mockuai.usercenter.common.dto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 8/6/15.
 */
public class EnterpriseAuthExtendDTO implements Serializable{
    private Long id;
    private String bizCode;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 营业执照注册号
     */
    private String licenseRegNo;
    /**
     * 营业执照开始时间
     */
    private String licenseStartTime;
    /**
     * 营业执照结束时间
     */
    private String licenseEndTime;
    /**
     * 注册资本，单位为分
     */
    private Long regCapital;
    /**
     * 经营范围
     */
    private String bizScopeDesc;
    /**
     * 营业执照扫描件
     */
    private String bizLicenseImg;
    /**
     * 注册机构码证件扫描件
     */
    private String orgCodeCertImg;
    /**
     * 税务注册证件扫描件
     */
    private String taxRegCertImg;

    /**
     * 营业执照所在地
     * */
    private String licenseLocation;
    /**
     * 公司网址
     * */
    private String webSite;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicenseRegNo() {
        return licenseRegNo;
    }

    public void setLicenseRegNo(String licenseRegNo) {
        this.licenseRegNo = licenseRegNo;
    }

    public String getLicenseStartTime() {
        return licenseStartTime;
    }

    public void setLicenseStartTime(String licenseStartTime) {
        this.licenseStartTime = licenseStartTime;
    }

    public String getLicenseEndTime() {
        return licenseEndTime;
    }

    public void setLicenseEndTime(String licenseEndTime) {
        this.licenseEndTime = licenseEndTime;
    }

    public Long getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(Long regCapital) {
        this.regCapital = regCapital;
    }

    public String getBizScopeDesc() {
        return bizScopeDesc;
    }

    public void setBizScopeDesc(String bizScopeDesc) {
        this.bizScopeDesc = bizScopeDesc;
    }

    public String getBizLicenseImg() {
        return bizLicenseImg;
    }

    public void setBizLicenseImg(String bizLicenseImg) {
        this.bizLicenseImg = bizLicenseImg;
    }

    public String getOrgCodeCertImg() {
        return orgCodeCertImg;
    }

    public void setOrgCodeCertImg(String orgCodeCertImg) {
        this.orgCodeCertImg = orgCodeCertImg;
    }

    public String getTaxRegCertImg() {
        return taxRegCertImg;
    }

    public void setTaxRegCertImg(String taxRegCertImg) {
        this.taxRegCertImg = taxRegCertImg;
    }

    public String getLicenseLocation() {
        return licenseLocation;
    }

    public void setLicenseLocation(String licenseLocation) {
        this.licenseLocation = licenseLocation;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}

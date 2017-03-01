package com.mockuai.appcenter.common.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class BizInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String bizCode;
    /**
     * 业务类型，1代表企业业务，2代表个人业务
     */
    private Integer bizType;
    private String bizName;
    private String bizDesc;
    /**
     * 公司名称
     */
    private String company;
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
    private Map<String, BizPropertyDTO> bizPropertyMap;

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

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizDesc() {
        return bizDesc;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Map<String, BizPropertyDTO> getBizPropertyMap() {
        return bizPropertyMap;
    }

    public void setBizPropertyMap(Map<String, BizPropertyDTO> bizPropertyMap) {
        this.bizPropertyMap = bizPropertyMap;
    }
}

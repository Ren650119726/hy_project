package com.mockuai.marketingcenter.core.domain;

import java.util.Date;

public class MarketComponentDO {

    private Long id;
    private String bizCode;
    private String componentCode;
    private Integer componentType;
    private String componentName;
    private Integer implType;
    private String implContent;
    private Integer providerType;
    private Long providerId;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;


    public Long getId() {

        return this.id;
    }


    public void setId(Long id) {

        this.id = id;
    }


    public String getBizCode() {

        return this.bizCode;
    }


    public void setBizCode(String bizCode) {

        this.bizCode = bizCode;
    }

    public String getComponentCode() {

        return this.componentCode;
    }

    public void setComponentCode(String componentCode) {

        this.componentCode = componentCode;
    }

    public Integer getComponentType() {

        return this.componentType;
    }

    public void setComponentType(Integer componentType) {

        this.componentType = componentType;
    }

    public String getComponentName() {

        return this.componentName;
    }

    public void setComponentName(String componentName) {

        this.componentName = componentName;
    }

    public Integer getImplType() {

        return this.implType;
    }

    public void setImplType(Integer implType) {

        this.implType = implType;
    }

    public String getImplContent() {

        return this.implContent;
    }

    public void setImplContent(String implContent) {

        this.implContent = implContent;
    }

    public Integer getProviderType() {

        return this.providerType;
    }

    public void setProviderType(Integer providerType) {

        this.providerType = providerType;
    }

    public Long getProviderId() {

        return this.providerId;
    }

    public void setProviderId(Long providerId) {

        this.providerId = providerId;
    }

    public Integer getDeleteMark() {

        return this.deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {

        this.deleteMark = deleteMark;
    }

    public Date getGmtCreated() {

        return this.gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {

        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {

        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {

        this.gmtModified = gmtModified;
    }
}
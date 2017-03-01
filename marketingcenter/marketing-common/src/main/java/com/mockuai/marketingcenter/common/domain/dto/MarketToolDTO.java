package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

public class MarketToolDTO
        implements Serializable {
    private static final long serialVersionUID = 5504302173557246364L;
    private Long id;
    private String bizCode;
    /**
     * 工具类型，1代表普通工具，2代表复合工具（包含父子工具）
     */
    private Integer type;
    private String toolCode;
    private String toolName;
    private Integer implType;
    private String implContent;
    private Integer providerType;
    private Long providerId;
    private Integer status;
    private Integer deleteMark;

    private List<PropertyTmplDTO> propertyTmplList;
    /**
     * 复合工具的父工具id
     */
    private Long parentId;
    /**
     * 营销子工具列表，只有复合工具该属性才可能有值
     */
    private List<MarketToolDTO> subMarketToolList;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getToolCode() {
        return this.toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolName() {
        return this.toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteMark() {
        return this.deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public List<PropertyTmplDTO> getPropertyTmplList() {
        return this.propertyTmplList;
    }

    public void setPropertyTmplList(List<PropertyTmplDTO> propertyTmplList) {
        this.propertyTmplList = propertyTmplList;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<MarketToolDTO> getSubMarketToolList() {
        return subMarketToolList;
    }

    public void setSubMarketToolList(List<MarketToolDTO> subMarketToolList) {
        this.subMarketToolList = subMarketToolList;
    }
}
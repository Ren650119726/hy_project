package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

public class MarketComponentDTO
        implements Serializable {
    private static final long serialVersionUID = -6056490342515500574L;
    private Long id;
    private String moduleCode;
    private Integer moduleType;
    private String moduleName;
    private Integer provType;
    private String provAppcode;
    private Long provUserId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getProvType() {
        return this.provType;
    }

    public void setProvType(Integer provType) {
        this.provType = provType;
    }

    public String getProvAppcode() {
        return this.provAppcode;
    }

    public void setProvAppcode(String provAppcode) {
        this.provAppcode = provAppcode;
    }

    public Long getProvUserId() {
        return this.provUserId;
    }

    public void setProvUserId(Long provUserId) {
        this.provUserId = provUserId;
    }
}
package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class RuleModuleDTO implements Serializable {

    private Long id;
    private String bizCode;
    private Long creatorId;
    private Long grantRuleId;
    private Long paramA;
    private Long paramB;
    private Long amount;
    private Integer ratio;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getGrantRuleId() {
        return grantRuleId;
    }

    public void setGrantRuleId(Long grantRuleId) {
        this.grantRuleId = grantRuleId;
    }

    public Long getParamA() {
        return paramA;
    }

    public void setParamA(Long paramA) {
        this.paramA = paramA;
    }

    public Long getParamB() {
        return paramB;
    }

    public void setParamB(Long paramB) {
        this.paramB = paramB;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
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
}
package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

public class MarketingTrackDTO
        implements Serializable {
    private static final long serialVersionUID = -560623054507704041L;
    private Long id;
    private Long recordedId;
    private Integer recordedType;
    private Integer auditType;
    private Long operatorId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordedId() {
        return this.recordedId;
    }

    public void setRecordedId(Long recordedId) {
        this.recordedId = recordedId;
    }

    public Integer getRecordedType() {
        return this.recordedType;
    }

    public void setRecordedType(Integer recordedType) {
        this.recordedType = recordedType;
    }

    public Integer getAuditType() {
        return this.auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public Long getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}
package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

public class MarketingScopeDTO
        implements Serializable {
    private static final long serialVersionUID = -7629698233794016002L;
    private Long id;
    private Long actId;
    private Integer cludeType;
    private Integer marketType;
    private Long cludeId;
    private MarketActivityDTO activity;

    public MarketActivityDTO getActivity() {
        return this.activity;
    }

    public void setActivity(MarketActivityDTO activity) {
        this.activity = activity;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActId() {
        return this.actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Integer getCludeType() {
        return this.cludeType;
    }

    public void setCludeType(Integer cludeType) {
        this.cludeType = cludeType;
    }

    public Integer getMarketType() {
        return this.marketType;
    }

    public void setMarketType(Integer marketType) {
        this.marketType = marketType;
    }

    public Long getCludeId() {
        return this.cludeId;
    }

    public void setCludeId(Long cludeId) {
        this.cludeId = cludeId;
    }
}
package com.mockuai.virtualwealthcenter.common.domain.qto;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public class UsedLogQTO extends PageQTO {
    private String bizCode;
    private Long usedWealthId;
    private Integer status;

    public Long getUsedWealthId() {
        return usedWealthId;
    }

    public void setUsedWealthId(Long usedWealthId) {
        this.usedWealthId = usedWealthId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
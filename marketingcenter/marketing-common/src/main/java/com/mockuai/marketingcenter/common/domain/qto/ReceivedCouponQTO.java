package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;

public class ReceivedCouponQTO extends PageQTO
        implements Serializable {
    private Long userId;
    private Integer status;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
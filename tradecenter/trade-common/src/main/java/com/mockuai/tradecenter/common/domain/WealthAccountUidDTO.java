package com.mockuai.tradecenter.common.domain;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class WealthAccountUidDTO {
    private Long wealthAccountId;
    private Long userId;

    public Long getWealthAccountId() {
        return wealthAccountId;
    }

    public void setWealthAccountId(Long wealthAccountId) {
        this.wealthAccountId = wealthAccountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

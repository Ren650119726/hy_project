package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

/**
 * Created by edgar.zr on 12/21/15.
 */
public class GrantInfoDTO {
    private Long grantAmount;
    private String mobile;

    public Long getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(Long grantAmount) {
        this.grantAmount = grantAmount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
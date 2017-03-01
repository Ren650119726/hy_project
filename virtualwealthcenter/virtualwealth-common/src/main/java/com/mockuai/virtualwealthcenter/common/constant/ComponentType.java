package com.mockuai.virtualwealthcenter.common.constant;

/**
 * Created by edgar.zr on 1/12/16.
 */
public enum ComponentType {

    GRANT_VIRTUAL_WEALTH_SINGLE("grantVirtualWealthSingle", "单人虚拟财富发放记录"),
    VALIDATE_SETTLEMENT_OF_SECKILL("validateSettlementOfSeckill", "秒杀活动结算"),;

    private String code;
    private String intro;

    ComponentType(String code, String intro) {
        this.code = code;
        this.intro = intro;
    }

    public String getCode() {
        return code;
    }

    public String getIntro() {
        return intro;
    }
}
package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付方式枚举
 * @author hzmk
 *
 */
public enum EnumPaymentKey {

    HI_COIN_PAY("hi_coin_pay","嗨币支付"),
    ACCOUNT_BALANCE_PAY("account_balance_pay","账户余额支付"),
	WX_PAY("wx_pay","微信支付"),
	ALI_PAY("ali_pay","支付宝支付"),
    LIAN_LIAN_PAYP("lian_lian_pay","连连支付")
    ;

	EnumPaymentKey(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static EnumPaymentKey getByCode(String code){
        for(EnumPaymentKey type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumPaymentKey institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

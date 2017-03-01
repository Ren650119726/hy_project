package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付状态枚举
 * @author hzmk
 *
 */
public enum EnumPaymentMethod {


	SALE_PAY("0","优惠折扣"),
	ALI_PAY_FOR_APP("1","支付宝APP支付"),
	WX_PAY_FOR_APP("2","微信支付APP支付"),
	UNION_PAY_FOR_APP("3","银联支付APP支付"),
	ALI_PAY_FOR_WAP("4","支付宝wap支付"),
	WX_PAY_FOR_WAP("5","微信wap支付"),
	UNION_PAY_FOR_WAP("6","银联wap支付"),
	SUM_PAY_FOR_APP("7","统统付app支付"),
	SUM_PAY_FOR_WAP("8","统统付wap支付"),
    ACCOUNT_BALANCE_PAY("11","账户余额支付"),
    HI_COIN_PAY("12","嗨币支付"),
    LIAN_LIAN_PAY_FOR_APP("13","连连支付APP支付"),
    LIAN_LIAN_PAY_FOR_WAP("14","连连支付WAP支付")
    ;

	EnumPaymentMethod(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static EnumPaymentMethod getByCode(String code){
        for(EnumPaymentMethod type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumPaymentMethod institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 连连支付退款状态枚举
 * @author 
 *
 */
public enum EnumLianLianRefundStatus {

    REFUND_APPLY("0","退款申请"),
    REFUND_PROCESSING("1","退款处理中"),
	REFUND_SUCCESS("2","退款给成功"),
	REFUND_FAIL("3","退款失败")
    ;

	EnumLianLianRefundStatus(String code, String description) {
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

    public static EnumLianLianRefundStatus getByCode(String code){
        for(EnumLianLianRefundStatus type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumLianLianRefundStatus institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

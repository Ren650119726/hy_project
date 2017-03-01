package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付状态枚举
 * @author hzmk
 *
 */
public enum EnumOrderStatus {
	
	UNPAID("10","待支付"),
	
    CANCELED("20","已取消"),
    
	SELLER_CLOSE("21","卖家取消"),
    
    PAID("30","待推送"), // 已支付
    
    UN_DELIVER("35","待发货"),
    
    DELIVERIED("40","待收货"), // 已发货
    
	SIGN_OFF("50","已签收"), 
	
	REFUND_FINISHED("80","订单关闭"), // 退款完成
	
	FINISHED("90","已完成"), // 分佣到账

	PRE_ORDER("5","预单"), // 魔筷
	
	COMMENTED("60","已评价"), // 魔筷
	
	REFUND_APPLY("70","退款中"), // 魔筷
	
	PARTIAL_REFUND("71","部分退款中"), // 魔筷
	
	PARTIAL_REFUND_FINISHED("72","部分退款完成") // 魔筷
    ;

	EnumOrderStatus(String code, String description) {
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

    public static EnumOrderStatus getByCode(String code){
        for(EnumOrderStatus type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumOrderStatus institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

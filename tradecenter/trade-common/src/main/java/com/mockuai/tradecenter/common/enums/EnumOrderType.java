package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumOrderType {


	NORMAL(1,"正常"),
    
    SECKILL(3,"秒杀"),
    
    GIFT_PACK(7,"分销商开店礼包"),
	
    GROUP_BUYING(2,"团购"),
    
    AUCTION(4,"拍卖"),
    
    AUCTION_MARGIN(5,"拍卖保证金"),
    
    RECHARGE(6,"充值"),
    
    COMB(22,"组合"),
    ;

	EnumOrderType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;

    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static EnumOrderType getByCode(int code){
        for(EnumOrderType type:values()){
            if(type.getCode() == code){
                return type;
            }
        }
        return null;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new HashMap<Integer, String>();
        for (EnumOrderType institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

package com.mockuai.tradecenter.common.enums;


public enum EnumTransMode {
	TXCDOE_CART("00","购物车"),
	TXCODE_ORDER("01","订单"),
    TXCODE_MARKTING("02","营销"),
    TXCODE_PAYMENT("03","支付"),
    TXCODE_ITEM("04","商品"),
    ;

    EnumTransMode(String code, String description) {
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

    public static EnumTransMode getByCode(String code){
        for(EnumTransMode transCode:values()){
            if(transCode.getCode().equals(code)){
                return transCode;
            }
        }
        return null;
    }
}
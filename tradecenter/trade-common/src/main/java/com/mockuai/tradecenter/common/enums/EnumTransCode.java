package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;


public enum EnumTransCode {
	TXCODE_ADD("00","新增")    ,
    TXCODE_DELETE("01","删除"),
    TXCODE_UPDATE("02","修改"),
    TXCODE_QUERY("03","查询"),
    TXCODE_GET("04","详细"),
    TXCODE_MARKTING("05","营销相关"),
    TXCODE_PARAM_CHECK("06","参数校验"),
    TX_CODE_ITEM("07","商品相关"),
    TX_CODE_STORE("08","门店"),
    TX_CODE_CONSIGNEE("09","收货人"),
    ;

    EnumTransCode(String code, String description) {
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

    public static EnumTransCode getByCode(String code){
        for(EnumTransCode transCode:values()){
            if(transCode.getCode().equals(code)){
                return transCode;
            }
        }
        return null;
    }
    
    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumTransCode institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }
    
    
    public static Map<String, String> toMapExceptTrans() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumTransCode institution : values()) {
        		enumDataMap.put(institution.getCode(), institution.getDescription());
            
        }
        return enumDataMap;
    }
    
}
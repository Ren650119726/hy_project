package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 提现状态
 *
 */
public enum EnumSettlementMark {


	SETTLEMENTED("0","Y"),
	
	NOT_SETTLED("1","N"),
	
    ;

	EnumSettlementMark(String code, String description) {
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

    public static EnumSettlementMark getByCode(String code){
        for(EnumSettlementMark type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumSettlementMark institution : values()) {
            enumDataMap.put(institution.getDescription(), institution.getCode());
        }
        return enumDataMap;
    }

}

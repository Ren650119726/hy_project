package com.mockuai.tradecenter.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不可用余额枚举
 *
 */
public enum EnumNotAvailableBalance {


	FINE(4,"罚款"),
	
	DEPOSIT(5,"店铺保证金"),
	
    ;

	EnumNotAvailableBalance(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;

    private String description;
    
    
    

	public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static EnumNotAvailableBalance getByCode(Integer code){
        for(EnumNotAvailableBalance type:values()){
            if(type.getCode().intValue()==code.intValue()){
                return type;
            }
        }
        return null;
    }
    

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new HashMap<Integer, String>();
        for (EnumNotAvailableBalance institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }
    
    public static List<Integer> getCodeList(){
    	List<Integer> ids = new ArrayList<Integer>();
    	for(EnumNotAvailableBalance type:values()){
    		ids.add(type.code);
    	}
    	return ids;
    }

}

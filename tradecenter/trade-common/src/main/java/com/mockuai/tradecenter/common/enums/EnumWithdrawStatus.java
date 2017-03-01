package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 提现状态
 *
 */
public enum EnumWithdrawStatus {


	WAIT("1","等待","WAIT"),
	
	PROCESSING("2","处理中","PROCESSING"),
	
	FINISHED("3","完成","FINISHED"),
	
	FAILED("4","失败","FAILED"),
	
	REFUSE("5","拒绝","REFUSE"),
	
//	MALL_PAID("6","已打款","MALL_PAID"),
	
    ;

	EnumWithdrawStatus(String code, String description,String oldCode) {
        this.code = code;
        this.description = description;
        this.oldCode = oldCode;
	}

    private String code;

    private String description;
    
    private String oldCode;

    public String getCode() {
        return code;
    }
    
    

    public String getOldCode() {
		return oldCode;
	}



	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
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
    
    public static EnumWithdrawStatus getByOldCode(String oldcode){
    	 for(EnumWithdrawStatus type:values()){
             if(type.getOldCode().equals(oldcode)){
                 return type;
             }
         }
         return null;
    }

    public static EnumWithdrawStatus getByCode(String code){
        for(EnumWithdrawStatus type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumWithdrawStatus institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

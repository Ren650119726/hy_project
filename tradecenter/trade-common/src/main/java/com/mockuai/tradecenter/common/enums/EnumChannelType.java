package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付状态枚举
 * @author hzmk
 *
 */
public enum EnumChannelType {


	ALIPAY_APP("1","ALIPAY","支付宝"),
	
    WECHANPAY_APP("2","WECHANPAY","微信"),
    
    
    UNIPAY_APP("3","UNIPAY","银联"),
    
	
	
    ;

	EnumChannelType(String code,String oldCode, String description) {
        this.code = code;
        this.description = description;
        this.oldCode = oldCode;
    }

    private String code;

    private String description;
    
    private String oldCode;
    
    

    public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

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

    public static EnumChannelType getByCode(String code){
        for(EnumChannelType type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }
    
    public static EnumChannelType getByOldCode(String oldcode){
        for(EnumChannelType type:values()){
            if(type.getOldCode().equals(oldcode)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumChannelType institution : values()) {
            enumDataMap.put(institution.getOldCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

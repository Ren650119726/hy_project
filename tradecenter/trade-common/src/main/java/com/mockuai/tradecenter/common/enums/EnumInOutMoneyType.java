package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 收支类型
 * @author hzmk
 *
 */
public enum EnumInOutMoneyType {


	COMMISSION("0","COMMISSION","佣金"),
	
	INCOME("1","INCOME","自营收入"),
	
	REFUND("2","REFUND","退款"),
	
	WITHDRWD("3","WITHDRWD","提现"),
	
	FINE("4","FINE","店铺罚款"),
	
	DEPOSIT("5","DEPOSIT","店铺保证金"),
	
	MALL_WITHDRAW("6","MALL_WITHDRAW","商城提现"),
	
//	DEPOSIT_INCOME
	
    ;

	EnumInOutMoneyType(String code, String oldCode,String description) {
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

    public static EnumInOutMoneyType getByCode(String code){
        for(EnumInOutMoneyType type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }
    
    public static EnumInOutMoneyType getByOldCode(String oldcode){
        for(EnumInOutMoneyType type:values()){
            if(type.getOldCode().equals(oldcode)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumInOutMoneyType institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

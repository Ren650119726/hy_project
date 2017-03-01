package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付方式状态枚举
 * @author 
 *
 */
public enum EnumPaymentConfigStatus {

    CLOSE(0,"关闭"),
    OPEN(1,"开启")
    ;

    private EnumPaymentConfigStatus(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	private Integer code;

    private String description;
    
    
    public Integer getCode() {
		return code;
	}

	public String getDescription() {
        return description;
    }

    public static EnumPaymentConfigStatus getByCode(Integer code){
        for(EnumPaymentConfigStatus type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }


}

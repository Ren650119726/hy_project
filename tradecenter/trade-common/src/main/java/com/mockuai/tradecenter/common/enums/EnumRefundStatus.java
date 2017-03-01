package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付状态枚举
 *   状态变更：  申请--->退货中-->退款中-->退款完成
 *             申请--->不同意
 */
public enum EnumRefundStatus {


	APPLY("1","apply","申请"),
	REFUNDING("2","processing","退款中"),
	REFUSE("3","refuse","不同意"),
    REFUND_FINISHED("4","finished","退款完成"),
    REFUND_FAILED("5","failed","退款失败"),
	RETURNING("6","return_processing","退货中")	
    ;

	EnumRefundStatus(String code,String oldCode, String description) {
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

    public static EnumRefundStatus getByCode(String code){
        for(EnumRefundStatus type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }
    
    public static EnumRefundStatus getByOldCode(String oldcode){
        for(EnumRefundStatus type:values()){
            if(type.getOldCode().equals(oldcode)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumRefundStatus institution : values()) {
            enumDataMap.put(institution.getOldCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}

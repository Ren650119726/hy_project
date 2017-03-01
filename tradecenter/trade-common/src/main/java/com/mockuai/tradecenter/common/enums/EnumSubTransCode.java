package com.mockuai.tradecenter.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumSubTransCode {

	PRE_ADD_ORDER(EnumTransMode.TXCODE_ORDER, EnumTransCode.TXCODE_ADD,"00","预下单"),
	ADD_AUCTION_DEPOSIT_ORDER(EnumTransMode.TXCODE_ORDER, EnumTransCode.TXCODE_ADD,"02", "新增保证金订单"),
	
	CHECK_ITEM(EnumTransMode.TXCODE_ITEM,EnumTransCode.TX_CODE_ITEM,"00","验证商品"),
	
	QUERY_RECHARGE_ITEM(EnumTransMode.TXCODE_ITEM,EnumTransCode.TX_CODE_ITEM,"01","查询充值商品"),
	
	GET_SETTLEMENT_INFO(EnumTransMode.TXCODE_MARKTING,EnumTransCode.TXCODE_MARKTING,"00","获取结算信息"),
	
	ADD_ACTIVITY_ORDER(EnumTransMode.TXCODE_ORDER, EnumTransCode.TXCODE_ADD,"03", "新增活动订单"),
	
	ADD_RECHARGE_ORDER(EnumTransMode.TXCODE_ORDER, EnumTransCode.TXCODE_ADD,"04", "新增充值订单"),
	
	;
	
	EnumSubTransCode( EnumTransMode enumTransMode,EnumTransCode enumTransCode,String code, String description ) {
		this.code = code;
		this.description = description;
		this.enumTransCode = enumTransCode;
		this.enumTransMode = enumTransMode;
	}
	private EnumTransMode enumTransMode;
	private EnumTransCode enumTransCode;
	private String code;
	private String description;



	public EnumTransMode getEnumTransMode() {
		return enumTransMode;
	}

	public void setEnumTransMode(EnumTransMode enumTransMode) {
		this.enumTransMode = enumTransMode;
	}

	public String getCode() {
		return enumTransMode.getCode()+enumTransCode.getCode()+code;
	}

	public String operatorCode(){
		return code;
	}

	public EnumTransCode getEnumTransCode() {
		return enumTransCode;
	}

	public void setEnumTransCode(EnumTransCode enumTransCode) {
		this.enumTransCode = enumTransCode;
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

	public static EnumSubTransCode getByCode(String code) {
		for (EnumSubTransCode subTrans : values()) {
			if (subTrans.getCode().equals(code)) {
				return subTrans;
			}
		}
		return null;
	}

  public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumSubTransCode institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }
}

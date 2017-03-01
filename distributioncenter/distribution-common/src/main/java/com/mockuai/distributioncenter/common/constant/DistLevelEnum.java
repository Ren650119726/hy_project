package com.mockuai.distributioncenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description 
 * @author Administrator
 * @date 2016年12月15日 上午10:57:17
 */
public enum DistLevelEnum {
	FIRST_LVL(2,"直接上级"),
	SECOND_LVL(3,"直接上级的直接上级");
	
	private int code ;
	private String desc;
	
	private DistLevelEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static DistLevelEnum getByCode(int code){
		for(DistLevelEnum type:values()){
			if(type.getCode()==code){
				return type;
			}
		}
		return null;
	}
	
	public static Map<Integer,String> toMap(){
		Map<Integer,String> enumDataMap = new HashMap<Integer, String>();
		for(DistLevelEnum type:values()){
			enumDataMap.put(type.getCode(), type.getDesc());
		}
		return enumDataMap;
	}
	
}

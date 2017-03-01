package com.mockuai.distributioncenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description 
 * @author Administrator
 * @date 2016年12月15日 上午10:22:55
 */
public enum SelfBuyEnum {

	ON(1,"开启"),
	
	OFF(0,"关闭");

	private int code;
	private String desc;
	
	private SelfBuyEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	/**
	 * 
	 * @Description 
	 * @author Administrator
	 * @param code
	 * @return
	 */
	public static SelfBuyEnum getByCode(int code){
		
		for(SelfBuyEnum type:values()){
			if(type.getCode() == code){
				return type;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Description 
	 * @author Administrator
	 * @return
	 */
	public static Map<Integer,String> toMap(){
		Map<Integer,String> enumDataMap = new HashMap<Integer,String>();
		
		for(SelfBuyEnum type:values()){
			enumDataMap.put(type.getCode(), type.getDesc());
		}
		
		return enumDataMap;
		
	}
	
}

/**
 * 
 * Project Name ：user-core 
 * Package      ：com.mockuai.usercenter.core.enums
 * File Name    ：HandleTypeEnum.java
 * Date         ：2016年5月14日-上午10:23:59 
 * 
 * Copyright© wsmall 2009-2016，All Rights Reserved
 * 
 */
package com.mockuai.messagecenter.common.constant;

import java.util.Objects;

/**
 * 
 * Class Name        ：HandleTypeEnum
 * Class Description ：
 * Creater           ：ly
 * Create Time       ：2016年5月14日 上午10:23:59
 * 
 */
public enum HandleTypeEnum {
	
	REGISTER("register","注册"),
	
	LOGIN("login","登录"),
	
	BINDING_MOBILE("binding_mobile","绑定手机号"),
	
	MODIFY_MOBILE_OLD("modify_mobile_old","修改手机号"),
	
	MODIFY_MOBILE_NEW("modify_mobile_new","修改手机号"),
	
	SET_PASSWORD("set_password","设置密码"),
	
	MODIFY_PASSWORD("modify_password","修改密码"),
	
	REAL_NAME_AUTH("real_name_auth","实名认证");
		
	public String code ;
	
	public String desc;

	/**
	 * @param code
	 * @param desc
	 */
	private HandleTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static HandleTypeEnum getByCode(String code){
		HandleTypeEnum[] temps = HandleTypeEnum.values();
		for(HandleTypeEnum temp:temps){
			if(Objects.equals(temp.getCode(), code)){
				return temp;
			}
		}
		return null;
	}
		
}

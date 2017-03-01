package com.mockuai.messagecenter.common.action;

public enum ActionEnum {
	
	/**
	 * 发送短信接口
	 */
	SEND_SMS("sendSms"),
	
	/*短信服务*/
	SMS_SERVICE("smsMobileVerifyCore"),
	
	/*获取缓存的短信验证码接口*/	 
	GET_MOBILE_VERIFY_CODE("getMobileVerifyCode"),
	
	
	/*删除缓存的短信验证码接口*/
	DELETE_MOBILE_VERIFY_CODE("deleteMobileVerifyCode");


	private String actionName;

	private ActionEnum(String actionName) {
		this.actionName = actionName;
	}

	public static ActionEnum getActionEnum(String actionName) {
		for (ActionEnum ae : ActionEnum.values()) {
			if (ae.actionName.equals(actionName)) {
				return ae;
			}
		}
		return null;
	}

	public String getActionName() {
		return actionName;
	}
}

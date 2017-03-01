package com.mockuai.usercenter.core.util;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.domain.UserDO;

public class ExceptionUtil {
	
	/**
	 * 获得指定用户名不存在情况下的异常
	 * */
	public static Response userNotExistException(UserDO usreDo){
		if(usreDo==null){
			UserResponse response = new UserResponse(false);
			return response;
		}
		return null;
	}
}

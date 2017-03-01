package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;
import com.mockuai.usercenter.common.api.UserDispatchService;
/**
 * @author cwr
 */
public abstract class BaseAction implements Action{
	private UserDispatchService userDispatchService;


	public UserDispatchService getUserDispatchService() {
		return userDispatchService;
	}

	public void setUserDispatchService(UserDispatchService userDispatchService) {
		this.userDispatchService = userDispatchService;
	}

	public ResponseFormat getResponseFormat() {
		return ResponseFormat.STANDARD;
	}
}


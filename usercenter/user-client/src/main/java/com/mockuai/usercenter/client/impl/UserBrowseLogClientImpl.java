package com.mockuai.usercenter.client.impl;

import javax.annotation.Resource;

import com.mockuai.usercenter.client.UserBrowseLogClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.UserBrowseLogDTO;

@SuppressWarnings("restriction")
public class UserBrowseLogClientImpl implements UserBrowseLogClient {
	@Resource
    private UserDispatchService userDispatchService;
	/**
	 * 根据用户id查询用户访问记录
	 * 
	 * @author csy
	 * @Date 2016-05-26 
	 */
	@SuppressWarnings("unchecked")
	public Response<UserBrowseLogDTO> getBrowseLogByUserId(Long userId, String appKey) {
		Request request = new BaseRequest();
        request.setParam("user_id",userId);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GETBROWSELOGBYUSERID.getActionName());
        Response<UserBrowseLogDTO> response = this.userDispatchService.execute(request);
        return response;
	}

}

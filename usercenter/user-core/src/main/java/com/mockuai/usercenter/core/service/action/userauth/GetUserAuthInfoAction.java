package com.mockuai.usercenter.core.service.action.userauth;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.virtualwealthcenter.client.UserAuthonClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;

@Service
public class GetUserAuthInfoAction implements Action {
	@Resource
	private UserAuthonClient userAuthonClient;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest userRequest = context.getRequest();
		
		Long userId = (Long) userRequest.getParam("user_id");
		String appKey = (String)userRequest.getParam("appKey");
		
		if(null == userId){
			throw new UserException(ResponseCode.P_PARAM_NULL, "用户未登录或不存在");
		}

		//查询用户是否实名认证
		Response<MopUserAuthonAppDTO> userAuthonAppDTO = userAuthonClient.findWithdrawalsItem(userId, appKey);
		
		if(userAuthonAppDTO.isSuccess() == false || null == userAuthonAppDTO.getModule()){
			throw new UserException(ResponseCode.P_PARAM_NULL, "用户未进行实名认证");
		}
		
		return new UserResponse(true);
	}

	public String getName() {
		return ActionEnum.GET_USER_AUTH_INFO.getActionName();
	}
}

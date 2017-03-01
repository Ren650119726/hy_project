package com.mockuai.usercenter.core.service.action.consignee;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;

@Service
public class SetDefConsigneeAction extends TransAction {

	@Resource
	private UserConsigneeManager userConsigneeManager;

	@Override
	public String getName() {
		return ActionEnum.SET_DEFAULT_CONSIGNEE.getActionName();
	}

	@Override
	protected UserResponse doTransaction(RequestContext context)
			throws UserException {

		UserRequest userRequest = context.getRequest();
		Long userId = (Long) userRequest.getParam("userId");
		Long consigneeId = (Long) userRequest.getParam("consigneeId");// 收货地址id
		// 查询是否存在指定id对应的收货地址
		UserConsigneeDTO userConsigneeDto = userConsigneeManager.getConsigneeById(userId,
				consigneeId);
		if (null == userConsigneeDto) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"consignee is not exist");
		}

		// 如果修改的收货地址为默认地址，则返回
		// updated by cwr 如果是已经是默认地址了话 允许返回成功
		/* 
		if (consigneeDto.getIsDefault() == true) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"consignee is default");
		}
		*/

		// 将指定用户的其他地址改为非默认
		userConsigneeManager.updateUserDefaultConsignee(userId);
		userConsigneeManager.setDefConsignee(userId, consigneeId);

		return new UserResponse(true);

	}

}

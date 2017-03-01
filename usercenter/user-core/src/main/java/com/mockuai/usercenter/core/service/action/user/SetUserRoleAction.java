//package com.mockuai.usercenter.core.service.action.user;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
//@Service
//public class SetUserRoleAction implements Action {
//
//	@Resource
//	private UserManager userManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		UserRequest request = context.getRequest();
//		Long userId = (Long) request.getParam("userId");
//		 role = (Byte) request.getParam("roleMark");// 用户角色id
//		userManager.setUserRoleMark(userId, role);
//
//		return new UserResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.SET_ROLE.getActionName();
//	}
//
//}

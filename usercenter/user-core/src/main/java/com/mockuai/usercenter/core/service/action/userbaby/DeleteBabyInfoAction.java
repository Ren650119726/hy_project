//package com.mockuai.usercenter.core.service.action.userbaby;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserBabyInfoManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
//@Service
//public class DeleteBabyInfoAction implements Action {
//
//	@Resource
//	private UserBabyInfoManager userBabyInfoManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//		Long userId = (Long) userRequest.getParam("userId");
//		Long babyId = (Long) userRequest.getParam("babyId");
//
//		userBabyInfoManager.deleteBabyInfo(userId, babyId);
//		return new UserResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.DELETE_USER_BABY_INFO.getActionName();
//	}
//
//}

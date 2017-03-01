//package com.mockuai.usercenter.core.service.action.userbaby;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.constant.ResponseCode;
//import com.mockuai.usercenter.common.dto.UserBabyInfoDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserBabyInfoManager;
//import com.mockuai.usercenter.core.employee.UserManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.TransAction;
//
//@Service
//public class AddBabyInfoAction extends TransAction {
//
//	@Resource
//	private UserBabyInfoManager userBabyInfoManager;
//
//	@Resource
//	private UserManager userManager;
//
//	@Override
//	public String getName() {
//
//		return ActionEnum.ADD_USER_BABY_INFO.getActionName();
//	}
//
//	@Override
//	protected UserResponse doTransaction(RequestContext context)
//			throws UserException {
//
//		UserBabyInfoDTO babyInfoDto = null;
//
//		UserRequest userRequest = context.getRequest();
//		babyInfoDto = (UserBabyInfoDTO) userRequest.getParam("userBabyDTO");
//
//		if (null == babyInfoDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"babyInfoDTO is null");
//		}
//
//		if (babyInfoDto.getIsDefault() == true) {
//
//			// 获取指定用户的默认baby，如果不存在默认的baby，则直接插入，如果存在默认的baby，则先将改baby的状态改为非默认
//			UserBabyInfoDTO babyDto = userBabyInfoManager
//					.getDefBabyInfo(babyInfoDto.getUserId());
//			if (babyDto != null) {
//				userBabyInfoManager.setNonDef(babyDto.getId());
//			}
//		}
//
//		babyInfoDto = userBabyInfoManager.addBabyInfo(babyInfoDto);
//		return new UserResponse(babyInfoDto);
//
//	}
//
//}

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
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.TransAction;
//
//@Service
//public class UpdateBabyInfoAction extends TransAction {
//
//	@Resource
//	private UserBabyInfoManager userBabyInfoManager;
//
//	@Override
//	public String getName() {
//
//		return ActionEnum.UPDATE_USER_BABY_INFO.getActionName();
//	}
//
//	@Override
//	protected UserResponse doTransaction(RequestContext context)
//			throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//		UserBabyInfoDTO babyInfoDto = (UserBabyInfoDTO) userRequest
//				.getParam("userBabyDTO");
//
//		if (null == babyInfoDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"babyInfoDTO is null");
//		}
//
//		if (babyInfoDto.getIsDefault() == true) {
//			// 查看指定的baby是否存在
//			UserBabyInfoDTO getBabyInfoDto = userBabyInfoManager
//					.getBabyInfoById(babyInfoDto.getId());
//
//			if (null == getBabyInfoDto) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"baby is not exist");
//			}
//
//			// 获取指定用户的默认baby，如果不存在默认的baby，则直接修改，如果存在默认的baby，则先将改baby的状态改为非默认
//			UserBabyInfoDTO babyDto = userBabyInfoManager
//					.getDefBabyInfo(getBabyInfoDto.getUserId());
//			if (babyDto != null) {
//				userBabyInfoManager.setNonDef(babyDto.getId());
//			}
//		}
//		userBabyInfoManager.updateBabyInfo(babyInfoDto);
//
//		return new UserResponse(true);
//
//	}
//
//}

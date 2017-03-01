//package com.mockuai.usercenter.core.service.action.usergroup;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.constant.ResponseCode;
//import com.mockuai.usercenter.common.dto.UserGroupDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserGroupManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.TransAction;
//
//@Service
//public class UpdateUserGroupAction extends TransAction {
//
//	@Resource
//	private UserGroupManager userGroupManager;
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.UPDATE_USER_GROUP.getActionName();
//	}
//
//	@Override
//	protected UserResponse doTransaction(RequestContext context)
//			throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//		UserGroupDTO userGroupDto = (UserGroupDTO) userRequest
//				.getParam("userGroupDTO");
//
//		if (null == userGroupDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"userGroupDTO is null");
//		}
//
//		// 最大积分值
//		Long maxIntegral = userGroupDto.getMaxIntegral();
//
//		// 当传入的最大积分值不为空，这需要验证需要修改的积分值是否符合规范
//		if (maxIntegral != null) {
//			// 根据组id，获取用户组
//			UserGroupDTO queryUserGroupDto = userGroupManager
//					.getUserGroup(userGroupDto.getId());
//
//			// 查询指定用户组的上一等级的用户组，如果存在上一等级的用户组，则要将上一等级的最小积分值修改
//			UserGroupDTO upUserGroupDto = userGroupManager
//					.getUpUserGroup(queryUserGroupDto.getGroupNo());
//
//			if (null != upUserGroupDto) {
//				if (upUserGroupDto.getMaxIntegral() <= maxIntegral) {
//					throw new UserException(ResponseCode.P_PARAM_ERROR,
//							"maxIntegral greater than upGroup maxIntegral");
//				}
//
//				// 将传入的用户组的最大积分赋值给上一等级的最小积分
//				upUserGroupDto.setMinIntegral(maxIntegral);
//				// 修改上一组等级
//				userGroupManager.updateUserGroup(upUserGroupDto);
//			}
//		}
//		userGroupManager.updateUserGroup(userGroupDto);
//		return new UserResponse(true);
//
//	}
//
//}

//package com.mockuai.usercenter.core.service.action.user;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.constant.ResponseCode;
//import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserConsigneeManager;
//import com.mockuai.usercenter.core.employee.UserAuthInfoManager;
//import com.mockuai.usercenter.core.employee.UserManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.TransAction;
//
///**
// * 逻辑删除用户:改删除不用户不能恢复，因此要删除该用户相关联的其他表内的所有记录， 要慎重操作
// * */
//@Service
//public class DeleteUserAction extends TransAction {
//
//	@Resource
//	private UserManager userManager;
//
//	@Resource
//	private UserConsigneeManager userConsigneeManager;
//
//	@Resource
//	private UserAuthInfoManager userAuthInfoManager;
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.DELETE_USER.getActionName();
//	}
//
//	@Override
//	protected UserResponse doTransaction(RequestContext context)
//			throws UserException {
//
//		// TODO Auto-generated method stub
//
//		UserRequest request = context.getRequest();
//		Long userId = (Long) request.getParam("userId");
//
//		// 根据用户id，获取用户
//		UserDTO userDto = userManager.getUserById(userId);
//
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user not exist");
//		}
//
//		//TODO 如果用户为卖家，则逻辑删除卖家的扩展信息
//
//
//		//TODO 逻辑删除指定用户买家账号的扩展信息
//
//		//TODO 逻辑删除指定用户的baby的信息
//
//		// 删除指定用户的收货地址信息
//		int consigneeCount = userConsigneeManager.getConsigneeCountByUserId(userId);
//		if (consigneeCount > 0) {
//			// 如果被删除的收货地址数和收货地址总数不相同，则删除失败
//			if (consigneeCount != userConsigneeManager.deleteUserConsignee(userId)) {
//				throw new UserException(ResponseCode.B_DELETE_ERROR,
//						"delete configneeInfo error");
//			}
//		}
//
//		// 删除用户的实名认证消息,存在则逻辑删除
//		UserAuthInfoDTO userAuthInfoDto = userAuthInfoManager
//				.getAuthInfoByUserId(userId);
//		if (userAuthInfoDto != null) {
//			if (userAuthInfoManager.deleteUserAuth(userId) != 1) {
//				throw new UserException(ResponseCode.B_DELETE_ERROR,
//						"delete userAuthInfoDto error");
//			}
//		}
//
//		// 删除用户
//		userManager.deleteUser(userId);
//
//		return new UserResponse(ResponseCode.REQUEST_SUCCESS);
//
//	}
//
//}

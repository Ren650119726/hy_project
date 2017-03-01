package com.mockuai.usercenter.core.service.action.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.RoleType;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.NotifyManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import com.mockuai.usercenter.core.util.UserUtil;

/**
 * 添加用户接口
 * */
@Service
public class AddUserAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(AddUserAction.class);

	@Resource
	private UserManager userManager;

	@Resource
	private NotifyManager notifyManager;

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	public UserResponse doTransaction(RequestContext context) throws UserException {
		log.info("Enter Action [{}]", getName());
		UserRequest userRequest = context.getRequest();
		UserDTO userDTO = (UserDTO) userRequest.getParam("userDTO");
		String bizCode = (String)context.get("bizCode");
		Integer appType = (Integer)context.get("appType");
		//邀请码
		Integer invitationSource = userDTO.getInvitationSource();

		//TODO 入参校验
		if (bizCode == null) {
			log.error("bizCode is null");
			return new UserResponse(ResponseCode.P_PARAM_NULL, "bizCode is null");
		}

		if(userDTO == null){
			log.error("userDTO is null, bizCode = {}", bizCode);
			return new UserResponse(ResponseCode.P_PARAM_NULL, "userDTO is null");
		}

		//当role mark是null时
		if(userDTO.getRoleMark()==null){
			userDTO.setRoleMark(RoleType.BUYER.getValue());
		}

		userDTO.setBizCode(bizCode);

		//判断用户状态，如果为空，则设置为0
		if (userDTO.getStatus() == null) {
			userDTO.setStatus(0);
		}

		if (appType != null) {
			userDTO.setSourceType(appType);
		} else {
			log.error("app type is null when add user, bizCode : {}", bizCode);
			return new UserResponse(ResponseCode.B_APPTYPE_IS_NULL);
		}

		try {
			// 检查手机号格式是否正确
			if (userDTO.getMobile() != null) {
				UserUtil.checkMobile(userDTO.getMobile());
			}
		} catch (UserException err) {
			log.error("error when checkout mobile number format, bizCode = {}, errorCode = {}, errorMsg = {}, mobile = {}",
					bizCode, err.getResponseCode(), err.getMessage(), userDTO.getMobile());
			return new UserResponse(err.getResponseCode(), err.getMessage());
		}

		try{
			// 判断邀请码是否为空，如果不为空，判断是否存在指定的用户
			String invitationCode = userDTO.getInvitationCode();
			if (StringUtils.isBlank(invitationCode) == false) {
				UserDTO invUser = userManager.getUserByInvitationCode(invitationCode);
				if (invUser == null) {
					log.error("invitation code :{}, no user own this invitationCode", invitationCode);
					throw new UserException(ResponseCode.B_INVITATION_CODE_INVALID,
							"invitation code error");
				}
				userDTO.setInviterId(invUser.getId());
			} else {
				log.warn("invitation code is empty when add user with biz code = {}", userDTO.getBizCode());
				userDTO.setInviterId(0L);
			}

			userDTO = userManager.addUser(userDTO);

			//添加成功，推送新增用户消息（TODO 保证事务性，异步任务化）
			notifyManager.notifyAddUserMsg(userDTO.getId(), bizCode);

			// 数据中心埋点，记录统计数据
			Map<String, String> params = new HashMap<String, String>();
			//params.put("property", "1");	// 添加一个新注册用户
			//dataManager.buriedPoint(null, "userRegister", params, System.currentTimeMillis(), (String)userRequest.getParam("appKey"));

			// 发送创建用户成功的消息
			userDTO.setInvitationSource(invitationSource);
			return new UserResponse(userDTO);
		}catch(UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_USER.getActionName();
	}
}

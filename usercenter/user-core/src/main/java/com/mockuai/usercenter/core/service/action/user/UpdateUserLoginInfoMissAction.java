package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.virtualwealthcenter.common.api.Response;

@Service
public class UpdateUserLoginInfoMissAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserLoginInfoMissAction.class);

	@Resource
	private UserManager userManager;
	@Resource
    private DistributionClient distributionClient;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		try {
			UserRequest request = context.getRequest();		

			Long userId = (Long)request.getParam("user_id");//用户id
	        String mobile = (String)request.getParam("mobile");//手机号
	        String password = (String)request.getParam("password");//密码
	        String verifyCode = (String)request.getParam("verifyCode");//邀请码
	        String invitationCode = (String)request.getParam("invitationCode");
	        String appKey = (String)request.getParam("appKey");

			if (userId == null) {
				log.error("user id is null when update mobile");
			}
			
			//根据邀请码查询卖家信息
			com.mockuai.distributioncenter.common.api.Response<SellerDTO> sellerDtoRe = null;
			Long invitationId = null;
			
			if(null != invitationCode && !"".equals(invitationCode)){
				sellerDtoRe = distributionClient.getSellerByInviterCode(invitationCode, appKey);
				
				if(sellerDtoRe.isSuccess() == false || null == sellerDtoRe || null == sellerDtoRe.getModule()){
		        	return new UserResponse(ResponseCode.P_PARAM_NULL, "请输入卖家正确的邀请码");
		        }
				
				invitationId = sellerDtoRe.getModule().getUserId();
			}       

			userManager.updateUserLoginInfoMiss(userId, mobile, password, verifyCode, invitationId);
			return new UserResponse(true);
		} catch (UserException e) {
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}		
	}

	public String getName() {
		return ActionEnum.UPDATEUSERLOGININFOMISS.getActionName();
	}

}

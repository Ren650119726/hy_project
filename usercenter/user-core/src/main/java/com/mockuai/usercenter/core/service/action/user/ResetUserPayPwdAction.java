package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class ResetUserPayPwdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(ResetUserPayPwdAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		
		Long userId = (Long) request.getParam("user_id");//用户id
		String pay_pwd = (String) request.getParam("pay_pwd");//支付密码		
        String once_pay_pwd = (String)request.getParam("once_pay_pwd");//确认支付密码
        String id_card = (String)request.getParam("id_card");//身份证号
        String payFlag = (String)request.getParam("payFlag");//支付方法标识
        String appKey = (String)request.getParam("appKey");

		if (userId == null) {
			log.error("user id is null when update pay password");
		}
		
		if (pay_pwd == null) {
			log.error("pay_pwd is null when update pay password");
		}

		if (once_pay_pwd == null) {
			log.error("once_pay_pwd is null when update pay password");
		}	

		userManager.resetUserPayPwd(userId, pay_pwd, once_pay_pwd, id_card, appKey, payFlag);
		return new UserResponse(true);
	}

	public String getName() {
		return ActionEnum.RESETUSERPAYPWD.getActionName();
	}

}

package com.mockuai.usercenter.core.service.action.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserBrowseLogDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserBrowseLogManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
/**
 * 登录方法已经不再使用
 * 
 * @author Administrator
 *
 */
@Service
public class UserLoginAction implements Action {
	private final static Logger log = LoggerFactory.getLogger(UserLoginAction.class);

	@Resource
	private UserManager userManager;
	@Resource
	private UserBrowseLogManager userBrowseLogManager;
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserResponse execute(RequestContext context) throws UserException {
		log.info("Enter Action [{}]", getName());
		UserRequest userRequest = context.getRequest();
		String loginName = (String) userRequest.getParam("loginName");//手机号
		String loginPwd = (String) userRequest.getParam("loginPwd");//登陆的密码
		String loginVerifyCode = (String) userRequest.getParam("loginVerifyCode");//手机验证码
		String loginFlag = (String) userRequest.getParam("loginFlag");//登录标识(0代表密码验证1代码验证码登录)
    	String loginSource = (String) userRequest.getParam("loginSource");//登录类型

		if (null == loginName) {
			log.error("loginName is null when login");
		}

		if (null == loginPwd) {
			log.error("login password is null when login, loginName = {}", loginName);
		}
		
		if(null == loginVerifyCode){
			log.error("login verify_code is null when login, loginName = {}", loginName);
		}
		
		if(null == loginFlag){
			log.error("login flag is null when login, loginName = {}", loginName);
		}
		
		if(null == loginSource){
			log.error("login source is null when login, loginName = {}", loginName);
		}

		UserDTO userDto = userManager.userLogin(loginName, loginPwd, loginVerifyCode, loginFlag);		
		
		log.info("Exit Action [{}]", getName());
		return new UserResponse(getUserDtoMap(userDto,loginSource));
	}

	@Override
	public String getName() {
		return ActionEnum.USER_LOGIN.getActionName();
	}
	
	/**
	 * 判断手机号、密码、邀请码是否为空
	 * 
	 * @throws UserException 
	 * 
	 */
	private Map<Object, Object> getUserDtoMap(UserDTO userDto,String loginSource) throws UserException{
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(null == loginSource){
			throw new UserException(ResponseCode.P_PARAM_NULL,"登录异常，请重新登录");
		}
		
		//0表示有值不为空      1表示为空没有值
		if(null == userDto.getMobile()){
			map.put("mobile", "1");
		}else{
			map.put("mobile", "0");
		}
		
		if(null == userDto.getPassword()){
			map.put("password", "1");
		}else{
			map.put("password", "0");
		}
		
		//0标示h5登录 1标示app登录
		if("1".equals(loginSource)){
			//获取用户访问记录信息
			UserBrowseLogDTO userBabyInfoDTO = userBrowseLogManager.getBrowseLogByUserId(userDto.getId());
			
			if(null == userDto.getInviterId() && null == userBabyInfoDTO){
				map.put("inviter", "1");
			}else{
				map.put("inviter", "0");
			}
		}		
		
		map.put("UserDTO", userDto);
		
		return map;
	}
}

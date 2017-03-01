package com.mockuai.usercenter.core.service.action.userauth;

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
import com.mockuai.usercenter.common.constant.UserAuthStatus;
import com.mockuai.usercenter.common.constant.UserAuthType;
import com.mockuai.usercenter.common.dto.EnterpriseAuthExtendDTO;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import com.mockuai.usercenter.core.util.ModelUtil;

/**
 * 原先的添加用户认证信息和修改用户认证信息改为同一个接口
 * */
@Service
public class AddUserAuthInfoAction extends TransAction {

	private final static Logger log = LoggerFactory.getLogger(AddUserAuthInfoAction.class);

	@Resource
	private UserAuthInfoManager userAuthInfoManager;

	@Resource
	private EnterpriseAuthExtendManager enterpriseAuthExtendManager;

	@Override
	protected UserResponse doTransaction(RequestContext context) throws UserException {
		log.info("enter action <{}>", getName());
		UserRequest userRequest = context.getRequest();
		UserAuthInfoDTO authInfoDTO = (UserAuthInfoDTO) userRequest.getParam("userAuthInfoDTO");// 用户实名认证的真实消息
		String bizCode = (String)context.get("bizCode");

		authInfoDTO.setBizCode(bizCode);

		//如果未设置认证类型，则认证类型默认置为买家个人认证
		if(authInfoDTO.getType() == null){
			authInfoDTO.setType(UserAuthType.BUYER_AUTH.getValue());
		}

		//入参检查
		if(authInfoDTO.getType().intValue() == UserAuthType.SELLER_AUTH.getValue()){
			if(StringUtils.isBlank(authInfoDTO.getIdcardHoldImg())){
				log.error("id card hold image is null");
				return new UserResponse(ResponseCode.P_PARAM_NULL, "idcard hold img is null");
			}
		}else if(authInfoDTO.getType().intValue() == UserAuthType.ENTERPRISE_AUTH.getValue()){
			if(authInfoDTO.getEnterpriseAuthExtendDTO() == null){
				log.error("enterprise auth extend info is null");
				return new UserResponse(ResponseCode.P_PARAM_NULL, "enterprise auth extend info is null");
			}
		}

		try {

			UserAuthInfoDO userAuthInfoDO = ModelUtil.convertToUserAuthInfoDO(authInfoDTO);

			//判断是否已经认证过了，避免重复插入数据
			if(userAuthInfoDO.getType().intValue() == UserAuthType.ENTERPRISE_AUTH.getValue()){
				//企业认证
				EnterpriseAuthExtendDO getResult = enterpriseAuthExtendManager.getEnterpriseAuthExtend(
						userAuthInfoDO.getUserId());
				if(getResult != null){
					return new UserResponse(ResponseCode.B_USER_AUTH_INFO_IS_EXIST, "已经实名认证过了");
				}
			}else{
				//个人认证（买家认证/卖家认证）
				UserAuthInfoDO getResult = userAuthInfoManager.getAuthInfoByUserId(userAuthInfoDO.getUserId(),
						userAuthInfoDO.getType(), bizCode);
				if(getResult != null){
					return new UserResponse(ResponseCode.B_USER_AUTH_INFO_IS_EXIST, "已经实名认证过了");
				}
			}

			//默认设置为待认证状态
			userAuthInfoDO.setStatus(UserAuthStatus.AUTH_WAIT.getValue());

			//添加认证信息
			Long authInfoId = userAuthInfoManager.addUserAuthInfo(userAuthInfoDO);

			//如果是卖家企业认证，则需要添加企业认证扩展信息
			if(authInfoDTO.getType().intValue() == UserAuthType.ENTERPRISE_AUTH.getValue()){
				EnterpriseAuthExtendDTO enterpriseAuthExtendDTO = authInfoDTO.getEnterpriseAuthExtendDTO();
				enterpriseAuthExtendDTO.setBizCode(bizCode);

				// 如果用户的认证信息提交成功，则 userAuthInfoDO 对象中就会存有该用户的用户ID
				enterpriseAuthExtendDTO.setUserId(userAuthInfoDO.getUserId());

				enterpriseAuthExtendManager.addEnterpriseAuthExtend(
						ModelUtil.convertToEnterpriseAuthExtendDO(enterpriseAuthExtendDTO));
			}
			// 对新注册店铺进行埋点
			//Map<String, String> params = new HashMap<String, String>();
			//params.put("property", "1");
			//dataManager.buriedPoint(null, "shopRegister", params, System.currentTimeMillis(), (String) userRequest.getParam("appKey"));

			return new UserResponse(authInfoId);
		} catch (UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_USER_AUTH_INFO.getActionName();
	}

}

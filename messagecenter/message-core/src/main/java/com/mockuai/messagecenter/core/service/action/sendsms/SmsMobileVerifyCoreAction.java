package com.mockuai.messagecenter.core.service.action.sendsms;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.messagecenter.common.action.ActionEnum;
import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.common.constant.HandleTypeEnum;
import com.mockuai.messagecenter.common.constant.ResponseCode;
import com.mockuai.messagecenter.common.constant.SmsTempSnEnum;
import com.mockuai.messagecenter.common.qto.VerifySmsQTO;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.manager.CacheManager;
import com.mockuai.messagecenter.core.manager.SmsServiceManager;
import com.mockuai.messagecenter.core.manager.impl.CacheManagerImpl;
import com.mockuai.messagecenter.core.service.RequestContext;
import com.mockuai.messagecenter.core.service.UserRequest;
import com.mockuai.messagecenter.core.service.action.Action;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;

/**
 * 根据短信模板以及动态参数，发送短信
 * */
@Service
public class SmsMobileVerifyCoreAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(SmsMobileVerifyCoreAction.class);

	@Resource
	private SmsServiceManager smsServiceManager;
	
	/*超时时间，单位分钟*/
	private final int minute = 10;

	@Resource
	UserClient userClient ;
	
	@Resource
	CacheManager cachemanager ;
	
	/*手机验证码模板编号*/
	private final String tempSn = SmsTempSnEnum.MOBILE_VERIFY.code;

	@Override
	public MessageResponse execute(RequestContext context) {
		
		/*String bizCode = (String)context.get("bizCode");*/
		String appKey = (String) context.get("appKey");
		
		UserRequest request = context.getRequest();
		VerifySmsQTO verifySmsQTO = (VerifySmsQTO) request.getParam("verifySmsQTO");
		try {
			checkParams(verifySmsQTO);
		} catch (MessageException e) {
			return new MessageResponse(e.getResponseCode(), e.getMessage());
		}
		
		
		String handleType = verifySmsQTO.getHandleType();
		String mobile = verifySmsQTO.getMobile();	
				
        String handleName = HandleTypeEnum.getByCode(handleType).desc;
        
        /*验证手机号*/
        try {
        	Response<UserDTO> result = userClient.getUserByMobile(mobile, appKey);
        	if(result == null){
            	return new MessageResponse(ResponseCode.B_BIZ_EXCEPTION," userClient.getUserByMobile异常 ");
        	}
            if(handleType.equals(HandleTypeEnum.REGISTER.code) || handleType.equals(HandleTypeEnum.BINDING_MOBILE.code) || handleType.equals(HandleTypeEnum.MODIFY_MOBILE_NEW.code)){
            	if(result.getModule()!=null){
            		return new MessageResponse(ResponseCode.B_BIZ_EXCEPTION," 该手机号已被使用，请更换 ");
            	}
            }
            if(handleType.equals(HandleTypeEnum.LOGIN.code) || handleType.equals(HandleTypeEnum.MODIFY_PASSWORD.code) || handleType.equals(HandleTypeEnum.SET_PASSWORD.code) || handleType.equals(HandleTypeEnum.MODIFY_MOBILE_OLD.code)){
            	if(result.getModule()==null){
            		return new MessageResponse(ResponseCode.B_BIZ_EXCEPTION," 手机号未注册 ");
            	}
            }
		} catch (Exception e) {
			log.error(" 用户服务userClient.getUserByMobile异常 ");
			return new MessageResponse(e);
		}
        
        /*生成验证码*/
        long verifyCode = System.currentTimeMillis() % 1000000;		
		String verifyCodeStr = String.format("%06d", verifyCode); 
		
		/*短信动态内容*/
		String[] values = {verifyCodeStr,handleName,String.valueOf(minute)} ;	
		
		log.info(" set in cache key : "+mobile+handleType+" value : "+verifyCodeStr);
		try {
			/*验证码信息缓存，10分钟，按秒计算，key为mobile+handleType*/
			cachemanager.set(mobile+handleType, minute*60, verifyCodeStr);
			
			log.info(" cache set success key : "+mobile+handleType+" value : "+cachemanager.get(mobile+handleType));

			Boolean resultRtn = smsServiceManager.sendSms(mobile, tempSn, values);

			String rtnStr = "短信发送失败";
			if(resultRtn){
				rtnStr="短信已发送";
			}
			return new MessageResponse(rtnStr);
		} catch (MessageException e) {
			return new MessageResponse(e.getResponseCode(), e.getMessage());
		}
		
		
	}
	
	/**
	 * 验证接口参数是否为空
	 * @param tempSn
	 * @param mobile
	 * @param vcode
	 */
	private void checkParams(VerifySmsQTO verifySmsQTO) throws MessageException {

		if(verifySmsQTO==null){
			log.error("接口参数对象verifySmsQTO为空");
			throw new MessageException(ResponseCode.P_PARAM_NULL, "接口参数对象verifySmsQTO为空");
		}
		String mobile = verifySmsQTO.getMobile();
		String handleType = verifySmsQTO.getHandleType();
		if (StringUtils.isBlank(mobile)) {
			log.error("手机号码mobile不能为空");
			throw new MessageException(ResponseCode.P_PARAM_NULL, "手机号码mobile不能为空");
		}
		if (StringUtils.isBlank(handleType)) {
			log.error("操作类型handleType不能为空");
			throw new MessageException(ResponseCode.P_PARAM_NULL, "操作类型handleType不能为空");
		}
		if(HandleTypeEnum.getByCode(handleType)==null){
			log.error("操作类型handleType格式错误");
			throw new MessageException(ResponseCode.P_PARAM_ERROR, "操作类型handleType格式错误");
		}
	}

	@Override
	public String getName() {
		return ActionEnum.SMS_SERVICE.getActionName();
	}

}

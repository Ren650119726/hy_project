package com.mockuai.messagecenter.core.manager.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.core.dao.SmsServiceDAO;
import com.mockuai.messagecenter.core.domain.SmsTemplateDO;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.manager.CacheManager;
import com.mockuai.messagecenter.core.manager.SmsServiceManager;
import com.mockuai.messagecenter.core.smsclient.UegateSoap;

@Service
public class SmsServiceManagerImpl implements SmsServiceManager {
	
	@Resource
    private SmsServiceDAO smsServiceDao;

	@Resource
	private CacheManager cacheManager;
	
	/*短信接口配置*/
	private final String spID = "000050";
	private final String password = "yyzws1234";
	private final String accessCode = "1069032239089";
	
	private static final Logger log = LoggerFactory.getLogger(SmsServiceManagerImpl.class);

	@Override
	public Boolean sendSms(String mobile, String tempSn, String... values) throws MessageException {
		
		String content = generateSms(tempSn, values);
		
		return sendSms( mobile, content);
	}

	@Override
	public SendSmsDTO getMobileVerifyCode(SendSmsDTO sendSmsDto)
			throws MessageException {
		SendSmsDTO result =new SendSmsDTO();
		result.setVerifyCode((String)cacheManager.get(sendSmsDto.getMobile()));
		return result;
	}
	
	@Override
	public Boolean deleteMobileVerifyCode(SendSmsDTO sendSmsDto)
			throws MessageException {
		cacheManager.remove(sendSmsDto.getMobile());
		return true;
	}

	/**
	 * 调优易网短信接口
	 * @param mobile
	 * @param content
	 * @return
	 */
	private Boolean sendSms(String mobile,String content) throws MessageException{
		UegateSoap uegatesoap = new  UegateSoap();
		String submitResult=uegatesoap.Submit(spID,
				password, accessCode, content, mobile);
		return true;
	}
	
	/**
	 * 生成短信内容
	 * @param tempSN
	 * @param values
	 * @return
	 */
	private String generateSms(String tempSn,String... values){
		
		String smsTemplate = getSmsTemplate(tempSn);
		
		if(StringUtils.isBlank(smsTemplate)){
			StringBuffer sb = new StringBuffer();
			for(String value : values){
				sb.append(value);
			}
			return sb.toString();
		}
		int index = 0;
		String msg = smsTemplate.replaceFirst("\\{\\w+\\}", values[index++]);
		while(index < values.length){
			msg = msg.replaceFirst("\\{\\w+\\}", values[index++]);
		}

        return msg;
	}
	
	private String getSmsTemplate(String tempSn){
		
		SmsTemplateDO smsTemplateDO = smsServiceDao.getSmsTemplate(tempSn);
		
//		SmsTemplateDTO smsTemplateDTO = null;
//		
//		if(smsTemplateDO!=null){
//			smsTemplateDTO =  new SmsTemplateDTO();
//			BeanUtils.copyProperties(smsTemplateDO, smsTemplateDTO);
//		}
		
		return smsTemplateDO.getTempContent();
	}
}

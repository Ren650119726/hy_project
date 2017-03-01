package com.mockuai.messagecenter.core.manager;

import org.springframework.stereotype.Service;

import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.core.exception.MessageException;

@Service
public interface SmsServiceManager {
	
	/**
	 * 发送短信
	 * @param mobile
	 * @param tempSn
	 * @param values
	 * @return
	 */
	public Boolean sendSms(String mobile,String tempSn,String... values) throws MessageException;
	
	/**
	 * 获取缓存验证码，测试用
	 * @param sendSmsDto
	 * @return
	 * @throws MessageException
	 */
	public SendSmsDTO getMobileVerifyCode(SendSmsDTO sendSmsDto) throws MessageException;
	
	/**
	 * 
	 * @param sendSmsDto
	 * @return
	 * @throws MessageException
	 */
	public Boolean deleteMobileVerifyCode(SendSmsDTO sendSmsDto) throws MessageException;

}

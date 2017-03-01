package com.mockuai.messagecenter.core.dao;

import org.springframework.stereotype.Service;

import com.mockuai.messagecenter.core.domain.SmsTemplateDO;
import com.mockuai.messagecenter.core.exception.MessageException;

@Service
public interface SmsServiceDAO {
	
	public SmsTemplateDO getSmsTemplate(String tempSn) ;
	
}

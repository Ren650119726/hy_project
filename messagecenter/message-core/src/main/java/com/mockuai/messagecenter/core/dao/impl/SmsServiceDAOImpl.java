package com.mockuai.messagecenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.messagecenter.common.constant.ResponseCode;
import com.mockuai.messagecenter.core.dao.SmsServiceDAO;
import com.mockuai.messagecenter.core.domain.SmsTemplateDO;
import com.mockuai.messagecenter.core.exception.MessageException;

@Service
public class SmsServiceDAOImpl extends SqlMapClientDaoSupport implements SmsServiceDAO {

    
    @Override
	public SmsTemplateDO getSmsTemplate(String tempSn) {

    	SmsTemplateDO smsTemplateDO = (SmsTemplateDO) getSqlMapClientTemplate().queryForObject(
                "sms_template.selectByTempSn", tempSn);
    	try {
    		if(smsTemplateDO == null){
    			throw new MessageException(ResponseCode.B_SELECT_ERROR, "找不到模板编号"+tempSn+"对应的数据");
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    		
		return smsTemplateDO;
	}

    
}

package com.mockuai.messagecenter.core.service.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.service.RequestContext;

/**
 * 操作对像基类
 * 
 * @author wujin.zzq
 * 
 */
@Service
public interface Action {

	@SuppressWarnings("rawtypes")
	public MessageResponse execute(RequestContext context) throws MessageException;

	public String getName();
}

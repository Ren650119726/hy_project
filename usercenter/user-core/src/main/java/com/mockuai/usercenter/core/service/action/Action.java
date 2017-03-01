package com.mockuai.usercenter.core.service.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.service.RequestContext;

/**
 * 操作对像基类
 * 
 * @author wujin.zzq
 * 
 */
@Service
public interface Action {

	@SuppressWarnings("rawtypes")
	public UserResponse execute(RequestContext context) throws UserException;

	public String getName();
}

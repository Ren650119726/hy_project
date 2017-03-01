package com.mockuai.usercenter.core.service.action.usermessage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.GlobalMessageDTO;
import com.mockuai.usercenter.common.qto.GlobalMessageQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserMessageManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

/**
 * 获取消息列表
 */
@Service
public class QueryGlobalMessageAction implements Action {
	
	private final Long DEFAULT_PAGE=1L;
	
	private final Integer DEFAULT_PAGE_SIZE =100;
	
	@Resource
	private UserMessageManager userMessageManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();

		try {
			if(request.getParam("globalMessageQTO") ==null){
				return new UserResponse(ResponseCode.P_PARAM_NULL,"globalMessageQTO is null");
			}

			GlobalMessageQTO globalMessageQTO = (GlobalMessageQTO) request.getParam("globalMessageQTO");
			if(globalMessageQTO.getPageSize()== null || globalMessageQTO.getPageSize().intValue() > DEFAULT_PAGE_SIZE){
				globalMessageQTO.setPageSize(DEFAULT_PAGE_SIZE);
			}if(globalMessageQTO.getPageNum() == null){
				globalMessageQTO.setPageNum(DEFAULT_PAGE);
			}
			Long startRow = (globalMessageQTO.getPageNum().longValue()-1) * ( globalMessageQTO.getPageSize().intValue());
			globalMessageQTO.setStartRow(startRow);
			
			List<GlobalMessageDTO> messages = userMessageManager.queryGlobalMessage(globalMessageQTO);
			Long total = userMessageManager.getGlobalMessageTotalCount(globalMessageQTO);
			return new UserResponse(messages, total);
		}catch (UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.QUERY_GLOBAL_MESSAGE.getActionName();
	}

}

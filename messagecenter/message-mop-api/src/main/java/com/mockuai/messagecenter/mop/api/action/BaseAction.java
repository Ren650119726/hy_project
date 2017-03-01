package com.mockuai.messagecenter.mop.api.action;

import com.mockuai.messagecenter.common.api.MessageDispatchService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;
/**
 * @author cwr
 */
public abstract class BaseAction implements Action{
	private MessageDispatchService messageDispatchService;


	
	public MessageDispatchService getMessageDispatchService() {
		return messageDispatchService;
	}



	public void setMessageDispatchService(
			MessageDispatchService messageDispatchService) {
		this.messageDispatchService = messageDispatchService;
	}



	public ResponseFormat getResponseFormat() {
		return ResponseFormat.STANDARD;
	}
}


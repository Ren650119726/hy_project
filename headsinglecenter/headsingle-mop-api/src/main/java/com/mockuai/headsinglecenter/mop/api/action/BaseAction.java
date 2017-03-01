package com.mockuai.headsinglecenter.mop.api.action;

import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

public abstract class BaseAction implements Action {
	@SuppressWarnings("rawtypes")
	private HeadSingleService headSingleService;

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }

	@SuppressWarnings("rawtypes")
	public HeadSingleService getHeadSingleService() {
		return headSingleService;
	}

	@SuppressWarnings("rawtypes")
	public void setHeadSingleService(HeadSingleService headSingleService) {
		this.headSingleService = headSingleService;
	}
}
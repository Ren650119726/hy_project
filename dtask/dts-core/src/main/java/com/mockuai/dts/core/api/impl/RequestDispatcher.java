package com.mockuai.dts.core.api.impl;

import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.core.service.AppContext;
import com.mockuai.dts.core.service.RequestContext;
import com.mockuai.dts.core.service.action.Action;
import com.mockuai.dts.core.service.action.ActionHolder;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * 
 * @author luliang
 *
 */
public class RequestDispatcher {
	private static final Logger log = LoggerFactory.getLogger(RequestDispatcher.class);

	/**
	 * 操作容器
	 */
	private ActionHolder actionHoloder;

	private AppContext appContext;

	public ActionHolder getActionHoloder() {
		return actionHoloder;
	}

	public void setActionHoloder(ActionHolder actionHoloder) {
		this.actionHoloder = actionHoloder;
	}

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public DtsResponse dispatch(DtsRequest req) {
		if (req == null) {
			throw new IllegalArgumentException("request is null!");
		}
		// 单例类限流措施
		ActionCall task = new ActionCall(req);
		DtsResponse response = null;
		try {
			long startTime = System.currentTimeMillis();
			response = task.call();
			return response;
		} catch (Throwable e) {
			log.error("call exception", e);
		}
		return response;
	}

	class ActionCall implements Callable<DtsResponse> {

		private DtsRequest req;

		public ActionCall(DtsRequest request) {
			this.req = request;
		}

		@Override
		public DtsResponse call() throws Exception {
			// 查找具体的请求操作类型
			Action action = actionHoloder.getAction(req.getCommand());
			if (null != action) {
				RequestContext context = new RequestContext(appContext, req);
				DtsResponse response = new DtsResponse(true);
				response = action.execute(context);
				return response;
			} else {
				DtsResponse res = new DtsResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
				return res;
			}
		}
	}
}

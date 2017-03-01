package com.mockuai.imagecenter.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.core.service.action.Action;
import com.mockuai.imagecenter.core.service.action.ActionHolder;
import com.mockuai.imagecenter.core.service.action.RequestContext;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * 
 * @author luliang
 *
 */
@Service
public class RequestDispatcher {
	private static final Logger log = LoggerFactory.getLogger(RequestDispatcher.class);

	/**
	 * 操作容器
	 */
	private ActionHolder actionHolder;

	private AppContext appContext;

	public ActionHolder getActionHolder() {
		return actionHolder;
	}

	public void setActionHolder(ActionHolder actionHolder) {
		this.actionHolder = actionHolder;
	}

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public ImageResponse dispatch(ImageRequest req) {
		if (req == null) {
			throw new IllegalArgumentException("request is null!");
		}
		// 单例类限流措施
		ActionCall task = new ActionCall(req);
		ImageResponse response = null;
		try {
			long startTime = System.currentTimeMillis();
			response = task.call();
			return response;
		} catch (Throwable e) {
			log.error("call exception", e);
		}
		return response;
	}

	class ActionCall implements Callable<ImageResponse> {

		private ImageRequest req;

		public ActionCall(ImageRequest request) {
			this.req = request;
		}

		@Override
		public ImageResponse call() throws Exception {
			// 查找具体的请求操作类型
			Action action = actionHolder.getAction(req.getCommand());
			if (null != action) {
				RequestContext context = new RequestContext(appContext, req);
				ImageResponse response = new ImageResponse(true);
				response = action.execute(context);
				return response;
			} else {
				ImageResponse res = new ImageResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
				return res;
			}
		}
	}
}

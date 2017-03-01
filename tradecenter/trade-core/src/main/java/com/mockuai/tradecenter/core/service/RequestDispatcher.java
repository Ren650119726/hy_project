package com.mockuai.tradecenter.core.service;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ParamEnum;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.filter.FilterManager;
import com.mockuai.tradecenter.core.filter.FilterChain;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.service.action.ActionHolder;

/**
 * 
 * @author wujin.zzq
 *
 */
public class RequestDispatcher {
	private static final Logger log = LoggerFactory.getLogger(RequestDispatcher.class);
	private static final Logger accessLog = LoggerFactory.getLogger("accessLogger");
	/**
	 * 操作容器
	 */
	private ActionHolder actionHoloder;

	private AppContext appContext;

	private Random random = new Random();

//	private LimitBarrier barrier = new LimitBarrier();

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public ActionHolder getActionHoloder() {
		return actionHoloder;
	}

	public void setActionHoloder(ActionHolder actionHoloder) {
		this.actionHoloder = actionHoloder;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TradeResponse<Boolean> dispatch(TradeRequest req) {
		if(req == null){
			throw new IllegalArgumentException("request is null!");
		}
		req.setAttribute(ParamEnum.SYS_APP_CODE.getValue(), "trade");
		// 单例类限流措施
		ActionCall task = new ActionCall(req);
		TradeResponse<Boolean> response = null;
		try {
			long startTime = System.currentTimeMillis();
			response = task.call();
//			logBizRt(req,startTime,response);
			return response;
		} catch (Throwable e) {
			log.error("call exception", e);
		}
		return response;
	}

	/**
	 * 取得业务code,如果只传了业务ID，也转下code,同时放到attribute里
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getAppCode(RequestContext context, TradeRequest req) {
		//TODO impl
		String appCode = null;
		if (null != req.getAttribute(ParamEnum.SYS_APP_CODE.getValue())) {
			appCode = req.getAttribute(ParamEnum.SYS_APP_CODE.getValue()).toString();
		}
//		if (StringUtils.isBlank(bizCode) && null != req.getParam(RdsConsts.BIZ_ID)) {
//			long bizId = Long.parseLong(req.getParam(RdsConsts.BIZ_ID).toString());
//			req.setAttribute(RdsConsts.BIZ_ID, bizId);
//			try {
//				BizDO biz = bizManager.queryBiz(context, new BizDO(bizId));
//				if (null == biz) {
//					res.setErrorMap(ErrorCodeEnum.BIZ_NOT_EXIST.getCodeMap());
//					res.setSuccess(false);
//				} else {
//					bizCode = biz.getCode();
//				}
//			} catch (TradeException e) {
//				log.error("getBizCode-failde-bizId:" + bizId, e);
//			}
//		}
		// 放到attribute里，供其他地方查询
		req.setAttribute(ParamEnum.SYS_APP_CODE.getValue(), appCode);
		return appCode;
	}
	
	private boolean allowAccess(String bizCode, Action action) {
		//TODO
		return true;
	}

	/**
	 * <pre>
	 * desc: action处理call 
	 * created: Oct 25, 2013 11:25:10 AM
	 * author: xiangfeng
	 * todo: 
	 * history:
	 * </pre>
	 */
	class ActionCall implements Callable<TradeResponse> {

		private TradeRequest req;

		public ActionCall(TradeRequest req) {
			super();
			this.req = req;
		}

		@Override
		public TradeResponse<Boolean> call() {

			// 查找具体的请求操作类型
			Action action = actionHoloder.getAction(req.getCommand());
			if (null != action) {
				RequestContext context = new RequestContext(appContext, req);
				// set request here
				FilterManager filterManager = FilterManager.getInstance();
				TradeResponse<Boolean> re = new TradeResponse<Boolean>(true);
				//获取appCode
				String appCode = getAppCode(context, req);
				if (!allowAccess(appCode, action)) {
					re = new TradeResponse<Boolean>(ResponseCode.BIZ_E_REQUEST_FORBIDDEN);
					return re;
				}

				/**
				 * 以下时间变量用来统计整个执行过程中的filter.before,action以及filter.after的耗时
				 */
				long startTime = System.currentTimeMillis();
				long beforeFilterEndTime = 0L;
				long actionEndTime = 0L;
				long afterFilterEndTime = 0L;
				TradeResponse<Boolean> res = null;
				try {
					// FIXME pass the correct appCode
					FilterChain filterChain = filterManager.getFilterChain(appCode, action.getName());
					
					//1. 执行filter.before流程
					TradeResponse<Boolean> beforeFilterResult = filterChain.before(context);
					beforeFilterEndTime = System.currentTimeMillis();
					
					
					//2. 如果filter.before流程成功，则执行action，否则不执行
					if (beforeFilterResult.isSuccess()) {
						// 执行操作
						res = action.execute(context);
						context.setResponse(res);
					}else{
						return beforeFilterResult;
					}
					actionEndTime = System.currentTimeMillis();
					
					//3. 执行filter.after流程
					TradeResponse<Boolean> afterFilterResult = filterChain.after(context);
					afterFilterEndTime = System.currentTimeMillis();
					if (afterFilterResult.isSuccess() == false) {
						res = afterFilterResult;
					}
					return res;
				} catch (TradeException e) {
					res = new TradeResponse<Boolean>(e.getResponseCode(), e.getMessage());
					log.error("do action:" + req.getCommand() + " occur Exception:", e);
					return res;
				} catch (Exception e) {
					res = new TradeResponse<Boolean>(ResponseCode.SYS_E_SERVICE_EXCEPTION);
					log.error("do action:" + req.getCommand() + " occur Exception:", e);
					return res;
				}finally{
					accessLog.info("action:{}, result:{}, filterBeforeCost:{}, actionCost:{}, filterAfterCost:{}, request:{}",
							req.getCommand(),res.getCode(), (beforeFilterEndTime-startTime),
							(actionEndTime-beforeFilterEndTime), (afterFilterEndTime-actionEndTime), JsonUtil.toJson(req));
					accessLog.debug("response:{}", JsonUtil.toJson(res));
				}
			} else {
				// 系统未建立相应的请求操作
				accessLog.error("no action mapping for:" + req.getCommand());
				TradeResponse<Boolean> res = new TradeResponse<Boolean>(ResponseCode.PARAM_E_ACTION_NOT_EXIST);
				return res;
			}
		}

	}

	private class DaemonThreadFactory implements ThreadFactory {
		final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		public DaemonThreadFactory() {
			super();
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() :
					Thread.currentThread().getThreadGroup();
			namePrefix = "media-Dispatcher-pool" +
					poolNumber.getAndIncrement() +
					"-thread-";
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r,
					namePrefix + threadNumber.getAndIncrement(),
					0);
			if (!t.isDaemon()) {
				t.setDaemon(true);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}
	}
}

package com.mockuai.rainbowcenter.core.service;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.constant.ParamEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.filter.FilterChain;
import com.mockuai.rainbowcenter.core.filter.FilterManager;
import com.mockuai.rainbowcenter.core.service.action.Action;
import com.mockuai.rainbowcenter.core.service.action.ActionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author wujin.zzq
 *
 */
public class RequestDispatcher {
	private static final Logger log = LoggerFactory.getLogger(RequestDispatcher.class);
	/**
	 * 操作容器
	 */
	private ActionHolder actionHoloder;

	private RainbowContext rainbowContext;

	private Random random = new Random();

//	private LimitBarrier barrier = new LimitBarrier();

	public RainbowContext getRainbowContext() {
		return rainbowContext;
	}

	public void setRainbowContext(RainbowContext rainbowContext) {
		this.rainbowContext = rainbowContext;
	}

	public ActionHolder getActionHoloder() {
		return actionHoloder;
	}

	public void setActionHoloder(ActionHolder actionHoloder) {
		this.actionHoloder = actionHoloder;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RainbowResponse dispatch(RainbowRequest req) {
		if(req == null){
			throw new IllegalArgumentException("request is null!");
		}
		// 单例类限流措施
		ActionCall task = new ActionCall(req);
		RainbowResponse response = null;
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
	private String getAppCode(RequestContext context, RainbowRequest req) {
		//TODO edb
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
	class ActionCall implements Callable<RainbowResponse> {

		private RainbowRequest req;

		public ActionCall(RainbowRequest req) {
			super();
			this.req = req;
		}

		@Override
		public RainbowResponse call() {

			// 查找具体的请求操作类型
			Action action = actionHoloder.getAction(req.getCommand());
			if (null != action) {
				RequestContext context = new RequestContext(rainbowContext, req);
				// set request here
				FilterManager filterManager = FilterManager.getInstance();
				RainbowResponse re = new RainbowResponse(true);
				//获取appCode
				String appCode = getAppCode(context, req);
				if (!allowAccess(appCode, action)) {
					re = new RainbowResponse(ResponseCode.BIZ_E_REQUEST_FORBIDDEN);
					return re;
				}

				/**
				 * 以下时间变量用来统计整个执行过程中的filter.before,action以及filter.after的耗时
				 */
				long startTime = System.currentTimeMillis();
				long beforeFilterEndTime = 0L;
				long actionEndTime = 0L;
				long afterFilterEndTime = 0L;
				RainbowResponse res = null;
				try {
					// FIXME pass the correct appCode
					FilterChain filterChain = filterManager.getFilterChain(appCode, action.getName());
					
					//1. 执行filter.before流程
					boolean beforeFilterResult = filterChain.before(context);
					beforeFilterEndTime = System.currentTimeMillis();
					
					
					//2. 如果filter.before流程成功，则执行action，否则不执行
					if (beforeFilterResult == true) {
						// 执行操作
						res = action.execute(context);
						context.setResponse(res);
					}else{
						res = context.getResponse();
					}
					actionEndTime = System.currentTimeMillis();
					
					//3. 执行filter.after流程
					boolean afterFilterResult = filterChain.after(context);
					afterFilterEndTime = System.currentTimeMillis();
					if (afterFilterResult == false) {
						res = context.getResponse();
					}
					return res;
				} catch (RainbowException e) {
					res = new RainbowResponse(e.getResponseCode(), e.getMessage());
					log.error("do action:" + req.getCommand() + " occur Exception:", e);
					return res;
				} catch (Exception e) {
					res = new RainbowResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
					log.error("do action:" + req.getCommand() + " occur Exception:", e);
					return res;
				}finally{
					if(System.currentTimeMillis()%128 == 1){
						log.info("result:"+res.getCode()+",filterBeforeCost:"+(beforeFilterEndTime-startTime)+
								",actionCost:"+(actionEndTime-beforeFilterEndTime)+",filterAfterCost:"+(afterFilterEndTime-actionEndTime));
					}
				}
			} else {
				// 系统未建立相应的请求操作
				log.error("no action mapping for:" + req.getCommand());
				RainbowResponse res = new RainbowResponse(ResponseCode.PARAM_E_ACTION_NOT_EXIST);
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

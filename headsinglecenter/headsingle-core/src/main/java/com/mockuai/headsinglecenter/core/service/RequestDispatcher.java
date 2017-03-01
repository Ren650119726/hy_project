package com.mockuai.headsinglecenter.core.service;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.common.constant.ParamEnum;
import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.filter.FilterChain;
import com.mockuai.headsinglecenter.core.filter.FilterManager;
import com.mockuai.headsinglecenter.core.service.action.Action;
import com.mockuai.headsinglecenter.core.service.action.ActionHolder;

/**
 * @author wujin.zzq
 */
public class RequestDispatcher {
    private static final Logger log = LoggerFactory.getLogger(RequestDispatcher.class);
    /**
     * 操作容器
     */
    private ActionHolder actionHolder;

    private AppContext appContext;

    private Random random = new Random();

    public AppContext getAppContext() {
        return appContext;
    }

    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }

    public ActionHolder getActionHolder() {
        return actionHolder;
    }

    public void setActionHolder(ActionHolder actionHolder) {
        this.actionHolder = actionHolder;
    }

    @SuppressWarnings({"rawtypes"})
    public HeadSingleResponse dispatch(HeadSingleRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("request is null!");
        }
        // 单例类限流措施
        ActionCall task = new ActionCall(req);
        HeadSingleResponse response = null;
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
    @SuppressWarnings({"rawtypes"})
    private String getAppCode(RequestContext context, HeadSingleRequest req) {
        //TODO impl
        String appCode = null;
        if (null != req.getAttribute(ParamEnum.SYS_APP_CODE.getValue())) {
            appCode = req.getAttribute(ParamEnum.SYS_APP_CODE.getValue()).toString();
        }
        // 放到attribute里，供其他地方查询
        req.setAttribute(ParamEnum.SYS_APP_CODE.getValue(), appCode);
        return appCode;
    }

    private boolean allowAccess(String bizCode, Action action) {
        //FIXME
        return true;
    }

    /**
     * <pre>
     * desc: action处理call
     * created: Oct 25, 2013 11:25:10 AM
     * author: xiangfeng
     * history:
     * </pre>
     */
    class ActionCall implements Callable<HeadSingleResponse> {

        private HeadSingleRequest req;

        public ActionCall(HeadSingleRequest req) {
            super();
            this.req = req;
        }

        @Override
        public HeadSingleResponse call() {

            // 查找具体的请求操作类型
            Action action = actionHolder.getAction(req.getCommand());
            if (null != action) {
                RequestContext context = new RequestContext(appContext, req);
                // set request here
                FilterManager filterManager = FilterManager.getInstance();
                HeadSingleResponse re = new HeadSingleResponse(true);
                //获取appCode
                String appCode = getAppCode(context, req);
                if (!allowAccess(appCode, action)) {
                    re = new HeadSingleResponse(ResponseCode.REQUEST_FORBIDDEN);
                    return re;
                }

                /**
                 * 以下时间变量用来统计整个执行过程中的filter.before,action以及filter.after的耗时
                 */
                long startTime = System.currentTimeMillis();
                long beforeFilterEndTime = 0L;
                long actionEndTime = 0L;
                long afterFilterEndTime = 0L;
                HeadSingleResponse res = null;
                try {
                    // FIXME pass the correct appCode
                    FilterChain filterChain = filterManager.getFilterChain(appCode, action.getName());

                    //1. 执行filter.before流程
                    HeadSingleResponse beforeFilterResult = filterChain.before(context);
                    beforeFilterEndTime = System.currentTimeMillis();


                    //2. 如果filter.before流程成功，则执行action，否则不执行
                    if (beforeFilterResult.isSuccess()) {
                        // 执行操作
                        res = action.execute(context);
                        context.setResponse(res);
                    } else {
                        return beforeFilterResult;
                    }
                    actionEndTime = System.currentTimeMillis();

                    //3. 执行filter.after流程
                    HeadSingleResponse afterFilterResult = filterChain.after(context);
                    afterFilterEndTime = System.currentTimeMillis();
                    if (afterFilterResult.isSuccess() == false) {
                        return afterFilterResult;
                    }
                    return res;
                } catch (HeadSingleException e) {
                    res = new HeadSingleResponse(e.getCode(), e.getMessage());
                    log.error("do action:" + req.getCommand() + " occur Exception:", e);
                    return res;
                } catch (Exception e) {
                    res = new HeadSingleResponse(ResponseCode.SERVICE_EXCEPTION);
                    log.error("do action:" + req.getCommand() + " occur Exception:", e);
                    return res;
                } finally {
                    if (System.currentTimeMillis() % 128 == 1) {
                        log.info("result:" + res.getResCode() + ",filterBeforeCost:" + (beforeFilterEndTime - startTime) +
                                ",actionCost:" + (actionEndTime - beforeFilterEndTime) + ",filterAfterCost:" + (afterFilterEndTime - actionEndTime));
                    }
                }
            } else {
                // 系统未建立相应的请求操作
                log.error("no action mapping for:" + req.getCommand());
                HeadSingleResponse res = new HeadSingleResponse(ResponseCode.ACTION_NO_EXIST);
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
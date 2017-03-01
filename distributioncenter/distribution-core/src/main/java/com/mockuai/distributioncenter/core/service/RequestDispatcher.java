package com.mockuai.distributioncenter.core.service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ParamEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.distributioncenter.core.service.action.ActionHolder;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.filter.FilterChain;
import com.mockuai.distributioncenter.core.filter.FilterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by duke on 15/10/28.
 */
@Service
public class RequestDispatcher {
    private static final Logger log = LoggerFactory.getLogger(RequestDispatcher.class);

    @Autowired
    private ActionHolder actionHolder;

    @Autowired
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    public DistributionResponse dispatch(Request req) {
        if (req == null) {
            throw new IllegalArgumentException("request is null!");
        }
        // 单例类限流措施
        ActionCall task = new ActionCall(req);
        DistributionResponse response = null;
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
    @SuppressWarnings({"unchecked", "rawtypes"})
    private String getAppCode(RequestContext context, Request req) {
        //TODO impl
        String appCode = null;
        if (null != req.getAttribute(ParamEnum.SYS_APP_CODE.getValue())) {
            appCode = req.getAttribute(ParamEnum.SYS_APP_CODE.getValue()).toString();
        }
        req.setAttribute(ParamEnum.SYS_APP_CODE.getValue(), appCode);
        return appCode;
    }

    private boolean allowAccess(String bizCode, Action action) {
        //FIXME
        return true;
    }

    class ActionCall implements Callable<DistributionResponse> {

        private Request req;

        public ActionCall(Request req) {
            super();
            this.req = req;
        }

        public DistributionResponse call() {

            // 查找具体的请求操作类型
            Action action = actionHolder.getAction(req.getCommand());
            if (null != action) {
                RequestContext context = new RequestContext(appContext, req);
                // set request here
                FilterManager filterManager = FilterManager.getInstance();
                DistributionResponse re = new DistributionResponse(true);
                //获取appCode
                String appCode = getAppCode(context, req);
                if (!allowAccess(appCode, action)) {
                    re = new DistributionResponse(ResponseCode.REQUEST_FORBBIDEN);
                    return re;
                }

                /**
                 * 以下时间变量用来统计整个执行过程中的filter.before,action以及filter.after的耗时
                 */
                long startTime = System.currentTimeMillis();
                long beforeFilterEndTime = 0L;
                long actionEndTime = 0L;
                long afterFilterEndTime = 0L;
                DistributionResponse res = null;
                try {
                    // FIXME pass the correct appCode
                    FilterChain filterChain = filterManager.getFilterChain(appCode, action.getName());

                    //1. 执行filter.before流程
                    DistributionResponse beforeFilterResult = filterChain.before(context);
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
                    DistributionResponse afterFilterResult = filterChain.after(context);
                    afterFilterEndTime = System.currentTimeMillis();
                    if (afterFilterResult.isSuccess() == false) {
                        return afterFilterResult;
                    }
                    return res;
                } catch (DistributionException e) {
                    res = new DistributionResponse(e.getCode(), e.getMessage());
                    log.error("do action:" + req.getCommand() + " occur Exception:", e);
                    return res;
                } catch (Exception e) {
                    res = new DistributionResponse(ResponseCode.SERVICE_EXCEPTION);
                    log.error("do action:" + req.getCommand() + " occur Exception:", e);
                    return res;
                } finally {
                    if (System.currentTimeMillis() % 128 == 1) {
                        log.info("result:" + res.getCode() + ",filterBeforeCost:" + (beforeFilterEndTime - startTime) +
                                ",actionCost:" + (actionEndTime - beforeFilterEndTime) + ",filterAfterCost:" + (afterFilterEndTime - actionEndTime));
                    }
                }
            } else {
                // 系统未建立相应的请求操作
                log.error("no action mapping for:" + req.getCommand());
                DistributionResponse res = new DistributionResponse(ResponseCode.ACTION_NO_EXIST);
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

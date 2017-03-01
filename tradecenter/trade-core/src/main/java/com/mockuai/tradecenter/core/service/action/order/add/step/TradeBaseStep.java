package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zengzhangqiang on 5/19/16.
 */
public abstract class TradeBaseStep implements Step {
    protected static final Logger logger = LoggerFactory.getLogger(TradeBaseStep.class);
    private ThreadLocal threadLocal = new ThreadLocal();


    public abstract StepName getName();

    /**
     * 继承该方法，需要遵循如下规范，这样方便后期维护：
     * （1）获取外部参数，使用getParam方法：
     * @see com.mockuai.tradecenter.core.service.action.order.add.step.TradeBaseStep#getParam
     * （2）获取内部参数，使用getAttr方法：
     * @see com.mockuai.tradecenter.core.service.action.order.add.step.TradeBaseStep#getAttr
     * （3）传递内部参数，使用setAttr方法：
     * @see com.mockuai.tradecenter.core.service.action.order.add.step.TradeBaseStep#setAttr
     * @return
     */
    protected abstract TradeResponse execute();


    public TradeResponse execute(RequestContext requestContext){
        try{
            threadLocal.set(requestContext);
            return execute();
        }finally {
            threadLocal.remove();
        }
    }

    /**
     * 封装getParam方法，一来方便具体交易step实现类获取外部参数;二来方便在代码编译时期统计交易step实现类总共取了哪些外部参数
     *
     * @param key
     * @return
     * @throws IllegalArgumentException
     */
    protected Object getParam(String key) throws IllegalArgumentException {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key is null");
        }

        //如果requestContext为空，则说明step框架的实现有问题，直接抛出运行时异常，让错误尽快暴露
        RequestContext requestContext = (RequestContext)threadLocal.get();
        if (requestContext == null) {
            throw new IllegalStateException("requestContext is null");
        }

        return requestContext.getRequest().getParam(key);
    }

    /**
     * 封装getAttr方法，一来方便具体交易step实现类获取内部参数;二来方便在代码编译时期统计交易step实现类总共取了哪些内部参数
     * @param key
     * @return
     * @throws IllegalArgumentException
     */
    protected Object getAttr(String key) throws IllegalArgumentException {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key is null");
        }

        //如果requestContext为空，则说明step框架的实现有问题，直接抛出运行时异常，让错误尽快暴露
        RequestContext requestContext = (RequestContext)threadLocal.get();
        if (requestContext == null) {
            throw new IllegalStateException("requestContext is null");
        }

        return requestContext.get(key);
    }

    /**
     * 封装setAttr方法，一来方便具体交易step实现类存放内部参数;二来方便在代码编译时期统计交易step实现类总共存放了哪些内部参数
     * @param key
     * @param value
     * @throws IllegalArgumentException
     */
    protected void setAttr(String key, Object value) throws IllegalArgumentException {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key is null");
        }

        //如果requestContext为空，则说明调用有问题，直接抛出运行时异常，让错误尽快暴露
        RequestContext requestContext = (RequestContext)threadLocal.get();
        if (requestContext == null) {
            throw new IllegalArgumentException("requestContext is null");
        }

        requestContext.put(key, value);
    }

    protected Object getBean(String beanName) throws IllegalArgumentException{
        if (StringUtils.isBlank(beanName)) {
            throw new IllegalArgumentException("key is null");
        }

        //如果requestContext为空，则说明step框架的实现有问题，直接抛出运行时异常，让错误尽快暴露
        RequestContext requestContext = (RequestContext)threadLocal.get();
        if (requestContext == null) {
            throw new IllegalStateException("requestContext is null");
        }

        return requestContext.getAppContext().getBean(beanName);
    }
}

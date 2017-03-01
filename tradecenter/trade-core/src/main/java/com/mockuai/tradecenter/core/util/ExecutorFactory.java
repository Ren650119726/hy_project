package com.mockuai.tradecenter.core.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author fish
 * 
 */
public class ExecutorFactory implements FactoryBean, InitializingBean, DisposableBean {

    private int corePoolSize = 2;

    // ------- 当前使用无边界(LinkedBlockingQueue)的线程池，所以maximumPoolSize 和 keepAliveSeconds 设置都无效
    private int maximumPoolSize = 10;

    private int keepAliveSeconds = 10;

    // ---- 上面都是线程池的配置

    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutor getObject() throws Exception {
        return this.threadPoolExecutor;
    }

    public Class<ThreadPoolExecutor> getObjectType() {
        return ThreadPoolExecutor.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadFactory = new LogExceptionTheadFactory(threadFactory);
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),threadFactory);
    }

    public void destroy() throws Exception {
        threadPoolExecutor.shutdown();
    }

    private static final class LogUncaughtExceptionHandler implements
            UncaughtExceptionHandler {
        private Log        logger = LogFactory.getLog(this.getClass());

        public void uncaughtException(Thread t, Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("caught exception in thrad" + t.getName()
                        + " exception class:" + e.getClass().getName()
                        + " message:" + e.getMessage(), e);
            }
        }
    }

    private static final class LogExceptionTheadFactory implements
            ThreadFactory {

        private ThreadFactory factory;

        public LogExceptionTheadFactory(ThreadFactory factory) {
            super();
            this.factory = factory;
        }

        public Thread newThread(Runnable r) {
            Thread t = factory.newThread(r);
            t.setUncaughtExceptionHandler(new LogUncaughtExceptionHandler());
            return t;
        }
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
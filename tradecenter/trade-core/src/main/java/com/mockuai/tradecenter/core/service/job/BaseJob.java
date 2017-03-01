package com.mockuai.tradecenter.core.service.job;

import java.io.Serializable;

import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by yeliming on 16/4/21.
 */
public abstract class BaseJob extends QuartzJobBean implements Serializable {

    /*protected SysConfigManager sysConfigManager;
    protected EdbUtils edbUtils;
    protected ThreadPoolManager threadPoolManager;
    protected SchedulerContext schedulerContext;
    protected ApplicationContext applicationContext;

    public SysConfigManager getSysConfigManager() {
        return sysConfigManager;
    }

    public void setSysConfigManager(SysConfigManager sysConfigManager) {
        this.sysConfigManager = sysConfigManager;
    }

    public EdbUtils getEdbUtils() {
        return edbUtils;
    }

    public void setEdbUtils(EdbUtils edbUtils) {
        this.edbUtils = edbUtils;
    }

    public ThreadPoolManager getThreadPoolManager() {
        return threadPoolManager;
    }

    public void setThreadPoolManager(ThreadPoolManager threadPoolManager) {
        this.threadPoolManager = threadPoolManager;
    }

    public SchedulerContext getSchedulerContext() {
        return schedulerContext;
    }

    public void setSchedulerContext(SchedulerContext schedulerContext) {
        this.schedulerContext = schedulerContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            schedulerContext = context.getScheduler().getContext();
            applicationContext = (ApplicationContext) schedulerContext.get("applicationContextKey");
            this.setSysConfigManager((SysConfigManager) applicationContext.getBean("sysConfigManager"));
            this.setEdbUtils((EdbUtils) applicationContext.getBean("edbUtils"));
            this.setThreadPoolManager((ThreadPoolManager) applicationContext.getBean("threadPoolManager"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        poll(context);
    }

    protected abstract void poll(JobExecutionContext context) throws JobExecutionException;*/

}

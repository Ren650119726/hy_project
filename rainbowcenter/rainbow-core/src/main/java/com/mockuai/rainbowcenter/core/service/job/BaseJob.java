package com.mockuai.rainbowcenter.core.service.job;

import com.mockuai.rainbowcenter.core.manager.HsThreadPoolManager;
import com.mockuai.rainbowcenter.core.manager.SysConfigManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by yeliming on 16/4/21.
 */
public abstract class BaseJob extends QuartzJobBean {

    protected SysConfigManager sysConfigManager;
    protected HsThreadPoolManager hsThreadPoolManager;
    protected SchedulerContext schedulerContext;
    protected ApplicationContext applicationContext;

    public SysConfigManager getSysConfigManager() {
        return sysConfigManager;
    }

    public void setSysConfigManager(SysConfigManager sysConfigManager) {
        this.sysConfigManager = sysConfigManager;
    }


    public HsThreadPoolManager getHsThreadPoolManage() {
        return hsThreadPoolManager;
    }

    public void setHsThreadPoolManage(HsThreadPoolManager hsThreadPoolManager) {
        this.hsThreadPoolManager = hsThreadPoolManager;
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
            this.setHsThreadPoolManage((HsThreadPoolManager)applicationContext.getBean("hsThreadPoolManager"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        poll(context);
    }

    protected abstract void poll(JobExecutionContext context) throws JobExecutionException;

}

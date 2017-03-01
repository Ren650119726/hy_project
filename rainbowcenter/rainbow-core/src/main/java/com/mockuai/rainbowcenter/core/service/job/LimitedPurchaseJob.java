package com.mockuai.rainbowcenter.core.service.job;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huangsiqian on 2016/11/10.
 */
public class LimitedPurchaseJob extends BaseJob {

    private static final Logger log = LoggerFactory.getLogger(LimitedPurchaseJob.class);

    public void LimitedPoll() throws RainbowException {

        log.info("start to LimitedPurchaseJob");
        this.hsThreadPoolManager.updateLimitedPurchaseStatus();

    }

    @Override
    protected void poll(JobExecutionContext context) throws JobExecutionException {

        try {
            LimitedPoll();
        } catch (RainbowException e) {
            e.printStackTrace();
        }
    }



}

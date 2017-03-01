package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.tradecenter.common.api.TradeResponse;

/**
 * Created by zengzhangqiang on 5/19/16.
 */
public class StepResp {
    private boolean success;
    private Step failedStep;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Step getFailedStep() {
        return failedStep;
    }

    public void setFailedStep(Step failedStep) {
        this.failedStep = failedStep;
    }
}

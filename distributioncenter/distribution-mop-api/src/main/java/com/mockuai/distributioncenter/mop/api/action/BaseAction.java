package com.mockuai.distributioncenter.mop.api.action;

import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.Action;

/**
 * Created by duke on 15/10/28.
 */
public abstract class BaseAction implements Action {
    private DistributionService distributionService;

    public DistributionService getDistributionService() {
        return distributionService;
    }

    public void setDistributionService(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.STANDARD;
    }
}

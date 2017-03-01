package com.mockuai.distributioncenter.core.service.action.gains;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lizg on 2016/9/2.
 */
@Service
public class GetGainSettingAction extends TransAction{

    private static final Logger logger = LoggerFactory.getLogger(GetGainSettingAction.class);

    @Autowired
    private GainsSetManager gainsSetManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        String appKey = (String) request.getParam("appKey");
        try {
            GainsSetDTO getGains = gainsSetManager.get();
            return new DistributionResponse(getGains);
        } catch (DistributionException e) {
            logger.error("Action failed {}", getName());
            return new DistributionResponse(e.getCode(), e.getMessage());

        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_GAINS_SET.getActionName();
    }
}

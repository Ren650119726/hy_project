package com.mockuai.distributioncenter.core.service.action;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by duke on 15/10/28.
 */
public interface Action<T> {
    DistributionResponse execute(RequestContext<T> context) throws DistributionException;

    String getName();
}

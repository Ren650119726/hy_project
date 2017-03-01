package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.MarketingManager;
import com.mockuai.marketingcenter.client.MarketingClient;
import com.mockuai.marketingcenter.common.api.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/6/6.
 */
@Service
public class MarketingManagerImpl implements MarketingManager {
    private static final Logger log = LoggerFactory.getLogger(MarketingManagerImpl.class);

    @Autowired
    private MarketingClient marketingClient;

    @Override
    public Boolean grantActivityCouponWithNumber(Long activityCouponId, Long receiverId, Integer num, String appKey) throws DistributionException {
        Response<Boolean> response = marketingClient.grantActivityCouponWithNumber(activityCouponId, receiverId, num, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("grant activity coupon with number error, errMsg: {}", response.getMessage());
            return false;
        }
    }
}

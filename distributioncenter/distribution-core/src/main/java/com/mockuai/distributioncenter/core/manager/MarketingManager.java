package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * Created by duke on 16/6/6.
 */
public interface MarketingManager {
    Boolean grantActivityCouponWithNumber(Long activityCouponId, Long receiverId, Integer num, String appKey) throws DistributionException;
}

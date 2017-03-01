package com.mockuai.giftscenter.core.manager; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;

import java.util.List;

/**
 * Created by guansheng on 2016/7/15.
 */
public interface MarketingManager {
    List<ActivityCouponDTO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO, String appKey) throws GiftsException;


    Boolean grantActivityCoupons(long receiverId, long couponId, int num, int grantSource, String appKey) throws GiftsException;
}

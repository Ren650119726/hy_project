package com.mockuai.shopcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.shopcenter.core.exception.ShopException;

import java.util.List;

/**
 * Created by yindingyu on 16/1/14.
 */
public interface CouponManager {
    List<ActivityCouponDTO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO, String appKey) throws ShopException;
}

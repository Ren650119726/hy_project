package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使优惠券失效
 */
@Service
public class InvalidActivityCouponAction implements Action<Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidActivityCouponAction.class);

    @Resource
    private ActivityCouponManager activityCouponManager;

    public MarketingResponse<Void> execute(final RequestContext<Void> request) throws MarketingException {

        String bizCode = (String) request.get("bizCode");
        Long couponId = (Long) request.getRequest().getParam("couponId");
        Long activityCreatorId = (Long) request.getRequest().getParam("activityCreatorId");

        if (couponId == null) {
            return new MarketingResponse<Void>(ResponseCode.PARAMETER_NULL, "couponId is null");
        }

        if (activityCreatorId == null) {
            return new MarketingResponse<Void>(ResponseCode.PARAMETER_NULL, "activityCreatorId is null");
        }

        ActivityCouponQTO activityCouponQTO = new ActivityCouponQTO();
        activityCouponQTO.setId(couponId);
        activityCouponQTO.setBizCode(bizCode);

        List<ActivityCouponDO> activityCouponDOs = activityCouponManager.queryActivityCoupon(activityCouponQTO);

        if (activityCouponDOs.isEmpty()) {
            return new MarketingResponse<Void>(ResponseCode.COUPON_NOT_EXIST);
        }

        try {

            activityCouponManager.invalidActivityCoupon(couponId, activityCreatorId, activityCouponDOs.get(0).getActivityId(), bizCode);
            return MarketingUtils.getSuccessResponse();
        } catch (MarketingException e) {
            LOGGER.error("Action:" + request.getRequest().getCommand(), e);
            return MarketingUtils.getFailResponse(e.getCode(), e.getMessage());
        }
    }

    public String getName() {
        return ActionEnum.INVALID_ACTIVITY_COUPON.getActionName();
    }
}
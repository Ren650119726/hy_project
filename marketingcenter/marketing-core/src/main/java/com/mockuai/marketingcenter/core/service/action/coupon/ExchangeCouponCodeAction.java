package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.CouponCodeManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by edgar.zr on 11/6/15.
 * 用户输入优惠码领取优惠券
 * 一卡一码／通码后新增逻辑
 */
@Service
public class ExchangeCouponCodeAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeCouponCodeAction.class.getName());

    @Autowired
    private ActivityCouponManager activityCouponManager;

    @Autowired
    private CouponCodeManager couponCodeManager;

    @Autowired
    private GrantedCouponManager grantedCouponManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        String code = (String) context.getRequest().getParam("code");
        Long userId = (Long) context.getRequest().getParam("userId");
        String bizCode = (String) context.get("bizCode");

        MarketPreconditions.checkNotNull(userId, "userId");
        MarketPreconditions.checkNotNull(code, "code");
        if (code.length() != 8) {
            return new MarketingResponse(ResponseCode.BIZ_E_THE_COUPON_CODE_IS_INVALID);
        }

        Integer couponType;
        try {
            couponType = Integer.valueOf(code.substring(0, 1));
        } catch (Exception e) {
            LOGGER.error("error to get activity coupon info, code : {}", code, e);
            return new MarketingResponse(ResponseCode.BIZ_E_THE_COUPON_CODE_IS_INVALID);
        }

        if (couponType == null || couponType.intValue() != CouponType.TYPE_PER_CODE.getValue()
                && couponType.intValue() != CouponType.TYPE_COMMON_CODE.getValue()) {
            return new MarketingResponse(ResponseCode.BIZ_E_THE_COUPON_CODE_IS_INVALID);
        }

        // 根据优惠券码型查询优惠券
        ActivityCouponDO activityCouponDO = null;
        CouponCodeDO couponCodeDO = null;
        if (couponType == CouponType.TYPE_COMMON_CODE.getValue().intValue()) {// 通用码
            activityCouponDO = activityCouponManager.getByCode(code);
        } else if (couponType == CouponType.TYPE_PER_CODE.getValue().intValue()) {// 一卡一码
            couponCodeDO = couponCodeManager.getByCode(code);
            // 已经被领取
            if (couponCodeDO.getStatus().intValue() != UserCouponStatus.UN_ACTIVATE.getValue()) {
                return new MarketingResponse(ResponseCode.BIZ_E_THE_COUPON_CODE_ACTIVATED);
            }
            activityCouponDO = activityCouponManager.getActivityCoupon(couponCodeDO.getCouponId(), couponCodeDO.getActivityCreatorId(), bizCode);
        }

        if (activityCouponDO.getStatus() == ActivityCouponStatus.INVALID.getValue().intValue()
                || new Date().after(activityCouponDO.getEndTime())) {
            return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_COUPON_STATUS_ILLEGAL, "优惠活动无效");
        }

        if (grantedCouponManager.isOutOfUserReceiveLimit(activityCouponDO.getId(), userId, activityCouponDO.getUserReceiveLimit())) {
            return new MarketingResponse(ResponseCode.ACTIVITY_COUPON_RECEIVED_OUT_OF_LIMIT);
        }
        Long grantedCouponId = null;
        if (couponType.intValue() == CouponType.TYPE_COMMON_CODE.getValue()) {
            grantedCouponId = activityCouponManager.exchangeByCommonCode(activityCouponDO.getId(), activityCouponDO.getActivityCreatorId(), userId, bizCode);
        } else if (couponType.intValue() == CouponType.TYPE_PER_CODE.getValue()) {
            grantedCouponId = activityCouponManager.exchangeByPerCode(activityCouponDO.getId(), activityCouponDO.getActivityCreatorId(), userId, couponCodeDO, bizCode);
        }

        return new MarketingResponse(grantedCouponId);
    }

    @Override
    public String getName() {
        return ActionEnum.EXCHANGE_COUPON_CODE.getActionName();
    }
}
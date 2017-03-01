package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.CouponCodeManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 1/13/16.
 */
@Service
public class ReleaseMultiUsedCouponAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseMultiUsedCouponAction.class);

    @Autowired
    private GrantedCouponManager grantedCouponManager;

    @Autowired
    private ActivityCouponManager activityCouponManager;

    @Autowired
    private CouponCodeManager couponCodeManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        List<Long> orderIds = (List<Long>) context.getRequest().getParam("orderIds");
        Long userId = (Long) context.getRequest().getParam("userId");
        String bizCode = (String) context.get("bizCode");

        MarketPreconditions.checkNotEmpty(orderIds, "orderIds");
        MarketPreconditions.checkNotNull(userId, "userId");

        for (Long orderId : orderIds) {
            singleShop(orderId, userId, bizCode);
        }

        return MarketingUtils.getSuccessResponse();
    }

    private void singleShop(Long orderId, Long userId, String bizCode) throws MarketingException {

        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        grantedCouponQTO.setReceiverId(userId);
        grantedCouponQTO.setOrderId(orderId);
        grantedCouponQTO.setStatus(UserCouponStatus.PRE_USE.getValue());
        List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);

        //如果该订单下面没有任何预使用的优惠券信息，则打印日志，并直接返回成功
        if (grantedCouponDOs == null || grantedCouponDOs.isEmpty()) {
            LOGGER.warn("there is not any used coupon under this order, orderId:{}, userId:{}", orderId, userId);
            return;
        }

        List<Long> idList = new ArrayList<Long>();
        //优惠券状态检查
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            if (grantedCouponDO.getStatus().intValue() != UserCouponStatus.PRE_USE.getValue()) {
                LOGGER.warn("the grantedCouponDO's status is not PRE_USE to release, grantedCouponDO : {}",
                        JsonUtil.toJson(grantedCouponDO));
                throw new MarketingException(ResponseCode.COUPON_STATUS_ILLEGAL);
            }

            idList.add(grantedCouponDO.getId());
        }

        //将优惠券从预使用状态更新为未使用状态
        int opNum = grantedCouponManager.updateCouponStatus(idList, userId,
                UserCouponStatus.PRE_USE.getValue(), UserCouponStatus.UN_USE.getValue());

        if (opNum != idList.size()) {
            LOGGER.error("error to update coupon status, orderId : {}, userId:{}, fromStatus:{}, toStatus:{}",
                    orderId, userId, UserCouponStatus.PRE_USE.getValue(), UserCouponStatus.UN_USE.getValue());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }

        ActivityCouponDO activityCouponDO;
        CouponCodeDO couponCodeDO;
        // 逐个更新状态
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            activityCouponDO = activityCouponManager.getActivityCoupon(grantedCouponDO.getCouponId(), grantedCouponDO.getCouponCreatorId(), bizCode);
            // 预使用与正式使用意义相同，统一更新使用值
            activityCouponManager.increaseUsedCount(grantedCouponDO.getCouponId(), grantedCouponDO.getActivityCreatorId(), -1);
            // 一卡一码，更新 coupon_code  状态
            if (activityCouponDO.getCouponType().intValue() == CouponType.TYPE_PER_CODE.getValue()) {
                couponCodeDO = new CouponCodeDO();
                couponCodeDO.setCode(grantedCouponDO.getCode());
                couponCodeDO.setStatus(UserCouponStatus.UN_USE.getValue());
                couponCodeManager.updateCouponCode(couponCodeDO);
            }
        }
    }

    @Override
    public String getName() {
        return ActionEnum.RELEASE_MULTI_USED_COUPON.getActionName();
    }
}
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
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 确认使用用户优惠券，将优惠券状态从预使用改成已使用
 */
@Service
public class UseUserCouponAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(UseUserCouponAction.class);

    @Resource
    private GrantedCouponManager grantedCouponManager;

    @Autowired
    private ActivityCouponManager activityCouponManager;

    @Autowired
    private CouponCodeManager couponCodeManager;

    public MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        Long userId = (Long) context.getRequest().getParam("userId");
        Long orderId = (Long) context.getRequest().getParam("orderId");
        String bizCode = (String) context.get("bizCode");

        //入参检查
        if (userId == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "userId is null");
        }

        if (orderId == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "orderId is null");
        }

        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        grantedCouponQTO.setReceiverId(userId);
        grantedCouponQTO.setOrderId(orderId);
        grantedCouponQTO.setStatus(UserCouponStatus.PRE_USE.getValue());

        //查询指定订单预使用优惠券列表
        List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);

        //如果该订单下面没有任何预使用的优惠券信息，则打印日志，并直接返回成功
        if (grantedCouponDOs == null || grantedCouponDOs.isEmpty()) {
            LOGGER.warn("there is not any used coupon under this order, orderId:{}, userId:{}", orderId, userId);
            return ResponseUtil.getResponse(Boolean.valueOf(true));
        }

        List<Long> idList = new ArrayList<Long>();
        //优惠券状态检查
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            if (grantedCouponDO.getStatus().intValue() != UserCouponStatus.PRE_USE.getValue()) {
                return new MarketingResponse(ResponseCode.COUPON_STATUS_ILLEGAL);
            }
            idList.add(grantedCouponDO.getId());
        }

        //将优惠券从预使用状态更新为已使用状态
        int opNum = grantedCouponManager.updateCouponStatus(idList, userId,
                UserCouponStatus.PRE_USE.getValue(), UserCouponStatus.USED.getValue());

        if (opNum != idList.size()) {
            LOGGER.error("error to update coupon status, userId:{}, fromStatus:{}, toStatus:{}",
                    userId, UserCouponStatus.PRE_USE.getValue(), UserCouponStatus.USED.getValue());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }

        ActivityCouponDO activityCouponDO;
        CouponCodeDO couponCodeDO;
        // 逐个更新状态
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            activityCouponDO = activityCouponManager.getActivityCoupon(grantedCouponDO.getCouponId(),
                    grantedCouponDO.getCouponCreatorId(), bizCode);
            // 一卡一码，更新 coupon_code  状态
            if (activityCouponDO.getCouponType().intValue() == CouponType.TYPE_PER_CODE.getValue()) {
                couponCodeDO = new CouponCodeDO();
                couponCodeDO.setCode(grantedCouponDO.getCode());
                couponCodeDO.setStatus(UserCouponStatus.USED.getValue());
                couponCodeManager.updateCouponCode(couponCodeDO);
            }
        }

        return ResponseUtil.getResponse(Boolean.valueOf(true));
    }

    public String getName() {
        return ActionEnum.USE_USER_COUPON.getActionName();
    }
}
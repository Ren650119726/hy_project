package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
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
import java.util.Date;
import java.util.List;

/**
 * 预使用用户优惠券，将优惠券状态置为预使用状态
 */
@Service
public class PreUseUserCouponAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreUseUserCouponAction.class);

    @Resource
    private GrantedCouponManager grantedCouponManager;

    @Autowired
    private ActivityCouponManager activityCouponManager;

    @Autowired
    private CouponCodeManager couponCodeManager;

    public MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        List<Long> userCouponIdList = (List<Long>) context.getRequest().getParam("userCouponIdList");
        Long userId = (Long) context.getRequest().getParam("userId");
        Long orderId = (Long) context.getRequest().getParam("orderId");
        String bizCode = (String) context.get("bizCode");

        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        grantedCouponQTO.setIdList(userCouponIdList);
        grantedCouponQTO.setReceiverId(userId);
        List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);

        //查询到的优惠券数量与指定使用优惠券的ID数不匹配，则提示优惠券不存在
        if (grantedCouponDOs.size() != userCouponIdList.size()) {
            return new MarketingResponse(ResponseCode.COUPON_NOT_EXIST);
        }

        Date current = new Date();
        //用户优惠券状态检查，只有未使用过的优惠券才能使用，并且没有过期
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            if (grantedCouponDO.getStatus().intValue() == UserCouponStatus.PRE_USE.getValue())
                return new MarketingResponse(ResponseCode.COUPON_PRE_USE_ALREADY);
            if (grantedCouponDO.getStatus().intValue() == UserCouponStatus.USED.getValue())
                return new MarketingResponse(ResponseCode.COUPON_USED_ALREADY);
            if (grantedCouponDO.getInvalidTime() != null && grantedCouponDO.getInvalidTime().before(current))
                return new MarketingResponse(ResponseCode.BIZ_ACTIVITY_COUPON_OVERDUE);
        }

        //冻结指定的有效优惠券信息
        GrantedCouponDTO updateGrantedCoupon = new GrantedCouponDTO();
        updateGrantedCoupon.setIdList(userCouponIdList);
        updateGrantedCoupon.setReceiverId(userId);
        updateGrantedCoupon.setOrderId(orderId);
        updateGrantedCoupon.setUseTime(current);
        updateGrantedCoupon.setStatus(UserCouponStatus.PRE_USE.getValue());

        int opNum = grantedCouponManager.updateGrantedCoupon(updateGrantedCoupon);

        if (opNum != userCouponIdList.size()) {
            LOGGER.error("error to update coupon, userId:{}, orderId:{}",
                    userId, orderId);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }

        ActivityCouponDO activityCouponDO;
        CouponCodeDO couponCodeDO;
        // 逐个更新状态
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            activityCouponDO = activityCouponManager.getActivityCoupon(grantedCouponDO.getCouponId(), grantedCouponDO.getCouponCreatorId(), bizCode);
            // 预使用与正式使用意义相同，统一更新使用值
            activityCouponManager.increaseUsedCount(grantedCouponDO.getCouponId(), grantedCouponDO.getActivityCreatorId(), 1);
            // 一卡一码，更新 coupon_code  状态
            if (activityCouponDO.getCouponType().intValue() == CouponType.TYPE_PER_CODE.getValue()) {
                couponCodeDO = new CouponCodeDO();
                couponCodeDO.setCode(grantedCouponDO.getCode());
                couponCodeDO.setStatus(UserCouponStatus.PRE_USE.getValue());
                couponCodeManager.updateCouponCode(couponCodeDO);
            }
        }

        return ResponseUtil.getResponse(Boolean.valueOf(true));
    }

    public String getName() {
        return ActionEnum.PRE_USE_USER_COUPON.getActionName();
    }
}
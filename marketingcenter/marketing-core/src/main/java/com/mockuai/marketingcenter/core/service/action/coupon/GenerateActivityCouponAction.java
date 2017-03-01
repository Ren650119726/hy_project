package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 创建优惠券
 * <p/>
 * 首先创建了活动主体，对 mop 开放，赞不支持多店铺概念以及用户领用后的失效功能
 */
@Service
public class GenerateActivityCouponAction implements Action {

    /**
     * 单次创建优惠券的数量上限
     */
    public static final long MAX_COUPON_GEN_COUNT = 10000000L;

    /**
     * 单次创建优惠券的默认数量
     */
    private static final long DEFAULT_COUPON_GEN_COUNT = 100000L;

    @Resource
    private ActivityCouponManager activityCouponManager;

    @Resource
    private MarketActivityManager marketActivityManager;

    public MarketingResponse execute(RequestContext context) throws MarketingException {

        ActivityCouponDTO activityCouponDTO = (ActivityCouponDTO) context.getRequest().getParam("activityCouponDTO");
        String bizCode = (String) context.get("bizCode");

        //入参检查
        if (activityCouponDTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL.getCode(), "activityCouponDTO is null");
        }

        //活动主体id
        if (activityCouponDTO.getActivityId() == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL.getCode(), "activityId is null");
        }

        if (activityCouponDTO.getActivityCreatorId() == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "activityCreatorId is null");
        }

        //优惠券总量
        if (activityCouponDTO.getTotalCount() == null) {
            activityCouponDTO.setTotalCount(DEFAULT_COUPON_GEN_COUNT);
        }

        //优惠券上限个数
        if (activityCouponDTO.getTotalCount().longValue() > MAX_COUPON_GEN_COUNT) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL.getCode(), "coupon totalCount over max 10000000");
        }

        //优惠券类型
        if (activityCouponDTO.getCouponType() == null) {
            //默认设为无码优惠券
            activityCouponDTO.setCouponType(CouponType.TYPE_NO_CODE.getValue());
        }

        //查询指定的活动主体
        Long activityId = activityCouponDTO.getActivityId().longValue();
        MarketActivityDO marketActivityDO = marketActivityManager.getActivity(activityId, bizCode);

        //创建优惠券
        activityCouponDTO.setBizCode(marketActivityDO.getBizCode());
        ActivityCouponDO activityCoupon = genActivityCoupon(activityCouponDTO, marketActivityDO);
        activityCoupon.setTotalCount(activityCouponDTO.getTotalCount());
        activityCoupon.setStartTime(marketActivityDO.getStartTime());
        activityCoupon.setEndTime(marketActivityDO.getEndTime());

        //设置优惠券默认状态
        activityCoupon.setStatus(ActivityCouponStatus.NORMAL.getValue());

        //如果未设置每人限领个数，则默认设置每人限领个数为0，即不限领
        if (activityCoupon.getUserReceiveLimit() == null) {
            activityCoupon.setUserReceiveLimit(0);
        }

        //持久化新建的优惠券对象
        Long couponId = activityCouponManager.addActivityCoupon(activityCoupon);

//        activityCoupon = activityCouponManager.getActivityCoupon(couponId, activityCouponDTO.getActivityCreatorId(), bizCode);
        // TODO 一卡一码／通码 统一全部持久化，减少后续的查询、领取流程
        //TODO 有码优惠券逻辑暂时注释掉，等需要时再打开 add by zengzhangqiang on 2015-06-11
//        if (activityCouponDTO.getCouponType().intValue() == CouponType.TYPE_CODE.getValue().intValue()) {

//            List couponCodeDOs = new ArrayList();
//
//            for (int i = 0; i < activityCouponDTO.getTotalCount().longValue(); i++) {
//
//                CouponCodeDO couponCodeDO = new CouponCodeDO();
//
//                couponCodeDO.setActivityCouponId(couponId);
//
//                couponCodeDO.setCode(CouponCodeUtil.generateCouponCode());
//
//                couponCodeDO.setActivityCreatorId(activityCouponDTO.getActivityCreatorId());
//
//                couponCodeDO.setBizCode(activityCoupon.getBizCode());
//
//                couponCodeDO.setToolCode(activityCoupon.getToolCode());
//
//                couponCodeDOs.add(couponCodeDO);
//
//
//                if (couponCodeDOs.size() % CouponConstant.INCREASE_COUNT.intValue() == 0) {
//
//                    this.couponCodeManager.batchAddCouponCode(couponCodeDOs);
//
//                    couponCodeDOs.clear();
//
//                }
//
//            }
//
//
//            if (couponCodeDOs.size() > 0) {
//
//                this.couponCodeManager.batchAddCouponCode(couponCodeDOs);
//
//            }


//        }

        //TODO 这里修改了返回值，上线前mop-api也要修改, 这个应该已经改过了把?
        return ResponseUtil.getResponse(couponId);

    }

    /**
     * 创建活动优惠券
     *
     * @param activityCouponDTO
     * @param marketActivityDO
     * @return
     */
    private ActivityCouponDO genActivityCoupon(ActivityCouponDTO activityCouponDTO, MarketActivityDO marketActivityDO) {

        ActivityCouponDO activityCoupon = ModelUtil.genActivityCouponDO(activityCouponDTO);

        activityCoupon.setActivityId(marketActivityDO.getId());
        activityCoupon.setActivityCreatorId(marketActivityDO.getCreatorId());

        //初始化优惠券相关参数
        activityCoupon.setGrantedCount(0L);
        activityCoupon.setActivateCount(0L);
        activityCoupon.setStatus(ActivityCouponStatus.NORMAL.getValue());

        return activityCoupon;
    }

    public String getName() {
        return ActionEnum.GENERATE_ACTIVITY_COUPON.getActionName();
    }
}
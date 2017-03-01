package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 更新优惠券
 */
@Service
public class UpdateActivityCouponAction implements Action {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateActivityCouponAction.class.getName());

	@Resource
	private ActivityCouponManager activityCouponManager;

	@Resource
	private MarketActivityManager marketActivityManager;

	public MarketingResponse execute(RequestContext context) throws MarketingException {
		ActivityCouponDTO activityCouponDTO = (ActivityCouponDTO) context.getRequest().getParam("activityCouponDTO");
		String bizCode = (String) context.get("bizCode");

		//入参检查
		MarketPreconditions.checkNotNull(activityCouponDTO, "activityCouponDTO");
		activityCouponDTO.setBizCode(bizCode);

		//优惠券id检查
		MarketPreconditions.checkNotNull(activityCouponDTO.getId(), "id");

		//优惠活动创建者id检查
//		if (activityCouponDTO.getActivityCreatorId() == null) {
//			return new MarketingResponse(ResponseCode.PARAMETER_NULL, "activity creator id is null");
//		}

//        if (activityCouponDTO.getCouponType() == null) {
//            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "couponType is null");
//        }

		//查询优惠券
		ActivityCouponDO activityCouponDO = activityCouponManager.getActivityCoupon(activityCouponDTO.getId()
				, activityCouponDTO.getActivityCreatorId(), bizCode);

//		if (activityCouponDO.getCouponType().intValue() != activityCouponDTO.getCouponType().intValue()) {
//			return new MarketingResponse(ResponseCode.BIZ_E_NOT_THE_SAME_COUPON_TYPE);
//		}

		//只有正常状态的优惠券才允许更新相关信息
		if (activityCouponDO.getStatus().intValue() != ActivityCouponStatus.NORMAL.getValue()) {
			return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_COUPON_STATUS_ILLEGAL);
		}

		//检查优惠活动相关状态
		MarketActivityDO marketActivityDO = marketActivityManager.getActivity(activityCouponDO.getActivityId(), bizCode);

		//只有正常状态的优惠活动才允许更新相关信息
		if (marketActivityDO.getStatus().intValue() != ActivityStatus.NORMAL.getValue()) {
			return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_COUPON_STATUS_ILLEGAL);
		}

		//更新优惠券总量
		if (activityCouponDTO.getTotalCount() != null) {
			//优惠券上限个数
			if (activityCouponDTO.getTotalCount().longValue() > ActivityCouponDTO.MAX_COUPON_GEN_COUNT) {
				throw new MarketingException(ResponseCode.BIZ_E_THE_COUNT_OF_COUPON_CODE_IS_OUT_OF_RANGE, "数量上线" + ActivityCouponDTO.MAX_COUPON_GEN_COUNT);
			}

			// 修改的个数只允许比原来大
			if (activityCouponDTO.getTotalCount().longValue() < activityCouponDO.getTotalCount().longValue()) {
				throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "数量只能比原来的多");
			}
		}

		activityCouponManager.updateActivityCoupon(activityCouponDTO);

		//更新优惠活动的名称和内容
		MarketActivityDTO marketActivityDTO = activityCouponDTO.getMarketActivity();
		if (marketActivityDTO != null) {
			if (marketActivityDTO.getActivityName() != null || marketActivityDTO.getActivityContent() != null) {
				MarketActivityDO updateActivityDO = new MarketActivityDO();

				updateActivityDO.setId(marketActivityDO.getId());
				updateActivityDO.setBizCode(bizCode);
				updateActivityDO.setActivityName(marketActivityDTO.getActivityName());
				updateActivityDO.setActivityContent(marketActivityDTO.getActivityContent());
				int opNum = marketActivityManager.updateActivity(updateActivityDO);

				if (opNum != 1) {
					LOGGER.error("error of updateActivity, activityDO : {}", JsonUtil.toJson(updateActivityDO));
					return new MarketingResponse(ResponseCode.SERVICE_EXCEPTION);
				}
			}
		}

		return ResponseUtil.getResponse(ResponseCode.SUCCESS);
	}

	public String getName() {
		return ActionEnum.UPDATE_ACTIVITY_COUPON.getActionName();
	}
}
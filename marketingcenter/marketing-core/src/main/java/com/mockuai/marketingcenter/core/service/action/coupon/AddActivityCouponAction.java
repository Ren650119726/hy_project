package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.CommonItemEnum;
import com.mockuai.marketingcenter.common.constant.MarketLevel;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edgar.zr on 11/3/15.
 * <p/>
 * 统一创建优惠券流程，先创建活动主体，再创建优惠券
 * 优惠券的创建没有相互之间的排斥限制
 * <p/>
 * <p/>
 */
@Service
public class AddActivityCouponAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddActivityCouponAction.class);

	@Autowired
	private ActivityCouponManager activityCouponManager;

	@Autowired
	private MarketActivityManager marketActivityManager;

	@Override
	protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

		ActivityCouponDTO activityCouponDTO = (ActivityCouponDTO) context.getRequest().getParam("activityCouponDTO");
		String bizCode = (String) context.get("bizCode");

		// 入参检查
		if (activityCouponDTO == null) {
			return new MarketingResponse(ResponseCode.PARAMETER_NULL.getCode(), "activityCouponDTO is null");
		}

		// 活动主体检查
		MarketActivityDTO marketActivityDTO = activityCouponDTO.getMarketActivity();

		if (marketActivityDTO == null) {
			throw new MarketingException(ResponseCode.PARAMETER_NULL, "marketActivityDTO is null");
		}
		if (activityCouponDTO.getValidDuration() != null) {
			marketActivityDTO.setStartTime(new Date(0));
			try {
				marketActivityDTO.setEndTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:dd").parse("9999-12-31 00:00:00"));
			} catch (ParseException e) {
				LOGGER.error("error to parse date", e);
				throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_TIME_INVALID);
			}
		}

		marketActivityDTO.setBizCode(bizCode);
		// 持久化活动主体
		addMarketActivity(marketActivityDTO);

		activityCouponDTO.setBizCode(marketActivityDTO.getBizCode());
		activityCouponDTO.setName(marketActivityDTO.getActivityName());
		activityCouponDTO.setActivityCreatorId(marketActivityDTO.getCreatorId());
		activityCouponDTO.setActivityId(marketActivityDTO.getId());
		activityCouponDTO.setStartTime(marketActivityDTO.getStartTime());
		activityCouponDTO.setEndTime(marketActivityDTO.getEndTime());

		//优惠券上限个数
		if (activityCouponDTO.getTotalCount().longValue() > ActivityCouponDTO.MAX_COUPON_GEN_COUNT) {
			return new MarketingResponse(ResponseCode.PARAMETER_NULL, "优惠券数量最大不能超过 9999999");
		}

		if (activityCouponDTO.getCouponType() == null) {
			return new MarketingResponse(ResponseCode.PARAMETER_NULL, "couponType is null");
		}

		activityCouponManager.addActivityCoupon(activityCouponDTO);

		return ResponseUtil.getResponse(activityCouponDTO.getId());
	}

	/**
	 * 活动主体检查
	 *
	 * @param marketActivityDTO
	 * @throws MarketingException
	 */
	private void addMarketActivity(MarketActivityDTO marketActivityDTO) throws MarketingException {

		// 默认设置为店铺级别
		if (MarketLevel.getByValue(marketActivityDTO.getLevel()) == null)
			marketActivityDTO.setLevel(MarketLevel.SHOP_LEVEL.getValue());

		// 默认设置为全部可适用
		if (marketActivityDTO.getCommonItem() == null) {
			marketActivityDTO.setCommonItem(CommonItemEnum.ALL_ITEM.getValue());
		}
		//工具 code 不能为空
		if (StringUtils.isBlank(marketActivityDTO.getToolCode())) {
			throw new MarketingException(ResponseCode.PARAM_E_TOOL_CODE_CANNOT_BE_NULL);
		}

		//活动名称不能为空
		if (StringUtils.isBlank(marketActivityDTO.getActivityName())) {
			throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_NAME_CANNOT_BE_NULL);
		}

		if (StringUtils.isBlank(marketActivityDTO.getActivityContent())) {
			marketActivityDTO.setActivityContent("");//默认置为空字符串
		}

		//活动创建者id不能为空
		if (marketActivityDTO.getCreatorId() == null) {
			throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_CREATOR_ID_CANNOT_BE_NULL);
		}

		//活动范围默认值设置
		if (marketActivityDTO.getScope() == null) {
			//默认设为全店活动
			marketActivityDTO.setScope(ActivityScope.SCOPE_SHOP.getValue());
		} else {

			//营销活动范围有效性校验
			if (ActivityScope.getScopeByValue(marketActivityDTO.getScope()) == null) {
				throw new MarketingException(ResponseCode.BIZ_E_ACTIVITY_SCOPE_INVALID);
			}

			//如果是单品活动，则营销活动单品列表不能为空
			if (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()) {
				if (marketActivityDTO.getActivityItemList() == null || marketActivityDTO.getActivityItemList().isEmpty()) {
					throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_ITEM_CANNOT_BE_NULL);
				}
			}
		}
		//活动时间校验，活动时间不能为空
		if (marketActivityDTO.getStartTime() == null || marketActivityDTO.getEndTime() == null) {
			throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_TIME_CANNOT_BE_NULL);
		}

		// 活动时间有效性验证,只要判断结束时间是否在开始时间之前
		if (marketActivityDTO.getStartTime().after(marketActivityDTO.getEndTime())) {
			throw new MarketingException(ResponseCode.PARAM_E_ACTIVITY_TIME_INVALID);
		}

		// 如果活动优惠券标志为空，则默认设置为需要优惠券
		if (marketActivityDTO.getCouponMark() == null) {
			marketActivityDTO.setCouponMark(1);
		}

		// 活动创建者类型设置默认值
		if (marketActivityDTO.getCreatorType() == null) {
			marketActivityDTO.setCreatorType(1);
		}

		marketActivityManager.addActivity(marketActivityDTO);
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ACTIVITY_COUPON.getActionName();
	}
}
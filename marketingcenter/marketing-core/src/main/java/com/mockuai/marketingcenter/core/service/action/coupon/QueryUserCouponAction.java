package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryUserCouponAction implements Action {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryUserCouponAction.class);

	@Resource
	private ActivityCouponManager activityCouponManager;

	@Resource
	private MarketActivityManager marketActivityManager;

	@Resource
	private PropertyManager propertyManager;

	@Resource
	private GrantedCouponManager grantedCouponManager;

	public MarketingResponse execute(RequestContext context) throws MarketingException {

		GrantedCouponQTO grantedCouponQTO = (GrantedCouponQTO) context.getRequest().getParam("grantedCouponQTO");
		String bizCode = (String) context.get("bizCode");

		//入参检查
		MarketPreconditions.checkNotNull(grantedCouponQTO, "grantedCouponQTO");
		MarketPreconditions.checkNotNull(grantedCouponQTO.getReceiverId(), "userId");

		LOGGER.info("userCoupon : {}", JsonUtil.toJson(grantedCouponQTO));

		grantedCouponQTO.setBizCode(bizCode);
		grantedCouponQTO.setStatusList(new ArrayList<Integer>());

		int status = grantedCouponQTO.getStatus().intValue();
		grantedCouponQTO.setStatus(null);

		// 未使用的优惠券, 保证时间没有过期，后续排除相关的无效状态、领取后优惠券的有效时间
		if (status == 30) {
			grantedCouponQTO.setOutOfDate(0);
			grantedCouponQTO.getStatusList().add(UserCouponStatus.UN_USE.getValue());
		}
		// 已使用的,包含了预使用和已使用, 时间无关
		if (status == 50) {
			grantedCouponQTO.setOutOfDate(2);
			grantedCouponQTO.getStatusList().add(UserCouponStatus.PRE_USE.getValue());
			grantedCouponQTO.getStatusList().add(UserCouponStatus.USED.getValue());
		}
		// 已过期的,只包含在未使用状态下的过期优惠券、领取后优惠券有效时间已过
		if (status == 60) {
			grantedCouponQTO.setOutOfDate(1);
			grantedCouponQTO.getStatusList().add(UserCouponStatus.UN_USE.getValue());
		}

		long grantedCouponNum = 0;

		// 过滤掉无效的优惠券信息；只有当用户优惠券所关联的优惠券源头以及营销活动记录存在时，该优惠券才有效
		// 在 30/60 下, 需要确认是否为有效
		List<GrantedCouponDTO> grantedCouponDTOs = new ArrayList<GrantedCouponDTO>();
		StringBuilder sb;
		Date current = new Date();

		Map<Long, MarketActivityDTO> marketActivityIdKey = new HashMap<Long, MarketActivityDTO>();
		Map<Long, ActivityCouponDO> activityCouponIdKey = new HashMap<Long, ActivityCouponDO>();

		LOGGER.info("queryUserCoupon, {}", JsonUtil.toJson(grantedCouponQTO));

		//查询优惠券信息
		List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCouponSecond(grantedCouponQTO);

		LOGGER.debug("userCouponDOs : {}", JsonUtil.toJson(grantedCouponDOs));

		if (!grantedCouponDOs.isEmpty()) {

			MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
			marketActivityQTO.setBizCode(bizCode);
			marketActivityQTO.setIdList(new ArrayList<Long>());

			ActivityCouponQTO activityCouponQTO = new ActivityCouponQTO();
			activityCouponQTO.setBizCode(bizCode);
			activityCouponQTO.setIdList(new ArrayList<Long>());

			for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
				marketActivityQTO.getIdList().add(grantedCouponDO.getActivityId());
				activityCouponQTO.getIdList().add(grantedCouponDO.getCouponId());
			}

			List<MarketActivityDTO> marketActivityDTOs = ModelUtil.genMarketActivityDTOList(marketActivityManager.queryActivity(marketActivityQTO));
			propertyManager.fillUpMarketWithProperty(marketActivityDTOs, bizCode);
			for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
				marketActivityIdKey.put(marketActivityDTO.getId(), marketActivityDTO);
			}

			List<ActivityCouponDO> activityCouponDOs = activityCouponManager.queryActivityCoupon(activityCouponQTO);
			for (ActivityCouponDO activityCouponDO : activityCouponDOs) {
				activityCouponIdKey.put(activityCouponDO.getId(), activityCouponDO);
			}
		}

		ActivityCouponDO activityCouponDO;
		MarketActivityDTO marketActivityDTO;

		for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {

			activityCouponDO = activityCouponIdKey.get(grantedCouponDO.getCouponId());
			if (activityCouponDO == null) {
				LOGGER.error("activityCouponDO does not exist, grantedCouponId : {}, activityCouponId : {}, biz_code : {}",
						grantedCouponDO.getId(), grantedCouponDO.getCouponId(), bizCode);
				continue;
			}
			marketActivityDTO = marketActivityIdKey.get(grantedCouponDO.getActivityId());
			if (marketActivityDTO == null) {
				LOGGER.error("marketActivityDTO does not exist, grantedCouponId : {}, activityId : {}, biz_code : {}",
						grantedCouponDO.getId(), grantedCouponDO.getActivityId(), bizCode);
				continue;
			}

			// 优惠券要有效， 优惠券状态有效 and 优惠券活动有效 and 如果领取优惠券有效时间有限制那么需要在失效前
			if (status == 30) {
				if (activityCouponDO.getStatus().intValue() == ActivityCouponStatus.INVALID.getValue().intValue()
						    || marketActivityDTO.getStatus().intValue() == ActivityStatus.INVALID.getValue().intValue()
						    || grantedCouponDO.getInvalidTime() != null && grantedCouponDO.getInvalidTime().before(current)) {
					continue;
				}
			}

			grantedCouponNum++;

			GrantedCouponDTO grantedCouponDTO = ModelUtil.genGrantedCouponDTO(grantedCouponDO, marketActivityDTO);

			grantedCouponDTO.setScope(marketActivityDTO.getScope());
			grantedCouponDTO.setDesc(marketActivityDTO.getActivityContent());

			//FIXME 显示用户领取的优惠券, content 需要显示未 满XX减YY
			Long tConsume = propertyManager.extractPropertyConsume(marketActivityDTO.getPropertyMap());
			sb = new StringBuilder("满");
			sb.append(String.valueOf(tConsume / 100)).append("使用");
			grantedCouponDTO.setContent(sb.toString());
			grantedCouponDTOs.add(grantedCouponDTO);
		}
		Integer offset = grantedCouponQTO.getOffset();
		Integer last = grantedCouponQTO.getCount() + offset;
		if (offset > grantedCouponNum) {
			return ResponseUtil.getResponse(new ArrayList(), grantedCouponNum);
		}
		return ResponseUtil.getResponse(
				grantedCouponDTOs.subList(grantedCouponQTO.getOffset(),
						(int) Math.min(last, grantedCouponNum)), grantedCouponNum);
	}

	public String getName() {
		return ActionEnum.QUERY_USER_COUPON.getActionName();
	}

//	public static void main(String[] args) {
//		String x = "('hanshu', 1, 1841254, 1, 1841254, '2016-06-14 14:54:28', '2116-06-14 14:54:35', '2016-08-15 00:00:00',1841254, ?, 30, 'SYS_MARKET_TOOL_000001', 0, '2016-07-16 15:36:03', '2016-07-16 15:36:03', '2016-07-15 00:00:00'),";
//		Integer[] userIds = {1688104,1730387,1728369,1730380,1730203,1730353,724253,1730292,1485530,1730221,1730468,1706741,1690224,1730429,1627770,1730487,648223,61631,1727754,1730520,1164150,1730205,1730557,1729977,1730553,1730575,1728605,1730302,1730609,1727849,1070508,1730619,279124,1730258,1698249,1730699,1730718,1730723,1730715,1730749,1730756,1730761,1730729,266186,1272724,1640096,1730777,1719504,1722883,1730816,1730831};
//		for (Integer userId :userIds)
//		{
//			for (int i =0; i<10; i++){
//
//			System.err.println(x.replaceAll("\\?",userId.toString()));
//			}
//		}
//	}
}
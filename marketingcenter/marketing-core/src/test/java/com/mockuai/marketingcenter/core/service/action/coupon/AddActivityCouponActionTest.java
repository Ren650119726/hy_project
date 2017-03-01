package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.MarketLevel;
import com.mockuai.marketingcenter.common.constant.PropertyEnum;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 11/5/15.
 */
public class AddActivityCouponActionTest extends BaseActionTest {

	public AddActivityCouponActionTest() {
		super(AddActivityCouponActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.ADD_ACTIVITY_COUPON.getActionName();
	}

	@Test
	public void test() throws ParseException {

		ActivityCouponDTO activityCouponDTO = genActivityCouponDTO();
		MarketActivityDTO marketActivityDTO = genMarketActivityDTO(ActivityScope.SCOPE_WHOLE, MarketLevel.BIZ_LEVEL.getValue());
		activityCouponDTO.setMarketActivity(marketActivityDTO);
		request.setParam("activityCouponDTO", activityCouponDTO);

		doExecute();
	}

	public ActivityCouponDTO genActivityCouponDTO() {
		ActivityCouponDTO activityCouponDTO = new ActivityCouponDTO();
//        activityCouponDTO.setTotalCount(Math.abs(random.nextLong()) % ActivityCouponDTO.MAX_COUPON_GEN_COUNT);
		activityCouponDTO.setTotalCount(100L);
		activityCouponDTO.setOpen(1);
		activityCouponDTO.setUserReceiveLimit(Math.abs(random.nextInt()) % 6);
		// 无码优惠券
		activityCouponDTO.setCouponType(CouponType.TYPE_NO_CODE.getValue());
		// 一卡一码
//        activityCouponDTO.setCouponType(CouponType.TYPE_PER_CODE.getValue());
		// 通用码
//        activityCouponDTO.setCouponType(CouponType.TYPE_COMMON_CODE.getValue());
		activityCouponDTO.setValidDuration(10);
		return activityCouponDTO;
	}

	public MarketActivityDTO genMarketActivityDTO(ActivityScope activityScope, Integer level) throws ParseException {
		MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
		marketActivityDTO.setToolCode(ToolType.SIMPLE_TOOL.getCode());
		marketActivityDTO.setActivityName("优惠券名字");
		marketActivityDTO.setActivityContent("使用说明");
		marketActivityDTO.setLevel(level);

		marketActivityDTO.setScope(activityScope.getValue());
		// 指定商品
		if (activityScope.equals(ActivityScope.SCOPE_ITEM)) {
			marketActivityDTO.setActivityItemList(new ArrayList<ActivityItemDTO>());
			marketActivityDTO.getActivityItemList().add(genActivityItemDTO(101501L));
			marketActivityDTO.getActivityItemList().add(genActivityItemDTO(101499L));
		}

		marketActivityDTO.setCreatorId(38699L);
//        marketActivityDTO.setStartTime(format.parse("20151012000000"));
//        marketActivityDTO.setEndTime(format.parse("20151013000000"));
		marketActivityDTO.setCouponMark(1);
		marketActivityDTO.setCommonItem(1);
		marketActivityDTO.setPropertyList(genPropertyDTOs());
		return marketActivityDTO;
	}

	public ActivityItemDTO genActivityItemDTO(Long itemId) {
		ActivityItemDTO activityItemDTO = new ActivityItemDTO();
//        activityItemDTO.setItemId(itemId);
//        activityItemDTO.setBrandId(10L);
		activityItemDTO.setCategoryId(11L);
		activityItemDTO.setSellerId(38699L);
		activityItemDTO.setActivityCreatorId(activityItemDTO.getSellerId());
		return activityItemDTO;
	}

	public List<PropertyDTO> genPropertyDTOs() {
		List<PropertyDTO> propertyDTOs = new ArrayList<PropertyDTO>();
		PropertyDTO propertyDTO = new PropertyDTO();
		propertyDTO.setPkey(PropertyEnum.CONSUME.getValue());
		propertyDTO.setName(PropertyEnum.CONSUME.getName());
		propertyDTO.setValue("1000");
		propertyDTO.setValueType(1);
		propertyDTOs.add(propertyDTO);

		propertyDTO = new PropertyDTO();
		propertyDTO.setPkey(PropertyEnum.QUOTA.getValue());
		propertyDTO.setValue("200");
		propertyDTO.setName(PropertyEnum.QUOTA.getName());
		propertyDTO.setValueType(1);
		propertyDTOs.add(propertyDTO);
		return propertyDTOs;
	}
}
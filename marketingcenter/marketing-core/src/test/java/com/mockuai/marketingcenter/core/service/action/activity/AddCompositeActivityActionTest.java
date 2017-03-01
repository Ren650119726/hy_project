package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import com.mockuai.marketingcenter.core.util.DateUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 8/10/15.
 */
public class AddCompositeActivityActionTest extends BaseActionTest {

	private MarketActivityDTO marketActivityDTO;

	public AddCompositeActivityActionTest() {
		super(AddCompositeActivityActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.ADD_COMPOSITE_ACTIVITY.getActionName();
	}

	@Test
	public void addCompositeActivity() {

		marketActivityDTO = new MarketActivityDTO();
		request.setParam("marketActivityDTO", marketActivityDTO);

		marketActivityDTO.setToolCode(ToolType.COMPOSITE_TOOL.getCode());

		marketActivityDTO.setStartTime(new Date());
		marketActivityDTO.setEndTime(DateUtils.addDays(new Date(), 5));

		marketActivityDTO.setActivityCode("");
		marketActivityDTO.setActivityContent("复合活动~~~~");
		marketActivityDTO.setActivityName("满减送");
		marketActivityDTO.setCreatorId(1841254L);
		marketActivityDTO.setExclusiveMark(1);
		marketActivityDTO.setScope(ActivityScope.SCOPE_ITEM.getValue());

		ActivityItemDTO activityItemDTO = new ActivityItemDTO();
		activityItemDTO.setItemId(28950L);
		activityItemDTO.setIconUrl("wwww");
		activityItemDTO.setItemName("bb");
		activityItemDTO.setSellerId(1841254L);

		marketActivityDTO.setActivityItemList(Arrays.asList(activityItemDTO));
		List<MarketActivityDTO> subMarketActivityDTOs = new ArrayList<MarketActivityDTO>();
		subMarketActivityDTOs.add(genSubMarketActivityDTO());
		marketActivityDTO.setSubMarketActivityList(subMarketActivityDTOs);

		doExecute();
	}

	public MarketActivityDTO genSubMarketActivityDTO() {
		MarketActivityDTO subMarketActivityDTO = new MarketActivityDTO();
		subMarketActivityDTO.setPropertyList(genPropertyDTOs());
		return subMarketActivityDTO;
	}

	public List<PropertyDTO> genPropertyDTOs() {
		List<PropertyDTO> propertyDTOs = new ArrayList<PropertyDTO>();
		propertyDTOs.add(genPropertyDTO(PropertyDTO.CONSUME, "1000"));
		propertyDTOs.add(genPropertyDTO(PropertyDTO.FREE_POSTAGE, "0"));
		propertyDTOs.add(genPropertyDTO(PropertyDTO.GIFT_ITEM_LIST, "[]"));
		propertyDTOs.add(genPropertyDTO(PropertyDTO.QUOTA, "500"));
		propertyDTOs.add(genPropertyDTO(PropertyDTO.COUPON_LIST, "[2433, 2430]"));
		return propertyDTOs;
	}

	public PropertyDTO genPropertyDTO(String propertyKey, String value) {
		PropertyDTO propertyDTO = new PropertyDTO();
		propertyDTO.setPkey(propertyKey);
		propertyDTO.setValue(value);
		propertyDTO.setValueType(2);
		return propertyDTO;
	}
}
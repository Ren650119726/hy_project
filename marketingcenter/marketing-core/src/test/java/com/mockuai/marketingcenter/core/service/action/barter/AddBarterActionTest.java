package com.mockuai.marketingcenter.core.service.action.barter;

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
import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 12/8/15.
 */
public class AddBarterActionTest extends BaseActionTest {

    private MarketActivityDTO marketActivityDTO;

    public AddBarterActionTest() {
        super(AddBarterActionTest.class.getName());
    }

    @Test
    public void test() {

        marketActivityDTO = new MarketActivityDTO();
        request.setParam("marketActivityDTO", marketActivityDTO);

        marketActivityDTO.setActivityContent("content");
        marketActivityDTO.setActivityName("name");
        marketActivityDTO.setToolCode(ToolType.BARTER_TOOL.getCode());

//        marketActivityDTO.setScope(ActivityScope.SCOPE_WHOLE.getValue());
        marketActivityDTO.setScope(ActivityScope.SCOPE_ITEM.getValue());

        marketActivityDTO.setStartTime(DateUtils.addMonths(new Date(), 3));
        marketActivityDTO.setEndTime(DateUtils.addMonths(new Date(), 4));

        marketActivityDTO.setCreatorId(91L);

        if (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()) {
            marketActivityDTO.setActivityItemList(genActivityItemDTOs());
        }
        marketActivityDTO.setPropertyList(genPropertyDTOs());

        doExecute();
    }

    public List<ActivityItemDTO> genActivityItemDTOs() {
        List<ActivityItemDTO> activityItemDTOs = new ArrayList<ActivityItemDTO>();
        ActivityItemDTO activityItemDTO = new ActivityItemDTO();
        activityItemDTO.setItemId(101059L);
        activityItemDTO.setSellerId(38699L);
        activityItemDTOs.add(activityItemDTO);
        return activityItemDTOs;
    }

    public List<PropertyDTO> genPropertyDTOs() {
        List<PropertyDTO> propertyDTOs = new ArrayList<PropertyDTO>();
        propertyDTOs.add(genPropertyDTO(PropertyDTO.CONSUME, "1000", "换购要求"));
        propertyDTOs.add(genPropertyDTO(PropertyDTO.EXTRA, "2000", "换购价格"));
        propertyDTOs.add(genPropertyDTO(PropertyDTO.LIMIT, "3", "每人限买"));
        propertyDTOs.add(genPropertyDTO(PropertyDTO.SKUID, "535", "换购商品"));
        return propertyDTOs;
    }

    public PropertyDTO genPropertyDTO(String propertyKey, String value, String name) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPkey(propertyKey);
        propertyDTO.setValue(value);
        propertyDTO.setValueType(1);
        propertyDTO.setName(name);
        return propertyDTO;
    }

    @Override
    protected String getCommand() {
        return ActionEnum.ADD_BARTER.getActionName();
    }
}
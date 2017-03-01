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
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 8/12/15.
 */
public class AddActivityActionTest extends BaseActionTest {

    private MarketActivityDTO marketActivityDTO;

    public AddActivityActionTest() {
        super(AddActivityActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.ADD_ACTIVITY.getActionName();
    }

    @Test
    public void addCompositeActivity() {

        marketActivityDTO = new MarketActivityDTO();
        request.setParam("marketActivityDTO", marketActivityDTO);

        marketActivityDTO.setToolCode("SYS_MARKET_TOOL_000001");
        marketActivityDTO.setToolType(ToolType.SIMPLE_TOOL.getValue());

        marketActivityDTO.setScope(ActivityScope.SCOPE_WHOLE.getValue());

        marketActivityDTO.setStartTime(DateUtils.addYears(new Date(), -3));
        marketActivityDTO.setEndTime(DateUtils.addMonths(new Date(), 4));

        marketActivityDTO.setActivityCode("");
        marketActivityDTO.setActivityContent("简单活动~~~~");
        marketActivityDTO.setActivityName("简单活动1");
        marketActivityDTO.setCreatorId(1841254L);

        marketActivityDTO.setActivityItemList(Collections.<ActivityItemDTO>emptyList());
        marketActivityDTO.setPropertyList(genPropertyDTOs());

//        List<MarketActivityDTO> subMarketActivityDTOs = new ArrayList<MarketActivityDTO>();
//        subMarketActivityDTOs.add(genSubMarketActivityDTO());
//        marketActivityDTO.setSubMarketActivityList(subMarketActivityDTOs);

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
//        propertyDTOs.add(genPropertyDTO(ReachMultipleReduceTool.FREE_POSTAGE, "0"));
//        propertyDTOs.add(genPropertyDTO(ReachMultipleReduceTool.GIFT_ITEM_LIST, "[]"));
        propertyDTOs.add(genPropertyDTO(PropertyDTO.QUOTA, "500"));
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
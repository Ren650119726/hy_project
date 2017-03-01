package com.mockuai.distributioncenter.core.service.action.plan;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.ItemDistPlanDTO;
import com.mockuai.distributioncenter.core.service.action.UnitTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by duke on 16/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ItemDistPlanActionTest {
    private static final String appKey = "3bc25302234640259fadea047cb7c7d3";

    @Autowired
    private DistributionService distributionService;

    @Test
    public void add() {
        BaseRequest baseRequest = new BaseRequest();
        ItemDistPlanDTO itemDistPlanDTO = new ItemDistPlanDTO();
        itemDistPlanDTO.setItemId(1L);
        itemDistPlanDTO.setLevel(1);
        itemDistPlanDTO.setRealDistRatio(1.0);
        itemDistPlanDTO.setVirtualDistRatio(1.0);
        baseRequest.setParam("itemDistPlanDTO", itemDistPlanDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.ADD_ITEM_DIST_PLAN.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void get() {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("itemId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_ITEM_DIST_PLAN_BY_ITEM.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void update() {
        BaseRequest baseRequest = new BaseRequest();
        ItemDistPlanDTO itemDistPlanDTO = new ItemDistPlanDTO();
        itemDistPlanDTO.setId(1L);
        itemDistPlanDTO.setRealDistRatio(0.8);
        itemDistPlanDTO.setVirtualDistRatio(0.7);
        baseRequest.setParam("itemDistPlanDTO", itemDistPlanDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_ITEM_DIST_PLAN.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }
}

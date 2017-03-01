package com.mockuai.distributioncenter.core.service.action.plan;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
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
public class ItemSkuDistPlanActionTest {
    private static final String appKey = "3bc25302234640259fadea047cb7c7d3";

    @Autowired
    private DistributionService distributionService;

    @Test
    public void add() throws Exception {
        ItemSkuDistPlanDTO itemSkuDistPlanDTO = new ItemSkuDistPlanDTO();
        itemSkuDistPlanDTO.setItemId(1L);
        itemSkuDistPlanDTO.setItemSkuId(1L);
        itemSkuDistPlanDTO.setSaleDistRatio(1.0);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("itemSkuDistPlanDTO", itemSkuDistPlanDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.ADD_ITEM_SKU_DIST_PLAN.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void get() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("itemSkuId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_ITEM_SKU_DIST_PLAN_BY_ITEM_SKU.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void getByItem() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("itemId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_ITEM_SKU_DIST_PLAN_BY_ITEM.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void update() throws Exception {
        ItemSkuDistPlanDTO itemSkuDistPlanDTO = new ItemSkuDistPlanDTO();
        itemSkuDistPlanDTO.setId(1L);
        itemSkuDistPlanDTO.setSaleDistRatio(0.8);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("itemSkuDistPlanDTO", itemSkuDistPlanDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_ITEM_SKU_DIST_PLAN.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));

    }
}

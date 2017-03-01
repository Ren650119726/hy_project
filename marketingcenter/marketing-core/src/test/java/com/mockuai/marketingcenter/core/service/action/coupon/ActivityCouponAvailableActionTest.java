package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by edgar.zr on 11/9/15.
 */
public class ActivityCouponAvailableActionTest extends BaseActionTest {

    public ActivityCouponAvailableActionTest() {
        super(ActivityCouponAvailableActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.ACTIVITY_COUPON_AVAILABLE.getActionName();
    }

    @Test
    public void test() {

        List<MarketItemDTO> itemDTOList = new ArrayList<MarketItemDTO>();
        setMarketItemDTO(itemDTOList);
        request.setParam("itemList", itemDTOList);
        request.setParam("couponId", 894L);
        doExecute();
    }

    public void setMarketItemDTO(List<MarketItemDTO> marketItemDTOs) {
        List<Long> skuIds = new ArrayList<Long>(Arrays.asList(new Long[]{
                2325L, 2148L
        }));

        MarketItemDTO dto;
        for (Long skuId : skuIds) {
            dto = new MarketItemDTO();
            marketItemDTOs.add(dto);
            dto.setItemSkuId(skuId);
            dto.setNumber(1);
            dto.setItemType(1);
            dto.setSellerId(38699L);
        }
    }

}
package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 1/21/16.
 */
public class QueryItemDiscountInfoForMopActionTest extends BaseActionTest {

    private MarketItemDTO marketItemDTO;

    public QueryItemDiscountInfoForMopActionTest() {
        super(QueryItemDiscountInfoForMopActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_ITEM_DISCOUNTINFO_MOP.getActionName();
    }

    @Test
    public void testItem() {

        marketItemDTO = new MarketItemDTO();
        request.setParam("marketItemDTO", marketItemDTO);

        marketItemDTO.setVirtualMark(0);
        marketItemDTO.setItemType(1);
        marketItemDTO.setItemId(28977L);
        marketItemDTO.setSellerId(1841254L);

        doExecute();
    }
}
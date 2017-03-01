package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 8/6/15.
 */
public class QueryItemDiscountInfoActionTest extends BaseActionTest {

    private MarketItemDTO marketItemDTO;

    public QueryItemDiscountInfoActionTest() {
        super(QueryItemDiscountInfoActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_ITEM_DISCOUNTINFO.getActionName();
    }

    @Test
    public void test_item() {

        marketItemDTO = new MarketItemDTO();
        request.setParam("marketItemDTO", marketItemDTO);

        // 这里限制 同一个 itemId 都是由同一个 creatorId 来创建的
        marketItemDTO.setSellerId(38699L);
        marketItemDTO.setItemId(101504L);

        doExecute();
    }
}
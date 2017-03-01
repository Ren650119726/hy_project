package com.mockuai.marketingcenter.core.service.action.barter;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 12/8/15.
 */
public class QueryBarterActionTest extends BaseActionTest {

    public QueryBarterActionTest() {
        super(QueryBarterActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_BARTER.getActionName();
    }

    @Test
    public void test() {

        MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
        marketActivityQTO.setLifecycle(0);
        marketActivityQTO.setToolType(ToolType.BARTER_TOOL.getValue());
        request.setParam("marketActivityQTO", marketActivityQTO);

        doExecute();
    }
}
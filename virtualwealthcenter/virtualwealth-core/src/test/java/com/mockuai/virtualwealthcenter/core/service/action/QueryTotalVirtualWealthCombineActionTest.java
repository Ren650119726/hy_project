package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/19/2016.
 */
public class QueryTotalVirtualWealthCombineActionTest extends BaseActionTest {

    public QueryTotalVirtualWealthCombineActionTest() {
        super(QueryTotalVirtualWealthCombineActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_TOTAL_VIRTUAL_WEALTH_COMBINE.getActionName();
    }

    @Test
    public void test() {
        Long userId = 38699L;
        request.setParam("userId", userId);
        doExecute();
    }
}
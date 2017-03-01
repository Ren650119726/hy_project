package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class ListWealthAccountActionTest extends BaseActionTest {

    public ListWealthAccountActionTest() {
        super(ListWealthAccountActionTest.class.getName());
    }

    @Test
    public void test() {

//        request.setParam("wealthType", 1);
        request.setParam("userId", 2222L);
        doExecute();
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_WEALTH_ACCOUNT.getActionName();
    }
}

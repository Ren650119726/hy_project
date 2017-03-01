package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class GetVirtualWealthActionTest extends BaseActionTest {

    public GetVirtualWealthActionTest() {
        super(GetVirtualWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_VIRTUAL_WEALTH.getActionName();
    }

    @Test(threadPoolSize = 2, invocationCount = 10)
    public void test() {
        Long creatorId = 19L;
        Integer wealthType = 2;
        request.setParam("creatorId", creatorId);
        request.setParam("wealthType", wealthType);
        doExecute();
    }
}
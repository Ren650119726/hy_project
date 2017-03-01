package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 9/22/15.
 */
public class ReleaseUsedWealthActionTest extends BaseActionTest {

    public ReleaseUsedWealthActionTest() {
        super(ReleaseUsedWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.RELEASE_USED_WEALTH.getActionName();
    }

    @Test
    public void releaseUsedWealth() {

        request.setParam("userId", 18L);
        request.setParam("orderId", 3333L);
        doExecute();
    }
}
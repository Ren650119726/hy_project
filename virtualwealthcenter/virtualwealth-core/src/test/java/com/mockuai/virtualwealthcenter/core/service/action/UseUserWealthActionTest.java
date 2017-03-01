package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/17/2016.
 */
public class UseUserWealthActionTest extends BaseActionTest {

    public UseUserWealthActionTest() {
        super(UseUserWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.USE_USER_WEALTH.getActionName();
    }

    @Test
    public void test() {
        request.setParam("userId", 18L);
        request.setParam("orderId", 3334L);
        doExecute();
    }
}
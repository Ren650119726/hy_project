package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/24/15.
 */
public class PreUseUserWealthActionActionTest extends BaseActionTest {

    public PreUseUserWealthActionActionTest() {
        super(PreUseUserWealthActionActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.PRE_USE_USER_WEALTH.getActionName();
    }

    @Test
    public void test() {

        request.setParam("userId", 38699L);
        request.setParam("wealthType", 3);
//        request.setParam("orderId", 3334L);
        request.setParam("useAmount", 10L);
        doExecute();
    }
}
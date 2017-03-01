package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 11/4/15.
 */
public class GiveBackPartialUsedWealthActionTest extends BaseActionTest {

    public GiveBackPartialUsedWealthActionTest() {
        super(GiveBackPartialUsedWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GIVE_BACK_PARTIAL_USED_WEALTH.getActionName();
    }

    @Test
    public void test() {
        request.setParam("userId", 38757L);
        request.setParam("orderId", 1491L);
        request.setParam("itemId", 1L);
        Map<Integer, Long> amounts = new HashMap<Integer, Long>();
        amounts.put(1, 1L);
        request.setParam("amounts", amounts);

        doExecute();
    }
}
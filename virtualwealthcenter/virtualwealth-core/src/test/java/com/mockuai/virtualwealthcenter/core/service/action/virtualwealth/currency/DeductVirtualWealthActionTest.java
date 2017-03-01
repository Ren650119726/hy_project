package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth.currency;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/20/15.
 */
public class DeductVirtualWealthActionTest extends BaseActionTest {

    public DeductVirtualWealthActionTest() {
        super(DeductVirtualWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.DEDUCT_VIRTUAL_WEALTH.getActionName();
    }

    @Test
    public void test() {
        Long userId = 38760L;
        Integer wealthType = WealthType.CREDIT.getValue();
        Long amount = 10L;

        request.setParam("userId", userId);
        request.setParam("wealthType", wealthType);
        request.setParam("amount", amount);

        doExecute();

    }
}
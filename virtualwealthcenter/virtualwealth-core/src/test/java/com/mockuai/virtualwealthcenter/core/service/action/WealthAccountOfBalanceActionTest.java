package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/17/2016.
 */
public class WealthAccountOfBalanceActionTest extends BaseActionTest {

    public WealthAccountOfBalanceActionTest() {
        super(WealthAccountOfBalanceActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_BALANCE.getActionName();
    }

    @Test
    public void test() {
        request.setParam("userId", 38699L);
        doExecute();
    }
}
package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/17/2016.
 */
public class WealthAccountOfHiCoinActionTest extends BaseActionTest {

    public WealthAccountOfHiCoinActionTest() {
        super(WealthAccountOfHiCoinActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_HI_COIN.getActionName();
    }

    @Test
    public void test() {
        request.setParam("userId", 18L);
        doExecute();
    }
}
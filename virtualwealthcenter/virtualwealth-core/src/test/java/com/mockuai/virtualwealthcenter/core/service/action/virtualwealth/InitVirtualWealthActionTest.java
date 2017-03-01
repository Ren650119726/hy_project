package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import org.junit.Test;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;

/**
 * Created by edgar.zr on 12/22/15.
 */
public class InitVirtualWealthActionTest extends BaseActionTest {

    public InitVirtualWealthActionTest() {
        super(InitVirtualWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.INIT_VIRTUAL_WEALTH.getActionName();
    }

    @Test
    public void test() {
        request.setParam("sellerId", 100L);
        doExecute();
    }
}
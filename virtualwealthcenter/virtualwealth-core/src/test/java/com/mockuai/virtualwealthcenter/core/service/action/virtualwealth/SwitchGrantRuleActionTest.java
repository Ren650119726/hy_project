package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class SwitchGrantRuleActionTest extends BaseActionTest {

    public SwitchGrantRuleActionTest() {
        super(SwitchGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.SWITCH_GRANT_RULE.getActionName();
    }

    @Test
    public void test() {
        Long grantRuleId = 2L;
        Long creatorId = 2L;
        Integer status = 1;
        request.setParam("grantRuleId", grantRuleId);
        request.setParam("creatorId", creatorId);
        request.setParam("status", status);
        doExecute();
    }
}
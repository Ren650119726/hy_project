package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class DeleteGrantRuleActionTest extends BaseActionTest {


    public DeleteGrantRuleActionTest() {
        super(DeleteGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.DELETE_GRANT_RULE.getActionName();
    }

    @Test
    public void test() {
        Long grantRuleId = 0L;
        Long creatorId = 1L;
        request.setParam("grantRuleId", grantRuleId);
        request.setParam("creatorId", creatorId);
        doExecute();
    }
}
package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class GetGrantRuleActionTest extends BaseActionTest {

    public GetGrantRuleActionTest() {
        super(GetGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_GRANT_RULE.getActionName();
    }

    @Test
    public void test() {
        Long grantRuleId = 3L;
        Long creatorId = 9L;
        request.setParam("grantRuleId", grantRuleId);
        request.setParam("creatorId", creatorId);
        doExecute();
    }
}
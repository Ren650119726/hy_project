package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class QueryGrantRuleActionTest extends BaseActionTest {

    public QueryGrantRuleActionTest() {
        super(QueryGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_GRANT_RULE.getActionName();
    }

    @Test
    public void test() {
        Integer wealthType = 2;
        Integer sourceType = 1;
        request.setParam("wealthType", wealthType);
        request.setParam("sourceType", sourceType);
        request.setParam("creatorId", 1L);
        doExecute();
    }
}
package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class
        QueryVirtualWealthWithGrantRuleActionTest extends BaseActionTest {

    public QueryVirtualWealthWithGrantRuleActionTest() {
        super(QueryVirtualWealthWithGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_VIRTUAL_WEALTH_WITH_GRANT_RULE.getActionName();
    }

    @Test
    public void test() {

        Integer sourceType = SourceType.ORDER_PAY.getValue();
        Integer wealthType = WealthType.CREDIT.getValue();
        request.setParam("sourceType", sourceType);
        request.setParam("wealthType", wealthType);
        doExecute();
    }
}
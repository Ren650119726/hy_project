package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;

import org.junit.Test;

/**
 * Created by edgar.zr on 12/22/15.
 */
public class ListWealthAccountBatchActionTest extends BaseActionTest {

    public ListWealthAccountBatchActionTest() {
        super(ListWealthAccountBatchActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_WEALTH_ACCOUNT_BATCH.getActionName();
    }

    @Test
    public void test() {
        request.setParam("offset", 0);
        request.setParam("count", 10);
        doExecute();
    }
}
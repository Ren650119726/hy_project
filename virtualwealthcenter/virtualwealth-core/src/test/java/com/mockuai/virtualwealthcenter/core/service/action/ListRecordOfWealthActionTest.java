package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/17/2016.
 */
public class ListRecordOfWealthActionTest extends BaseActionTest {

    public ListRecordOfWealthActionTest() {
        super(ListRecordOfWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.LIST_RECORD_OF_WEALTH.getActionName();
    }

    @Test
    public void test() {
//        Integer tradeType = (Integer) context.getRequest().getParam("tradeType");
//        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
//        Integer offset = (Integer) context.getRequest().getParam("offset");
//        Integer count = (Integer) context.getRequest().getParam("count");
//        Long userId = (Long) context.getRequest().getParam("userId");
        request.setParam("tradeType", 2);
        request.setParam("wealthType", WealthType.VIRTUAL_WEALTH.getValue());
        request.setParam("userId", 1841256L);
        doExecute();
    }
}
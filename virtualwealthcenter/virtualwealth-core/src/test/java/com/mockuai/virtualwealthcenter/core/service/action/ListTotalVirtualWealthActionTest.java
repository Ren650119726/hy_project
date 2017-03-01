package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 5/18/2016.
 */
public class ListTotalVirtualWealthActionTest extends BaseActionTest {

    public ListTotalVirtualWealthActionTest() {
        super(ListTotalVirtualWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.LIST_TOTAL_VIRTUAL_WEALTH.getActionName();
    }

    @Test
    public void test() {

        List<Long> userIds = new ArrayList<>();
        Integer wealthType = 1 ;
        userIds.add(38699L);
        request.setParam("userIds", userIds);
        request.setParam("wealthType", wealthType);
        doExecute();
    }

}
package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by edgar.zr on 12/22/15.
 */
public class GrantVirtualWealthBatchActionTest extends BaseActionTest {

    public GrantVirtualWealthBatchActionTest() {
        super(GrantVirtualWealthBatchActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GRANT_VIRTUAL_WEALTH_BATCH.getActionName();
    }

    @Test
    public void test() {
        Integer wealthType = WealthType.VIRTUAL_WEALTH.getValue();
        Integer sourceType = SourceType.OUTER_GRANT.getValue();
        List<String> mobiles = Arrays.asList("18268836320", "15869179713");
        List<Long> grantAmounts = Arrays.asList(188L, 199L);

        request.setParam("wealthType", wealthType);
        request.setParam("sourceType", sourceType);
        request.setParam("mobiles", mobiles);
        request.setParam("grantAmounts", grantAmounts);

        doExecute();
    }
}
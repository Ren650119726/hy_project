package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class GrantVirtualWealthActionTest extends BaseActionTest {

    public GrantVirtualWealthActionTest(String className) {
        super(className);
    }

    @Test
    public void test() {

        request.setParam("wealthCreatorId", 3L);
        request.setParam("grantAmount", 5L);
        request.setParam("receiverId", 38969L);
        request.setParam("wealthType", WealthType.CREDIT.getValue());
        request.setParam("sourceType", SourceType.SIGN_IN.getValue());
        doExecute();
        try {
            TimeUnit.SECONDS.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GRANT_VIRTUAL_WEALTH.getActionName();
    }
}
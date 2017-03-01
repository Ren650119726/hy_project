package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 8/12/15.
 */
public class InvalidActivityActionTest extends BaseActionTest {

    public InvalidActivityActionTest() {
        super(InvalidActivityActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.INVALID_ACTIVITY.getActionName();
    }

    @Test
    public void test_invalidActivity() {
        request.setParam("activityId", 582L);
        request.setParam("activityCreatorId", 1L);
        doExecute();
    }
}
package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 8/20/15.
 */
public class GetActivityActionTest extends BaseActionTest {

    public GetActivityActionTest() {
        super(GetActivityActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_ACTIVITY.getActionName();
    }

    @Test
    public void test_getActivity() {
        request.setParam("activityId", 2443L);
        request.setParam("creatorId", 1841254L);
        doExecute();
    }
}
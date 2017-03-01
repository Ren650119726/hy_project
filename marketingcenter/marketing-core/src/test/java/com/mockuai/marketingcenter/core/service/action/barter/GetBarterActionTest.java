package com.mockuai.marketingcenter.core.service.action.barter;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 12/8/15.
 */
public class GetBarterActionTest extends BaseActionTest {

    public GetBarterActionTest() {
        super(GetBarterActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_BARTER.getActionName();
    }

    @Test
    public void test() {
        Long activityId = 473L;
        Long creatorId = 38699L;

        request.setParam("activityId", activityId);
        request.setParam("creatorId", creatorId);
        doExecute();

    }
}
package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 1/20/16.
 */
public class ActivityItemListActionTest extends BaseActionTest {

    public ActivityItemListActionTest() {
        super(ActivityItemListActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.ACTIVITY_ITEM_LIST.getActionName();
    }

    @Test
    public void test() {
        request.setParam("activityId", 379L);

        doExecute();
    }
}
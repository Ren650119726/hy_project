package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 1/21/16.
 */
public class GetActivityCouponByActivityActionTest extends BaseActionTest {

    public GetActivityCouponByActivityActionTest() {
        super(GetActivityCouponByActivityActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_ACTIVITY_COUPON_BY_ACTIVITY.getActionName();
    }

    @Test
    public void test() {
        request.setParam("activityId", 394L);
        request.setParam("sellerId", 38699L);
        doExecute();
    }
}
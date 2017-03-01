package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/5/15.
 */
public class GetActivityCouponActionTest extends BaseActionTest {

    public GetActivityCouponActionTest() {
        super(GetActivityCouponActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_ACTIVITY_COUPON.getActionName();
    }

    @Test
    public void test() {
        request.setParam("activityCouponId", 192L);
        request.setParam("creatorId", 38699L);
        doExecute();
    }
}
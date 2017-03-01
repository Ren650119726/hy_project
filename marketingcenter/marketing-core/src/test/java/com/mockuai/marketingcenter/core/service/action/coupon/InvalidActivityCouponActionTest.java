package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/6/15.
 */
public class InvalidActivityCouponActionTest extends BaseActionTest {

    public InvalidActivityCouponActionTest() {
        super(InvalidActivityCouponActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.INVALID_ACTIVITY_COUPON.getActionName();
    }

    @Test
    public void test() {
        Long couponId = 225L;
        Long activityCreatorId = 38699L;
        request.setParam("couponId", couponId);
        request.setParam("activityCreatorId", activityCreatorId);
        doExecute();
    }
}
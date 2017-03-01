package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 6/06/2016.
 */
public class GrantActivityCouponByMultiNumActionTest extends BaseActionTest{

    public GrantActivityCouponByMultiNumActionTest() {
        super(GrantActivityCouponByMultiNumActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GRANT_ACTIVITY_COUPON_BATCH_BY_NUMBER.getActionName();
    }

    @Test
    public void test() {
        request.setParam("receiverId", 10L);
        request.setParam("num", 10);
        request.setParam("activityCouponId", 1513L);
        doExecute();
    }
}
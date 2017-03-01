package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/9/15.
 */
public class ReleaseUsedCouponActionTest extends BaseActionTest {

    public ReleaseUsedCouponActionTest() {
        super(ReleaseUsedCouponActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.RELEASE_USED_COUPON.getActionName();
    }

    @Test
    public void test() {
        Long userId = 38759L;
        Long orderId = 112L;
        request.setParam("userId", userId);
        request.setParam("orderId", orderId);
        doExecute();
    }
}
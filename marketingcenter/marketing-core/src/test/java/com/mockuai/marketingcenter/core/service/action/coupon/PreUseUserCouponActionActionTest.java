package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 11/9/15.
 */
public class PreUseUserCouponActionActionTest extends BaseActionTest {

    public PreUseUserCouponActionActionTest() {
        super(PreUseUserCouponActionActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.PRE_USE_USER_COUPON.getActionName();
    }

    @Test
    public void test() {
        List<Long> userCouponIdList = new ArrayList<Long>();
//        userCouponIdList.add(11L);
//        Long userId = 38759L;
//        Long orderId = 112L;
        userCouponIdList.add(10L);
        Long userId = 38758L;
        Long orderId = 113L;

        request.setParam("userCouponIdList", userCouponIdList);
        request.setParam("userId", userId);
        request.setParam("orderId", orderId);
        doExecute();
    }
}
package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;

/**
 * Created by edgar.zr on 11/9/15.
 */
public class ExchangeCouponCodeActionTest extends BaseActionTest {

    public ExchangeCouponCodeActionTest() {
        super(ExchangeCouponCodeActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.EXCHANGE_COUPON_CODE.getActionName();
    }

    @org.testng.annotations.Test
    public void test() {
        request.setParam("code", "3Q6T56UP");
        request.setParam("userId", 38758L);

//        request.setParam("code", "2FA98RMT");
//        request.setParam("userId", 38759L);

        doExecute();
    }
}
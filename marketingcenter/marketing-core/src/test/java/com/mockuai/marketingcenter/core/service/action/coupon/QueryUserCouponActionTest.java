package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 8/11/15.
 */
public class QueryUserCouponActionTest extends BaseActionTest {

    private GrantedCouponQTO grantedCouponQTO;

    public QueryUserCouponActionTest() {
        super(QueryUserCouponActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_USER_COUPON.getActionName();
    }

    @Test
    public void test_user_coupon() {

        grantedCouponQTO = new GrantedCouponQTO();
        request.setParam("grantedCouponQTO", grantedCouponQTO);

        grantedCouponQTO.setStatus(30);
        grantedCouponQTO.setReceiverId(10L);
        grantedCouponQTO.setOffset(0);
        grantedCouponQTO.setCount(25);

        doExecute();
    }
}
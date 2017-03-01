package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 9/2/15.
 */
public class QueryUserGatherCouponActionTest extends BaseActionTest {

    public QueryUserGatherCouponActionTest() {
        super(QueryUserGatherCouponActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_USER_GATHER_COUPON.getActionName();
    }

    @Test
    public void queryUserGatherCoupon() {
        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        request.setParam("grantedCouponQTO", grantedCouponQTO);

        grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());
        grantedCouponQTO.setReceiverId(38760L);
        doExecute();
    }
}
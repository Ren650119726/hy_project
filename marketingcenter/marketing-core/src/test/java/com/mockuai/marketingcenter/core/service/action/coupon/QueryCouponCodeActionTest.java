package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/6/15.
 */
public class QueryCouponCodeActionTest extends BaseActionTest {

    public QueryCouponCodeActionTest() {
        super(QueryCouponCodeActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_COUPON_CODE.getActionName();
    }

    @Test
    public void test() {

        CouponCodeQTO couponCodeQTO = new CouponCodeQTO();

        couponCodeQTO.setCouponId(1545L);
        couponCodeQTO.setActivityCreatorId(1841254L);
//        couponCodeQTO.setStatus(UserCouponStatus.UN_USE.getValue());
        couponCodeQTO.setUserName("15869179711");
//        couponCodeQTO.setCode("2HK7H52T");
//        couponCodeQTO.setOffset(5);
//        couponCodeQTO.setCount(5);

        request.setParam("couponCodeQTO", couponCodeQTO);

        doExecute();
    }
}
package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by edgar.zr on 8/15/15.
 */
public class QueryActivityCouponActionTest extends BaseActionTest {

    public QueryActivityCouponActionTest() {
        super(QueryActivityCouponActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_ACTIVITY_COUPON.getActionName();
    }

    @Test
    public void test_query() {
        ActivityCouponQTO activityCouponQTO = new ActivityCouponQTO();
        activityCouponQTO.setIdList(new ArrayList<Long>());
        activityCouponQTO.getIdList().add(178L);
//        activityCouponQTO.setId(11L);
//        activityCouponQTO.setActivityCreatorId(91L);
//        activityCouponQTO.setActivityCreatorId(38699L);
//        activityCouponQTO.setHasCode(false);
//        activityCouponQTO.setLifecycle(3);
//        activityCouponQTO.setLifecycle(0);
//        activityCouponQTO.setLevel(MarketLevel.BIZ_LEVEL.getValue());
//        activityCouponQTO.setLifecycle(3);
//        activityCouponQTO.setName("我");
//        activityCouponQTO.setOffset(0);
//        activityCouponQTO.setCount(10);
        request.setParam("activityCouponQTO", activityCouponQTO);
        doExecute();
    }
}
package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 3/03/2016.
 */
public class GetActivityCouponListByIdsActionTest extends BaseActionTest {

    public GetActivityCouponListByIdsActionTest() {
        super(GetActivityCouponListByIdsActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_ACTIVITY_COUPON_LIST_BY_IDS.getActionName();
    }

    @Test
    public void test() {
        List<Long> activityCouponIdList = new ArrayList<Long>();
        activityCouponIdList.add(317L);
        activityCouponIdList.add(318L);

        request.setParam("activityCouponIdList", activityCouponIdList);

        doExecute();
    }
}
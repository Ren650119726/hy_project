package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/6/15.
 */
public class UpdateActivityCouponActionTest extends BaseActionTest {

    public UpdateActivityCouponActionTest() {
        super(UpdateActivityCouponActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.UPDATE_ACTIVITY_COUPON.getActionName();
    }

    @Test
    public void test() {

        ActivityCouponDTO activityCouponDTO = new ActivityCouponDTO();
        activityCouponDTO.setId(230L);
        activityCouponDTO.setActivityCreatorId(38699L);
        activityCouponDTO.setTotalCount(122L);
        activityCouponDTO.setCouponType(CouponType.TYPE_NO_CODE.getValue());
        MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
        marketActivityDTO.setActivityContent("寿变化");
        marketActivityDTO.setActivityName("欢乐");
        activityCouponDTO.setMarketActivity(marketActivityDTO);
        request.setParam("activityCouponDTO", activityCouponDTO);
        doExecute();
    }
}
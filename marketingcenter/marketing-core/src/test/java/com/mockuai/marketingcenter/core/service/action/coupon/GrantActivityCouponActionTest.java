package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.GrantSourceEnum;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 8/18/15.
 */
public class GrantActivityCouponActionTest extends BaseActionTest {

	public GrantActivityCouponActionTest() {
		super("cccc");
	}

	@Override
	protected String getCommand() {
		return ActionEnum.GRANT_ACTIVITY_COUPON.getActionName();
	}

	@Test
	public void testGrantActivityCoupon() {

		Long activityCouponId = 1556L;
		Long creatorId = 1841254L;
		Long receiverId = 1841984L;
		Integer grantSource = GrantSourceEnum.RECEIVE.getValue();
		request.setParam("activityCouponId", activityCouponId);
		request.setParam("creatorId", creatorId);
		request.setParam("receiverId", receiverId);
        request.setParam("grantSource", grantSource);

		doExecute();
	}
}
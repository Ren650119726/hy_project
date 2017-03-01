package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 7/23/2016.
 */
public class QueryGrantedCouponActionTest extends BaseActionTest {

	public QueryGrantedCouponActionTest() {
		super("");
	}

	@Override
	protected String getCommand() {
		return ActionEnum.QUERY_GRANTED_COUPON.getActionName();
	}

	@Test
	public void test() {
		GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
		grantedCouponQTO.setOffset(0);
		grantedCouponQTO.setCount(10);
		grantedCouponQTO.setCouponId(1513L);

		request.setParam("grantedCouponQTO", grantedCouponQTO);

		doExecute();
	}
}
package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 12/15/15.
 */
public class ApplySeckillActionTest extends BaseActionTest {

	public ApplySeckillActionTest() {
		super(ApplySeckillActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.APPLY_SECKILL.getActionName();
	}

	@Test(invocationCount = 100)
	public void test() {

		Long seckillId = 168L;
		Long sellerId = 1841254L;
		Long skuId = 43491L;
		Long userId = 22L;
		Long distributorId = 1842012L;

		request.setParam("seckillId", seckillId);
		request.setParam("sellerId", sellerId);
		request.setParam("distributorId", distributorId);
		request.setParam("skuId", skuId);
		request.setParam("userId", userId);

		doExecute();
	}
}
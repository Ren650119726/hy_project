package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 8/21/15.
 */
public class QueryActivityActionTest extends BaseActionTest {

	public QueryActivityActionTest() {
		super(QueryActivityActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.QUERY_ACTIVITY.getActionName();
	}

	@Test
	public void testQuery() {
		MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
		request.setParam("marketActivityQTO", marketActivityQTO);

		marketActivityQTO.setLifecycle(2);
		marketActivityQTO.setCreatorId(1841254L);
		doExecute();
	}
}
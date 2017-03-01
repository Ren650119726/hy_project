package com.mockuai.giftscenter.core.service.action.seckill;

import org.testng.annotations.Test;

import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.qto.SeckillQTO;
import com.mockuai.giftscenter.core.BaseActionTest;

/**
 * Created by edgar.zr on 12/13/15.
 */
public class QuerySeckillActionTest extends BaseActionTest {

    public QuerySeckillActionTest() {
        super(QuerySeckillActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.QUERY_SECKILL.getActionName();
    }

    @Test
    public void test() {

        SeckillQTO seckillQTO = new SeckillQTO();
        seckillQTO.setLifecycle(0);
        seckillQTO.setOffset(0);
        seckillQTO.setCount(100);
        request.setParam("seckillQTO", seckillQTO);
        doExecute();

    }
}
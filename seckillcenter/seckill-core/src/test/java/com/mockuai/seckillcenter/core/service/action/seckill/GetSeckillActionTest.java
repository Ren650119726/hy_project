package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 12/13/15.
 */
public class GetSeckillActionTest extends BaseActionTest {

    public GetSeckillActionTest() {
        super(GetSeckillActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GET_SECKILL.getActionName();
    }

    @Test
    public void test() {
        request.setParam("sellerId", 1841254L);
        request.setParam("seckillId", 163L);
        doExecute();
    }
}
package com.mockuai.giftscenter.core.service.action.seckill;

import org.testng.annotations.Test;

import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.core.BaseActionTest;

/**
 * Created by edgar.zr on 12/13/15.
 */
public class InvalidateSeckillActionTest extends BaseActionTest {

    public InvalidateSeckillActionTest() {
        super(InvalidateSeckillActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.INVALIDATE_SECKILL.getActionName();
    }

    @Test
    public void test() {
        request.setParam("sellerId", 38699L);
        request.setParam("seckillId", 2L);
        doExecute();
    }
}
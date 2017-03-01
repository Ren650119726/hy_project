package com.mockuai.giftscenter.core.service.action.seckill;

import org.testng.annotations.Test;

import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.core.BaseActionTest;

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
        request.setParam("sellerId", 38699L);
        request.setParam("seckillId", 8L);
//        try {
//            Thread.sleep(1000000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        doExecute();
    }
}
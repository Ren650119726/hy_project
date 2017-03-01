package com.mockuai.giftscenter.core.service.action.seckill;

import org.testng.annotations.Test;

import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.core.BaseActionTest;

/**
 * Created by edgar.zr on 12/15/15.
 */
public class ValidateForSettlementActionTest extends BaseActionTest {

    public ValidateForSettlementActionTest() {
        super(ValidateForSettlementActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.VALIDATE_FOR_SETTLEMENT.getActionName();
    }

    @Test
    public void test() {
        Long skuId = 1039L;
        Long sellerId = 38699L;
        Long userId = 22L;

        request.setParam("skuId", skuId);
        request.setParam("sellerId", sellerId);
        request.setParam("userId", userId);

        doExecute();
    }
}
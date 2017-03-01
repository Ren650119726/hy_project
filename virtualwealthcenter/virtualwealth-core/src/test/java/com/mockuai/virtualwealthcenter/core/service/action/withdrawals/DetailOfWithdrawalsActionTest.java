package com.mockuai.virtualwealthcenter.core.service.action.withdrawals;

import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.DETAIL_OF_WITHDRAWALS;

/**
 * Created by edgar.zr on 6/02/2016.
 */
public class DetailOfWithdrawalsActionTest extends BaseActionTest {

    public DetailOfWithdrawalsActionTest() {
        super(DetailOfWithdrawalsActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return DETAIL_OF_WITHDRAWALS.getActionName();
    }

    @Test
    public void test() {
        request.setParam("withdrawalsNumber", "4c0f3183-bf54-4ac8-ab50-d6652ed77ff2");
        doExecute();
    }
}
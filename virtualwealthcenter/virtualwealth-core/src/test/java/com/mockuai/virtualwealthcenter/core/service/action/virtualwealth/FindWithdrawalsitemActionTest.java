package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by hy on 2016/5/26.
 */
public class FindWithdrawalsitemActionTest  extends BaseActionTest {
    public FindWithdrawalsitemActionTest(String className) {
        super(className);
    }

    @Override
    protected String getCommand() {
        return ActionEnum.FIND_WITHDRAWALS_ITEM.getActionName();
    }

    @Test
    public void test() {
        WithdrawalsItemQTO withdrawalsItemQTO = new WithdrawalsItemQTO();
        withdrawalsItemQTO.setOffset(0);
        withdrawalsItemQTO.setCount(20);
        withdrawalsItemQTO.setWithdrawalsNumber("f3e35160");
        withdrawalsItemQTO.setRealName("少翔");
        request.setParam("withdrawalsItem",withdrawalsItemQTO);

        doExecute();
    }
}

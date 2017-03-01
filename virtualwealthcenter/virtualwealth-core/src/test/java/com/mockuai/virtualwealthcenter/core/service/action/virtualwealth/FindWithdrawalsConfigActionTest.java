package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by hy on 2016/5/26.
 */
public class FindWithdrawalsConfigActionTest extends BaseActionTest {
    public FindWithdrawalsConfigActionTest(String className) {
        super(className);
    }

    @Override
    protected String getCommand() {
        return ActionEnum.FIND_WITHDRAWALS_CONFIG.getActionName();
    }

    @Test
    public void test() {
        WithdrawalsConfigQTO configQTO = new WithdrawalsConfigQTO();

        request.setParam("withdrawalsConfig",configQTO);

        doExecute();
    }
}

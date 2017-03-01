package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by hy on 2016/5/26.
 */
public class SaveWithdrawalsConfigActionTest extends BaseActionTest {
    public SaveWithdrawalsConfigActionTest(String className) {
        super(className);
    }


    @Autowired
    private WithdrawalsItemDAO withdrawalsItemDAO;


    @Override
    protected String getCommand() {
        return ActionEnum.ADD_WITHDRAWALS_CONFIG.getActionName();
    }

    @Test
    public void test() {
        double maxNum  = 499999.49;
        WithdrawalsConfigQTO configQTO = new WithdrawalsConfigQTO();
        configQTO.setWdConfigMaximum(49999999L);
        configQTO.setWdConfigMinimum(1L);
        configQTO.setWdIsflag((byte)1);
        configQTO.setWdConfigText("ookok");
        //configQTO.setId(5l);
        request.setParam("withdrawalsConfig",configQTO);

        doExecute();
    }
}

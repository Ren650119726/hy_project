package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by hy on 2016/5/26.
 */
public class UpdateWithdrawalsitemActionTest extends BaseActionTest {
    public UpdateWithdrawalsitemActionTest(String className) {
        super(className);
    }

    @Override
    protected String getCommand() {
        return ActionEnum.UPDATE_WITHDRAWALS_ITEM.getActionName();
    }
    //打款
    @Test
    public void transfer() {
        WithdrawalsItemQTO itemQTO = new WithdrawalsItemQTO();
        itemQTO.setWithdrawalsNumber("44296a37-d3ce-48b5-9d3c-5c0ac6019825");
        itemQTO.setDotype((byte)3);
        itemQTO.setBanklog("12233556");
        request.setParam("withdrawalsItem",itemQTO);

        doExecute();
    }

    @Test
    public void testRejected() {
        WithdrawalsItemQTO itemQTO = new WithdrawalsItemQTO();
        itemQTO.setWithdrawalsNumber("44296a37-d3ce-48b5-9d3c-5c0ac6019825");
        itemQTO.setDotype((byte)2);
        itemQTO.setRefuse("用户拒绝");
        request.setParam("withdrawalsItem",itemQTO);

        doExecute();
    }
}

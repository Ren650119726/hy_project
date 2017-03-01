package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/17/2016.
 */
public class UpdateStatusOfVirtualWealthDistributorGrantedActionTest extends BaseActionTest {

    public UpdateStatusOfVirtualWealthDistributorGrantedActionTest() {
        super(UpdateStatusOfVirtualWealthDistributorGrantedActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.UPDATE_STATUS_OF_VIRTUAL_WEALTH_DISTRIBUTOR_GRANTED.getActionName();
    }

    @Test
    public void testCancel() {
        request.setParam("orderId", 22L);
        request.setParam("sourceType", SourceType.GROUP_SELL.getValue());
        request.setParam("skuId", 222L);
        request.setParam("status", GrantedWealthStatus.TRANSFERRED.getValue());
        doExecute();
    }
}
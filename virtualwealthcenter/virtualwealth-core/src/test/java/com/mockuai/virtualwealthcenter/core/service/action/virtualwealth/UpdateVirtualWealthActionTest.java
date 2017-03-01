package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class UpdateVirtualWealthActionTest extends BaseActionTest {

    public UpdateVirtualWealthActionTest() {
        super(UpdateVirtualWealthActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.UPDATE_VIRTUAL_WEALTH.getActionName();
    }

    @Test
    public void test() {
        VirtualWealthDTO virtualWealthDTO = new VirtualWealthDTO();
        virtualWealthDTO.setId(8L);
//        virtualWealthDTO.setCreatorId(91L);
//        virtualWealthDTO.setAmount(100L);
//        virtualWealthDTO.setType(WealthType.VIRTUAL_WEALTH.getValue());
        virtualWealthDTO.setExchangeRate(90.0);
        virtualWealthDTO.setUpperLimit(49);
        virtualWealthDTO.setType(WealthType.CREDIT.getValue());
        request.setParam("virtualWealthDTO", virtualWealthDTO);
        doExecute();
    }
}
package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by edgar.zr on 11/13/15.
 */
public class GrantVirtualWealthWithGrantRuleActionTest extends BaseActionTest {

    public GrantVirtualWealthWithGrantRuleActionTest() {
        super(GrantVirtualWealthWithGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.GRANT_VIRTUAL_WEALTH_WITH_GRANT_RULE.getActionName();
    }

    @Test
    public void test() {
        GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
        grantedWealthDTO.setSourceType(SourceType.GROUP_BUY.getValue());
        grantedWealthDTO.setWealthType(WealthType.VIRTUAL_WEALTH.getValue());
        grantedWealthDTO.setBaseAmount(12000L);
        grantedWealthDTO.setGrantedTime(new Date());
        grantedWealthDTO.setGranterId(38699L);
        grantedWealthDTO.setOwnerId(2L);
//        grantedWealthDTO.setOrderId(118L);
        grantedWealthDTO.setReceiverIdList(new ArrayList<Long>());
        grantedWealthDTO.getReceiverIdList().add(38757L);
        request.setParam("grantedWealthDTO", grantedWealthDTO);
        doExecute();
    }
}
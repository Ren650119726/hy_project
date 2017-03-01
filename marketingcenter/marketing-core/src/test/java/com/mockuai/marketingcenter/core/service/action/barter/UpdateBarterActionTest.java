package com.mockuai.marketingcenter.core.service.action.barter;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by edgar.zr on 12/25/15.
 */
public class UpdateBarterActionTest extends BaseActionTest {

    public UpdateBarterActionTest() {
        super(UpdateBarterActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.UPDATE_BARTER.getActionName();
    }

    @Test
    public void test() {
        MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
        marketActivityDTO.setId(530L);
        marketActivityDTO.setBizCode("mockuai_demo");
        marketActivityDTO.setCreatorId(38699L);
        marketActivityDTO.setItemInvalidTime(new Date());
        request.setParam("marketActivityDTO", marketActivityDTO);

        doExecute();
    }
}
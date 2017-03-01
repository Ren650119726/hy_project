package com.mockuai.marketingcenter.core.service.action.tool;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketToolDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 8/12/15.
 */
public class AddToolActionTest extends BaseActionTest {

    private MarketToolDTO marketToolDTO;

    public AddToolActionTest() {
        super(AddToolActionTest.class.getName());
    }

    @Test
    public void test_addToolAction() {

        marketToolDTO = new MarketToolDTO();
        request.setParam("marketToolDTO", marketToolDTO);

        doExecute();
    }

    @Override
    protected String getCommand() {
        return ActionEnum.ADD_TOOL.getActionName();
    }
}
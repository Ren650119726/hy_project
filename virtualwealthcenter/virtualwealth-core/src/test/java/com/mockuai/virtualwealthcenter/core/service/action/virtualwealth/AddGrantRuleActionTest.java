package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class AddGrantRuleActionTest extends BaseActionTest {

    Random random = new Random();

    public AddGrantRuleActionTest() {
        super(AddGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.ADD_GRANT_RULE.getActionName();
    }

    @Test(invocationCount = 200)
    public void test() {
        Long creatorId = 91L;
        Integer ruleType = random.nextInt(2) + 1;

        GrantRuleDTO grantRuleDTO = new GrantRuleDTO();
        grantRuleDTO.setCreatorId(creatorId);
        grantRuleDTO.setRuleType(ruleType);
        grantRuleDTO.setSourceType(SourceType.ORDER_PAY.getValue());
        grantRuleDTO.setWealthType(WealthType.CREDIT.getValue());
        grantRuleDTO.setName(ruleType == 1 ? "按层级发放" : "按比率发放");
        grantRuleDTO.setRuleModuleDTOs(genRuleModuleDTOList(ruleType));
        request.setParam("grantRuleDTO", grantRuleDTO);
        doExecute();
    }

    private List<RuleModuleDTO> genRuleModuleDTOList(Integer ruleType) {
        List<RuleModuleDTO> ruleModuleDTOs = new ArrayList<RuleModuleDTO>();
        RuleModuleDTO ruleModuleDTO;
        Integer sizeOfRuleModule = random.nextInt(5) + 1;
        if (ruleType == 2) {
            sizeOfRuleModule = 1;
        }
        long step = 0;
        for (int i = 0; i < sizeOfRuleModule; i++) {
            ruleModuleDTO = new RuleModuleDTO();
            if (ruleType == 1) {
                ruleModuleDTO.setParamA(step);
                ruleModuleDTO.setParamB(ruleModuleDTO.getParamA() + random.nextInt(100) + 1);
                ruleModuleDTO.setAmount((long) (random.nextInt(100) + 1));
                step = ruleModuleDTO.getParamB();
            } else {
                ruleModuleDTO.setRatio(random.nextInt(100) + 1);
            }
            ruleModuleDTOs.add(ruleModuleDTO);
        }
        return ruleModuleDTOs;
    }
}
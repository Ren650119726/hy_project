package com.mockuai.virtualwealthcenter.core.service.action.virtualwealth;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 11/12/15.
 */
public class UpdateGrantRuleActionTest extends BaseActionTest {

    public UpdateGrantRuleActionTest() {
        super(UpdateGrantRuleActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.UPDATE_GRANT_RULE.getActionName();
    }

    @Test
    public void test() {
        GrantRuleDTO grantRuleDTO = new GrantRuleDTO();
        grantRuleDTO.setId(20L);
        grantRuleDTO.setCreatorId(9L);
        grantRuleDTO.setName("xxxx");
        grantRuleDTO.setRuleType(random.nextInt(2) + 1);
        grantRuleDTO.setRuleModuleDTOs(genRuleModuleDTOList(grantRuleDTO.getRuleType()));
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
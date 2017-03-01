package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.core.domain.RuleModuleDO;

import java.util.List;

public interface RuleModuleDAO {

    Integer batchAddRuleModule(List<RuleModuleDO> ruleModuleDOList);

    List<RuleModuleDO> getRuleModule(RuleModuleDO ruleModuleDO);

    /**
     * 物理删除详细财富发送规则
     *
     * @param ruleModuleDO
     * @return
     */
    int deleteRuleModuleByGrantRuleId(RuleModuleDO ruleModuleDO);
}
package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.core.dao.RuleModuleDAO;
import com.mockuai.virtualwealthcenter.core.domain.RuleModuleDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class RuleModuleDAOImpl extends SqlMapClientDaoSupport implements RuleModuleDAO {

    @Override
    public Integer batchAddRuleModule(List<RuleModuleDO> ruleModuleDOList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleModules", ruleModuleDOList);
        return (Integer) getSqlMapClientTemplate().insert("rule_module.batchInsert", params);
    }

    @Override
    public List<RuleModuleDO> getRuleModule(RuleModuleDO ruleModuleDO) {
        return getSqlMapClientTemplate().queryForList("rule_module.getRuleModule", ruleModuleDO);
    }

    @Override
    public int deleteRuleModuleByGrantRuleId(RuleModuleDO ruleModuleDO) {
        return getSqlMapClientTemplate().delete("rule_module.deleteRuleModuleByGrantRuleId", ruleModuleDO);
    }
}

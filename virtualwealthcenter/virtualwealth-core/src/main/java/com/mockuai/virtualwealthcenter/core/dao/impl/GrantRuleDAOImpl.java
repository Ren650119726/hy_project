package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.GrantRuleQTO;
import com.mockuai.virtualwealthcenter.core.dao.GrantRuleDAO;
import com.mockuai.virtualwealthcenter.core.domain.GrantRuleDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class GrantRuleDAOImpl extends SqlMapClientDaoSupport implements GrantRuleDAO {

    @Override
    public Long addGrantRule(GrantRuleDO grantRuleDO) {
        return (Long) getSqlMapClientTemplate().insert("grant_rule.addGrantRule", grantRuleDO);
    }

    @Override
    public List<GrantRuleDO> queryGrantRule(GrantRuleQTO grantRuleQTO) {
        return getSqlMapClientTemplate().queryForList("grant_rule.queryGrantRule", grantRuleQTO);
    }

    @Override
    public GrantRuleDO getGrantRule(GrantRuleDO grantRuleDO) {
        return (GrantRuleDO) getSqlMapClientTemplate().queryForObject("grant_rule.getGrantRule", grantRuleDO);
    }

    @Override
    public int deleteGrantRule(GrantRuleDO grantRuleDO) {
        return getSqlMapClientTemplate().update("grant_rule.deleteGrantRule", grantRuleDO);
    }

    @Override
    public int updateGrantRule(GrantRuleDO grantRuleDO) {
        return getSqlMapClientTemplate().update("grant_rule.updateGrantRule", grantRuleDO);
    }
}
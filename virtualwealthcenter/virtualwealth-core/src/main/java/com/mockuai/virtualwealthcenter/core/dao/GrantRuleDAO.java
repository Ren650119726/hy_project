package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.GrantRuleQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantRuleDO;

import java.util.List;

public interface GrantRuleDAO {

    Long addGrantRule(GrantRuleDO grantRuleDO);

    List<GrantRuleDO> queryGrantRule(GrantRuleQTO grantRuleQTO);

//    GrantRuleDO getGrantRuleStatusOn(GrantRuleDO grantRuleDO);

    GrantRuleDO getGrantRule(GrantRuleDO grantRuleDO);

    int deleteGrantRule(GrantRuleDO grantRuleDO);

    int updateGrantRule(GrantRuleDO grantRuleDO);
}
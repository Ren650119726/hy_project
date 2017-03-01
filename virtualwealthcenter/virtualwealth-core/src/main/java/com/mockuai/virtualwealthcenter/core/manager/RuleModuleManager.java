package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by edgar.zr on 11/11/15.
 */
public interface RuleModuleManager {

    /**
     * 批量添加财富发放规则
     *
     * @param grantRuleDTO
     * @throws VirtualWealthException
     */
    void batchAddRuleModule(GrantRuleDTO grantRuleDTO) throws VirtualWealthException;

    /**
     * 批量更新财富发放规则
     * 如果没有 id 的需要添加
     *
     * @param ruleModuleDTOs
     * @throws VirtualWealthException
     */
    void batchUpdateRuleModule(List<RuleModuleDTO> ruleModuleDTOs) throws VirtualWealthException;

    /**
     * 获取财富发放规则列表
     *
     * @param bizCode
     * @param grantRuleId
     * @param creatorId
     * @return
     * @throws VirtualWealthException
     */
    List<RuleModuleDTO> getRuleModuleListByGrantRuleId(String bizCode, Long grantRuleId, Long creatorId) throws VirtualWealthException;

    /**
     * 根据财富发送规则删除财富发送详细规则
     *
     * @param bizCode
     * @param grantRuleId
     * @param creatorId
     * @throws VirtualWealthException
     */
    void deleteRuleModuleByGrantRuleId(String bizCode, Long grantRuleId, Long creatorId) throws VirtualWealthException;
}

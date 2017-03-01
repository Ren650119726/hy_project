package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

import java.util.List;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class MopGrantRuleDTO {
    Integer sourceType;
    List<MopRuleModuleDTO> ruleModuleList;
    private Integer ruleType;

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public List<MopRuleModuleDTO> getRuleModuleList() {
        return ruleModuleList;
    }

    public void setRuleModuleList(List<MopRuleModuleDTO> ruleModuleList) {
        this.ruleModuleList = ruleModuleList;
    }
}
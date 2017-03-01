package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class MopRuleModuleDTO {
    private Long paramA;
    private Long paramB;
    private Long amount;
    private Integer ratio;

    public Long getParamA() {
        return paramA;
    }

    public void setParamA(Long paramA) {
        this.paramA = paramA;
    }

    public Long getParamB() {
        return paramB;
    }

    public void setParamB(Long paramB) {
        this.paramB = paramB;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }
}
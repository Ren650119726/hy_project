package com.mockuai.virtualwealthcenter.core.rule.module.impl;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.rule.module.Module;

/**
 * Created by edgar.zr on 12/13/15.
 */
public class SingleReachModule implements Module {

    private Long value;
    private Long virtualWealth;

    @Override
    public Long execute(Long amount) throws VirtualWealthException {
        return amount.longValue() >= value.longValue() ? virtualWealth : -1;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getVirtualWealth() {
        return virtualWealth;
    }

    public void setVirtualWealth(Long virtualWealth) {
        this.virtualWealth = virtualWealth;
    }
}
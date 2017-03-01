package com.mockuai.virtualwealthcenter.core.rule.module.impl;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.rule.module.Module;
import org.springframework.stereotype.Service;

/**
 * Created by edgar.zr on 11/13/15.
 */
@Service
public class HalfClosedIntervalModule implements Module {

    private Long left;
    private Long right;
    private Long credit;

    @Override
    public Long execute(Long amount) throws VirtualWealthException {

        return left <= amount.longValue() && (right == -1 || amount.longValue() < right) ? credit : -1;
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(Long left) {
        this.left = left;
    }

    public Long getRight() {
        return right;
    }

    public void setRight(Long right) {
        this.right = right;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }
}
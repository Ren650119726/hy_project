package com.mockuai.virtualwealthcenter.core.rule.module;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

/**
 * Created by edgar.zr on 11/13/15.
 */
public interface Module {

    Long execute(Long amount) throws VirtualWealthException;
}

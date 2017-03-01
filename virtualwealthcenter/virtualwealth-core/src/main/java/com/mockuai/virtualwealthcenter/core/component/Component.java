package com.mockuai.virtualwealthcenter.core.component;

import com.mockuai.virtualwealthcenter.core.util.Context;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

public interface Component {

    void init();

    <T> T execute(Context context) throws VirtualWealthException;

    String getComponentCode();
}
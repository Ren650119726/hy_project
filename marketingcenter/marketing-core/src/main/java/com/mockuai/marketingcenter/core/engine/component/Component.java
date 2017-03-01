package com.mockuai.marketingcenter.core.engine.component;

import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.exception.MarketingException;

public interface Component {

    void init();

    <T> T execute(Context context) throws MarketingException;

    String getComponentCode();
}
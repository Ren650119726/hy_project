package com.mockuai.seckillcenter.core.component;

import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.util.Context;

public interface Component {

    void init();

    <T> T execute(Context context) throws SeckillException;

    String getComponentCode();
}
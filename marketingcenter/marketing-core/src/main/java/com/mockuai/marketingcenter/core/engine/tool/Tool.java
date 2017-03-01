package com.mockuai.marketingcenter.core.engine.tool;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketToolDTO;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.ComponentHolder;
import com.mockuai.marketingcenter.core.exception.MarketingException;

public interface Tool<T> {
    void init();

    ComponentHolder getComponentHolder();

    /**
     * 执行优惠计算
     *
     * @param context
     * @return
     * @throws MarketingException
     */
    DiscountInfo execute(Context context) throws MarketingException;

    String getToolCode();

    MarketToolDTO getMarketTool();
}
package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.SAME_SELLER;

/**
 * Created by edgar.zr on 1/13/16.
 */
@Service
public class SameSeller implements Component {

    public static Context wrapParams(List<MarketItemDTO> itemList, Long sellerId) {
        Context context = new Context();
        context.setParam("itemList", itemList);
        context.setParam("sellerId", sellerId);
        context.setParam("component", SAME_SELLER);
        return context;
    }

    @Override
    public void init() {

    }

    /**
     * @param context
     * @return
     */
    @Override
    public Boolean execute(Context context) throws MarketingException {
        List<MarketItemDTO> itemList = (List<MarketItemDTO>) context.getParam("itemList");
        if (itemList == null || itemList.isEmpty())
            return Boolean.TRUE;

        Long sellerId = (Long) context.getParam("sellerId");
        if (sellerId == null)
            sellerId = itemList.get(0).getSellerId();
        for (MarketItemDTO marketItemDTO : itemList) {
            if (marketItemDTO.getSellerId() != sellerId.longValue())
                return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    @Override
    public String getComponentCode() {
        return SAME_SELLER.getCode();
    }
}
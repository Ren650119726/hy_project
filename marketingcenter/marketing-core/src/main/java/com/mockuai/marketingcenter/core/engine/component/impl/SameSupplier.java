package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.Context;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.SAME_SELLER;
import static com.mockuai.marketingcenter.common.constant.ComponentType.SAME_SUPPLIER;

/**
 * 判断商品是否处于同一个供应商下
 * <p/>
 * Created by edgar.zr on 3/18/2016.
 */
@Service
public class SameSupplier implements Component {

    public static Context wrapParams(List<MarketItemDTO> itemList) {
        Context context = new Context();
        context.setParam("itemList", itemList);
        context.setParam("component", SAME_SELLER);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public Boolean execute(Context context) throws MarketingException {
        List<MarketItemDTO> itemList = (List<MarketItemDTO>) context.getParam("itemList");
        if (itemList == null || itemList.isEmpty())
            return Boolean.TRUE;

        Long supplierId = itemList.get(0).getSupplierId();

        for (int i = 1; i < itemList.size(); i++) {
            if (itemList.get(i).getSellerId().longValue() != supplierId)
                return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public String getComponentCode() {
        return SAME_SUPPLIER.getCode();
    }
}
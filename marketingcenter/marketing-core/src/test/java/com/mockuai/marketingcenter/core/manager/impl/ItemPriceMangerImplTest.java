package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.manager.ItemPriceManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 12/11/15.
 */
public class ItemPriceMangerImplTest extends BaseTest {

    @Autowired
    private ItemPriceManager itemPriceManager;

    @Test
    public void testQueryItemPriceDTO() throws Exception {
        List<ItemPriceQTO> itemPriceQTOs = new ArrayList<ItemPriceQTO>();
        ItemPriceQTO itemPriceQTO;
        Long[] skuIds = new Long[]{43486L};
        for (Long skuId : skuIds) {
            itemPriceQTO = new ItemPriceQTO();
            itemPriceQTO.setSellerId(1841254L);
            itemPriceQTO.setItemSkuId(skuId);
            itemPriceQTOs.add(itemPriceQTO);
        }
        List<ItemPriceDTO> itemPriceDTOs = itemPriceManager.queryItemPriceDTO(itemPriceQTOs, null, "27c7bc87733c6d253458fa8908001eef");
        System.err.println(JsonUtil.toJson(itemPriceDTOs));
    }
}
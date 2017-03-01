package com.mockuai.marketingcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by edgar.zr on 12/7/15.
 */
public interface ItemPriceManager {

    /**
     * 查询商品的价格
     *
     * @param itemPriceQTOs
     * @param userId
     * @param appKey
     * @return
     */
    List<ItemPriceDTO> queryItemPriceDTO(List<ItemPriceQTO> itemPriceQTOs, Long userId, String appKey) throws MarketingException;
}
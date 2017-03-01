package com.mockuai.marketingcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by edgar.zr on 12/7/15.
 */
public interface ItemSuitManager {

    /**
     * 查询 item 商品所有的套装
     *
     * @param itemQTO
     * @param userId
     * @param appKey
     * @return
     */
    List<ItemDTO> querySuitsByItem(ItemQTO itemQTO, Long userId, String appKey) throws MarketingException;

    /**
     * 查询套装信息
     *
     * @param itemQTO
     * @param userId
     * @param appKey
     * @return
     */
    List<ItemDTO> querySuit(ItemQTO itemQTO, Long userId, String appKey) throws MarketingException;

    ItemDTO getSuit(Long suitId, Long userId, String appKey) throws MarketingException;
}
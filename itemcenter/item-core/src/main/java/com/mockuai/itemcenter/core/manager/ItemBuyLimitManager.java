package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.core.domain.ItemBuyLimitDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 限购
 * Created by luliang on 15/7/17.
 */
@Service
public interface ItemBuyLimitManager {

    Integer queryItemBuyLimit(Long sellerId, Long itemId) throws ItemException;

    List<ItemBuyLimitDTO> queryItemBuyLimitRecord(Long sellerId, Long itemId) throws ItemException;

    List<ItemBuyLimitDTO> queryItemBuyLimitRecord(Long sellerId, List<Long> itemIdList) throws ItemException;

    Long addItemBuyLimit(ItemBuyLimitDTO itemBuyLimitDTO) throws ItemException;

    Boolean deleteItemBuyLimit(Long sellerId, Long itemId) throws ItemException;

    Long trashItemBuyLimit(Long sellerId, Long itemId) throws ItemException;

    Long recoveryItemBuyLimit(Long sellerId, Long itemId) throws ItemException;

    Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException;
}

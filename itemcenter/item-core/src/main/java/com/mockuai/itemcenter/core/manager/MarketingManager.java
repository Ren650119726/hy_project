package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MopCombineDiscountDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.MopDiscountInfo;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/10/15.
 */
public interface MarketingManager {

    MopCombineDiscountDTO getItemDiscountInfo(ItemDTO itemDTO, Long userId, String appKey) throws ItemException;

    List<DiscountInfo> discountInfoOfItemListAction(List<ItemDTO> itemDTOList, Long userId, String appKey) throws ItemException;
}

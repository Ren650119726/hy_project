package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/3/16.
 */
public interface ItemManager {

    List<ItemSearchDTO> searchItem(ItemSearchQTO itemSearchQTO,String appKey) throws DistributionException;

    ItemDTO getItem(Long id, Long sellerId, String appKey) throws DistributionException;
}

package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSuitDTO;
import com.mockuai.itemcenter.common.domain.dto.RItemSuitDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSuitQTO;

import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public interface ItemSuitClient {

    public Response<List<ItemDTO>> querySuitsByItem(ItemQTO itemQTO,Long userId,String appkey);

    public Response<List<ItemDTO>> querySuit(ItemQTO itemQTO,Long userId,String appKey);

    public Response<ItemDTO> getSuit(Long sellerId,Long itemId,String appKey);

    public Response<Long> addRItemSuit(List<RItemSuitDTO> rItemSuitDTOList,String appKey);

    public Response<Long> addItemSuit(ItemDTO itemDTO,List<RItemSuitDTO> rItemSuitDTOList,String appKey);

    public Response<Long> disableSuit(Long sellerId,Long itemId,String appKey);

    public Response<ItemSuitDTO> getSuitExtraInfo(Long sellerId,Long itemId,String appKey);

    public Response<List<ItemSuitDTO>> queryItemSuitDiscount(ItemSuitQTO itemSuitQTO,String appKey);
}

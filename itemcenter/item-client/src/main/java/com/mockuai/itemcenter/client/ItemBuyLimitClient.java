package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;

import java.util.List;

/**
 * Created by luliang on 15/7/17.
 */
public interface ItemBuyLimitClient {

    public Response<Integer> getItemBuyLimit(Long itemId, Long sellerId, String appKey);

    public Response<List<ItemBuyLimitDTO>> queryItemBuyLimit(List<Long> itemIdList,Long sellerId,String appKey);
}

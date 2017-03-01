package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;

import java.util.List;

/**
 * Created by yindingyu on 15/12/23.
 */
public interface ItemLabelClient {

    Response<ItemLabelDTO> getItemLabel(Long id,Long sellerId,String appKey);

    Response<List<ItemLabelDTO>> queryItemLabel(ItemLabelQTO itemLabelQTO,String appKey);

    Response<Long> addItemLabel(ItemLabelDTO itemLabelDTO,String appKey);

    Response<Long> deleteItemLabel(Long id,Long sellerId,String appKey);

    Response<Long> updateItemLabel(ItemLabelDTO itemLabelDTO,String appKey);
}

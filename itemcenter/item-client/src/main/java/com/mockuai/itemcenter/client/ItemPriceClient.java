package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;

import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public interface ItemPriceClient {

    Response<List<ItemPriceDTO>> queryItemPriceDTO(List<ItemPriceQTO> itemPriceQTOList,Long userId,String appKey);
}

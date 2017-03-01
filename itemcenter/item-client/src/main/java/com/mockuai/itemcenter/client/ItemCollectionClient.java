package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;

import java.util.List;

/**
 * Created by yindingyu on 15/12/23.
 */
public interface ItemCollectionClient {

    Response<List<ItemDTO>> queryItemCollection(ItemCollectionQTO itemCollectionQTO, String appKey);
}

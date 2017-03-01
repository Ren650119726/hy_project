package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/21.
 */
public interface ItemDetailTemplateClient {

    public Response<Long> addItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO, String appKey);

    public Response<ItemDetailTemplateDTO> getItemDetailTemplate(Long id, Long sellerId, String appKey);

    public Response<List<ItemDetailTemplateDTO>> queryItemDetailTemplate(ItemDetailTemplateQTO itemDetailTemplateQTO, String appKey);

    public Response<Integer> updateItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO, String appKey);

    public Response<Integer> deleteItemDetailTemplate(Long id, Long sellerId, String appKey);
}

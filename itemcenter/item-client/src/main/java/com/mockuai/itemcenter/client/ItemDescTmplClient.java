package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDescTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDescTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;

import java.util.List;

public interface ItemDescTmplClient {

    public Response<ItemDescTmplDTO> getItemDescTmpl(Long tmplId, Long sellerId, String appKey);

    public Response<Long> deleteItemDescTmpl(Long tmplId, Long sellerId, String appKey);

    public Response<Long> addItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO, String appKey);

    public Response<Long> updateItemDescTmpl(ItemDescTmplDTO itemDescTmplDTO, String appKey);

    public Response<List<ItemDescTmplDTO>> queryItemDescTmpl(ItemDescTmplQTO itemDescTmplQTO, String appKey);

}

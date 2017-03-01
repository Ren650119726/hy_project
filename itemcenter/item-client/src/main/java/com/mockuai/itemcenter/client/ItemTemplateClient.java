package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;

/**
 * 商品模版接口
 * @author cwr
 */
public interface ItemTemplateClient {
	
	public Response<ItemTemplateDTO> addItemTemplate(ItemTemplateDTO itemTemplateDTO, String appKey);
	
	public Response<Boolean> updateItemTemplate(ItemTemplateDTO itemTemplateDTO, String appKey);
	
	public Response<List<ItemTemplateDTO>> queryItemTemplate(ItemTemplateQTO itemTemplateQTO, String appKey);
	
	public Response<Boolean> deleteItemTemplate(Long itemTemplateId,Long supplierId, String appKey);
	
	public Response<ItemTemplateDTO> getItemTemplate(Long itemTemplateId,Long supplierId, String appKey);
}

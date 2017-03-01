package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

public interface ItemTemplateManager {
	
	public Long addItemTemplate(ItemTemplateDTO itemTemplateDTO)throws ItemException;
	
	public List<ItemTemplateDTO> queryItemTemplate(ItemTemplateQTO itemTemplateQTO)throws ItemException;
	
	public int deleteItemTemplate(Long templateId,Long supplierId)throws ItemException;
	
	public ItemTemplateDTO getItemTemplate(Long templateId,Long supplierId)throws ItemException;
	
	public int updateItemTemplate(ItemTemplateDTO itemTemplate)throws ItemException;

}

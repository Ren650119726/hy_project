package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.itemcenter.core.dao.ItemTemplateDAO;
import com.mockuai.itemcenter.core.domain.ItemTemplateDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemTemplateManager;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class ItemTemplateManagerImpl implements ItemTemplateManager {
	
	@Resource
	private ItemTemplateDAO itemTemplateDao;

	@Override
	public Long addItemTemplate(ItemTemplateDTO itemTemplateDTO) throws ItemException {
		ItemTemplateDO itemTemplateDO =new ItemTemplateDO();
		ItemUtil.copyProperties(itemTemplateDTO, itemTemplateDO);
		return (Long)this.itemTemplateDao.addItemTemplate(itemTemplateDO);
	}

	@Override
	public List<ItemTemplateDTO> queryItemTemplate(
			ItemTemplateQTO itemTemplateQTO) throws ItemException {
		itemTemplateQTO.setIsDeleted(0);
		List<ItemTemplateDO> list = this.itemTemplateDao.queryItemTemplate(itemTemplateQTO);
		List<ItemTemplateDTO> result = new ArrayList<ItemTemplateDTO>();
		if(!CollectionUtils.isEmpty(list)){
			for(ItemTemplateDO item : list){
				ItemTemplateDTO dto =new ItemTemplateDTO();
				dto.setId(item.getId());
				dto.setTemplateName(item.getTemplateName());
				dto.setGmtCreated(item.getGmtCreated());
				dto.setSellerId(item.getSellerId());
				dto.setCategoryId(item.getCategoryId());
				dto.setItemBrandId(item.getItemBrandId());
				result.add(dto);
			}
		}
		return result;
	}
	
	@Override
	public int deleteItemTemplate(Long templateId,Long supplierId){
		ItemTemplateDO itemTemplateDO = new ItemTemplateDO();
		itemTemplateDO.setId(templateId);
		itemTemplateDO.setSellerId(supplierId);
		int result = this.itemTemplateDao.DeleteItemTemplate(itemTemplateDO);
		return result;
	}
	
	@Override
	public ItemTemplateDTO getItemTemplate(Long templateId,Long supplierId){
		ItemTemplateDO itemTemplateDO= new ItemTemplateDO();
		itemTemplateDO.setId(templateId);
		itemTemplateDO.setSellerId(supplierId);
		ItemTemplateDO item = this.itemTemplateDao.getItemTemplate(itemTemplateDO);
		if(item == null){
			return null;
		}
		ItemTemplateDTO itemTemplateDTO = new ItemTemplateDTO();
		BeanUtils.copyProperties(item,itemTemplateDTO);
		return itemTemplateDTO;
	}
	
	@Override
	public int updateItemTemplate(ItemTemplateDTO itemTemplateDTO)throws ItemException{
		ItemTemplateDO itemTemplateDO = new ItemTemplateDO();
		ItemUtil.copyProperties(itemTemplateDTO, itemTemplateDO);
		int result = this.itemTemplateDao.updateItemTemplate(itemTemplateDO);
		return result;
	}
	
	
	//TODO
	private void validateFields4Update(ItemTemplateDTO itemTemplateDTO)throws ItemException{
		if(itemTemplateDTO == null){
			//throw 
		}
	}
}

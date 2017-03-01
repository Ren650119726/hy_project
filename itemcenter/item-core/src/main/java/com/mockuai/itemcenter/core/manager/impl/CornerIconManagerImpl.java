package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.itemcenter.core.dao.CornerIconDAO;
import com.mockuai.itemcenter.core.domain.CornerIconDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CornerIconManager;

@Service
public class CornerIconManagerImpl implements CornerIconManager {
	
	@Resource
	private CornerIconDAO cornerIconDAO;
	
	public Long addCornerIcon(CornerIconDTO cornerIconDTO)throws ItemException {
		CornerIconDO cornerIconDO =new CornerIconDO();
		BeanUtils.copyProperties(cornerIconDTO, cornerIconDO);
		return this.cornerIconDAO.addCornerIcon(cornerIconDO);
	}

	public int deleteCornerIcon(Long id, Long sellerId)throws ItemException {
		CornerIconDO cornerIconDO = new CornerIconDO();
		cornerIconDO.setId(id);
		cornerIconDO.setSellerId(sellerId);
		int result = this.cornerIconDAO.deleteCornerIcon(cornerIconDO);
		return result;
	}

	public List<CornerIconDTO> queryCornerIcon(CornerIconQTO cornerIconQTO) throws ItemException{
		List<CornerIconDO> list = this.cornerIconDAO.queryCornerIcon(cornerIconQTO);
		System.out.println("size: " + list.size());
		List<CornerIconDTO> result =new  ArrayList<CornerIconDTO>();
		
		if(!CollectionUtils.isEmpty(list)){
			for(CornerIconDO item : list){
				CornerIconDTO dto =new CornerIconDTO();
				dto.setId(item.getId());
				dto.setIconDesc(item.getIconDesc());
				dto.setIconName(item.getIconName());
				dto.setIconUrl(item.getIconUrl());
				result.add(dto);
			}
		}
		return result;
	}
	
	public CornerIconDTO getCornerIcon(Long id)throws ItemException{
		CornerIconDO obj  = new CornerIconDO();
		obj.setId(id);
		obj.setDeleteMark(0);
		obj = this.cornerIconDAO.getCornerIcon(obj);
		CornerIconDTO cornerIconDTO;
		if(obj ==null){
			return null;
		}else{
			 cornerIconDTO = new CornerIconDTO();
			 cornerIconDTO.setId(obj.getId());
			 cornerIconDTO.setIconName(obj.getIconName());
			 cornerIconDTO.setIconDesc(obj.getIconDesc());
			 cornerIconDTO.setSellerId(obj.getSellerId());
			 cornerIconDTO.setIconUrl(obj.getIconUrl());
			 return cornerIconDTO;
		}
		
	}

}

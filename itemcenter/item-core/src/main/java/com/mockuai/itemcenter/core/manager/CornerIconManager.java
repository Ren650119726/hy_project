package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

public interface CornerIconManager {

	public Long addCornerIcon(CornerIconDTO cornerIconDTO)throws ItemException; 
	
	public int deleteCornerIcon(Long id,Long sellerId)throws ItemException;

	/**
	 * 查询角标信息列表
	 * TODO 该接口还未做分页查询限制，需要
	 * @param cornerIconQTO
	 * @return
	 * @throws ItemException
	 */
	public List<CornerIconDTO> queryCornerIcon(CornerIconQTO cornerIconQTO)throws ItemException;
	
	public CornerIconDTO getCornerIcon(Long id)throws ItemException;
	
}

package com.mockuai.itemcenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyValueQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public interface GlobalPropertyValueManager {

	/**
	 * 添加全局属性值域
	 * 
	 * @param globalPropertyValueDO
	 * @return
	 * @throws ItemException
	 */
	public GlobalPropertyValueDTO addGlobalPropertyValue(GlobalPropertyValueDTO globalPropertyValueDTO)
			throws ItemException;

	/**
	 * 添加全局属性值域
	 * 
	 * @param globalPropertyValueDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateGlobalPropertyValue(GlobalPropertyValueDTO globalPropertyValueDTO) throws ItemException;

	/**
	 * 查看全局属性值域
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public GlobalPropertyValueDTO getGlobalPropertyValue(Long id) throws ItemException;

	/**
	 * 删除全局属性值域
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteGlobalPropertyValue(Long id) throws ItemException;

	/**
	 * 根据globalPropertyId删除全局属性值域
	 * 
	 * @param globalPropertyId
	 * @return
	 * @throws ItemException
	 */
	public int deleteGlobalPropertyValueListByGlobalPropertyId(Long globalPropertyId) throws ItemException;

	/**
	 * 查询全局属性值域列表
	 * 
	 * @param globalPropertyValueQTO
	 * @return
	 * @throws ItemException
	 */
	public List<GlobalPropertyValueDTO> queryGlobalPropertyValue(GlobalPropertyValueQTO globalPropertyValueQTO)
			throws ItemException;

}

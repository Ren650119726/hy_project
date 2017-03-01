package com.mockuai.itemcenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.SpuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public interface SpuPropertyManager {

	/**
	 * 添加SPU属性
	 * 
	 * @param spuPropertyDO
	 * @return
	 * @throws ItemException
	 */
	public SpuPropertyDTO addSpuProperty(SpuPropertyDTO spuPropertyDTO) throws ItemException;

	/**
	 * 添加SPU属性
	 * 
	 * @param spuPropertyDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateSpuProperty(SpuPropertyDTO spuPropertyDTO) throws ItemException;

	/**
	 * 查看SPU属性
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public SpuPropertyDTO getSpuProperty(Long id) throws ItemException;

	/**
	 * 删除SPU属性
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteSpuProperty(Long id) throws ItemException;

	/**
	 * 验证SpuPropertyDTO
	 * 
	 * @param spuPropertyDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean verifySpuPropertyDTOProperty(SpuPropertyDTO spuPropertyDTO) throws ItemException;

	/**
	 * 查询SPU属性列表
	 * 
	 * @param spuPropertyQTO
	 * @return
	 * @throws ItemException
	 */
	public List<SpuPropertyDTO> querySpuProperty(SpuPropertyQTO spuPropertyQTO) throws ItemException;

}

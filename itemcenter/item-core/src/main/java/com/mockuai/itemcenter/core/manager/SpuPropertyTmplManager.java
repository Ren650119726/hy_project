package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.SpuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyTmplQTO;

@Service
public interface SpuPropertyTmplManager {

	/**
	 * 添加SPU属性模板
	 * 
	 * @param spuPropertyTmplDTO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public SpuPropertyTmplDTO addSpuPropertyTmpl(SpuPropertyTmplDTO spuPropertyTmplDTO) throws ItemException;

	/**
	 * 添加SPU属性模板
	 * 
	 * @param spuPropertyTmplDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateSpuPropertyTmpl(SpuPropertyTmplDTO spuPropertyTmplDTO) throws ItemException;

	/**
	 * 查看SPU属性模板
	 * 
	 * @param SpuPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public SpuPropertyTmplDTO getSpuPropertyTmpl(Long id) throws ItemException;

	/**
	 * 删除SPU属性模板
	 * 
	 * @param SpuPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteSpuPropertyTmpl(Long id) throws ItemException;

	/**
	 * 验证SpuPropertyTmplDTO
	 * 
	 * @param spuPropertyTmplDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean verifySpuPropertyTmplDTOProperty(SpuPropertyTmplDTO spuPropertyTmplDTO) throws ItemException;

	/**
	 * 查询SPU属性模板列表
	 * 
	 * @param spuPropertyTmplQTO
	 * @return
	 * @throws ItemException
	 */
	public List<SpuPropertyTmplDTO> querySpuPropertyTmpl(SpuPropertyTmplQTO spuPropertyTmplQTO) throws ItemException;

}

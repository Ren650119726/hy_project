package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;

@Service
public interface SkuPropertyTmplManager {

	/**
	 * 添加SKU属性模板
	 * 
	 * @param SkuPropertyTmplDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public Long addSkuPropertyTmpl(SkuPropertyTmplDTO SkuPropertyTmplDTO) throws ItemException;

	/**
	 * 添加SKU属性模板
	 * 
	 * @param SkuPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateSkuPropertyTmpl(SkuPropertyTmplDTO SkuPropertyTmplDTO) throws ItemException;

	/**
	 * 查看SKU属性模板
	 * 
	 * @param SkuPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public SkuPropertyTmplDTO getSkuPropertyTmpl(Long id) throws ItemException;

	/**
	 * 删除SKU属性模板
	 * 
	 * @param SkuPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteSkuPropertyTmpl(Long id) throws ItemException;

	/**
	 * 查询SKU属性模板列表
	 * 
	 * @param SkuPropertyTmplQTO
	 * @return
	 * @throws ItemException
	 */
	public List<SkuPropertyTmplDTO> querySkuPropertyTmpl(SkuPropertyTmplQTO SkuPropertyTmplQTO) throws ItemException;
	
	/**
	 * 根据类目查找对应的属性及其基本取值列表
	 * @param skuPropertyTmplQTO
	 * @return
	 * @throws ItemException
	 */
	public List<SkuPropertyTmplDTO> querySkuPropertyTmplWithValue(SkuPropertyTmplQTO skuPropertyTmplQTO)throws ItemException;


}

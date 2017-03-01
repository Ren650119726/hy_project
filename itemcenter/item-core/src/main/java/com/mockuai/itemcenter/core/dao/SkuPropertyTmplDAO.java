package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.SkuPropertyTmplDO;

@Service
public interface SkuPropertyTmplDAO {

	/**
	 * 增加SKU属性模板
	 * 
	 * @param skuPropertyDO
	 * @return
	 */
	Long addSkuPropertyTmpl(SkuPropertyTmplDO skuPropertyTmplDO);

	/**
	 * 根据id获取SKU属性模板
	 * 
	 * @param id
	 * @return
	 */
	SkuPropertyTmplDO getSkuPropertyTmpl(Long id);
	
	/**
	 * 根据id删除SKU属性模板
	 * 
	 * @param id
	 * @return
	 */
	int deleteSkuPropertyTmpl(Long id);

	/**
	 * 更新SKU属性模板信息
	 * 
	 * @param skuPropertyTmplDO
	 * @return
	 */
	int updateSkuPropertyTmpl(SkuPropertyTmplDO skuPropertyDO);

	/**
	 * 返回SKU属性模板列表
	 * 
	 * @param skuPropertyTmplQTO
	 * @return
	 */
	List<SkuPropertyTmplDO> querySkuPropertyTmpl(SkuPropertyTmplQTO skuPropertyTmplQTO);
	
	
	/**
	 * 获取商品的销售属性及其取值列表
	 * @param skuPropertyTmplQTO
	 * @return
	 */
	List<SkuPropertyTmplDO> querySkuPropertyTmplWithValue(SkuPropertyTmplQTO skuPropertyTmplQTO);

	
	
}
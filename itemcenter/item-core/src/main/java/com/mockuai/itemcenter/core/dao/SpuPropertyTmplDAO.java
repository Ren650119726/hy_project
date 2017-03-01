package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SpuPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.SpuPropertyTmplDO;

@Service
public interface SpuPropertyTmplDAO {

	/**
	 * 增加SPU属性模板
	 * 
	 * @param skuPropertyDO
	 * @return
	 */
	Long addSpuPropertyTmpl(SpuPropertyTmplDO spuPropertyTmplDO);

	/**
	 * 根据id获取SPU属性模板
	 * 
	 * @param id
	 * @return
	 */
	SpuPropertyTmplDO getSpuPropertyTmpl(Long id);

	/**
	 * 更新SPU属性模板信息
	 * 
	 * @param spuPropertyTmplDO
	 * @return
	 */
	int updateSpuPropertyTmpl(SpuPropertyTmplDO skuPropertyDO);

	/**
	 * 返回SPU属性模板列表
	 * 
	 * @param spuPropertyTmplQTO
	 * @return
	 */
	List<SpuPropertyTmplDO> querySpuPropertyTmpl(SpuPropertyTmplQTO spuPropertyTmplQTO);

}
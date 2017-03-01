package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.core.domain.SpuPropertyDO;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SpuPropertyQTO;

@Service
public interface SpuPropertyDAO {

	/**
	 * 增加SPU属性
	 * 
	 * @param spuPropertyDO
	 * @return
	 */
	Long addSpuProperty(SpuPropertyDO spuPropertyDO);

	/**
	 * 根据id获取SPU属性
	 * 
	 * @param id
	 * @return
	 */
	SpuPropertyDO getSpuProperty(Long id);

	/**
	 * 更新SPU属性信息
	 * 
	 * @param spuPropertyDO
	 * @return
	 */
	int updateSpuProperty(SpuPropertyDO spuPropertyDO);

	/**
	 * 返回SPU属性列表
	 * 
	 * @param spuPropertyQTO
	 * @return
	 */
	List<SpuPropertyDO> querySpuProperty(SpuPropertyQTO spuPropertyQTO);

}
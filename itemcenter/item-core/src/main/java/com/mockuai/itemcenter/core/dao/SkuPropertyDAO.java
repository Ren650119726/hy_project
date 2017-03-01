package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.domain.SkuPropertyDO;

@Service
public interface SkuPropertyDAO {

	/**
	 * 增加SKU属性
	 * 
	 * @param skuPropertyDO
	 * @return
	 */
	Long addSkuProperty(SkuPropertyDO skuPropertyDO);

	/**
	 * 根据id,sellerId获取SKU属性
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	SkuPropertyDO getSkuProperty(Long id, Long sellerId, String bizCode);

	/**
	 * 根据id,sellerId删除SKU属性
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteSkuProperty(Long id, Long sellerId, String bizCode);

	/**
	 * 根据skuId,sellerId删除SKU属性
	 * 
	 * @param id
	 * @param sellerId
	 * @param bizCode
	 * @return
	 */
	int deleteSkuPropertyBySkuId(Long skuId, Long sellerId, String bizCode);

	/**
	 * 更新SKU属性信息
	 * 
	 * @param skuPropertyDO
	 * @return
	 */
	int updateSkuProperty(SkuPropertyDO skuPropertyDO);

	/**
	 * 返回SKU属性列表
	 * 
	 * @param skuPropertyQTO
	 * @return
	 */
	List<SkuPropertyDO> querySkuProperty(SkuPropertyQTO skuPropertyQTO);
	
	/**
	 * 查找对应的销售属性及其基本值
	 * @param skuPropertyQTO
	 * @return
	 */
	List<SkuPropertyDO> querySkuPropertyWithValue(SkuPropertyQTO skuPropertyQTO);
	
	
	int deleteByItemId(SkuPropertyQTO skuPropertyQTO);

	Long batchUpdateSkuProperty(SkuPropertyDO skuPropertyDO);

	Long trashByItemId(SkuPropertyDO query);

	Long recoveryByItemId(SkuPropertyDO query);

	Long emptyRecycleBin(SkuPropertyDO query);
}
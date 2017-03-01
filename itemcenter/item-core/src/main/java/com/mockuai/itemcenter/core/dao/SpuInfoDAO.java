package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.core.domain.SpuInfoDO;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SpuInfoQTO;

@Service
public interface SpuInfoDAO {

	/**
	 * 增加SPU_Info信息
	 * 
	 * @param spuInfoDO
	 * @return
	 */
	Long addSpuInfo(SpuInfoDO spuInfoDO);

	/**
	 * 根据id获取SPU_Info信息
	 * 
	 * @param id
	 * @return
	 */
	SpuInfoDO getSpuInfo(Long id);

	/**
	 * 更新SPU_Info信息信息
	 * 
	 * @param spuInfoDO
	 * @return
	 */
	int updateSpuInfo(SpuInfoDO spuInfoDO);

	/**
	 * 返回SPU_Info信息列表
	 * 
	 * @param spuInfoQTO
	 * @return
	 */
	List<SpuInfoDO> querySpuInfo(SpuInfoQTO spuInfoQTO);

}
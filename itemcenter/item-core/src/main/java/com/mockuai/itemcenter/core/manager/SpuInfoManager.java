package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.SpuInfoDTO;
import com.mockuai.itemcenter.common.domain.qto.SpuInfoQTO;

@Service
public interface SpuInfoManager {

	/**
	 * 添加SPU_Info信息
	 * 
	 * @param spuInfoDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public SpuInfoDTO addSpuInfo(SpuInfoDTO spuInfoDTO) throws ItemException;

	/**
	 * 添加SPU_Info信息
	 * 
	 * @param spuInfoDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateSpuInfo(SpuInfoDTO spuInfoDTO) throws ItemException;

	/**
	 * 查看SPU_Info信息
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public SpuInfoDTO getSpuInfo(Long id) throws ItemException;

	/**
	 * 删除SPU_Info信息
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteSpuInfo(Long id) throws ItemException;

	/**
	 * 验证SpuInfoDTO
	 * 
	 * @param spuInfoDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean verifySpuInfoDTOProperty(SpuInfoDTO spuInfoDTO) throws ItemException;

	/**
	 * 查询SPU_Info信息列表
	 * 
	 * @param spuInfoQTO
	 * @return
	 * @throws ItemException
	 */
	public List<SpuInfoDTO> querySpuInfo(SpuInfoQTO spuInfoQTO) throws ItemException;

}

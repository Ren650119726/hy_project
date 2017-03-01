package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.domain.SellerBrandDO;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public interface SellerBrandManager {
	
	/**
	 * 查询品牌
	 * @param sellerBrandQTO
	 * @return
	 */
	public List<SellerBrandDTO> querySellerBrand(SellerBrandQTO sellerBrandQTO)throws ItemException;
	
	/**
	 * 新增供应商的品牌
	 * @param sellerBrandDTO
	 * @return
	 * @throws ItemException
	 */
	public Long addSellerBrand(SellerBrandDTO sellerBrandDTO)throws ItemException;
	
	/**
	 * 删除供应的品牌
	 * @param sellerBrandDTO
	 * @return
	 * @throws ItemException
	 */
	public int deleteSellerBrand(Long id)throws ItemException;
	
	public SellerBrandDTO getSellerBrand(Long id)throws ItemException;
	
	public int updateSellerBrand(SellerBrandDTO sellerBrandDTO)throws ItemException;


	
}	

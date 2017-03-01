package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.domain.SellerBrandDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerBrandDAO {
	
	/**
	 * 复合条件查询商家的品牌
	 * @param sellerBrandQTO
	 * @return
	 */
	public List<SellerBrandDO> querySellerBrand(SellerBrandQTO sellerBrandQTO);


	public Long addSellerBrand(SellerBrandDO sellerBrandDO);
	
	public int deleteSellerBrand(SellerBrandDO sellerBrandDO);
	
	public SellerBrandDO getSellerBrand(SellerBrandDO sellerBrandDO);
	
	public int updateSellerBrand(SellerBrandDO sellerBrandDO);
	
}

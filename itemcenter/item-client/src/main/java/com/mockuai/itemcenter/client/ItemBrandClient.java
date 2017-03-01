package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;

public interface ItemBrandClient {
	
	/**
	 * 复合条件查询品牌
	 * @param itemBrandQTO
	 */
	public Response<List<ItemBrandDTO>> queryBrand(ItemBrandQTO itemBrandQTO, String appKey);

	/**
	 * 复合条件查询品牌
	 * @param itemBrandDTO
	 */
	public Response<ItemBrandDTO> addItemBrand(ItemBrandDTO itemBrandDTO, String appKey);
	
	/**
	 * 查询某个供应商下关联的品牌
	 * @param sellerBrandQTO
	 * @return
	 */
	public Response<List<SellerBrandDTO>> querySellerBrand(SellerBrandQTO sellerBrandQTO, String appKey);

	/**
	 * 新增商家关联的品牌
	 * @param sellerBrandDTO
	 * @return
	 */
	public Response<SellerBrandDTO> addSellerBrand(SellerBrandDTO sellerBrandDTO, String appKey);
	
	public Response<Boolean> deleteSellerBrand(Long id,Long supplierId, String appKey);

	public Response<Boolean> deleteItemBrand(Long id, String appKey);
	
	public Response<SellerBrandDTO> getSellerBrand(Long id,Long supplierId, String appKey);
	
	public Response<Boolean> updateSellerBrand(SellerBrandDTO sellerBrandDTO, String appKey);

	public Response<SellerBrandDTO> getSellerBrandById(Long brandId);
	
}

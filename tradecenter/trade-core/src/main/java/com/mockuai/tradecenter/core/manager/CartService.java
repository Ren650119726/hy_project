package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.CartItemDTO;

public interface CartService {
	
	/**
	 * 根据商品ID、价格、数量来查询是满赠的商品
	 * @param ItemSkuId
	 * @param supplierId
	 * @param price
	 * @param number
	 * @return
	 */
	public CartItemDTO getPromotionItem(Long ItemSkuId,Long supplierId,Long price,Integer number);
	  
	/**
	 * 根据商品来获取购物车中优惠的金额
	 * @param cartItemDTOList
	 * @return
	 */
	public Long getCoupon(List<CartItemDTO> cartItemDTOList);
		
	
}


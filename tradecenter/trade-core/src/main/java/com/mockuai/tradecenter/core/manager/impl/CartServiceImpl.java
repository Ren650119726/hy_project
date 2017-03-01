package com.mockuai.tradecenter.core.manager.impl;

import java.util.List;

import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.core.manager.CartService;

public class CartServiceImpl implements CartService{

	@Override
	public CartItemDTO getPromotionItem(Long ItemSkuId, Long supplierId,
			Long price, Integer number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCoupon(List<CartItemDTO> cartItemDTOList) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

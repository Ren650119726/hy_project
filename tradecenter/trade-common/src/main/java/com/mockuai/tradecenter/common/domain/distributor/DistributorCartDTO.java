package com.mockuai.tradecenter.common.domain.distributor;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.common.domain.CartItemDTO;

import java.util.List;

public class DistributorCartDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9017378831860168335L;

	/**
	 * 分销商id，代表该商品列表从哪个分销商那里购买的
	 */
	private Long distributorId;

	/**
	 * 分销商店铺名称
	 */
	private String distributorName;

	private List<CartItemDTO> cartItems;
	
	private List<DiscountInfo> discountInfoList;

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorShopName) {
		this.distributorName = distributorShopName;
	}

	public List<CartItemDTO> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemDTO> cartItems) {
		this.cartItems = cartItems;
	}

	public List<DiscountInfo> getDiscountInfoList() {
		return discountInfoList;
	}

	public void setDiscountInfoList(List<DiscountInfo> discountInfoList) {
		this.discountInfoList = discountInfoList;
	}
	
	

}

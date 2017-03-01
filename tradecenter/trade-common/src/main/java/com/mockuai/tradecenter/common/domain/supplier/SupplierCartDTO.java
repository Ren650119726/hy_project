package com.mockuai.tradecenter.common.domain.supplier;

import java.util.List;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.common.domain.CartItemDTO;

public class SupplierCartDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017378831860168335L;

	String supplierName;
	
	Long supplierId;
	
	List<CartItemDTO> cartItems;
	
	List<DiscountInfo> discountInfoList;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

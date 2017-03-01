package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 用于促销平台查询的单个商品类
 * @author cwr
 */
public class PromotionItemQTO implements Serializable{
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<ItemSkuQTO> getItems() {
		return items;
	}

	public void setItems(List<ItemSkuQTO> items) {
		this.items = items;
	}

	public List<String> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<String> couponList) {
		this.couponList = couponList;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}


	private Long userId;  // 用户id
 	
	private List<ItemSkuQTO> items; // 商品列表
	
	private List<String> couponList; // 优惠券列表
	
	private Integer platform; // 平台
	
}

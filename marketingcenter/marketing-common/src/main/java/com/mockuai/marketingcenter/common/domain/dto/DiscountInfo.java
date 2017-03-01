package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

public class DiscountInfo implements Serializable {
	private static final long serialVersionUID = 4336574627439107164L;
	private MarketActivityDTO activity;
	/**
	 * 在此单中享有优惠活动的单品,非单品,此处为空
	 */
	private List<MarketItemDTO> itemList;
	/**
	 * 无券优惠活动,此处为空
	 */
	private List<GrantedCouponDTO> availableCoupons;
	/**
	 * 赠送优惠券列表
	 */
	private List<ActivityCouponDTO> couponList;
	/**
	 * 节省的邮费
	 */
	private Long savedPostage;
	private Long consume;
	/**
	 * 优惠券特有,优惠力度说明, 满 XXXX 即可使用
	 */
	private String desc;
	private boolean freePostage;
	private Long discountAmount;
	/**
	 * 换购是否可用
	 */
	private boolean isAvailable;
	private List<MarketItemDTO> giftList;

	public DiscountInfo() {
	}

	public DiscountInfo(MarketActivityDTO activity) {
		this.activity = activity;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public MarketActivityDTO getActivity() {
		return this.activity;
	}

	public void setActivity(MarketActivityDTO activity) {
		this.activity = activity;
	}

	public List<MarketItemDTO> getItemList() {
		return this.itemList;
	}

	public void setItemList(List<MarketItemDTO> itemList) {
		this.itemList = itemList;
	}

	public List<MarketItemDTO> getGiftList() {
		return this.giftList;
	}

	public void setGiftList(List<MarketItemDTO> giftList) {
		this.giftList = giftList;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isFreePostage() {
		return this.freePostage;
	}

	public void setFreePostage(boolean freePostage) {
		this.freePostage = freePostage;
	}

	public Long getConsume() {
		return consume;
	}

	public void setConsume(Long consume) {
		this.consume = consume;
	}

	public Long getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public List<GrantedCouponDTO> getAvailableCoupons() {
		return this.availableCoupons;
	}

	public void setAvailableCoupons(List<GrantedCouponDTO> availableCoupons) {
		this.availableCoupons = availableCoupons;
	}

	public List<ActivityCouponDTO> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<ActivityCouponDTO> couponList) {
		this.couponList = couponList;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Long getSavedPostage() {
		return this.savedPostage;
	}

	public void setSavedPostage(Long savedPostage) {
		this.savedPostage = savedPostage;
	}
}
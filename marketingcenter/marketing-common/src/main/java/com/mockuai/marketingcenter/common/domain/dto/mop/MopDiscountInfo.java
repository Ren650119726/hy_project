package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopDiscountInfo implements Serializable {
	private MopMarketActivityDTO marketActivity;
	private List<MopMarketItemDTO> itemList;
	private List<MopGrantedCouponDTO> availableCouponList;
	private List<MopActivityCouponDTO> couponList;
	private Long discountAmount;
	private Long savedPostage;
	private Long consume;
	private String desc;
	private int freePostage;
	private int isAvailable;
	private List<MopMarketItemDTO> giftList;

	public MopMarketActivityDTO getMarketActivity() {
		return marketActivity;
	}

	public void setMarketActivity(MopMarketActivityDTO marketActivity) {
		this.marketActivity = marketActivity;
	}

	public List<MopMarketItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<MopMarketItemDTO> itemList) {
		this.itemList = itemList;
	}

	public List<MopGrantedCouponDTO> getAvailableCouponList() {
		return availableCouponList;
	}

	public void setAvailableCouponList(List<MopGrantedCouponDTO> availableCouponList) {
		this.availableCouponList = availableCouponList;
	}

	public List<MopActivityCouponDTO> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<MopActivityCouponDTO> couponList) {
		this.couponList = couponList;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getSavedPostage() {
		return savedPostage;
	}

	public void setSavedPostage(Long savedPostage) {
		this.savedPostage = savedPostage;
	}

	public Long getConsume() {
		return consume;
	}

	public void setConsume(Long consume) {
		this.consume = consume;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int isFreePostage() {
		return freePostage;
	}

	public int getFreePostage() {
		return freePostage;
	}

	public void setFreePostage(int freePostage) {
		this.freePostage = freePostage;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public List<MopMarketItemDTO> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<MopMarketItemDTO> giftList) {
		this.giftList = giftList;
	}
}
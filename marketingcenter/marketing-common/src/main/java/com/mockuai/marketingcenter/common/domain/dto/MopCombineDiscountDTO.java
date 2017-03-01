package com.mockuai.marketingcenter.common.domain.dto;

import com.mockuai.marketingcenter.common.domain.dto.mop.MopDiscountInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edgar.zr on 2/02/2016.
 */
public class MopCombineDiscountDTO implements Serializable {
	/**
	 * 包含以下的所有优惠信息
	 */
	private List<MopDiscountInfo> discountInfoList;
	/**
	 * 满减送优惠
	 */
	private List<MopDiscountInfo> compositeActivityList;
	/**
	 * 优惠券优惠
	 */
	private List<MopDiscountInfo> couponList;
	/**
	 * 套装优惠
	 */
	private List<MopDiscountInfo> suitList;
	/**
	 * 换购优惠
	 */
	private List<MopDiscountInfo> barterList;

	/**
	 * 限时购优惠
	 */
	private List<MopDiscountInfo> timeLimitList;
	
	/**
	 * 限时购的收益比例描述
	 */
	private String gainPercentDesc;
	/**
	 * 限时购的收益比例描述(1.2.8期调整)
	 */
	private String gainSharingDesc;
	/**
	 * 限时购的收益描述(1.2.8期调整)
	 */
	private String sharingGains;

	public String getGainSharingDesc() {
		return gainSharingDesc;
	}

	public void setGainSharingDesc(String gainSharingDesc) {
		this.gainSharingDesc = gainSharingDesc;
	}

	public String getSharingGains() {
		return sharingGains;
	}

	public void setSharingGains(String sharingGains) {
		this.sharingGains = sharingGains;
	}

	public String getGainPercentDesc() {
		return gainPercentDesc;
	}

	public void setGainPercentDesc(String gainPercentDesc) {
		this.gainPercentDesc = gainPercentDesc;
	}

	public List<MopDiscountInfo> getDiscountInfoList() {
		return discountInfoList;
	}

	public void setDiscountInfoList(List<MopDiscountInfo> discountInfoList) {
		this.discountInfoList = discountInfoList;
	}

	public List<MopDiscountInfo> getCompositeActivityList() {
		return compositeActivityList;
	}

	public void setCompositeActivityList(List<MopDiscountInfo> compositeActivityList) {
		this.compositeActivityList = compositeActivityList;
	}

	public List<MopDiscountInfo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<MopDiscountInfo> couponList) {
		this.couponList = couponList;
	}

	public List<MopDiscountInfo> getSuitList() {
		return suitList;
	}

	public void setSuitList(List<MopDiscountInfo> suitList) {
		this.suitList = suitList;
	}

	public List<MopDiscountInfo> getBarterList() {
		return barterList;
	}

	public void setBarterList(List<MopDiscountInfo> barterList) {
		this.barterList = barterList;
	}

	public List<MopDiscountInfo> getTimeLimitList() {
		return timeLimitList;
	}

	public void setTimeLimitList(List<MopDiscountInfo> timeLimitList) {
		this.timeLimitList = timeLimitList;
	}
}
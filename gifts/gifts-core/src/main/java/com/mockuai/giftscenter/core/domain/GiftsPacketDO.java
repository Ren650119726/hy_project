package com.mockuai.giftscenter.core.domain;

import java.util.Date;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class GiftsPacketDO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private Integer giftsValidate;
    private Long giftsAdminId;
    private String giftsName;
    private Date giftsStartTime;
    private Date giftsEndTime;
    private Long giftsDealPrice;
    private Long giftsOldPrice;
    private Long giftsReturnedMoney;
    private String giftsImgPath;
    private Long itemId;
    private Long skuId;
    private Long goodsId;
    private Long goodsSkuId;
    private Long goodsAmount;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    
    
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Long getGoodsSkuId() {
		return goodsSkuId;
	}
	public void setGoodsSkuId(Long goodsSkuId) {
		this.goodsSkuId = goodsSkuId;
	}
	public Long getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(Long goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getGiftsValidate() {
		return giftsValidate;
	}
	public void setGiftsValidate(Integer giftsValidate) {
		this.giftsValidate = giftsValidate;
	}
	public Long getGiftsAdminId() {
		return giftsAdminId;
	}
	public void setGiftsAdminId(Long giftsAdminId) {
		this.giftsAdminId = giftsAdminId;
	}
	public String getGiftsName() {
		return giftsName;
	}
	public void setGiftsName(String giftsName) {
		this.giftsName = giftsName;
	}
	public Date getGiftsStartTime() {
		return giftsStartTime;
	}
	public void setGiftsStartTime(Date giftsStartTime) {
		this.giftsStartTime = giftsStartTime;
	}
	public Date getGiftsEndTime() {
		return giftsEndTime;
	}
	public void setGiftsEndTime(Date giftsEndTime) {
		this.giftsEndTime = giftsEndTime;
	}

	public String getGiftsImgPath() {
		return giftsImgPath;
	}
	public void setGiftsImgPath(String giftsImgPath) {
		this.giftsImgPath = giftsImgPath;
	}
	
	public Long getGiftsDealPrice() {
		return giftsDealPrice;
	}
	public void setGiftsDealPrice(Long giftsDealPrice) {
		this.giftsDealPrice = giftsDealPrice;
	}
	public Long getGiftsOldPrice() {
		return giftsOldPrice;
	}
	public void setGiftsOldPrice(Long giftsOldPrice) {
		this.giftsOldPrice = giftsOldPrice;
	}
	public Long getGiftsReturnedMoney() {
		return giftsReturnedMoney;
	}
	public void setGiftsReturnedMoney(Long giftsReturnedMoney) {
		this.giftsReturnedMoney = giftsReturnedMoney;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
    
    
    
   
}
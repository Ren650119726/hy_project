package com.mockuai.seckillcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class SeckillDTO implements Serializable {

	private Long id;
	private String bizCode;
	private Long sellerId;
	/**
	 * 多店铺商品卖家 id
	 */
	private Long itemSellerId;
	private Long itemId;
	private Long originItemId;
	private Long skuId;
	private Long originSkuId;
	private String itemName;
	/**
	 * 原商品原库存
	 */
	private Long originStockNum;
	/**
	 * 秒杀原库存
	 */
	private Long stockNum;
	/**
	 * 当前秒杀商品库存
	 */
	private Long currentStockNum;
	private Long frozenNum;
	/**
	 * 原价
	 */
	private Long marketPrice;
	private String content;
	private Date startTime;
	private Date endTime;
	private Integer limit;
	/**
	 * 秒杀价格
	 */
	private Long price;
	private Long sales;
	private String iconUrl;

	private Integer lifecycle;
	/**
	 * 商品售空的时间
	 */
	private Date itemInvalidTime;
	private Integer status;
	/**
	 * 退还 0:no 1:yes
	 */
	private Integer giveBack;
	private Integer deleteMark;
	private Date gmtCreated;
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getItemSellerId() {
		return itemSellerId;
	}

	public void setItemSellerId(Long itemSellerId) {
		this.itemSellerId = itemSellerId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getOriginItemId() {
		return originItemId;
	}

	public void setOriginItemId(Long originItemId) {
		this.originItemId = originItemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getOriginSkuId() {
		return originSkuId;
	}

	public void setOriginSkuId(Long originSkuId) {
		this.originSkuId = originSkuId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getOriginStockNum() {
		return originStockNum;
	}

	public void setOriginStockNum(Long originStockNum) {
		this.originStockNum = originStockNum;
	}

	public Long getStockNum() {
		return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public Long getCurrentStockNum() {
		return currentStockNum;
	}

	public void setCurrentStockNum(Long currentStockNum) {
		this.currentStockNum = currentStockNum;
	}

	public Long getFrozenNum() {
		return frozenNum;
	}

	public void setFrozenNum(Long frozenNum) {
		this.frozenNum = frozenNum;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Integer getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Integer lifecycle) {
		this.lifecycle = lifecycle;
	}

	public Date getItemInvalidTime() {
		return itemInvalidTime;
	}

	public void setItemInvalidTime(Date itemInvalidTime) {
		this.itemInvalidTime = itemInvalidTime;
	}

	public Integer getGiveBack() {
		return giveBack;
	}

	public void setGiveBack(Integer giveBack) {
		this.giveBack = giveBack;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
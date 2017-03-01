package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class ItemExtraInfoDO {
	private Long id;

	private Long supplierId;// 供应商ID

	private Long itemId;// 商品ID

	private Integer cartType;// 购物车业务类型，0：不允许购物车，1：允许购物车

	private Integer levelLimit;// 购物车业务类型，0：不允许购物车，1：允许购物车

	private Integer commentCount;// 评论数

	private Integer collectCount;// 收藏数

	private Integer score;// 商品评分，由所有买家的评价评分算得

	private Integer stockNum;// 库存总量

	private Integer soldNum;// 已售商品总量

	private Integer isDeleted;

	private Date gmtCreated;

	private Date gmtModified;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getCartType() {
		return cartType;
	}

	public void setCartType(Integer cartType) {
		this.cartType = cartType;
	}

	public Integer getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(Integer levelLimit) {
		this.levelLimit = levelLimit;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(Integer soldNum) {
		this.soldNum = soldNum;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ItemExtraInfoDO [id=" + id + ", supplierId=" + supplierId + ", itemId=" + itemId + "]";
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
}
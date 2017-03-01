package com.mockuai.seckillcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 缓存秒杀数据
 * <p/>
 * Created by edgar.zr on 6/14/2016.
 */
public class SeckillCacheDTO implements Serializable {
	private Long seckillId;
	private Long sellerId;
	private Long itemId;
	private Long skuId;
	private Date startTime;
	private Date endTime;
	private Integer status;
	/**
	 * 秒杀商品的初始库存数量
	 */
	private Long originStockNum;
	/**
	 * 秒杀商品当前库存
	 */
	private Long stockNum;
	private Long frozenNum;
	private Date itemInvalidTime;

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getFrozenNum() {
		return frozenNum;
	}

	public void setFrozenNum(Long frozenNum) {
		this.frozenNum = frozenNum;
	}

	public Date getItemInvalidTime() {
		return itemInvalidTime;
	}

	public void setItemInvalidTime(Date itemInvalidTime) {
		this.itemInvalidTime = itemInvalidTime;
	}
}
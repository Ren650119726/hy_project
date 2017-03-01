package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

public class PropertyDTO implements Serializable {

	public final static String CONSUME = "consume";
	public final static String QUOTA = "quota";
	public final static String FREE_POSTAGE = "freePostage";
	public final static String GIFT_ITEM_LIST = "giftItemList";
	public final static String EXTRA = "extra";
	public final static String LIMIT = "limit";
	public final static String SKUID = "skuId";
	public final static String ITEMID = "itemId";
	// id 为 优惠券 的 id
	public final static String COUPON_LIST = "couponList";

	private Long id;
	private Integer type;
	private Integer ownerType;
	private Long ownerId;
	private String name;
	private String pkey;
	private String value;
	private Integer valueType;
	private String desc;
	private Integer creatorType;
	private Long creatorId;
	private String bizCode;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPkey() {
		return this.pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getValueType() {
		return this.valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getCreatorType() {
		return this.creatorType;
	}

	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getBizCode() {
		return this.bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
}
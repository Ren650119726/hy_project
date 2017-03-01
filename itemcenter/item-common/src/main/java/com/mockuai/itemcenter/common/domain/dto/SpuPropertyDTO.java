package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

/**
 * SPU属性DTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */

public class SpuPropertyDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4603410702095268035L;

	private Long id;

	private Long spuId;

	private String keyName;// 属性名

	private String val;// 属性名

	private Integer valType;// 属性值类型

	private Integer sort;// 排序字段，如果该字段值相同，则按照属性key来排序

	private Long bizMark;// 业务标志，例如是否包含图片等等

	private String imgUrl;// 属性图

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName == null ? null : keyName.trim();
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val == null ? null : val.trim();
	}

	public Integer getValType() {
		return valType;
	}

	public void setValType(Integer valType) {
		this.valType = valType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getBizMark() {
		return bizMark;
	}

	public void setBizMark(Long bizMark) {
		this.bizMark = bizMark;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl == null ? null : imgUrl.trim();
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	@Override
	public String toString() {
		return "SpuPropertyDTO [id=" + id + ", spuId=" + spuId + ", keyName=" + keyName + ", val=" + val + "]";
	}

}
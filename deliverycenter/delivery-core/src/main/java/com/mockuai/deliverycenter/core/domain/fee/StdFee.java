package com.mockuai.deliverycenter.core.domain.fee;

import java.util.Date;

import com.mockuai.deliverycenter.core.domain.BaseDo;

public class StdFee extends BaseDo {
	private Integer id;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String name;
	private String code;
	private String remark;
	private Integer allowDefault;
	private Integer sort;
	private Long firstWeight;

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	private Long continueWeight;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getAllowDefault() {
		return allowDefault;
	}

	public void setAllowDefault(Integer allowDefault) {
		this.allowDefault = allowDefault;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(Long firstWeight) {
		this.firstWeight = firstWeight;
	}

	public Long getContinueWeight() {
		return continueWeight;
	}

	public void setContinueWeight(Long continueWeight) {
		this.continueWeight = continueWeight;
	}

	public Integer getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(Integer weightUnit) {
		this.weightUnit = weightUnit;
	}

	public Long getFirstFee() {
		return firstFee;
	}

	public void setFirstFee(Long firstFee) {
		this.firstFee = firstFee;
	}

	public Long getContinueFee() {
		return continueFee;
	}

	public void setContinueFee(Long continueFee) {
		this.continueFee = continueFee;
	}

	public Integer getFeeLimitMark() {
		return feeLimitMark;
	}

	public void setFeeLimitMark(Integer feeLimitMark) {
		this.feeLimitMark = feeLimitMark;
	}

	public Long getMinFee() {
		return minFee;
	}

	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}

	public Long getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(Long maxFee) {
		this.maxFee = maxFee;
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

	private Integer weightUnit;
	private Long firstFee;
	private Long continueFee;
	private Integer feeLimitMark;
	private Long minFee;
	private Long maxFee;
	private Integer deleted;
	private Date gmtCreated;
	private Date gmtModified;
}

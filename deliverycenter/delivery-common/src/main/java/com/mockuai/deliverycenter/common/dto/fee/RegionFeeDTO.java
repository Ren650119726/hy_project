package com.mockuai.deliverycenter.common.dto.fee;

import java.util.Date;

import com.mockuai.deliverycenter.common.dto.BaseDTO;

public class RegionFeeDTO extends BaseDTO implements java.io.Serializable {
	private Integer id;
	private Integer regionId;
	private Integer feeId;
	private Long firstWeight;
	private Long continueWeight;
	private Long firstFee;
	private Long continueFee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getFirstWeight() {
		return firstWeight;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getFeeId() {
		return feeId;
	}

	public void setFeeId(Integer feeId) {
		this.feeId = feeId;
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

	private Date gmtCreated;
	private Date gmtModified;
}

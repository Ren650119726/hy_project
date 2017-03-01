package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SellerBrandDTO implements Serializable{
	private Long itemCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandEnName() {
		return brandEnName;
	}

	public void setBrandEnName(String brandEnName) {
		this.brandEnName = brandEnName;
	}
	
	public String getCategoryClass() {
		return categoryClass;
	}

	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	private Long id;

    private Integer status;

    private String bizCode;
    
    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    private String brandName;
    
	private String brandEnName;
    
	private String categoryClass;
	
	private String logo;
    
    private String brandDesc;

    private String bannerImg ;


	private List<Long> categoryIdList;



    public List<Long> getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(List<Long> categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	public Long getItemCount() {
		return itemCount;
	}

    public String getBannerImg() {
        return bannerImg;
    }

	@Override
	public String toString() {
		return "SellerBrandDTO{" +
				"itemCount=" + itemCount +
				", id=" + id +
				", status=" + status +
				", bizCode='" + bizCode + '\'' +
				", deleteMark=" + deleteMark +
				", gmtCreated=" + gmtCreated +
				", gmtModified=" + gmtModified +
				", brandName='" + brandName + '\'' +
				", brandEnName='" + brandEnName + '\'' +
				", categoryClass='" + categoryClass + '\'' +
				", logo='" + logo + '\'' +
				", brandDesc='" + brandDesc + '\'' +
				", bannerImg='" + bannerImg + '\'' +
				", categoryIdList=" + categoryIdList +
				'}';
	}

	/**
	 * banner huangsiqian git username Test
	 * @param bannerImg
     */
    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }
}

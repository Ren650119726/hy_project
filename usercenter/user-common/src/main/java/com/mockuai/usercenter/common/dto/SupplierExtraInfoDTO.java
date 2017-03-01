package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

public class SupplierExtraInfoDTO extends BaseDTO implements Serializable {
	private Long id;

	private Long userId;

	private String showName;

	private String supplierName;

	private boolean isDefault;

	private String address;

	private String tel;

	private String zip;

	private Byte divisionId;

	private String contactName;

	private String contactPosition;

	private String contactIdcard;

	private String contactMobile;

	private String contactQq;

	private String contactEmail;

	private Byte type;

	private Byte buyLimit;

	private Byte storageDeliveryId;

	private String licenseNo;

	private Integer licenseBegin;

	private Integer licenseEnd;

	private String licenseAddr;

	private Integer regCapital;

	private String busScope;

	private String legalPersonName;

	private String fax;

	private String website;

	private String brands;

	private String tmallWebsite;

	private String licenseScanImg;

	private String orgScanImg;

	private String taxScanImg;

	private String idcardFrontImg;

	private String idcardReverseImg;

	private Byte authStatus;

	private boolean isCapital;

	private Long guaranteeCapital;

	private Long recruiterId;

	private Integer company;

	private Long straightPoint;

	private Byte deleted;

	private Date gmtCreated;

	private Date gmtModified;

	private String remark;

	private String contactAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName == null ? null : showName.trim();
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName == null ? null : supplierName.trim();
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip == null ? null : zip.trim();
	}

	public Byte getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Byte divisionId) {
		this.divisionId = divisionId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName == null ? null : contactName.trim();
	}

	public String getContactPosition() {
		return contactPosition;
	}

	public void setContactPosition(String contactPosition) {
		this.contactPosition = contactPosition == null ? null : contactPosition
				.trim();
	}

	public String getContactIdcard() {
		return contactIdcard;
	}

	public void setContactIdcard(String contactIdcard) {
		this.contactIdcard = contactIdcard == null ? null : contactIdcard
				.trim();
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile == null ? null : contactMobile
				.trim();
	}

	public String getContactQq() {
		return contactQq;
	}

	public void setContactQq(String contactQq) {
		this.contactQq = contactQq == null ? null : contactQq.trim();
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail == null ? null : contactEmail.trim();
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(Byte buyLimit) {
		this.buyLimit = buyLimit;
	}

	public Byte getStorageDeliveryId() {
		return storageDeliveryId;
	}

	public void setStorageDeliveryId(Byte storageDeliveryId) {
		this.storageDeliveryId = storageDeliveryId;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo == null ? null : licenseNo.trim();
	}

	public Integer getLicenseBegin() {
		return licenseBegin;
	}

	public void setLicenseBegin(Integer licenseBegin) {
		this.licenseBegin = licenseBegin;
	}

	public Integer getLicenseEnd() {
		return licenseEnd;
	}

	public void setLicenseEnd(Integer licenseEnd) {
		this.licenseEnd = licenseEnd;
	}

	public String getLicenseAddr() {
		return licenseAddr;
	}

	public void setLicenseAddr(String licenseAddr) {
		this.licenseAddr = licenseAddr == null ? null : licenseAddr.trim();
	}

	public Integer getRegCapital() {
		return regCapital;
	}

	public void setRegCapital(Integer regCapital) {
		this.regCapital = regCapital;
	}

	public String getBusScope() {
		return busScope;
	}

	public void setBusScope(String busScope) {
		this.busScope = busScope == null ? null : busScope.trim();
	}

	public String getLegalPersonName() {
		return legalPersonName;
	}

	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName == null ? null : legalPersonName
				.trim();
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax == null ? null : fax.trim();
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website == null ? null : website.trim();
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands == null ? null : brands.trim();
	}

	public String getTmallWebsite() {
		return tmallWebsite;
	}

	public void setTmallWebsite(String tmallWebsite) {
		this.tmallWebsite = tmallWebsite == null ? null : tmallWebsite.trim();
	}

	public String getLicenseScanImg() {
		return licenseScanImg;
	}

	public void setLicenseScanImg(String licenseScanImg) {
		this.licenseScanImg = licenseScanImg == null ? null : licenseScanImg
				.trim();
	}

	public String getOrgScanImg() {
		return orgScanImg;
	}

	public void setOrgScanImg(String orgScanImg) {
		this.orgScanImg = orgScanImg == null ? null : orgScanImg.trim();
	}

	public String getTaxScanImg() {
		return taxScanImg;
	}

	public void setTaxScanImg(String taxScanImg) {
		this.taxScanImg = taxScanImg == null ? null : taxScanImg.trim();
	}

	public String getIdcardFrontImg() {
		return idcardFrontImg;
	}

	public void setIdcardFrontImg(String idcardFrontImg) {
		this.idcardFrontImg = idcardFrontImg == null ? null : idcardFrontImg
				.trim();
	}

	public String getIdcardReverseImg() {
		return idcardReverseImg;
	}

	public void setIdcardReverseImg(String idcardReverseImg) {
		this.idcardReverseImg = idcardReverseImg == null ? null
				: idcardReverseImg.trim();
	}

	public Byte getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Byte authStatus) {
		this.authStatus = authStatus;
	}

	public boolean getIsCapital() {
		return isCapital;
	}

	public void setIsCapital(boolean isCapital) {
		this.isCapital = isCapital;
	}

	public Long getGuaranteeCapital() {
		return guaranteeCapital;
	}

	public void setGuaranteeCapital(Long guaranteeCapital) {
		this.guaranteeCapital = guaranteeCapital;
	}

	public Long getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public Long getStraightPoint() {
		return straightPoint;
	}

	public void setStraightPoint(Long straightPoint) {
		this.straightPoint = straightPoint;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}

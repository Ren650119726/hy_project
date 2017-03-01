//package com.mockuai.usercenter.common.dto;
//
//import java.io.Serializable;
//import java.util.Date;
//
//public class UserExtraInfoDTO extends BaseDTO implements Serializable {
//	private Long id;
//
//	private Long userId;
//
//	private Integer country;
//
//	private Integer province;
//
//	private Integer city;
//
//	private Integer area;
//
//	private String address;
//
//	private Byte sex;
//
//	private Integer birthday;
//
//	private Integer height;
//
//	private Integer weight;
//
//	private String profession;
//
//	private String alipayId;
//
//	private String taobaoId;
//
//	private String weiboId;
//
//	private String weiboToken;
//
//	private Byte syncWeiboMark;
//
//	private String qqId;
//
//	private Byte syncQqMark;
//
//	private Integer authType;
//
//	private String openId;
//
//	private Integer sourceType;
//
//	private String utmMedium;
//
//	private Byte deleted;
//
//	private Date gmtCreated;
//
//	private Date gmtModified;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//	public Integer getCountry() {
//		return country;
//	}
//
//	public void setCountry(Integer country) {
//		this.country = country;
//	}
//
//	public Integer getProvince() {
//		return province;
//	}
//
//	public void setProvince(Integer province) {
//		this.province = province;
//	}
//
//	public Integer getCity() {
//		return city;
//	}
//
//	public void setCity(Integer city) {
//		this.city = city;
//	}
//
//	public Integer getArea() {
//		return area;
//	}
//
//	public void setArea(Integer area) {
//		this.area = area;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address == null ? null : address.trim();
//	}
//
//	public Byte getSex() {
//		return sex;
//	}
//
//	public void setSex(Byte sex) {
//		this.sex = sex;
//	}
//
//	public Integer getBirthday() {
//		return birthday;
//	}
//
//	public void setBirthday(Integer birthday) {
//		this.birthday = birthday;
//	}
//
//	public Integer getHeight() {
//		return height;
//	}
//
//	public void setHeight(Integer height) {
//		this.height = height;
//	}
//
//	public Integer getWeight() {
//		return weight;
//	}
//
//	public void setWeight(Integer weight) {
//		this.weight = weight;
//	}
//
//	public String getProfession() {
//		return profession;
//	}
//
//	public void setProfession(String profession) {
//		this.profession = profession == null ? null : profession.trim();
//	}
//
//	public String getAlipayId() {
//		return alipayId;
//	}
//
//	public void setAlipayId(String alipayId) {
//		this.alipayId = alipayId == null ? null : alipayId.trim();
//	}
//
//	public String getTaobaoId() {
//		return taobaoId;
//	}
//
//	public void setTaobaoId(String taobaoId) {
//		this.taobaoId = taobaoId == null ? null : taobaoId.trim();
//	}
//
//	public String getWeiboId() {
//		return weiboId;
//	}
//
//	public void setWeiboId(String weiboId) {
//		this.weiboId = weiboId == null ? null : weiboId.trim();
//	}
//
//	public String getWeiboToken() {
//		return weiboToken;
//	}
//
//	public void setWeiboToken(String weiboToken) {
//		this.weiboToken = weiboToken == null ? null : weiboToken.trim();
//	}
//
//	public Byte getSyncWeiboMark() {
//		return syncWeiboMark;
//	}
//
//	public void setSyncWeiboMark(Byte syncWeiboMark) {
//		this.syncWeiboMark = syncWeiboMark;
//	}
//
//	public String getQqId() {
//		return qqId;
//	}
//
//	public void setQqId(String qqId) {
//		this.qqId = qqId == null ? null : qqId.trim();
//	}
//
//	public Byte getSyncQqMark() {
//		return syncQqMark;
//	}
//
//	public void setSyncQqMark(Byte syncQqMark) {
//		this.syncQqMark = syncQqMark;
//	}
//
//	public Integer getAuthType() {
//		return authType;
//	}
//
//	public void setAuthType(Integer authType) {
//		this.authType = authType;
//	}
//
//	public String getOpenId() {
//		return openId;
//	}
//
//	public void setOpenId(String openId) {
//		this.openId = openId == null ? null : openId.trim();
//	}
//
//	public Integer getSourceType() {
//		return sourceType;
//	}
//
//	public void setSourceType(Integer sourceType) {
//		this.sourceType = sourceType;
//	}
//
//	public String getUtmMedium() {
//		return utmMedium;
//	}
//
//	public void setUtmMedium(String utmMedium) {
//		this.utmMedium = utmMedium == null ? null : utmMedium.trim();
//	}
//
//	public Date getGmtCreated() {
//		return gmtCreated;
//	}
//
//	public void setGmtCreated(Date gmtCreated) {
//		this.gmtCreated = gmtCreated;
//	}
//
//	public Date getGmtModified() {
//		return gmtModified;
//	}
//
//	public void setGmtModified(Date gmtModified) {
//		this.gmtModified = gmtModified;
//	}
//
//	public Byte getDeleted() {
//		return deleted;
//	}
//
//	public void setDeleted(Byte deleted) {
//		this.deleted = deleted;
//	}
//
//}

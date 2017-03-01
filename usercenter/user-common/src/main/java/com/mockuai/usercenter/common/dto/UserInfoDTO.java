package com.mockuai.usercenter.common.dto;

import java.io.Serializable;

public class UserInfoDTO extends BaseDTO implements Serializable {
	private Long userId;

	private String name;

	private String password;

	private String imgUrl;

	private Long integral;

	private Long experience;

	private String phoneNo;

	private String mPhoneNo;

	private String email;

	private Byte status;

	private Long authMark;

	private Long featureMark;

	private Byte role;

	private Long recommenderId;

	private Long inviterId;

	private Byte deleted;

	private Integer province;

	private Integer city;

	private Integer area;

	private String address;

	private Byte sex;

	private Integer birthday;

	private Integer height;

	private Integer weight;

	private String profession;

	private String alipayId;

	private String taobaoId;

	private String weiboId;

	private String weiboToken;

	private Byte syncWeiboMark;

	private String qqId;

	private Byte syncQqMark;

	private Integer authType;

	private String openId;

	private String sourceType;

	private String utmMedium;

	private Byte extraDeleted;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Long getExperience() {
		return experience;
	}

	public void setExperience(Long experience) {
		this.experience = experience;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getmPhoneNo() {
		return mPhoneNo;
	}

	public void setmPhoneNo(String mPhoneNo) {
		this.mPhoneNo = mPhoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getAuthMark() {
		return authMark;
	}

	public void setAuthMark(Long authMark) {
		this.authMark = authMark;
	}

	public Long getFeatureMark() {
		return featureMark;
	}

	public void setFeatureMark(Long featureMark) {
		this.featureMark = featureMark;
	}

	public Byte getRole() {
		return role;
	}

	public void setRole(Byte role) {
		this.role = role;
	}

	public Long getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(Long recommenderId) {
		this.recommenderId = recommenderId;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Integer getBirthday() {
		return birthday;
	}

	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getWeiboToken() {
		return weiboToken;
	}

	public void setWeiboToken(String weiboToken) {
		this.weiboToken = weiboToken;
	}

	public Byte getSyncWeiboMark() {
		return syncWeiboMark;
	}

	public void setSyncWeiboMark(Byte syncWeiboMark) {
		this.syncWeiboMark = syncWeiboMark;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public Byte getSyncQqMark() {
		return syncQqMark;
	}

	public void setSyncQqMark(Byte syncQqMark) {
		this.syncQqMark = syncQqMark;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getUtmMedium() {
		return utmMedium;
	}

	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}

	public Byte getExtraDeleted() {
		return extraDeleted;
	}

	public void setExtraDeleted(Byte extraDeleted) {
		this.extraDeleted = extraDeleted;
	}

}

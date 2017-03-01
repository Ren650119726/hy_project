package com.mockuai.usercenter.common.dto;

import java.io.Serializable;
import java.util.Date;

public class UserAuthInfoDTO extends BaseDTO implements Serializable {
	private Long id;
	private String bizCode;
	private Long userId;
	private String mobile;
	/**
	 * 认证类型，1代表买家实名认证，2代表个人卖家实名认证，3代表企业实名认证.
	 * 参考 {@link com.mockuai.usercenter.common.constant.UserAuthType}
	 */
	private Integer type;
	/**
	 * 用户真实姓名
	 */
	private String realName;
	/**
	 * 身份证号。如果认证类型为企业认证，则这里的身份证号为企业法人的身份证号
	 */
	private String idcardNo;
	/**
	 * 身份证正面照
	 */
	private String idcardFrontImg;
	/**
	 * 身份证反面照
	 */
	private String idcardReverseImg;
	/**
	 * 手持身份证半身照
	 */
	private String idcardHoldImg;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 联系人地址
	 */
	private String contactAddress;
	/**
	 * 联系人在公司中的职位
	 */
	private String contactPosition;
	/**
	 * 联系人手机号
	 */
	private String contactMobile;
	private String remark;

	/**
	 * 店铺名
	 * */
	private String shopName;
	/**
	 * 认证状态
	 */
	private Integer status;

	/**
	 * 创建时间
	 * */
	private Date gmtCreated;

	/**
	 * 修改时间
	 * */
	private Date gmtModified;

	/**
	 * 企业认证扩展信息
	 */
	private EnterpriseAuthExtendDTO enterpriseAuthExtendDTO;

	/**
	 * 保证金标志,0:未交,1:已交
	 */
	private Integer guaranteeMark;

	/**
	 * 保证金金额
	 */
	private Long guaranteeAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getIdcardFrontImg() {
		return idcardFrontImg;
	}

	public void setIdcardFrontImg(String idcardFrontImg) {
		this.idcardFrontImg = idcardFrontImg;
	}

	public String getIdcardReverseImg() {
		return idcardReverseImg;
	}

	public void setIdcardReverseImg(String idcardReverseImg) {
		this.idcardReverseImg = idcardReverseImg;
	}

	public String getIdcardHoldImg() {
		return idcardHoldImg;
	}

	public void setIdcardHoldImg(String idcardHoldImg) {
		this.idcardHoldImg = idcardHoldImg;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPosition() {
		return contactPosition;
	}

	public void setContactPosition(String contactPosition) {
		this.contactPosition = contactPosition;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public EnterpriseAuthExtendDTO getEnterpriseAuthExtendDTO() {
		return enterpriseAuthExtendDTO;
	}

	public void setEnterpriseAuthExtendDTO(EnterpriseAuthExtendDTO enterpriseAuthExtendDTO) {
		this.enterpriseAuthExtendDTO = enterpriseAuthExtendDTO;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getGuaranteeMark() {
		return guaranteeMark;
	}

	public void setGuaranteeMark(Integer guaranteeMark) {
		this.guaranteeMark = guaranteeMark;
	}

	public Long getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public void setGuaranteeAmount(Long guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
}

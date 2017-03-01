package com.mockuai.tradecenter.core.domain;

public class PaymentDO {
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(Boolean onlinePay) {
		this.onlinePay = onlinePay;
	}

	public Long getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	public Boolean getIsPc() {
		return isPc;
	}

	public void setIsPc(Boolean isPc) {
		this.isPc = isPc;
	}

	public Boolean getIsWap() {
		return isWap;
	}

	public void setIsWap(Boolean isWap) {
		this.isWap = isWap;
	}

	public Boolean getIsApp() {
		return isApp;
	}

	public void setIsApp(Boolean isApp) {
		this.isApp = isApp;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	private Integer id; // 主键

    private String className;  

    private Boolean isActive;//是否开启货到付款

    private Boolean onlinePay;

    private Long feeAmount;

    private String name;

    private String desc;

    private Long totalAmount;

    private String logo;

    private Integer sort;

    private Integer  feeType;

    private Boolean isPc;

    private Boolean isWap;

    private Boolean isApp;

    private String key;

    private String config;

    
}
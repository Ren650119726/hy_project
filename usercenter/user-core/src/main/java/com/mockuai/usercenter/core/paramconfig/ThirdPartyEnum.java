package com.mockuai.usercenter.core.paramconfig;

public enum ThirdPartyEnum {

	QQ(1, "qq"), SINA_WEIBO(2, "新浪微博"), ALIPAY(3, "支付宝"), WEIXIN(4, "微信"), KAIXIN(
			5, "开心"), RENREN(6, "人人"), WANGYI(7, "网易");

	private Integer no;
	private String name;

	private ThirdPartyEnum(Integer no, String name) {
		this.no = no;
		this.name = name;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

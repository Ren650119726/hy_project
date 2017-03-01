package com.mockuai.deliverycenter.core.config;
/**
 * 快递100配置
 * @author hzmk
 *
 */
public class Kuaidi100Config {

	
	private String key;
	
	private String show;
	
	/**
	 * desc：按时间由新到旧排列，
		asc：按时间由旧到新排列。
		不填默认返回倒序（大小写不敏感
	 */
	private String order;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
}

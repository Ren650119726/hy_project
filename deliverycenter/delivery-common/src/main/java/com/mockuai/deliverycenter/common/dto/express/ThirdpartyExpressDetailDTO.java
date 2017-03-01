package com.mockuai.deliverycenter.common.dto.express;
/**
 * 第三方物流信息
 * @author hzmk
 *
 */
public class ThirdpartyExpressDetailDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3973976011147757734L;
	
	private String time;
	
	private String context;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	

}

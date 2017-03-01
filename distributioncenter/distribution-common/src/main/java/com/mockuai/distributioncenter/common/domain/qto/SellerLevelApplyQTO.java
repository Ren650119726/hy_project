package com.mockuai.distributioncenter.common.domain.qto;

import java.io.Serializable;


/**
 * Created by wgl on 16/7/20.
 */
public class SellerLevelApplyQTO extends PageQTO  implements Serializable{
    private String  realName;
    private Integer status;
    private Integer type;
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

    
}

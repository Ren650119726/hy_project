package com.mockuai.giftscenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class GiftsPacketQTO extends PageQTO implements Serializable {

    private Long id;
    private String giftsName;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGiftsName() {
		return giftsName;
	}
	public void setGiftsName(String giftsName) {
		this.giftsName = giftsName;
	}
   
}
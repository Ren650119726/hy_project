package com.mockuai.seckillcenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 7/20/2016.
 */
public class OrderHistoryQTO extends PageQTO implements Serializable {
	private Long seckillId;

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}
}
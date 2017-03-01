package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.core.domain.RefundItemImageDO;

public interface RefundItemImageDAO {

	
	public Long addRefundItemImage(RefundItemImageDO refundItemImageDO);
	
	public List<RefundItemImageDO> queryRefundItemImage(Long refundItemId);
	
	
}

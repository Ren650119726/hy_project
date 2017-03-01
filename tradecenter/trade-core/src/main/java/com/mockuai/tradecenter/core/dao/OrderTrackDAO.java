package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderTrackQTO;
import com.mockuai.tradecenter.core.domain.OrderTrackDO;

public interface OrderTrackDAO {

	public Long addOrderTrack(OrderTrackDO record);
	
	public List<OrderTrackDO> querOrderTrack(OrderTrackQTO query);
	
}

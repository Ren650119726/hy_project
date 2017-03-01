package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderTrackQTO;
import com.mockuai.tradecenter.core.domain.OrderTrackDO;

public interface OrderTrackManager {
	
	public Long addOrderTrack(OrderTrackDO record);
	
	public List<OrderTrackDO> queryOrderTrack(OrderTrackQTO query);

}

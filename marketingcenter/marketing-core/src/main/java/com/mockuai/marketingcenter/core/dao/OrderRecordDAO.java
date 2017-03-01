package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;
import com.mockuai.marketingcenter.core.domain.OrderRecordDO;

import java.util.List;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public interface OrderRecordDAO {

	Long addOrderRecord(OrderRecordDO orderRecordDO);

	List<OrderRecordDO> queryOrderRecord(OrderRecordQTO orderRecordQTO);
}

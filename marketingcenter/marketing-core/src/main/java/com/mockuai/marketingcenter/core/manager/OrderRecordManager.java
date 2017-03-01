package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;
import com.mockuai.marketingcenter.core.domain.OrderRecordDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.List;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public interface OrderRecordManager {

	Long addOrderRecord(OrderRecordDO orderRecordDO) throws MarketingException;

	List<OrderRecordDO> queryOrderRecord(OrderRecordQTO orderRecordQTO) throws MarketingException;
}
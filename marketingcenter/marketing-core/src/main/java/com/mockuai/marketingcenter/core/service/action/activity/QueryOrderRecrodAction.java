package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.OrderRecordDTO;
import com.mockuai.marketingcenter.common.domain.qto.OrderRecordQTO;
import com.mockuai.marketingcenter.core.domain.OrderRecordDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.OrderRecordManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by edgar.zr on 7/25/2016.
 */
@Service
public class QueryOrderRecrodAction implements Action {

	public static final Logger LOGGER = LoggerFactory.getLogger(QueryOrderRecrodAction.class);

	@Autowired
	private OrderRecordManager orderRecordManager;

	@Override
	public MarketingResponse execute(RequestContext context) throws MarketingException {
		OrderRecordQTO orderRecordQTO = (OrderRecordQTO) context.getRequest().getParam("orderRecordQTO");

		List<OrderRecordDO> orderRecordDOs = orderRecordManager.queryOrderRecord(orderRecordQTO);
		List<OrderRecordDTO> orderRecordDTOs = ModelUtil.genOrderRecordDTOList(orderRecordDOs);

		return MarketingUtils.getSuccessResponse(orderRecordDTOs, orderRecordQTO.getTotalCount());
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ORDER_RECORD.getActionName();
	}
}
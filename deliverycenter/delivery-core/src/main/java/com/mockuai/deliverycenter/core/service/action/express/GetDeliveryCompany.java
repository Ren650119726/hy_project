package com.mockuai.deliverycenter.core.service.action.express;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.LogisticsCompanyDTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.Action;
import com.mockuai.deliverycenter.core.util.LogisticsCompanyUtil;
import com.mockuai.deliverycenter.core.util.ResponseUtil;
/**
 * 查询物流公司
 * @author hzmk
 *
 */
@Service
public class GetDeliveryCompany implements Action {
	

	/**
	 * 查询快递接口
	 */
	@Override
	public DeliveryResponse execute(RequestContext context)
			throws DeliveryException {
		
		List<LogisticsCompanyDTO> result = LogisticsCompanyUtil.getLogisticsCompany();
		return ResponseUtil.getResponse(result);
		
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_DELIVERY_COMPANY.getActionName();
	}
}

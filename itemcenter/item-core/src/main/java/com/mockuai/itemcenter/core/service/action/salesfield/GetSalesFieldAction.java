package com.mockuai.itemcenter.core.service.action.salesfield;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SalesFieldDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SalesFieldManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看全局属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class GetSalesFieldAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetSalesFieldAction.class);

	@Resource
	private SalesFieldManager salesFieldManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		SalesFieldDTO salesFieldDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getInteger("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "salesField ID is missing");
		}
		Integer salesFieldId = request.getInteger("ID");// SKU属性模板ID
		try {
			salesFieldDTO = salesFieldManager.getSalesField(salesFieldId);

		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(salesFieldDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SALES_FIELD.getActionName();
	}
}

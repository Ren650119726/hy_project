package com.mockuai.itemcenter.core.service.action.globalpropertyvalue;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.GlobalPropertyValueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyValueQTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryGlobalPropertyValueAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryGlobalPropertyValueAction.class);
	@Resource
	private GlobalPropertyValueManager globalPropertyValueManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("globalPropertyValueQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "globalPropertyValueQTO is null");
		}
		GlobalPropertyValueQTO globalPropertyValueQTO = (GlobalPropertyValueQTO) request
				.getParam("globalPropertyValueQTO");

		try {
			List<GlobalPropertyValueDTO> GlobalPropertyValueDTOList = globalPropertyValueManager
					.queryGlobalPropertyValue(globalPropertyValueQTO);
			response = ResponseUtil.getSuccessResponse(GlobalPropertyValueDTOList, globalPropertyValueQTO
					.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_GLOBAL_PROPERTY_VALUE.getActionName();
	}
}

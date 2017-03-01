package com.mockuai.itemcenter.core.service.action.globalproperty;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.GlobalPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询全局属性列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryGlobalPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryGlobalPropertyAction.class);
	@Resource
	private GlobalPropertyManager globalPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("globalPropertyQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "globalPropertyQTO is null");
		}
		GlobalPropertyQTO globalPropertyQTO = (GlobalPropertyQTO) request.getParam("globalPropertyQTO");

		try {
			List<GlobalPropertyDTO> GlobalPropertyDTOList = globalPropertyManager
					.queryGlobalProperty(globalPropertyQTO);
			response = ResponseUtil.getSuccessResponse(GlobalPropertyDTOList, globalPropertyQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_GLOBAL_PROPERTY.getActionName();
	}
}

package com.mockuai.itemcenter.core.service.action.globalpropertyvalue;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.GlobalPropertyValueManager;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyValueDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddGlobalPropertyValueAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddGlobalPropertyValueAction.class);
	@Resource
	private GlobalPropertyValueManager globalPropertyValueManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("globalPropertyValueDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "globalPropertyValueDTO is null");
		}
		GlobalPropertyValueDTO globalPropertyValueDTO = (GlobalPropertyValueDTO) request
				.getParam("globalPropertyValueDTO");
		try {
			globalPropertyValueDTO = globalPropertyValueManager.addGlobalPropertyValue(globalPropertyValueDTO);// 新增加的globalPropertyValueDO
			response = ResponseUtil.getSuccessResponse(globalPropertyValueDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_GLOBAL_PROPERTY_VALUE.getActionName();
	}
}

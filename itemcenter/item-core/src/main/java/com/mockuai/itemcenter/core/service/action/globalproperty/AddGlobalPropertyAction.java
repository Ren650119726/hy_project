package com.mockuai.itemcenter.core.service.action.globalproperty;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.GlobalPropertyManager;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加全局属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddGlobalPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddGlobalPropertyAction.class);
	@Resource
	private GlobalPropertyManager globalPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("globalPropertyDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "globalPropertyDTO is null");
		}
		GlobalPropertyDTO globalPropertyDTO = (GlobalPropertyDTO) request.getParam("globalPropertyDTO");
		try {
			globalPropertyDTO = globalPropertyManager.addGlobalProperty(globalPropertyDTO);// 新增加的globalPropertyDO
			response = ResponseUtil.getSuccessResponse(globalPropertyDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_GLOBAL_PROPERTY.getActionName();
	}
}

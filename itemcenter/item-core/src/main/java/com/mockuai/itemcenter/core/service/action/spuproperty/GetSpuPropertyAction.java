package com.mockuai.itemcenter.core.service.action.spuproperty;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpuPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看商品属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class GetSpuPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetSpuPropertyAction.class);

	@Resource
	private SpuPropertyManager spuPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		SpuPropertyDTO spuPropertyDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "spuPropertyID is missing");
		}
		Long spuPropertyId = request.getLong("ID");// SKU属性模板ID
		try {
			spuPropertyDTO = spuPropertyManager.getSpuProperty(spuPropertyId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(spuPropertyDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SPU_PROPERTY.getActionName();
	}
}

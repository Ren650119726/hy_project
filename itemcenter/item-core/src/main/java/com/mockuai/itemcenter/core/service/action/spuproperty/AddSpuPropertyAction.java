package com.mockuai.itemcenter.core.service.action.spuproperty;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpuPropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddSpuPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddSpuPropertyAction.class);
	@Resource
	private SpuPropertyManager spuPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("spuPropertyDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "spuPropertyDTO is null");
		}
		SpuPropertyDTO spuPropertyDTO = (SpuPropertyDTO) request.getParam("spuPropertyDTO");
		try {
			spuPropertyDTO = spuPropertyManager.addSpuProperty(spuPropertyDTO);// 新增加的spuPropertyDO
			response = ResponseUtil.getSuccessResponse(spuPropertyDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_SPU_PROPERTY.getActionName();
	}
}

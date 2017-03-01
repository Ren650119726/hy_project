package com.mockuai.itemcenter.core.service.action.spupropertytmpl;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpuPropertyTmplManager;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyTmplDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 更新商品属性模板Action
 * 
 * @author chen.huang
 *
 */

@Service
public class UpdateSpuPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateSpuPropertyTmplAction.class);
	@Resource
	private SpuPropertyTmplManager spuPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("spuPropertyTmplDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "spuPropertyTmplDTO is null");
		}
		SpuPropertyTmplDTO spuPropertyTmplDTO = (SpuPropertyTmplDTO) request.getParam("spuPropertyTmplDTO");
		try {
			Boolean isSuccessfullyUpdated = spuPropertyTmplManager.updateSpuPropertyTmpl(spuPropertyTmplDTO);
			response = ResponseUtil.getSuccessResponse(isSuccessfullyUpdated);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_SPU_PROPERTY_TMPL.getActionName();
	}
}

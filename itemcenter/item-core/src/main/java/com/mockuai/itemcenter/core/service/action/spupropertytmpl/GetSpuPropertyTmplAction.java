package com.mockuai.itemcenter.core.service.action.spupropertytmpl;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyTmplDTO;
import com.mockuai.itemcenter.core.manager.SpuPropertyTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class GetSpuPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetSpuPropertyTmplAction.class);

	@Resource
	private SpuPropertyTmplManager spuPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		SpuPropertyTmplDTO spuPropertyTmplDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "spuPropertyTmplID is missing");
		}
		Long spuPropertyTmplId = request.getLong("ID");// SKU属性模板ID
		try {
			spuPropertyTmplDTO = spuPropertyTmplManager.getSpuPropertyTmpl(spuPropertyTmplId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(spuPropertyTmplDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SPU_PROPERTY_TMPL.getActionName();
	}
}

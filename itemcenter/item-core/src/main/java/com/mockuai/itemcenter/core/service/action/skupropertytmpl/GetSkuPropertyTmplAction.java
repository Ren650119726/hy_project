package com.mockuai.itemcenter.core.service.action.skupropertytmpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SkuPropertyTmplManager;
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
public class GetSkuPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetSkuPropertyTmplAction.class);

	@Resource
	private SkuPropertyTmplManager skuPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		SkuPropertyTmplDTO skuPropertyTmplDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyTmplID is missing");
		}
		Long skuPropertyTmplId = request.getLong("ID");// SKU属性模板ID
		try {
			skuPropertyTmplDTO = skuPropertyTmplManager.getSkuPropertyTmpl(skuPropertyTmplId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(skuPropertyTmplDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SKU_PROPERTY_TMPL.getActionName();
	}
}

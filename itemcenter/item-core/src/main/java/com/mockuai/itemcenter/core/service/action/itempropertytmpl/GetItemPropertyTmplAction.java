package com.mockuai.itemcenter.core.service.action.itempropertytmpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemPropertyTmplManager;
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
public class GetItemPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemPropertyTmplAction.class);

	@Resource
	private ItemPropertyTmplManager itemPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemPropertyTmplDTO itemPropertyTmplDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemPropertyTmplID is missing");
		}
		Long itemPropertyTmplId = request.getLong("ID");// SKU属性模板ID
		try {
			itemPropertyTmplDTO = itemPropertyTmplManager.getItemPropertyTmpl(itemPropertyTmplId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(itemPropertyTmplDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEM_PROPERTY_TMPL.getActionName();
	}
}

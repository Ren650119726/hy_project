package com.mockuai.itemcenter.core.service.action.itempropertytmpl;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.manager.ItemPropertyTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 删除商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteItemPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemPropertyTmplAction.class);
	@Resource
	private ItemPropertyTmplManager itemPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("itemPropertyTmplId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemPropertyTmplID is missing");
		}
		Long itemPropertyTmplId = request.getLong("itemPropertyTmplId");// 商品品牌ID
		try {
			//TODO 确认属性模板如果被使用的情况下是否可以直接删除

			boolean result = itemPropertyTmplManager.deleteItemPropertyTmpl(itemPropertyTmplId);
			response = ResponseUtil.getSuccessResponse(result);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM_PROPERTY_TMPL.getActionName();
	}
}

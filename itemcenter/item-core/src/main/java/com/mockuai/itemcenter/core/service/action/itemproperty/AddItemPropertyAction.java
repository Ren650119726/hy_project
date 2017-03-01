package com.mockuai.itemcenter.core.service.action.itemproperty;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemPropertyAction.class);
	@Resource
	private ItemPropertyManager itemPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("itemPropertyDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemPropertyDTO is null");
		}
		ItemPropertyDTO itemPropertyDTO = (ItemPropertyDTO) request.getParam("itemPropertyDTO");
		try {
			itemPropertyDTO.setBizCode(bizCode);//填充bizCode
			itemPropertyDTO = itemPropertyManager.addItemProperty(itemPropertyDTO);// 新增加的itemPropertyDO
			response = ResponseUtil.getSuccessResponse(itemPropertyDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEM_PROPERTY.getActionName();
	}
}

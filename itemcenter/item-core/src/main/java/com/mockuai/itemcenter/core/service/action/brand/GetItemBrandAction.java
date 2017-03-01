package com.mockuai.itemcenter.core.service.action.brand;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemBrandManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看商品品牌Action
 * 
 * @author chen.huang
 *
 */

@Service
public class GetItemBrandAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemBrandAction.class);
	@Resource
	private ItemBrandManager itemBrandManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemBrandDTO itemBrandDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemBrandID is missing");
		}
		Long itemBrandId = request.getLong("ID");// 商品品牌ID

		try {
			itemBrandDTO = itemBrandManager.getItemBrand(itemBrandId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(itemBrandDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEMBRAND.getActionName();
	}
}

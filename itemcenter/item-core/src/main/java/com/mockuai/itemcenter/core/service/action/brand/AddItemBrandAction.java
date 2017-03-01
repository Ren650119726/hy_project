package com.mockuai.itemcenter.core.service.action.brand;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.core.service.action.TransAction;
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
 * 增加商品品牌Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemBrandAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(AddItemBrandAction.class);
	@Resource
	private ItemBrandManager itemBrandManager;


	@Override
	protected ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("itemBrandDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemBrandDTO is null");
		}
		ItemBrandDTO itemBrandDTO = (ItemBrandDTO) request.getParam("itemBrandDTO");

		try {
			itemBrandDTO = itemBrandManager.addItemBrand(itemBrandDTO);// 新增加的itemBrandDO
			response = ResponseUtil.getSuccessResponse(itemBrandDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEMBRAND.getActionName();
	}
}

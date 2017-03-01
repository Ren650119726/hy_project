package com.mockuai.itemcenter.core.service.action.itemextrainfo;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemExtraInfoDTO;
import com.mockuai.itemcenter.core.manager.ItemExtraInfoManager;
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
public class GetItemExtraInfoAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemExtraInfoAction.class);
	@Resource
	private ItemExtraInfoManager itemExtraInfoManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemExtraInfoDTO itemExtraInfoDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemExtraInfoID is missing");
		}
		// 验证sellerId
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long itemExtraInfoId = request.getLong("ID");// 商品品牌ID
		Long sellerId = request.getLong("sellerId");// 供应商ID

		try {
			itemExtraInfoDTO = itemExtraInfoManager.getItemExtraInfo(itemExtraInfoId, sellerId);
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(itemExtraInfoDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEM_EXTRA_INFO.getActionName();
	}
}

package com.mockuai.itemcenter.core.service.action.itemextrainfo;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.action.Action;
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
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品品牌Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemExtraInfoAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemExtraInfoAction.class);
	@Resource
	private ItemExtraInfoManager itemExtraInfoManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("itemExtraInfoDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemExtraInfoDTO is null");
		}
		ItemExtraInfoDTO itemExtraInfoDTO = (ItemExtraInfoDTO) request.getParam("itemExtraInfoDTO");
		try {
			itemExtraInfoDTO = itemExtraInfoManager.addItemExtraInfo(itemExtraInfoDTO);// 新增加的itemExtraInfoDO
			response = ResponseUtil.getSuccessResponse(itemExtraInfoDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEM_EXTRA_INFO.getActionName();
	}
}

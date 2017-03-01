package com.mockuai.itemcenter.core.service.action.itemextrainfo;

import java.util.List;

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
import com.mockuai.itemcenter.common.domain.qto.ItemExtraInfoQTO;
import com.mockuai.itemcenter.core.manager.ItemExtraInfoManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询商品品牌列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemExtraInfoAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemExtraInfoAction.class);
	@Resource
	private ItemExtraInfoManager itemExtraInfoManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("itemExtraInfoQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemExtraInfoQTO is null");
		}
		ItemExtraInfoQTO itemExtraInfoQTO = (ItemExtraInfoQTO) request.getParam("itemExtraInfoQTO");
		try {
			List<ItemExtraInfoDTO> itemExtraInfoDTOList = itemExtraInfoManager
					.queryItemExtraInfo(itemExtraInfoQTO);
			response = ResponseUtil.getSuccessResponse(itemExtraInfoDTOList, itemExtraInfoQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_EXTRA_INFO.getActionName();
	}
}

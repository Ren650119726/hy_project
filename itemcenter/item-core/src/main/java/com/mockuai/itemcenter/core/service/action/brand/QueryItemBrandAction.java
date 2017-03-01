package com.mockuai.itemcenter.core.service.action.brand;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemBrandManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询商品品牌列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemBrandAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemBrandAction.class);
	@Resource
	private ItemBrandManager itemBrandManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("itemBrandQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemBrandQTO is null");
		}
		ItemBrandQTO itemBrandQTO = (ItemBrandQTO) request.getParam("itemBrandQTO");
		try {
			List<ItemBrandDTO> itemBrandDTOList = itemBrandManager.queryItemBrand(itemBrandQTO);
			response = ResponseUtil.getSuccessResponse(itemBrandDTOList, itemBrandQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEMBRAND.getActionName();
	}
}

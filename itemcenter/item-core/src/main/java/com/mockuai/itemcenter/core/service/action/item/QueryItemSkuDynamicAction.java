package com.mockuai.itemcenter.core.service.action.item;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;

@Service
public class QueryItemSkuDynamicAction implements Action {

	private static final Logger log = LoggerFactory.getLogger(QueryItemSkuDynamicAction.class);
	
	@Resource
	private ItemSkuManager itemSkuManager;
	
	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemRequest request = context.getRequest();

		ItemSkuQTO itemSkuQTO = (ItemSkuQTO) context.getRequest().getParam("itemSkuQTO");
		if(itemSkuQTO==null){
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemSkuQTO is missing");
		}

        String bizCode = (String) context.get("bizCode");
        itemSkuQTO.setBizCode(bizCode);
        log.info(" ########## itemSkuQTO:{} ",JsonUtil.toJson(itemSkuQTO));
		try {
			List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemDynamic(itemSkuQTO);

			return ResponseUtil.getSuccessResponse(itemSkuDTOList);
		} catch (ItemException e) {
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEM_SKU_DYNAMIC.getActionName();
	}

	
	
}

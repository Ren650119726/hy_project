package com.mockuai.itemcenter.core.service.action.spuproperty;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpuPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QuerySpuPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QuerySpuPropertyAction.class);
	@Resource
	private SpuPropertyManager spuPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("spuPropertyQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "spuPropertyQTO is null");
		}
		SpuPropertyQTO spuPropertyQTO = (SpuPropertyQTO) request.getParam("spuPropertyQTO");

		try {
			List<SpuPropertyDTO> SpuPropertyDTOList = spuPropertyManager.querySpuProperty(spuPropertyQTO);
			response = ResponseUtil.getSuccessResponse(SpuPropertyDTOList, spuPropertyQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SPU_PROPERTY.getActionName();
	}
}

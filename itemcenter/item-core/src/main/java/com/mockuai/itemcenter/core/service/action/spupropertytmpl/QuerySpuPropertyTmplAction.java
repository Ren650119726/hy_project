package com.mockuai.itemcenter.core.service.action.spupropertytmpl;

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
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyTmplQTO;
import com.mockuai.itemcenter.core.manager.SpuPropertyTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性模板列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QuerySpuPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QuerySpuPropertyTmplAction.class);
	@Resource
	private SpuPropertyTmplManager spuPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("spuPropertyTmplQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "spuPropertyTmplQTO is null");
		}
		SpuPropertyTmplQTO spuPropertyTmplQTO = (SpuPropertyTmplQTO) request.getParam("spuPropertyTmplQTO");

		try {
			List<SpuPropertyTmplDTO> SpuPropertyTmplDTOList = spuPropertyTmplManager
					.querySpuPropertyTmpl(spuPropertyTmplQTO);
			response = ResponseUtil.getSuccessResponse(SpuPropertyTmplDTOList, spuPropertyTmplQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SPU_PROPERTY_TMPL.getActionName();
	}
}

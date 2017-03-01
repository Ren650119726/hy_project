package com.mockuai.itemcenter.core.service.action.skupropertytmpl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.itemcenter.core.manager.SkuPropertyTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性模板列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QuerySkuPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QuerySkuPropertyTmplAction.class);
	@Resource
	private SkuPropertyTmplManager skuPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("skuPropertyTmplQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyTmplQTO is null");
		}
		SkuPropertyTmplQTO skuPropertyTmplQTO = (SkuPropertyTmplQTO) request.getParam("skuPropertyTmplQTO");
		
		// 根据商品的类目找到该类目的对应的基本属性 及其对应的基础值  -- updated by cwr
		if(request.getParam("needPropertyValue") != null && ((Boolean)request.getParam("needPropertyValue"))){
			try {
				skuPropertyTmplQTO.setIsDeleted(0);
				skuPropertyTmplQTO.setNeedPaging(null); // 不需要分页
				List<SkuPropertyTmplDTO> SkuPropertyTmplDTOList = skuPropertyTmplManager
						.querySkuPropertyTmplWithValue(skuPropertyTmplQTO);
				response = ResponseUtil.getSuccessResponse(SkuPropertyTmplDTOList, skuPropertyTmplQTO.getTotalCount());
				return response;
			} catch (ItemException e) {
				response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
				log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
				return response;
			}
			
		}else{
			try {
				List<SkuPropertyTmplDTO> SkuPropertyTmplDTOList = skuPropertyTmplManager
						.querySkuPropertyTmpl(skuPropertyTmplQTO);
				response = ResponseUtil.getSuccessResponse(SkuPropertyTmplDTOList, skuPropertyTmplQTO.getTotalCount());
				return response;
			} catch (ItemException e) {
				response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
				log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
				return response;
			}
		}
		

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SKU_PROPERTY_TMPL.getActionName();
	}
}

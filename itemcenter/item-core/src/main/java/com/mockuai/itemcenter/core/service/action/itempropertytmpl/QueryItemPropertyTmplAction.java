package com.mockuai.itemcenter.core.service.action.itempropertytmpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemPropertyTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ItemUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性模板列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemPropertyTmplAction.class);
	@Resource
	private ItemPropertyTmplManager itemPropertyTmplManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		if (request.getParam("itemPropertyTmplQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemPropertyTmplQTO is null");
		}
		ItemPropertyTmplQTO itemPropertyTmplQTO = (ItemPropertyTmplQTO) request.getParam("itemPropertyTmplQTO");

		// 根据商品的类目找到该类目的对应的基本属性 及其对应的基础值  -- updated by cwr
		if(request.getParam("needPropertyValue") != null && ((Boolean)request.getParam("needPropertyValue"))){
			try {
				log.debug("enter QueryItemPropertyTmplAction " + request.getParam("needPropertyValue"));
				itemPropertyTmplQTO.setNeedPaging(null); //不需要分页
				List<ItemPropertyTmplDTO> ItemPropertyTmplDTOList = itemPropertyTmplManager
						.queryItemPropertyTmplWithValue(itemPropertyTmplQTO);
				response = ResponseUtil.getSuccessResponse(ItemPropertyTmplDTOList, itemPropertyTmplQTO
						.getTotalCount());
				log.debug("enter QueryItemPropertyTmplAction " + response);
				return response;
			} catch (ItemException e) {
				response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
				log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
				return response;
			}
			
		}else{
			try {
				List<ItemPropertyTmplDO> doList = itemPropertyTmplManager
						.queryItemPropertyTmpl(itemPropertyTmplQTO);
				// 在action层来转化位dto
				List<ItemPropertyTmplDTO> dtoList = new ArrayList<ItemPropertyTmplDTO>();// 需要返回的DTO列表
				if(dtoList != null){
					for (ItemPropertyTmplDO itemPropertyTmplDO : doList) {
						ItemPropertyTmplDTO dto = new ItemPropertyTmplDTO();
						ItemUtil.copyProperties(itemPropertyTmplDO, dto);
						dtoList.add(dto);
					}
				}
				response = ResponseUtil.getSuccessResponse(dtoList, itemPropertyTmplQTO
						.getTotalCount());
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
		return ActionEnum.QUERY_ITEM_PROPERTY_TMPL.getActionName();
	}
}

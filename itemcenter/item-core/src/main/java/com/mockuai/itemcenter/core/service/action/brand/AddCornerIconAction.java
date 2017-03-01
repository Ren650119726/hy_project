package com.mockuai.itemcenter.core.service.action.brand;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CornerIconManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

@Service
public class AddCornerIconAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemBrandAction.class);
	
	@Resource
	private CornerIconManager cornerIconManager;
	
	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("cornerIconDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "cornerIconDTO is null");
		}
		CornerIconDTO cornerIconDTO  = (CornerIconDTO) request.getParam("cornerIconDTO");
		Long id ;
		try {
			cornerIconDTO.setBizCode(bizCode);//填充bizCode
			id = this.cornerIconManager.addCornerIcon(cornerIconDTO);
			cornerIconDTO.setId(id);
			response = ResponseUtil.getSuccessResponse(cornerIconDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_CORNER_ICON.getActionName();
	}
}

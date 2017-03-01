package com.mockuai.itemcenter.core.service.action.brand;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CornerIconManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

@Service
public class DeleteCornerIconAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemBrandAction.class);
	
	@Resource
	private CornerIconManager cornerIconManager;
	
	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("id") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "cornerIconId is null");
		}
		if(request.getParam("sellerId")  ==null){
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is null");
		}
		
		Long id  = (Long) request.getParam("id");
		Long sellerId = (Long)request.getParam("sellerId");
		int result = 0;
		try {
			result = this.cornerIconManager.deleteCornerIcon(id, sellerId);
			if(result > 0){
				response = ResponseUtil.getSuccessResponse(true);
			}else{
				response = ResponseUtil.getSuccessResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
			}
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_CORNER_ICON.getActionName();
	}
}
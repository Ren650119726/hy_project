package com.mockuai.itemcenter.core.service.action.spuinfo;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpuInfoManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuInfoDTO;

/**
 * 更新商品属性Action
 * 
 * @author chen.huang
 *
 */

@Service
public class UpdateSpuInfoAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateSpuInfoAction.class);
	@Resource
	private SpuInfoManager spuInfoManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证DTO是否为空
		if (request.getParam("spuInfoDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "spuInfoDTO is null");
		}
		SpuInfoDTO spuInfoDTO = (SpuInfoDTO) request.getParam("spuInfoDTO");
		try {
			Boolean isSuccessfullyUpdated = spuInfoManager.updateSpuInfo(spuInfoDTO);
			response = ResponseUtil.getSuccessResponse(isSuccessfullyUpdated);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_SPU_INFO.getActionName();
	}
}

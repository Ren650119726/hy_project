package com.mockuai.itemcenter.core.service.action.spuinfo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuInfoDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpuInfoManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddSpuInfoAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddSpuInfoAction.class);
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
			spuInfoDTO = spuInfoManager.addSpuInfo(spuInfoDTO);// 新增加的spuInfoDO
			response = ResponseUtil.getSuccessResponse(spuInfoDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_SPU_INFO.getActionName();
	}
}

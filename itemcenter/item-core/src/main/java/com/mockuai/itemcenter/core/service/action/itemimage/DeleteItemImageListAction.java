package com.mockuai.itemcenter.core.service.action.itemimage;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemImageManager;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 删除商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteItemImageListAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemImageListAction.class);
	@Resource
	private ItemImageManager itemImageManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		// 验证ID
		if (request.getParam("deletedImageDTOList") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "deletedImageDTOList is missing");
		}
		// 待删除的图片列表
		List<ItemImageDTO> deletedImageDTOList = (List<ItemImageDTO>) request.getParam("deletedImageDTOList");
		try {
			if (deletedImageDTOList.size() > 0) {
				itemImageManager.deleteItemImageList(deletedImageDTOList);
				response = ResponseUtil.getSuccessResponse(true);
			} else {
				response = ResponseUtil.getSuccessResponse(false);
			}
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEM_IMAGELIST.getActionName();
	}
}

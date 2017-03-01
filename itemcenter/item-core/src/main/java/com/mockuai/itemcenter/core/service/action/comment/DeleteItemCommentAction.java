package com.mockuai.itemcenter.core.service.action.comment;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;

/**
 * 删除商品评论Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteItemCommentAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(DeleteItemCommentAction.class);
	@Resource
	private ItemCommentManager itemCommentManager;

	public ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();

		// 验证ID
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemCommentID is missing");
		}
		// 验证sellerId
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long itemCommentId = request.getLong("ID");// 商品评论ID
		Long sellerId = request.getLong("sellerId");// 供应商ID
		try {
			Boolean isDeletedSuccessfully = itemCommentManager.deleteItemComment(itemCommentId, sellerId);
			response = ResponseUtil.getSuccessResponse(isDeletedSuccessfully);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_ITEMCOMMENT.getActionName();
	}
}

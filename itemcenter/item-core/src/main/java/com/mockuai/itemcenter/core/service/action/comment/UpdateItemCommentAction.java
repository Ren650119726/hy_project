package com.mockuai.itemcenter.core.service.action.comment;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliang on 15/8/3.
 */
@Service
public class UpdateItemCommentAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(UpdateItemCommentAction.class);
    @Resource
    private ItemCommentManager itemCommentManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        // 验证DTO是否为空
        if (request.getParam("itemCommentDTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCommentDTO is null");
        }
        ItemCommentDTO itemCommentDTO = (ItemCommentDTO) request.getParam("itemCommentDTO");
        Boolean result = null;
        try {
            result = itemCommentManager.updateItemComment(itemCommentDTO);
            response = ResponseUtil.getSuccessResponse(result);
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_ITEM_COMMENT.getActionName();
    }
}

package com.mockuai.itemcenter.core.service.action.comment;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wanghailong on 15-8-8.
 */
@Service
public class CountItemCommentGradeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(CountItemCommentGradeAction.class);
    @Resource
    private ItemCommentManager itemCommentManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String bizCode = (String)context.get("bizCode");
        if (request.getParam("itemCommentQTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCommentQTO is null");
        }
        ItemCommentQTO itemCommentQTO = (ItemCommentQTO) request.getParam("itemCommentQTO");
        try {
            itemCommentQTO.setBizCode(bizCode);//填充bizCode
            CountCommentDTO result = itemCommentManager.countItemCommentGrade(itemCommentQTO);
            if(result == null) {
                response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
            } else {
                response = ResponseUtil.getSuccessResponse(result);
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
        return ActionEnum.COUNT_ITEMCOMMENTGRADE.getActionName();
    }
}

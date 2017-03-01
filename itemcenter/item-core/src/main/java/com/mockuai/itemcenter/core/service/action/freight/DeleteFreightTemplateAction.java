package com.mockuai.itemcenter.core.service.action.freight;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.FreightTemplateManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/9/23.
 */
@Service
public class DeleteFreightTemplateAction extends TransAction{

    @Resource
    private FreightTemplateManager freightTemplateManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        if (request.getLong("templateId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "id is missing");
        }
        if (request.getLong("sellerId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }

        Long templateId = request.getLong("templateId");
        Long sellerId = request.getLong("sellerId");


        Boolean result = freightTemplateManager.deleteFreightTemplate(templateId, sellerId);


        return ResponseUtil.getSuccessResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_FREIGHT_TEMPLATE_ACTION.getActionName();
    }
}

package com.mockuai.itemcenter.core.service.action.freight;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.FreightTemplateManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/9/23.
 */
@Service
public class QueryFreightTemplateAction extends TransAction {

    @Resource
    private FreightTemplateManager freightTemplateManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        if (request.getParam("freightTemplateQTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "freightTemplateQTO is missing");
        }



        FreightTemplateQTO freightTemplateQTO = (FreightTemplateQTO) request.getParam("freightTemplateQTO");

        List<FreightTemplateDTO> freightTemplateDTOList = freightTemplateManager.queryFeightTemplate(freightTemplateQTO);


        return ResponseUtil.getSuccessResponse(freightTemplateDTOList,freightTemplateQTO.getTotalCount());

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName();
    }
}

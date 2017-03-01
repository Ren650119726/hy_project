package com.mockuai.itemcenter.core.service.action.itemdetailtmpl;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemDetailTemplateManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/21.
 */
@Service
public class QueryItemDetailTemplateAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryItemDetailTemplateAction.class);

    @Resource
    private ItemDetailTemplateManager itemDetailTemplateManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        ItemDetailTemplateQTO itemDetailTemplateQTO = (ItemDetailTemplateQTO) request.getParam("itemDetailTemplateQTO");
        if(itemDetailTemplateQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "param is null");
        }
        try {
            List<ItemDetailTemplateDTO> itemDetailTemplateDTOs = itemDetailTemplateManager.queryItemDetailTemplate(itemDetailTemplateQTO);
            response = ResponseUtil.getSuccessResponse(itemDetailTemplateDTOs);
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ITEM_DETAIL_TEMPLATE.getActionName();
    }
}

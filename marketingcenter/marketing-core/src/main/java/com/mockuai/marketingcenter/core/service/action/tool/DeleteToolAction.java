package com.mockuai.marketingcenter.core.service.action.tool;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.manager.PropertyTmplManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeleteToolAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteToolAction.class);

    @Resource
    private MarketToolManager marketToolManager;

    @Resource
    private PropertyTmplManager propertyTmplManager;

    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        Long marketToolId = (Long) context.getRequest().getParam("marketToolId");
        Long providerId = (Long) context.getRequest().getParam("providerId");
        String bizCode = (String) context.get("bizCode");

        try {

            // FIXME 目前不支持 tool 的管理, 此处未作更改
            MarketToolDO marketToolDO = this.marketToolManager.getTool(marketToolId.longValue(), bizCode);

            if (marketToolDO == null) ;

            return MarketingUtils.getSuccessResponse();

        } catch (MarketingException e) {

            LOGGER.error("Action:" + context.getRequest().getCommand(), e);

            return MarketingUtils.getFailResponse(e.getCode(), e.getMessage());

        }
    }

    public String getName() {
        return ActionEnum.DELETE_TOOL.getActionName();
    }
}
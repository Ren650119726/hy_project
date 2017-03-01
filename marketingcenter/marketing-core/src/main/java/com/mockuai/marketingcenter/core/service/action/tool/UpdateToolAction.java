package com.mockuai.marketingcenter.core.service.action.tool;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketToolDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
public class UpdateToolAction implements Action<MarketToolDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateToolAction.class);
    @Resource
    TransactionTemplate transactionTemplate;
    @Resource
    private MarketToolManager marketToolManager;

    public MarketingResponse<MarketToolDTO> execute(final RequestContext<MarketToolDTO> request) {

        return (MarketingResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public MarketingResponse<MarketToolDTO> doInTransaction(TransactionStatus arg0) {

                MarketToolDTO dto = (MarketToolDTO) request.getRequest().getParam("PARAM_ENTITY");

                Preconditions.checkArgument(dto != null, "工具对象为null!");

                if (UpdateToolAction.LOGGER.isDebugEnabled())
                    UpdateToolAction.LOGGER.debug("MarketingToolDTO:" + JSONObject.toJSONString(dto, true));

                try {

                    UpdateToolAction.this.marketToolManager.updateTool(ModelUtil.genMarketToolDO(dto));

                    return MarketingUtils.getSuccessResponse();
                } catch (MarketingException e) {

                    arg0.setRollbackOnly();

                    UpdateToolAction.LOGGER.error("Action:" + request.getRequest().getCommand(), e);

                    return MarketingUtils.getFailResponse(e.getCode(), e.getMessage());
                }
            }

        });
    }

    public String getName() {
        return ActionEnum.UPDATE_TOOL.getActionName();
    }
}
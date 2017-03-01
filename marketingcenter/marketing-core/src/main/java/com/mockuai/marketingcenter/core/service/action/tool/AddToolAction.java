package com.mockuai.marketingcenter.core.service.action.tool;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.MarketToolDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyTmplDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.manager.PropertyTmplManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddToolAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddToolAction.class);

    @Resource
    private MarketToolManager marketToolManager;

    @Resource
    private PropertyTmplManager propertyTmplManager;

    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        MarketToolDTO marketToolDTO = (MarketToolDTO) context.getRequest().getParam("marketToolDTO");
        String bizCode = (String) context.get("bizCode");

        if (StringUtils.isBlank(bizCode)) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL, "bizCode is null");
        }

        Preconditions.checkArgument(marketToolDTO != null, "工具对象为null!");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("MarketingToolDTO:" + JSONObject.toJSONString(marketToolDTO, true));
        }

        marketToolDTO.setBizCode(bizCode);

        // 工具类型验证, 默认简单工具 TODO 后续应该删除复合工具类型
        if (marketToolDTO.getType() == null) {
            marketToolDTO.setParentId(0L);
            marketToolDTO.setType(ToolType.SIMPLE_TOOL.getValue());
        }

        // 工具的实现对象名称
        if (StringUtils.isBlank(marketToolDTO.getImplContent())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "implContent of MarketToolDTO is null");
        }

        try {

            long marketToolId = this.marketToolManager.addTool(ModelUtil.genMarketToolDO(marketToolDTO)).longValue();

            for (PropertyTmplDTO propertyTmplDTO : marketToolDTO.getPropertyTmplList()) {

                propertyTmplDTO.setOwnerType(Integer.valueOf(1));
                propertyTmplDTO.setOwnerId(Long.valueOf(marketToolId));
                this.propertyTmplManager.addPropertyTmpl(ModelUtil.genPropertyTmplDO(propertyTmplDTO));
            }

            marketToolDTO.setId(Long.valueOf(marketToolId));
            return MarketingUtils.getSuccessResponse(marketToolDTO);
        } catch (MarketingException e) {

            return MarketingUtils.getFailResponse(e.getCode(), e.getMessage());
        }
    }

    public String getName() {

        return ActionEnum.ADD_TOOL.getActionName();
    }
}
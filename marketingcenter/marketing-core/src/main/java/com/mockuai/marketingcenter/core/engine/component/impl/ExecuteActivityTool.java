package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.engine.tool.Tool;
import com.mockuai.marketingcenter.core.engine.tool.ToolHolder;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mockuai.marketingcenter.common.constant.ComponentType.EXECUTE_ACTIVITY_TOOL;

/**
 * Created by edgar.zr on 1/19/16.
 */
@Service
public class ExecuteActivityTool implements Component {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExecuteActivityTool.class);

    @Autowired
    ToolHolder toolHolder;

    public static Context wrapParams(MarketActivityDTO marketActivityDTO, Context activityContext) {
        Context context = new Context();
        context.setParam("context", activityContext);
        context.setParam("marketActivityDTO", marketActivityDTO);
        context.setParam("component", EXECUTE_ACTIVITY_TOOL);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public DiscountInfo execute(Context context) throws MarketingException {

        MarketActivityDTO marketActivityDTO = (MarketActivityDTO) context.getParam("marketActivityDTO");
        Context activityContext = (Context) context.getParam("context");

        if (marketActivityDTO == null) {
            return null;
        }

        String toolCode = marketActivityDTO.getToolCode();
        Tool tool = toolHolder.getTool(toolCode);

        if (tool == null) {
            LOGGER.error("tool not found, toolCode : {}, bizCode : {}",
                    marketActivityDTO.getToolCode(), activityContext.getParam("bizCode"));
            return null;
        }

        try {
            return tool.execute(activityContext);
        } catch (MarketingException e) {
            LOGGER.error("error of executing the tool, toolCode : {}, tool : {}, bizCode : {}",
                    toolCode, JsonUtil.toJson(tool), activityContext.getParam("bizCode"));
            return null;
        }
    }

    @Override
    public String getComponentCode() {
        return EXECUTE_ACTIVITY_TOOL.getCode();
    }
}
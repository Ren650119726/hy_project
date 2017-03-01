package com.mockuai.marketingcenter.core.engine.tool;

import com.mockuai.marketingcenter.common.domain.dto.MarketToolDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyTmplDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketToolQTO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.manager.PropertyTmplManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToolHolder implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToolHolder.class);
    private ApplicationContext applicationContext;
    private Map<String, Tool> toolMap;

    @Resource
    private MarketToolManager marketToolManager;

    @Autowired
    private PropertyTmplManager propertyTmplManager;

    public ToolHolder() {
        this.toolMap = new HashMap();
    }

    @PostConstruct
    public void loadTool() {
        try {
            //TODO 当工具量超过100时，这里需要分页查找，加载所有工具, 目前的方案还不会达到这个极限
            MarketToolQTO marketToolQTO = new MarketToolQTO();
            marketToolQTO.setOffset(Integer.valueOf(0));
            marketToolQTO.setCount(Integer.valueOf(100));
            List<MarketToolDO> marketToolDOs = this.marketToolManager.queryTool(marketToolQTO);

            // 查询所有工具实现
            Map<String, Tool> map = applicationContext.getBeansOfType(Tool.class);
            for (Tool act : map.values()) {
                LOGGER.info("getTool entity : {}", act.getToolCode());
                toolMap.put(act.getToolCode(), act);
            }

            Map<String, MarketToolDO> toolCodeKeyTool = new HashMap<String, MarketToolDO>();
            for (MarketToolDO marketToolDO : marketToolDOs)
                toolCodeKeyTool.put(marketToolDO.getToolCode(), marketToolDO);

            for (String toolCode : toolMap.keySet())
                if (!toolCodeKeyTool.containsKey(toolCode)) {
                    LOGGER.info("init tool entity : {}", toolCode);
                    initMarketTool(toolMap.get(toolCode));
                }

        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    /**
     * 初始化未生成的 marketTool 和 propertyTmpl
     *
     * @param tool
     */
    private void initMarketTool(Tool tool) {
        MarketToolDTO marketToolDTO = tool.getMarketTool();
        try {
            Long id = marketToolManager.addTool(ModelUtil.genMarketToolDO(marketToolDTO));
            if (id == null) {
                LOGGER.error("the marketTool can not init, toolCode ; {}", marketToolDTO.getToolCode());
            }
            for (PropertyTmplDTO propertyTmplDTO : marketToolDTO.getPropertyTmplList()) {
                propertyTmplDTO.setOwnerId(id);
                propertyTmplManager.addPropertyTmpl(ModelUtil.genPropertyTmplDO(propertyTmplDTO));
            }
        } catch (MarketingException e) {
            LOGGER.error("error to initMarketTool, tool : {}", JsonUtil.toJson(tool), e);
        }
    }

    public Tool getTool(String toolCode) {
        return this.toolMap.get(toolCode);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
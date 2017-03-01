package com.mockuai.seckillcenter.core.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ComponentHolder implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentHolder.class);

    private Map<String, Component> componentMap;
    private ApplicationContext applicationContext;

    public ComponentHolder() {
        componentMap = new HashMap<String, Component>();
    }

    @PostConstruct
    public void loadComponent() {
        // 查询所有工具实现
        Map<String, Component> map = applicationContext.getBeansOfType(Component.class);
        for (Component com : map.values()) {
            LOGGER.info("get component entity : {}", com.getComponentCode());
            componentMap.put(com.getComponentCode(), com);
        }
    }

    public Component getComponent(String componentCode) {

        return componentMap.get(componentCode);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
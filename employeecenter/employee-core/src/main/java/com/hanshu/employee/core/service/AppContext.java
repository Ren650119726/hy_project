package com.hanshu.employee.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.Map;


/**
 * @author wujin.zzq
 */
public class AppContext extends Context implements ApplicationContextAware {
    public static final Logger log = LoggerFactory.getLogger(AppContext.class);
    private static final long serialVersionUID = 6830942667813319841L;
    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> clz) {
        Map<String, T> beans = applicationContext.getBeansOfType(clz);

        Collection<T> values = beans.values();

        if (values.size() == 0) {
            log.warn("current size of beans is zero when get service " + clz.getName());
            return null;
        }
        return values.iterator().next();
    }

    public Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

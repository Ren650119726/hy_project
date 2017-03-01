package com.mockuai.imagecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by guansheng on 2016/8/2.
 */
public class ApplicationAware implements ApplicationContextAware {

    private  static ApplicationContext applicationContext;

    public static  Object getBean(String beanName){

        return  applicationContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.print("注入...............");
        this.applicationContext = applicationContext;
    }
}

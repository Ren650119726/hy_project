package com.mockuai.mainweb.core.util;

import com.mockuai.common.uils.StarterRunner;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author
 * @since 
 */
public class Starter {
    private static final String APP_NAME = "mainwebcenter";
    private static final String LOCAL_PROPERTIES = "e:/haiyn/haiyn_properties/mainweb/haiyn.properties";

	public static void main(String[] args) throws Exception {
        org.slf4j.Logger logger = LoggerFactory.getLogger(Starter.class);
        StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,args);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml"});
        logger.info("-------初始运行化完成--------");
        context.start();
        logger.info("--------启动完成---------");
        while(true){

        }
	}
}

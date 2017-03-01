package com.mockuai.shopcenter.core.util;

import com.mockuai.common.uils.StarterRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author
 * @since 
 */
public class Starter {

    private static final String APP_NAME = "shopcenter";
    private static final String LOCAL_PROPERTIES = "e:/haiyn/haiyn/haiyn_properties/shopcenter/haiyn-dev.properties";

    private static final Logger log = LoggerFactory.getLogger(Starter.class);
	public static void main(String[] args) throws Exception {
        StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,args);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml"});
        log.info("-------初始运行化完成--------");
        context.start();
        log.info("--------启动完成---------");
		Thread.sleep(1000*60*60*24*30*12);
	}
}

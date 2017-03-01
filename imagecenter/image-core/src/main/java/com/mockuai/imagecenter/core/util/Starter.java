package com.mockuai.imagecenter.core.util;

import com.mockuai.common.uils.StarterRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author
 * @since 
 */
public class Starter {
	private static final Logger log = LoggerFactory.getLogger(Starter.class);
    private static final String APP_NAME = "imagecenter";
    private static final String LOCAL_PROPERTIES = "e:/haiyn/haiynParent/haiyn_properties/image/haiyn.properties";

    public static void main(String[] args) throws Exception {

        StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,args);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml"});
        log.error("-------初始运行化完成--------");
        context.start();
        log.error("--------启动完成---------");
	}
}

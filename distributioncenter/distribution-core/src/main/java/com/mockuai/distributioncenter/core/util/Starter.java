package com.mockuai.distributioncenter.core.util;

import com.mockuai.common.uils.StarterRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
/**
 * @author
 * @since 
 */
public class Starter {
	private static final Logger log = LoggerFactory.getLogger(Starter.class);
    private static final String APP_NAME = "distributioncenter";
    private static final String LOCAL_PROPERTIES = "e:/hy_workspace_test/haiyn/haiyn_properties/distribution/haiyn.properties";
//    E:\hy_workspace_test\haiyn\haiyn_properties\distribution
	public static void main(String[] args) throws Exception {
        StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES, args);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml"});
        log.info("-------初始运行化完成--------");
        context.start();
        log.info("--------启动完成---------");
	}
}

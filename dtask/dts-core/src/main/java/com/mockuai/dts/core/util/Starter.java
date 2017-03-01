package com.mockuai.dts.core.util;

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
    private static final String APP_NAME = "dtaskcenter";
    private static final String LOCAL_PROPERTIES = "e:/hy_workspace_test/haiyn/haiyn_properties/dtaskcenter/haiyn.properties";
    //E:\hy_workspace_test\haiyn\haiyn_properties\dtaskcenter

    public static void main(String[] args) throws Exception {

        org.slf4j.Logger logger = LoggerFactory.getLogger(Starter.class);
        StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES, args);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml"});
        logger.error("-------初始运行化完成--------");
        context.start();

        logger.error("--------启动完成---------");
        while (true){

        }
    }
}

package com.mockuai.headsinglecenter.core.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Starter {
    private static final Logger log = LoggerFactory.getLogger(Starter.class);
//    private static final String APP_NAME = "headsingle";
//    private static final String LOCAL_PROPERTIES = "e:/haiyn/haiynParent/haiyn_properties/headsingle/haiyn.properties";


    public static void main(String[] args) throws Exception {
//        StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,args);
    	Properties properties = new Properties();

        // p.load(new FileInputStream(
        // "C:/workspaces/school/trunk1/school-parent/main-service/src/main/resources/druid-db-dev.properties"));
        properties.putAll(System.getProperties());
        if (args != null && args.length > 0) {
            String arg = args[0];
            //p.put("druid.config.file", args[0]);
            switch (arg) {
                case "local":
                    properties.load(Files.newInputStream(Paths.get("D:/DongYin/work/project_haiyn/haiyn/haiyn_properties/delivery", "haiyn-dev.properties")));
                    break;
                case "dev":
                	properties.load(Files.newInputStream(Paths.get("/data/prop/headsingle/haiyn-dev.properties")));
                    break;
                case "pre":
                	properties.load(Files.newInputStream(Paths.get("/data/prop/headsingle/haiyn-pre.properties")));
                    break;
                case "online":
                	properties.load(Files.newInputStream(Paths.get("/data/prop/headsingle/haiyn-online.properties")));
                    break;
            }
            System.setProperties(properties);
        }else{
            throw  new Exception("请输入环境变量 local test pre online");
        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml"});
        log.error("-------初始运行化完成--------");
        context.start();
        log.error("--------启动完成---------");
        while(true){

        }
    }
}

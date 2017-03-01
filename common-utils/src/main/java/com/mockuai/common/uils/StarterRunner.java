package com.mockuai.common.uils; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.common.collect.Lists;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * Created by hy on 2016/11/14.
 */
public class StarterRunner {

    private static final List<String> PARAMS= Lists.newArrayList("","dev","test","local","pre","online");

    public static void localSystemProperties(String appName,String localProperties,String[] args) throws Exception {
        Properties properties = new Properties();

        String arg = "";
        if(args.length > 0){
            arg = args[0];
        }
        properties.putAll(System.getProperties());
        if (PARAMS.contains(arg)) {

            //p.put("druid.config.file", args[0]);
            switch (arg) {
                default:
                case "local":
                    properties.load(Files.newInputStream(Paths.get(localProperties)));
                    break;
                case "test":
                case "dev":
                    properties.load(Files.newInputStream(Paths.get("/data", "prop",appName,"haiyn-dev.properties")));
                    break;
                case "pre":
                    properties.load(Files.newInputStream(Paths.get("/data", "prop",appName,"haiyn-pre.properties")));
                    break;
                case "online":
                    properties.load(Files.newInputStream(Paths.get("/data", "prop",appName,"haiyn-online.properties")));
                    break;
            }
            System.setProperties(properties);
        }else{
            throw  new Exception("请输入环境变量 local dev  pre online");
        }
    }
}

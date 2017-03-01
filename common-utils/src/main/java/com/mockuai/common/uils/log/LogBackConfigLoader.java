package com.mockuai.common.uils.log; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

/**
 * Created by hy on 2016/11/14.
 */
public class LogBackConfigLoader {

    public static void load (String externalConfigFileLocation) throws Exception {
    /*    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        File externalConfigFile = new File(externalConfigFileLocation);
        if(!externalConfigFile.exists()){
            throw new IOException("Logback External Config File Parameter does not reference a file that exists");
        }else{
            if(!externalConfigFile.isFile()){
                throw new IOException("Logback External Config File Parameter exists, but does not reference a file");
            }else{
                if(!externalConfigFile.canRead()){
                    throw new IOException("Logback External Config File exists and is a file, but cannot be read.");
                }else{
                    JoranConfigurator configurator = new JoranConfigurator();
                    configurator.setContext(lc);
                    lc.reset();
                    configurator.doConfigure(externalConfigFileLocation);
                    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
                }
            }
        }*/
    }
}

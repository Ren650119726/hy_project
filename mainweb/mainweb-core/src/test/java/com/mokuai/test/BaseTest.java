package com.mokuai.test; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hy on 2016/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BaseTest {
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);


    public void logClassName(){
        log.info("my name :{}",getClass().getSimpleName());
    }
}

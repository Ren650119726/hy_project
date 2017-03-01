package com.mokuai.test;

import com.mockuai.common.uils.StarterRunner;
import com.mockuai.mainweb.common.domain.dto.PagePictureDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PagePictureManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by huangsiqian  on 2016/12/20.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PagePictureTest extends BaseTest {

    private static final String APP_NAME = "mainwebcenter";
    //E:\hy_workspace_test\haiyn\haiyn_properties\mainweb
    private static final String LOCAL_PROPERTIES = "e:/hy_workspace_test/haiyn/haiyn_properties/mainweb/haiyn.properties";
    private static final Logger log = LoggerFactory.getLogger(PagePictureTest.class);

    static  {
        try {
            StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,new String[] {});
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Resource
    private PagePictureManager pagePictureManager;



    @Test
    public void test() throws MainWebException {
        PagePictureDTO pagePictureDTO = new PagePictureDTO();
        pagePictureDTO.setBlockId(1111L);
        pagePictureDTO.setImageUrl("http://avatar.csdn.net/9/F/2/1_5iasp.jpg");
//        pagePictureDTO.setId(1221L);
        pagePictureManager.addPagePictureBlock(pagePictureDTO);
    }

}

package com.mokuai.test;

import com.mockuai.mainweb.core.dao.PageGalleryDAO;
import com.mockuai.mainweb.core.domain.PageGalleryDO;
import com.mokuai.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class PageGalleryTest extends BaseTest {

    @Resource
    PageGalleryDAO pageGalleryDAO;

    @Test
    public void testt1(){
        try {
            List<PageGalleryDO> pageGalleryDOs = pageGalleryDAO.queryPageGallery(1L);
            System.out.println("++++++++++++++++++");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testt2(){
        PageGalleryDO pageGalleryDO = new PageGalleryDO();
        pageGalleryDO.setId(2L);
        pageGalleryDO.setImageUri("www.baidu.fuck");
        try {
            pageGalleryDAO.addPageGallery(pageGalleryDO);
            System.out.println("++++++++++++++++++");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

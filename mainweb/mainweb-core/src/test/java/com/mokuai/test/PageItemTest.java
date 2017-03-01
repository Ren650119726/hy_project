package com.mokuai.test;

import com.mockuai.mainweb.common.domain.dto.PageItemDTO;
import com.mockuai.mainweb.core.dao.PageItemDAO;
import com.mockuai.mainweb.core.domain.PageItemDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PageItemTest {
    @Autowired
    private PageItemDAO pageItemDAO;
    @Test
    public void testAdd() throws SQLException {
//       PageItemDO pageItemDO = new PageItemDO();
//        pageItemDO.setDeleteMark(0);
//        pageItemDO.setItemId(2L);
//        pageItemDO.setPageItemCategoryId(3L);
//        pageItemDO.setSellerId(4L);
//        Long row =pageItemDAO.addPageItem(pageItemDO);
////        Integer row2 = pageItemDAO.deletePageItem(3L);
//        System.out.println(row);
        PageItemDO p= new PageItemDO();
        p.setId(2L);
        //PageItemDO pageItemDO = pageItemDAO.queryPageItem(1L);
        //System.out.println(pageItemDO);
    }

    @Test
    public void testPageItem() throws SQLException {
       List<PageItemDO> pageItemDOs = pageItemDAO.queryPageItem(87L);
        System.out.println("+++++++++++++++++++++");
        for (PageItemDO pageItemDO : pageItemDOs){
            System.out.println(pageItemDO.getId()+" : "+pageItemDO.getItemId());
        }



    }


}

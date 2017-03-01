package com.mokuai.test;

import com.mockuai.mainweb.core.dao.PageBlockDAO;
import com.mockuai.mainweb.core.domain.PageBlockDO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class PageBlockTest extends BaseTest{
    private static final Logger log = LoggerFactory.getLogger(PageBlockTest.class);

    @Autowired
    PageBlockDAO pageBlockDAO;

    @Test
    public void test(){
        PageBlockDO pageBlockDO = new PageBlockDO();
        pageBlockDO.setId(2L);
        pageBlockDO.setBlockOrder(1);
        pageBlockDO.setBlockType(1);
        pageBlockDO.setHeight(10L);
        pageBlockDO.setTmsName("ff");

        try {
            pageBlockDAO.addPageBlock(pageBlockDO);
            log.info("pageBlock:{}",pageBlockDO.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test2(){
        try {
            List<PageBlockDO> list = pageBlockDAO.queryPageBlock(1L);
            System.out.println("+++++++++++"+list);
            for (PageBlockDO pageBlockDO:list){
                System.out.println("id:"+pageBlockDO.getId()+"\n"+
                                    "block_order"+pageBlockDO.getBlockOrder());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3(){




    }






}

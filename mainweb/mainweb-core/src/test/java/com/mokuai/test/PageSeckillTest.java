package com.mokuai.test;

import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.PageSeckillDTO;
import com.mockuai.mainweb.core.dao.PageGalleryDAO;
import com.mockuai.mainweb.core.dao.PageSeckillDAO;
import com.mockuai.mainweb.core.domain.PageGalleryDO;
import com.mockuai.mainweb.core.domain.PageSeckillDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.AppManager;
import com.mockuai.mainweb.core.manager.PageSeckillManager;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class PageSeckillTest extends BaseTest {

    @Resource
    PageSeckillDAO pageSeckillDAO;

    @Resource
    PageSeckillManager pageSeckillManager;

    @Resource
    MainWebService mainWebService;
//
//    @Resource
//    AppManager appManager;
    static final String appKey = "27c7bc87733c6d253458fa8908001ee9";

    @Test
    public void testt1(){
        PageSeckillDO pageSeckillDO = new PageSeckillDO();
        pageSeckillDO.setSeckillId(1L);
        pageSeckillDO.setId(1L);
        pageSeckillDO.setPageId(1L);
        pageSeckillDO.setBlockId(1L);
        pageSeckillDO.setImageUrl("www.baidu.com");
        pageSeckillDO.setTargetUrl("www.qq.com");
        pageSeckillDO.setSeckillTitle("剁手吗");

        try {
            pageSeckillDAO.addPageSeckill(pageSeckillDO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testt2(){
        PageSeckillDO pageSeckillDO = new PageSeckillDO();
        pageSeckillDO.setSeckillId(2L);
        pageSeckillDO.setId(2L);
        pageSeckillDO.setPageId(1L);
        pageSeckillDO.setBlockId(1L);
        pageSeckillDO.setImageUrl("www.baidu.com");
        pageSeckillDO.setTargetUrl("www.qq.com");
        pageSeckillDO.setSeckillTitle("剁手吗");

        PageSeckillDTO pageSeckillDTO = new PageSeckillDTO();
        BeanUtils.copyProperties(pageSeckillDO,pageSeckillDTO);

        try {
            pageSeckillManager.addPageSeckill(pageSeckillDTO);
        } catch (MainWebException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test3(){
        try {
          List<String> names =  pageSeckillDAO.queryPageSeckillTitles(1L);
            for (String name : names){
                System.out.println("++++++++++++++++++"+name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4(){
        List<String> names = null;
        try {
            names = pageSeckillManager.queryPageSeckillTitles(1L);
            for (String name : names){
                System.out.println("++++++++++++++++++"+name);
            }
        } catch (MainWebException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test5(){
        try {
            pageSeckillDAO.deletePageSeckill(1L);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6(){
        try {
            pageSeckillManager.deletePageSeckill(1L);
        } catch (MainWebException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test7(){
        try {
            List<PageSeckillDTO> pageSeckillDTOs = pageSeckillManager.queryPageSeckill(1L);
            for (PageSeckillDTO pageSeckillDTO : pageSeckillDTOs){
                System.out.println(pageSeckillDTO.getSeckillId());
            }
        } catch (MainWebException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test8() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_SECKILL_THEN_PUBLISH.getActionName());
        PageSeckillDTO pageSeckillDTO = new PageSeckillDTO();
        pageSeckillDTO.setPageId(147L);
        pageSeckillDTO.setBlockId(483L);
        pageSeckillDTO.setSeckillId(3L);
        pageSeckillDTO.setImageUrl("http://haiynoss.haiyn.com/images/20160824/18/20160824182656488.jpg");
        request.setParam("pageSeckillDTO",pageSeckillDTO);
        request.setParam("appKey",appKey);

        mainWebService.execute(request);
    }

    @Test
    public void test9() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.DELETE_SECKILL_THEN_PUBLISH.getActionName());
        Long pageId = 147L;
        request.setParam("pageId",pageId);
        request.setParam("appKey",appKey);
         mainWebService.execute(request);


    }




}

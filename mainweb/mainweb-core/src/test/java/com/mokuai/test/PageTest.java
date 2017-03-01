package com.mokuai.test;

import com.google.common.collect.Lists;
import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.*;
import com.mockuai.mainweb.core.dao.PageDAO;
import com.mockuai.mainweb.core.domain.IndexPageDO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.manager.PublishPageManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class PageTest extends BaseTest{
    private static final Logger log = LoggerFactory.getLogger(PageTest.class);

    @Autowired
    PageDAO pageDAO;
    @Autowired
    private PageManager pageManager;
    @Autowired
    private MainWebService mainWebService;
    @Autowired
    private PublishPageManager publishPageManager;


    @Test
    public void testQueryAllName() throws MainWebException {
        String appKey = "27c7bc87733c6d253458fa8908001ee9";
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_PAGE_NAMES.getActionName());
        request.setParam("appKey",appKey);
        try {
            pageManager.queryPageNameList();
        } catch (MainWebException e) {
            e.printStackTrace();
            throw new MainWebException("查询页面名字失败",e);
        }

    }


    @Test
    public void testPublishPage() throws MainWebException {
        String appKey = "27c7bc87733c6d253458fa8908001ee9";
//        PublishPageDTO publishPageDTO = new PublishPageDTO();
//        publishPageDTO.setPageId(7L);
        Long pageId = 112L;
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_PUBLISH_PAGE.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("pageId",pageId);
        MainWebResponse componentResponse = (MainWebResponse) mainWebService.execute(request);
//        String component = (String) componentResponse.getModule();
//        System.out.println("++++++++++"+component+"++++++++++++");

//        if (component!=null){
//            for (String s : component){
//                System.out.println("+++"+s+"+++");
//            }
//        }else {
//            System.out.println("component is null");
//        }

//        return response;
//        mainWebService.execute();
    }



    @Test
    public void testSavePage() throws SQLException {
        IndexPageDO indexPageDO = new IndexPageDO();
        indexPageDO.setBlockCount(1L);
        indexPageDO.setBlockPadding(10L);
        indexPageDO.setCategoryId(1L);
        indexPageDO.setSubCategoryId(2L);
        indexPageDO.setName("lala");
        pageDAO.addPage(indexPageDO);
        log.info("page id:{}",indexPageDO.getId());
    }
//    @Test
//    public void testSavePageDTO() throws MainWebException {
//        IndexPageDTO indexPageDTO = new IndexPageDTO();
//        indexPageDTO.setBlockCount(1L);
//        indexPageDTO.setBlockPadding(20L);
//        indexPageDTO.setCategoryId(1L);
//        indexPageDTO.setName("韩束一叶子");
//        indexPageDTO.setPageOrder(3);
//        List<PageBlockDTO<ImageElementDTO>> imageEleBlock = new ArrayList<>();
//        List<PageBlockDTO<ItemListElementDTO>> itemEleBlock = new ArrayList<>();
//        List<PageBlockDTO> tmsEleBlock = new ArrayList<>();
//        PageBlockDTO<ImageElementDTO> imgBlockDTO = new PageBlockDTO<>();
//        PageBlockDTO<ItemListElementDTO> itemBlockDTO = new PageBlockDTO<>();
//        imgBlockDTO.setBlockOrder(2);
//        imgBlockDTO.setBlockType(1);
//        imgBlockDTO.setHeight(50L);
//
//        itemBlockDTO.setBlockOrder(1);
//        itemBlockDTO.setBlockType(2);
//        itemBlockDTO.setHeight(300L);
//        buildImgBlock(imgBlockDTO);
//        buildItemBlock(itemBlockDTO);
//
//        imageEleBlock.add(imgBlockDTO);
//        itemEleBlock.add(itemBlockDTO);
//
//        PageBlockDTO tmsBlockDTO = new PageBlockDTO();
//        tmsBlockDTO.setTmsName("allfff");
//        tmsBlockDTO.setHeight(50L);
//        tmsBlockDTO.setBlockType(3);
//        tmsBlockDTO.setSubCategoryId(10L);
//        tmsBlockDTO.setBlockOrder(5);
//        PageBlockDTO tmsBlockDTO1 = new PageBlockDTO();
//        tmsBlockDTO1.setTmsName("duwo");
//        tmsBlockDTO1.setHeight(50L);
//        tmsBlockDTO1.setBlockType(3);
//        tmsBlockDTO1.setSubCategoryId(15L);
//        tmsBlockDTO1.setBlockOrder(4);
//
//        tmsEleBlock.add(tmsBlockDTO);
//        tmsEleBlock.add(tmsBlockDTO1);
//        indexPageDTO.setImgPageBlockList(imageEleBlock);
//        indexPageDTO.setItemPageBlockList(itemEleBlock);
//        indexPageDTO.setTmsPageBlockList(tmsEleBlock);
//        //addPageAction.save(indexPageDTO);
//        pageManager.addPage(indexPageDTO);
//        pageManager.addBizPage(indexPageDTO);
//
//    }

    @Test
    public void testGetPage() throws MainWebException {
      //getPageAction.getPage(22L);
    }

//    public void buildImgBlock(PageBlockDTO<ImageElementDTO> blockDTO){
//        ImageElementDTO imageElementDTO = new ImageElementDTO();
//        imageElementDTO.setImageUri("http://aaa.png");
//        imageElementDTO.setTarget("http://baidu.com");
//        imageElementDTO.setSortIndex(1);
//        ImageElementDTO image1ElementDTO = new ImageElementDTO();
//        image1ElementDTO.setImageUri("http://bbb.png");
//        image1ElementDTO.setTarget("http://sohu.com");
//        image1ElementDTO.setSortIndex(2);
//        blockDTO.setElementList(Lists.newArrayList(imageElementDTO,image1ElementDTO));
//
//
//    }

    public void buildItemBlock(PageBlockDTO<ItemListElementDTO> blockDTO){
        ItemListElementDTO itemListElementDTO = new ItemListElementDTO();
        itemListElementDTO.setSortIndex(2);
        itemListElementDTO.setCategoryTitle("基础护理");
        itemListElementDTO.setSubCategoryTitle("祛痘祛印");
        PageItemDTO pageItemDTO = new PageItemDTO();
        pageItemDTO.setItemId(33L);
        pageItemDTO.setSellerId(1841254L);
        PageItemDTO pageItemDTO1 = new PageItemDTO();
        pageItemDTO1.setItemId(35L);
        pageItemDTO1.setSellerId(1841254L);
        PageItemDTO pageItemDTO2 = new PageItemDTO();
        pageItemDTO2.setItemId(36L);
        pageItemDTO2.setSellerId(1841254L);
        itemListElementDTO.setProductListList(Lists.newArrayList(pageItemDTO,pageItemDTO1,pageItemDTO2));
        blockDTO.setElementList(Lists.newArrayList(itemListElementDTO));
    }



    @Test
    public void test(){
        IndexPageDO indexPageDO = new IndexPageDO();

        /*try {
            //List<IndexPageDO> indexPageDOs =  pageDAO.queryPage(1L);
            System.out.println("++++++++++++++++++"+ indexPageDOs);
            for (IndexPageDO indexPageDO1 : indexPageDOs){
                System.out.println(indexPageDO1.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void test2(){
        IndexPageDO indexPageDO = new IndexPageDO();
        indexPageDO.setId(2L);
        indexPageDO.setBlockCount(2L);
        indexPageDO.setName("hehe");

        try {
            pageDAO.addPage(indexPageDO);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3(){
        try {
            pageDAO.deletePage(1L);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test4(){
        IndexPageDO indexPageDO = new IndexPageDO();
        indexPageDO.setId(2L);
        indexPageDO.setName("update");
        try {
            pageDAO.updatePage(indexPageDO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test99(){
        IndexPageDO indexPageDO = new IndexPageDO();
        indexPageDO.setId(2L);
        indexPageDO.setName("update");
        try {
            pageDAO.updatePage(indexPageDO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void test5() throws MainWebException {
//        List<IndexPageDO> pageDOs = new ArrayList<>();
//        List<IndexPageDTO> pageDTOs = new ArrayList<>();
//
//        try {
//           pageDOs = pageDAO.queryPageNameList();
//            pageDTOs = pageManager.queryPageNameList();
//            for (IndexPageDO indexPageDO : pageDOs){
//                System.out.println(indexPageDO.getName());
//            }
//
//            System.out.println("----------------------------");
//
//            for (IndexPageDTO pageDTO : pageDTOs){
//                System.out.println(pageDTO.getName()+":::::"+pageDTO.getId());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }



    @Test
    public void testDeleteThenPublish(){
        Request request = new BaseRequest();
        String appKey = "27c7bc87733c6d253458fa8908001ee9";
        request.setCommand(ActionEnum.DELETE_THEN_PUBLISH.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("pageId",59L);
        mainWebService.execute(request);

    }






}

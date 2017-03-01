//package com.mokuai.test.action; /**
// * create by 冠生
// * @date #{DATE}
// **/
////import com.mockuai.mainwebcenter.common.api.ImageService;
////import com.mockuai.mainwebcenter.common.api.action.BaseRequest;
////import com.mockuai.mainwebcenter.common.constant.ActionEnum;
//import com.mokuai.test.UnitTestUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//
///**
// * Created by 冠生 on 2016/5/28.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//public class QrcodeTest {
//
//    @Resource
//    private ImageService imageService;
//    private String appKey = "27c7bc87733c6d253458fa8908001eef";
//
//
//
//    @Test
//    public void testGenerateRecommendQrcode() throws Exception {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setParam("userId", 1L);
//        baseRequest.setParam("appKey", appKey);
//        baseRequest.setParam("url","my/profile/1");
//        baseRequest.setCommand(ActionEnum.GENERATE_RECOMMEND_QRCODE.getActionName());
//        UnitTestUtils.assertAndPrint(imageService.execute(baseRequest));
//    }
//    @Test
//    public void testGenerateShopQrcode() throws Exception {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setParam("userId", 573151L);
//        baseRequest.setParam("appKey", appKey);
//        baseRequest.setParam("url",null);
//        baseRequest.setCommand(ActionEnum.GENERATE_SHOP_QRCODE.getActionName());
//        UnitTestUtils.assertAndPrint(imageService.execute(baseRequest));
//    }
//
//    @Test
//    public void testDelteItem() throws Exception {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setParam("itemId", 124455L);
//        baseRequest.setParam("appKey", appKey);
//        baseRequest.setCommand(ActionEnum.DELETE_ITEM.getActionName());
//        UnitTestUtils.assertAndPrint(imageService.execute(baseRequest));
//
//    }
//
//
//    //        imageManager.generateItemCode("28955",1841254l,"item/28956",appKey);
//
//    @Test
//    public void testGenerateItem() throws Exception {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setParam("itemId", 28956L);
//        baseRequest.setParam("sellerId", 1841254L);
//        baseRequest.setParam("distributorId", 1L);
//        baseRequest.setParam("appKey", appKey);
//        baseRequest.setParam("url","my/profile/1");
//        baseRequest.setCommand(ActionEnum.GET_ITEM.getActionName());
//        UnitTestUtils.assertAndPrint(imageService.execute(baseRequest));
//    }
//
//    @Test
//    public void testGetItem() throws Exception {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setParam("type", "ITEM");
//        baseRequest.setParam("itemId",28954L );
//        baseRequest.setParam("sellerId",1841254L );
//        baseRequest.setParam("distributorId",1L);
//        baseRequest.setParam("appKey", appKey);
//        baseRequest.setCommand(ActionEnum.GET_ITEM.getActionName());
//        UnitTestUtils.assertAndPrint(imageService.execute(baseRequest));
//    }
//    @Test
//    public void testGetShop() throws Exception {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setParam("type", "SHOP");
//        baseRequest.setParam("id",1L );
//        baseRequest.setParam("appKey", appKey);
//        baseRequest.setCommand(ActionEnum.GET_QRCODE.getActionName());
//        UnitTestUtils.assertAndPrint(imageService.execute(baseRequest));
//    }
//
//    @Test
//    public void testGetShopRecommend() throws Exception {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setParam("id",1L );
//        baseRequest.setParam("appKey", appKey);
//        baseRequest.setCommand(ActionEnum.GET_SHOP_RECOMMEND_QRCODE.getActionName());
//        UnitTestUtils.assertAndPrint(imageService.execute(baseRequest));
//    }
//}

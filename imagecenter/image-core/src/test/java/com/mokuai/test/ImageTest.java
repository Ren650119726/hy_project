package com.mokuai.test; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mockuai.imagecenter.common.api.ImageService;
import com.mockuai.imagecenter.common.api.action.BaseRequest;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.common.domain.dto.ImageDTO;
import com.mockuai.imagecenter.core.dao.ImageDAO;
import com.mockuai.imagecenter.core.dao.QrcodeConfigDAO;
import com.mockuai.imagecenter.core.domain.*;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.ImageManager;
import com.mockuai.imagecenter.core.manager.QrCodeItemGenerator;
import com.mockuai.imagecenter.core.manager.impl.ItemRender;
import com.mockuai.imagecenter.core.manager.impl.RecommendRender;
import com.mockuai.imagecenter.core.manager.impl.ShopRender;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hy on 2016/5/24.
 */

public class ImageTest extends  BaseTest {
    @Autowired
    public ImageDAO imageDAO;
    @Autowired
    private QrcodeConfigDAO qrcodeConfigDAO;


    @Autowired
    @Qualifier("itemQRCodeManagerImpl")
    private QrCodeItemGenerator itemManger;
    private String appKey = "e39f32712b4139ee5785f0c40a38031c";

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private ImageService imageService;


   @Test
    public void queryRecommend(){
        Long id = 1L;
        BaseRequest request = new BaseRequest();
        request.setParam("appKey",appKey);
        request.setParam("id",id);
        request.setParam("type","RECOMMEND");
       request.setCommand( ActionEnum.GET_QRCODE.getActionName());
        imageService.execute(request);
    }


    @Test
    public void queryShop(){
        Long id = 1L;
        BaseRequest request = new BaseRequest();
        request.setParam("appKey",appKey);
        request.setParam("id",id);
        request.setParam("type","SHOP");
        request.setCommand( ActionEnum.GET_QRCODE.getActionName());
        imageService.execute(request);
    }


    @Test
    public void testAdd(){
        logClassName();
        ImageDO item = new ImageDO();
        item.setpKey("wefwefwefwef");
        item.setUserId(11333333334L);
        item.setBizCode("shop");
        item.setImageUrl("ossgvweeweeFFF.do");
        item.setItemId(null);
        imageDAO.add(item);
        log.info("image:{}",item);
    }
    @Test
    public void testQuery() throws SQLException {
        ImageDTO imageDO =    imageDAO.queryByKey("1122ferf33");
           log.info("image:{}",imageDO);
    }
   @Test
    public void testGetConfig() throws SQLException {
        QrcodeConfigDO qrcodeConfigDO =   qrcodeConfigDAO.queryByBizType(BizType.RECOMMEND.name());
        System.out.print(qrcodeConfigDO);
    }
    //1841258
    @Test
    public void testGenerateQrcode() throws ImageException {
       // recommendQrCodeManager.genQRCode("1",null,"shop/1841258",appKey);
       imageManager.generateRecommendCode("1","my/profile/1",appKey);
    }
    @Test
    public void testGenerateShop() throws ImageException {
        //imageManager.generateShopCode("38699","my/profile/38699",appKey);
        imageManager.generateShopCode("573151","my/profile/38699",appKey);
    }

    @Test
    public void testGenerateShop1() throws ImageException {
        //imageManager.generateShopCode("38699","my/profile/38699",appKey);
        imageManager.generateShopCode("573151",appKey);
    }

    @Test
    public void testGenerateItem() throws ImageException {
        //28955 1841254

        itemManger.genQRCode("28954","1841254",1l,"item/28956",appKey);
    }
    @Test
    public void testAddItem() throws ImageException {
        imageManager.generateItemCode("28956",1841254L,1L,"http://m.haiyn.com/merchant.html?distributor_id=444455212",appKey);
    }

    @Test
    public void testBatchGenerateItem() throws ImageException {
        imageManager.batchGenerateItemCode(28956L,"hanshu",appKey);
    }
    @Test
    public void testDeleteByUser() throws SQLException {
        imageDAO.deleteItemCodeByUserId(1841287L);
    }

    @Test
    public void testselectByUser() throws SQLException {
         ImageDTO imageDO =   imageDAO.queryByKey("ITEM29054_1841287");
        log.info("img:{}",imageDO);
    }





    @Test
    public void testGenerateConfig() throws IOException, SQLException {
        QrcodeConfigDetailDO qrcodeConfigDetailDO = new QrcodeConfigDetailDO();
        qrcodeConfigDetailDO.setX(166);
        qrcodeConfigDetailDO.setY(557);
        qrcodeConfigDetailDO.setWidth(268);
        qrcodeConfigDetailDO.setHeight(268);
        qrcodeConfigDetailDO.setType(BizColType.QRCODE.name());
        QrcodeConfigDetailDO realNameDetailDO = new QrcodeConfigDetailDO();
//         String pathString = FontLoader.class.getResource("e:/recommend_code/HYCHENMEIZIJ.TTF").getFile();
        //  Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new File(pathString));
        realNameDetailDO.setX(20);
        realNameDetailDO.setY(93);
        realNameDetailDO.setFontFamily("汉仪晨妹子简");
        realNameDetailDO.setFontSize(36);
        realNameDetailDO.setColor("fffbf0");
        realNameDetailDO.setType(BizColType.USER_NAME.name());
        List<QrcodeConfigDetailDO> detailDOs = new ArrayList<>();
        detailDOs.add(qrcodeConfigDetailDO);
        detailDOs.add(realNameDetailDO);
        QrcodeConfigDO qrcodeConfigDO = new QrcodeConfigDO();
        qrcodeConfigDO.setBgImage("e:/recommend_code/bg.png");
        qrcodeConfigDO.setBizType(BizType.RECOMMEND.name());

        qrcodeConfigDO.setConfigInfo( JSON.toJSONString(detailDOs));
        qrcodeConfigDAO.add(qrcodeConfigDO);
    }
    @Test
    public void testGenerateConfigShop() throws IOException, SQLException {
        QrcodeConfigDetailDO qrcodeConfigDetailDO = new QrcodeConfigDetailDO();
        qrcodeConfigDetailDO.setX(60);
        qrcodeConfigDetailDO.setY(357);
        qrcodeConfigDetailDO.setWidth(490);
        qrcodeConfigDetailDO.setHeight(510);
        qrcodeConfigDetailDO.setType(BizColType.QRCODE.name());
        QrcodeConfigDetailDO avatarConfigDetailDO = new QrcodeConfigDetailDO();
        avatarConfigDetailDO.setX(20);
        avatarConfigDetailDO.setY(89);
        avatarConfigDetailDO.setWidth(116);
        avatarConfigDetailDO.setHeight(116);
        avatarConfigDetailDO.setImgPath("e:/shop_code/default_avatar.png");
        avatarConfigDetailDO.setType(BizColType.AVATAR.name());
        QrcodeConfigDetailDO shopNameConfigDetailDO = new QrcodeConfigDetailDO();
        shopNameConfigDetailDO.setX(27);
        shopNameConfigDetailDO.setY(10);
        shopNameConfigDetailDO.setFontSize(24);
        shopNameConfigDetailDO.setColor("252525");
        shopNameConfigDetailDO.setType(BizColType.SHOP_NAME.name());
        QrcodeConfigDetailDO whoseConfigDetailDO = new QrcodeConfigDetailDO();
        whoseConfigDetailDO.setX(20);
        whoseConfigDetailDO.setY(92);
        whoseConfigDetailDO.setFontSize(20);
        whoseConfigDetailDO.setColor("666666");
        whoseConfigDetailDO.setType(BizColType.WHOSE_QRCODE.name());
        List<QrcodeConfigDetailDO> detailDOs =  Lists.newArrayList(qrcodeConfigDetailDO,avatarConfigDetailDO,shopNameConfigDetailDO,whoseConfigDetailDO);
        QrcodeConfigDO qrcodeConfigDO = new QrcodeConfigDO();
        qrcodeConfigDO.setBgImage("e:/shop_code/bg.png");
        qrcodeConfigDO.setBizType(BizType.SHOP.name());

        qrcodeConfigDO.setConfigInfo( JSON.toJSONString(detailDOs));
        qrcodeConfigDAO.add(qrcodeConfigDO);
    }

    @Test
    public void testGenerateConfigItem() throws IOException, SQLException {
        QrcodeConfigDetailDO logoConfigDetailDO = new QrcodeConfigDetailDO();
        logoConfigDetailDO.setImgPath("e:/item_code/logo.png");
        logoConfigDetailDO.setType(BizColType.LOGO.name());
        QrcodeConfigDetailDO qrcodeConfigDetailDO = new QrcodeConfigDetailDO();
        qrcodeConfigDetailDO.setX(20);
        qrcodeConfigDetailDO.setY(628);
        qrcodeConfigDetailDO.setWidth(198);
        qrcodeConfigDetailDO.setHeight(198);
        qrcodeConfigDetailDO.setType(BizColType.QRCODE.name());
        QrcodeConfigDetailDO imageConfigDetailDO = new QrcodeConfigDetailDO();
        imageConfigDetailDO.setX(20);
        imageConfigDetailDO.setY(20);
        imageConfigDetailDO.setType(BizColType.ITEM_IMAGE.name());
        QrcodeConfigDetailDO itemNameConfigDetailDO = new QrcodeConfigDetailDO();
        itemNameConfigDetailDO.setX(40);
        itemNameConfigDetailDO.setY(80);
        itemNameConfigDetailDO.setFontSize(24);
        itemNameConfigDetailDO.setColor("252525");
        itemNameConfigDetailDO.setType(BizColType.ITEM_NAME.name());
        imageConfigDetailDO.setFontFamily("Bauhaus ITC");
        QrcodeConfigDetailDO itemPriceConfigDetailDO = new QrcodeConfigDetailDO();
        itemPriceConfigDetailDO.setX(40);
        itemPriceConfigDetailDO.setY(90);
        itemPriceConfigDetailDO.setFontSize(24);
        itemPriceConfigDetailDO.setColor("f84e37");
        itemPriceConfigDetailDO.setType(BizColType.ITEM_PRICE.name());
        itemPriceConfigDetailDO.setFontFamily("Bauhaus ITC");
        QrcodeConfigDetailDO shopDetailDO = new QrcodeConfigDetailDO();
        shopDetailDO.setX(40);
        shopDetailDO.setY(95);
        shopDetailDO.setFontSize(18);
        shopDetailDO.setColor("666666");
        shopDetailDO.setFontFamily("Bauhaus ITC");
        //shopDetailDO.setFontWeight(Font.BOLD);
        shopDetailDO.setType(BizColType.WHOSE_SHOP.name());
        List<QrcodeConfigDetailDO> detailDOs = new ArrayList<QrcodeConfigDetailDO>();
        detailDOs.add(qrcodeConfigDetailDO);
        detailDOs.add(imageConfigDetailDO);
        detailDOs.add(itemNameConfigDetailDO);
        detailDOs.add(itemPriceConfigDetailDO);
        detailDOs.add(shopDetailDO);
        detailDOs.add(logoConfigDetailDO);
        QrcodeConfigDO qrcodeConfigDO = new QrcodeConfigDO();
        qrcodeConfigDO.setBgImage("e:/item_code/bg.png");
        qrcodeConfigDO.setBizType(BizType.ITEM.name());
        qrcodeConfigDO.setConfigInfo( JSON.toJSONString(detailDOs));
        qrcodeConfigDAO.add(qrcodeConfigDO);

    }
    @Test
    public void test() throws SQLException, IOException, ImageException {
        QrcodeConfigDO configDO =   qrcodeConfigDAO.queryByBizType(BizType.ITEM.name());
        List<QrcodeConfigDetailDO> qrcodeConfigDetailDOList =
                com.alibaba.fastjson.JSON.parseArray(configDO.getConfigInfo(), QrcodeConfigDetailDO.class);

        ItemRender itemRender = new ItemRender("28951",qrcodeConfigDetailDOList,"http://m.haiyn.com/merchant.html?distributor_id=444455212",configDO.getBgImage(),true);
        itemRender.render("http://src.haiyn.com/uploads/goods/thump/2016-06-07/a847f552f59c5af0c94e37e57d1011c7_650_thump.jpg",
                "宠物违法我访问发小店","小狗问富翁违法问富翁抚慰复位法我玩具家狗圈","100.00");

    }

    @Test
    public void test1() throws SQLException, IOException, ImageException {
        QrcodeConfigDO configDO =   qrcodeConfigDAO.queryByBizType(BizType.RECOMMEND.name());
        List<QrcodeConfigDetailDO> qrcodeConfigDetailDOList =
                com.alibaba.fastjson.JSON.parseArray(configDO.getConfigInfo(), QrcodeConfigDetailDO.class);
/*
        ItemRender itemRender = new ItemRender("28951",qrcodeConfigDetailDOList,"item/28951",configDO.getBgImage());
        itemRender.render("http://img6.ph.126.net/cXco0-F0hCwsmRzcIdpiYA==/580964351948486486.jpg",
                "宠物小店","小狗玩具家狗圈","100.00");*/
        String url  = "http://m.haiyn.com/merchant-login.html?inviter_seller_id=420515&real_name=";
        url =url+   URLEncoder.encode("邵长生","UTF-8");
        RecommendRender recommendRender = new RecommendRender("123",qrcodeConfigDetailDOList,url,configDO.getBgImage());
        String tpl = "我是创业者%s,我为嗨云代言";
        String realName =String.format( tpl,"赵丕恒心");
        recommendRender.renderRecommend(realName);
    }
    @Test
    public void testShop() throws SQLException, IOException, ImageException {
        QrcodeConfigDO configDO =   qrcodeConfigDAO.queryByBizType(BizType.SHOP.name());
        List<QrcodeConfigDetailDO> qrcodeConfigDetailDOList =
                com.alibaba.fastjson.JSON.parseArray(configDO.getConfigInfo(), QrcodeConfigDetailDO.class);
        ShopRender shopRender = new ShopRender("1",qrcodeConfigDetailDOList,"shopCode",configDO.getBgImage());
        String avatar  = null;
        shopRender.render(avatar,"卡乐比的店铺");

    }
    @Test
    public void testAwareImage(){
      //  ImageUtil.getItemUrlTpl(1,2,3,"hanshu");
    }

}
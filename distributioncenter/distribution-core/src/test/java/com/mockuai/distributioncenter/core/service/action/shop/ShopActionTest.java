package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.CollectionShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.common.domain.qto.MyFansQTO;
import com.mockuai.distributioncenter.core.service.action.UnitTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by duke on 16/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ShopActionTest {
    private static final String appKey = "6562b5ddf0aed2aad8fe471ce2a2c8a0";

    @Autowired
    private DistributionService distributionService;

    @Test
    public void testAddCollectionShop() throws Exception {
        CollectionShopDTO collectionShopDTO = new CollectionShopDTO();
        collectionShopDTO.setShopId(1L);
        collectionShopDTO.setUserId(1L);
        collectionShopDTO.setShopName("测试店铺");
        collectionShopDTO.setShopDesc("测试签名");

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("collectionShopDTO", collectionShopDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.ADD_COLLECTION_SHOP.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testDeleteCollectionShop() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("id", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.DELETE_COLLECTION_SHOP.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testQueryCollectionShopByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_COLLECTION_SHOP_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetShopByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SHOP_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testQueryDistShop() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        DistShopQTO distShopQTO = new DistShopQTO();
        baseRequest.setParam("distShopQTO", distShopQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_SHOP.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testUpdateShop() throws Exception {
        DistShopDTO distShopDTO = new DistShopDTO();
        distShopDTO.setId(1L);
        distShopDTO.setShopDesc("平台店铺");
        distShopDTO.setShopName("平台店铺描述");
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("shopDTO", distShopDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_SHOP.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetShopBySellerId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("sellerId", 1l);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SHOP_BY_SELLER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));

    }


    @Test
    public void testGetMyFans() throws Exception {
        MyFansQTO myFansQTO = new MyFansQTO();
        myFansQTO.setUserId(1L);
        myFansQTO.setSort(1);
        myFansQTO.setUpdown(2);
        myFansQTO.setOffset(1L);
        myFansQTO.setCount(30);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("myFansQTO",myFansQTO);
        baseRequest.setParam("appKey",appKey);
        baseRequest.setCommand(ActionEnum.GET_MY_FANS.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }
}

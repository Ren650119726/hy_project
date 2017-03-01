package com.mockuai.distributioncenter.core.service.action.shop; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.aliyun.oss.common.utils.HttpUtil;
import com.mockuai.distributioncenter.common.api.*;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.SellerUpgradeApplyStatus;
import com.mockuai.distributioncenter.common.domain.dto.GoodsItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by 冠生 on 2016/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class QueryItemsTest {
    private static final Logger log = LoggerFactory.getLogger(QueryItemsTest.class);

    @Resource
    private DistributionService distributionService;

    private String appKey = "27c7bc87733c6d253458fa8908001eef";
    @Autowired
    private ShopManager shopManager;
    @Test
    public void getShopGroupTest() {

        Request request = new BaseRequest();
        request.setParam("appKey", appKey);
        request.setParam("distShopQTO",new DistShopQTO());
        request.setCommand(ActionEnum.GET_SHOP_GROUP.getActionName());
        Response<List<Map<String,Object>>> response = this.distributionService.execute(request);
        List<Map<String,Object>> data = response.getModule();
        log.info("item+{}",data.size());
        for(Map<String,Object> item :data){
            log.info("item:{}",item);
        }
        Assert.assertTrue(response.isSuccess());
    }
   @Test
    public void getItemListTest(){
        Request request = new BaseRequest();
        request.setParam("appKey",appKey);
        request.setParam("groupId","655");
        request.setParam("sellerId",1841254L);
        request.setParam("offset",0);
        request.setParam("count",20);
        request.setParam("itemName","宠物");
        //request.setParam("orderBy",3);
        //request.setParam("desc",1);
        request.setCommand(ActionEnum.QUERY_GOODS_ITEM.getActionName());
        Response<List<GoodsItemDTO>> response = this.distributionService.execute(request);
        List<GoodsItemDTO> data = response.getModule();
        log.info("item+{}",data.size());
        for(GoodsItemDTO item :data){
            log.info("item:{}",item);
        }
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testQueryShopGroup() throws DistributionException {
        shopManager.queryShopItemGroup(appKey);
    }
    @Test
    public void testQueryItemList() throws DistributionException {
        Long sellerId = 1L;
        Long groupId = 1L;
        int offset = 0;
        int count = 20;
        String itemName = "";
        //String  orderBy = "sale_volume";
        String  orderBy = "sale_commission";
        int desc = 0 ;
        shopManager.queryGoodsList(true,appKey,sellerId,groupId,offset,count,itemName,orderBy,desc);
    }

    @Autowired
    private ItemClient itemClient;
    @Test
    public void testQueryItem(){
        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        com.mockuai.itemcenter.common.api.Response<List<ItemSearchDTO>>
         response =  itemClient.searchItem(itemSearchQTO,appKey);
        log.info("response:{}",response);
    }
   @Test
    public void testGetAllItem() throws DistributionException {
       DistributionResponse response = shopManager.getAllItem(false,0,20,appKey);
       log.info(response.toString());
    }





}

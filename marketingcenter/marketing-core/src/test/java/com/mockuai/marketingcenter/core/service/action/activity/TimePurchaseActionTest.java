package com.mockuai.marketingcenter.core.service.action.activity;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseGoodsQTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseSKUGoodsQTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangsiqian on 2016/10/13.
 // */
//public class TimePurchaseActionTest extends BaseActionTest {
//    public TimePurchaseActionTest() {
//        super(TimePurchaseActionTest.class.getName());
//    }
//    @Override
//    protected String getCommand() {
//         return ActionEnum.TIME_PURCHASE_BY_ID.getActionName();
//    }
//    @Test
//    public void timePurchaseById(){
//        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
//        request.setParam("timePurchaseQTO", timePurchaseQTO);
//        timePurchaseQTO.setId(34L);
//        doExecute();
//
//    }

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TimePurchaseActionTest {
    @Autowired
    protected MarketingService marketingService;
    protected Request request;

    @Test
    public void testSelectById() {
        Request request = new BaseRequest();
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        request.setParam("timePurchaseQTO", timePurchaseQTO);
        timePurchaseQTO.setId(34L);


        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");

        request.setCommand(ActionEnum.TIME_PURCHASE_BY_ID.getActionName());
        Response response = marketingService.execute(request);
        System.out.println(response.getModule());

    }

    @Test
    public void testStartLimitedPurchase() {
        Request request = new BaseRequest();
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        request.setParam("timePurchaseQTO", timePurchaseQTO);
        timePurchaseQTO.setId(35L);


        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");

        request.setCommand(ActionEnum.START_LIMITED_PURCHASE_ACTION.getActionName());
        Response response = marketingService.execute(request);
        System.out.println(response.getModule());

    }
    @Test
    public void testStopLimitedPurchase() {
        Request request = new BaseRequest();
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        request.setParam("timePurchaseQTO", timePurchaseQTO);
        timePurchaseQTO.setId(38L);


        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");

        request.setCommand(ActionEnum.STOP_TIME_PURCHASE.getActionName());
        Response response = marketingService.execute(request);
        System.out.println(response.getModule());

    }
    @Test
    public void testLimitedPurchaseList() {
        Request request = new BaseRequest();
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        //timePurchaseQTO.setActivityName("测试");
        //timePurchaseQTO.setRunStatus(2L);
        timePurchaseQTO.setOffset(1);
        timePurchaseQTO.setCount(20);
        request.setParam("timePurchaseQTO", timePurchaseQTO);
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");

        request.setCommand(ActionEnum.TIME_PURCHASE_LIST.getActionName());
        Response response = marketingService.execute(request);
        System.out.println(response.getModule());

    }

    @Test
    public void testAddActivity() throws Exception {
        Request request = new BaseRequest();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = formatter.parse("2016-10-1 10:20:00");
        Date endTime = formatter.parse("2016-10-1 20:20:00");
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        timePurchaseQTO.setActivityName("双12活动");
        timePurchaseQTO.setActivityTag("角标地址");
        //timePurchaseQTO.setVoucherType(0l);
        timePurchaseQTO.setStartTime(startTime);
        timePurchaseQTO.setEndTime(endTime);
        List<TimePurchaseGoodsQTO> spuGoods= new ArrayList<>();
        TimePurchaseGoodsQTO spu = new TimePurchaseGoodsQTO();
        spu.setItemId(112L);
//        spu.setGoodsQuantity(60L);
        List<TimePurchaseSKUGoodsQTO> skuGoods = new ArrayList<>();

        TimePurchaseSKUGoodsQTO sku1 = new TimePurchaseSKUGoodsQTO();
        sku1.setSkuId(133L);
        sku1.setGoodsPrice(70L);
        TimePurchaseSKUGoodsQTO sku2 = new TimePurchaseSKUGoodsQTO();
        sku2.setSkuId(134L);
        sku2.setGoodsPrice(100L);
        skuGoods.add(sku1);
        skuGoods.add(sku2);
        spu.setSkuInfo(skuGoods);
        spuGoods.add(spu);
        timePurchaseQTO.setGoodsInfo(spuGoods);
        request.setParam("timePurchaseQTO", timePurchaseQTO);
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        request.setCommand(ActionEnum.ADD_TIME_PURCHASE.getActionName());
        Response response = marketingService.execute(request);
        System.out.println(response.getModule());
    }
    @Test
    public void testUpdateActivity() throws Exception {
        Request request = new BaseRequest();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date startTime = formatter.parse("2016-10-1 3:20:00");
        Date endTime = formatter.parse("2016-10-1 20:20:00");
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        timePurchaseQTO.setId(39L);
        timePurchaseQTO.setActivityName("双12活动修改");
        timePurchaseQTO.setActivityTag("角标地址");
        //timePurchaseQTO.setVoucherType(1l);
        timePurchaseQTO.setStartTime(startTime);
        timePurchaseQTO.setEndTime(endTime);
        List<TimePurchaseGoodsQTO> spuList= new ArrayList<>();
        //第一个item_id
        TimePurchaseGoodsQTO spu = new TimePurchaseGoodsQTO();
        spu.setItemId(113L);
//        spu.setGoodsQuantity(100L);
        List<TimePurchaseSKUGoodsQTO> skuGoods = new ArrayList<>();

        TimePurchaseSKUGoodsQTO sku1 = new TimePurchaseSKUGoodsQTO();
        sku1.setSkuId(133L);
        sku1.setGoodsPrice(100L);
        TimePurchaseSKUGoodsQTO sku2 = new TimePurchaseSKUGoodsQTO();
        sku2.setSkuId(134L);
        sku2.setGoodsPrice(120L);
        skuGoods.add(sku1);
        skuGoods.add(sku2);
        spu.setSkuInfo(skuGoods);
        spuList.add(spu);
        //第二个item_id
        TimePurchaseGoodsQTO spu2= new TimePurchaseGoodsQTO();
        spu2.setItemId(112L);
//        spu2.setGoodsQuantity(70L);
        List<TimePurchaseSKUGoodsQTO> skuGoods2 = new ArrayList<>();

        TimePurchaseSKUGoodsQTO sku3 = new TimePurchaseSKUGoodsQTO();
        sku3.setSkuId(133L);
        sku3.setGoodsPrice(200L);
        TimePurchaseSKUGoodsQTO sku4 = new TimePurchaseSKUGoodsQTO();
        sku4.setSkuId(134L);
        sku4.setGoodsPrice(300L);
        skuGoods2.add(sku3);
        skuGoods2.add(sku4);
        spu2.setSkuInfo(skuGoods2);

        spuList.add(spu2);

        spu.setSkuInfo(skuGoods);
        timePurchaseQTO.setGoodsInfo(spuList);
        request.setParam("timePurchaseQTO", timePurchaseQTO);
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        request.setCommand(ActionEnum.UPDATE_TIME_PURCHASE.getActionName());
        Response response = marketingService.execute(request);
        System.out.println(response.getModule());
    }


}
// {"module":
//         {"id":34,"activity_name":"测试活动3","start_time":"Oct 9, 2016 8:30:00 PM","end_time":"Oct 10, 2016 8:30:00 PM","activity_tag":"http://img.mockuai.com/images/201610/11/11/20161011111258262.jpg","voucher_type":0,"issue_status":0,"run_status":2,
//         "goods":[{"activity_id":34,"item_id":214,"goods_quantity":100,"map":{},
//            "list":[{"sku_id":209,"goods_price":25,"sku_code":"XXL","promotion_price":30}]},
//         {"activity_id":34,"item_id":371,"goods_quantity":100,"map":{},
//            "list":[{"sku_id":355,"goods_price":800,"sku_code":"XXL","promotion_price":30}]},
//         {"activity_id":34,"item_id":270,"goods_quantity":10,"map":{},
//             "list":[{"sku_id":251,"goods_price":50,"sku_code":"XXL","promotion_price":30}]},
//         {"activity_id":34,"item_id":33,"goods_quantity":10,"map":{},
//            "list":[{"sku_id":258,"goods_price":50,"sku_code":"XXL","promotion_price":30},
//                    {"sku_id":257,"goods_price":60,"sku_code":"XXL","promotion_price":30}]},
//         {"activity_id":34,"item_id":32,"goods_quantity":100,"map":{},
//                "list":[{"sku_id":255,"goods_price":60,"sku_code":"XXL","promotion_price":30},
//                         {"sku_id":253,"goods_price":60,"sku_code":"XXL","promotion_price":30}]},
//                        {"activity_id":34,"item_id":39,"goods_quantity":60,"map":{},
//                "list":[{"sku_id":253,"goods_price":90,"sku_code":"XXL","promotion_price":30}]}]},
//         "res_code":10000,"message":"success","total_count":1}

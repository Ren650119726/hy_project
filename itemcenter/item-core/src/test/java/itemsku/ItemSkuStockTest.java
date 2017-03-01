package itemsku;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.StockFrozenRecordStatusEnum;
import com.mockuai.itemcenter.common.domain.dto.OrderStockDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.StockFrozenRecordManager;
import com.mockuai.itemcenter.core.service.action.itemsku.CrushOrderSkuStockAction;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ItemSkuStockTest {

    @Resource
    private ItemService itemService;

    @Resource
    private StockFrozenRecordManager stockFrozenRecordManager;

    @Resource
    private CrushOrderSkuStockAction crushOrderSkuStockAction;

    private  String orderSn = "test-84e3444946845";

    @Test
    /**
     * 正常加库存
     */
    public void test001() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.FREEZE_ORDER_SKU_STOCK);

        OrderStockDTO orderStockDTO = new OrderStockDTO();
        orderStockDTO.setOrderSn(orderSn);
        orderStockDTO.setSellerId(1841254L);

        OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
        orderSku.setNumber(6);
        orderSku.setSkuId(18671L);
        orderSku.setDistributorId(19L);

        OrderStockDTO.OrderSku orderSku1 = new OrderStockDTO.OrderSku();
        orderSku1.setNumber(6);
        orderSku1.setSkuId(18646L);
        orderSku1.setDistributorId(19L);

        orderStockDTO.setOrderSkuList(Lists.newArrayList(orderSku1));
        request.setParam("orderStockDTO", orderStockDTO);
        Response<OrderStockDTO> response = itemService.execute(request);

        request.setCommand(ActionEnum.CRUSH_ORDER_SKU_STOCK.getActionName());

        request.setParam("orderSn",orderSn);
        Response response1 = itemService.execute(request);


//        request.setParam("orderStockDTO", response.getModule());
//        request.setCommand(ActionEnum.INCREASE_ORDER_SKU_STOCK_PARTIALLY.getActionName());
//
//        Response<OrderStockDTO> response2 = itemService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + JsonUtil.toJson(response.getModule()));
        System.out.println("Model:" + JsonUtil.toJson(response1.getModule()));
       // System.out.println("Model:" + JsonUtil.toJson(response2.getModule()));
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    /**
     * 正常加库存
     */
    public void test002() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.THAW_ORDER_SKU_STOCK);

        request.setParam("orderSn", orderSn);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    /**
     * 正常加库存
     */
    public void test003() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.CRUSH_ORDER_SKU_STOCK);

        request.setParam("orderSn", orderSn);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
    }

    @Test
    /**
     * 正常加库存
     */
    public void test004() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.THAW_ORDER_SKU_STOCK);

        request.setParam("orderSn", orderSn);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    /**
     * 正常加库存
     */
    public void test006() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.INCREASE_ORDER_SKU_STOCK_PARTIALLY);

        OrderStockDTO orderStockDTO = new OrderStockDTO();
        orderStockDTO.setOrderSn(orderSn);
        orderStockDTO.setSellerId(1841254L);

        OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
        orderSku.setNumber(2);
        orderSku.setSkuId(43270L);


        orderStockDTO.setOrderSkuList(Lists.newArrayList(orderSku));
        request.setParam("orderStockDTO", orderStockDTO);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }



    @Test
    /**
     * 正常加库存
     */
    public void test005() {

        try {
            stockFrozenRecordManager.updateStockFrozenRecordStatus("snrrrrnn3uun", 1841254L, StockFrozenRecordStatusEnum.THAWY.getStatus(),AppKeyEnum.HAIYUN.bizCode());
        } catch (ItemException e) {
            e.printStackTrace();
        }


        System.out.println("**************************************");
        System.out.println("**************************************");
    }


    @Test
    public void test0010() {

        try {
            crushOrderSkuStockAction.crush("2016062915262352801696291478","hanshu",AppKeyEnum.HAIYUN.getAppKey());
        } catch (ItemException e) {
            e.printStackTrace();
        }


        System.out.println("**************************************");
        System.out.println("**************************************");
    }


}

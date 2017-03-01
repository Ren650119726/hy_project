package test.order;

import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/25/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryYdxOrderTest {
    @Resource
    private ItemService itemServiceRemote;

    @Resource
    private TradeService tradeServiceRemote;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test(){
        OrderQTO orderQTO = new OrderQTO();
        orderQTO.setOrderStatus(null);
        orderQTO.setSellerId(91L);
        orderQTO.setOffset(0);
        orderQTO.setCount(1000);
        com.mockuai.tradecenter.common.api.Request tradeReq = new com.mockuai.tradecenter.common.api.BaseRequest();
        tradeReq.setCommand("queryUserOrder");
        tradeReq.setParam("orderQTO", orderQTO);
        com.mockuai.tradecenter.common.api.Response tradeResp = tradeServiceRemote.execute(tradeReq);

        List<Data> datas = new ArrayList<Data>();
        if(tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()){
            List<OrderDTO> orderDTOs = (List)tradeResp.getModule();

            for(OrderDTO orderDTO:orderDTOs){

                com.mockuai.tradecenter.common.api.Request getReq = new com.mockuai.tradecenter.common.api.BaseRequest();
                tradeReq.setCommand("getOrder");
                tradeReq.setParam("orderId", orderDTO.getId());
                tradeReq.setParam("userId", orderDTO.getUserId());

                //TODO refactor the response data's type of cancalOrder service to void
                com.mockuai.tradecenter.common.api.Response<OrderDTO> getResult = tradeServiceRemote.execute(getReq);

                if(getResult.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()){
                    for(OrderItemDTO orderItemDTO: orderDTO.getOrderItems()){
                        com.mockuai.itemcenter.common.api.Request itemReq = new com.mockuai.itemcenter.common.api.BaseRequest();
                        itemReq.setCommand(com.mockuai.itemcenter.common.constant.ActionEnum.GET_ITEM.getActionName());
                        itemReq.setParam("id", orderItemDTO.getItemId());
                        itemReq.setParam("supplierId", 91L);
                        itemReq.setParam("needDetail", true);
                        com.mockuai.itemcenter.common.api.Response<ItemDTO> itemResp = itemServiceRemote.execute(itemReq);

                        Data data = new Data();
                        data.setOrderSn(orderDTO.getOrderSn());
                        data.setOrderTime(dateFormat.format(orderDTO.getOrderTime()));
                        data.setTotalPrice(""+ orderDTO.getTotalPrice());
                        data.setProvince(orderDTO.getOrderConsigneeDTO().getProvince());
                        data.setCity(orderDTO.getOrderConsigneeDTO().getCity());
                        data.setAddress(orderDTO.getOrderConsigneeDTO().getAddress());
                        data.setItemName(orderItemDTO.getItemName());
                        data.setNumber("" + orderItemDTO.getNumber());
                        data.setUnitPrice("" + orderItemDTO.getUnitPrice());
                        if(itemResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()){
                            ItemDTO itemDTO = itemResp.getModule();
                            List<ItemPropertyDTO> itemPropertyDTOs = itemDTO.getItemPropertyList();
                            for(ItemPropertyDTO itemPropertyDTO: itemPropertyDTOs){
                                if("IC_APP_P_ITEM_000001".equals(itemPropertyDTO.getCode())){
                                    data.setBrand(itemPropertyDTO.getCode());
                                }
                            }

                            for(ItemSkuDTO itemSkuDTO: itemDTO.getItemSkuDTOList()){
                                data.setEan(itemSkuDTO.getBarCode());
                            }

                            data.setCategoryName(itemDTO.getCategoryName());
                        }
                        datas.add(data);
                    }
                }
            }
        }

        System.out.println(JsonUtil.toJson(datas));
    }

    public static class Data{
        private String orderSn;
        private String orderTime;
        private String ean;
        private String itemName;
        private String unitPrice;
        private String number;
        private String totalPrice;
        private String brand;
        private String categoryName;
        private String province;
        private String city;
        private String address;

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getEan() {
            return ean;
        }

        public void setEan(String ean) {
            this.ean = ean;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}

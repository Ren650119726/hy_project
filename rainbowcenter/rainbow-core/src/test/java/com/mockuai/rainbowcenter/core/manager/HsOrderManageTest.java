package com.mockuai.rainbowcenter.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.common.uils.StarterRunner;
import com.mockuai.rainbowcenter.UnitTestUtils;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.RainbowService;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.constant.SysFieldNameEnum;
import com.mockuai.rainbowcenter.common.dto.SingleSkuSnStockSyncDTO;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.service.action.erp.entity.*;
import com.mockuai.rainbowcenter.core.util.GyERPSignUtil;
import com.mockuai.rainbowcenter.core.util.GyERPUtils;
import com.mockuai.rainbowcenter.core.util.PropertyConfig;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lizg on 2016/6/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class HsOrderManageTest {

    private static final String  APP_KEY = "6562b5ddf0aed2aad8fe471ce2a2c8a0";


    private static final String APP_NAME = "rainbowcenter";
    private static final String LOCAL_PROPERTIES = "e:/haiyn/haiynParent/haiyn_properties/rainbowcenter/haiyn.properties";

    @Autowired
    private RainbowService rainbowService;


  static  {
      try {
          StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,new String[] {});
      }catch (Exception e) {
            e.printStackTrace();
      }

  }
    @Test
    public void deliveryOrderToEdb() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", "126225");
        jsonObject.put("sessionkey", "fb3e86be560c4faa9ec2310a35b105d7");
        jsonObject.put("method", "gy.erp.trade.add");
        jsonObject.put("order_type_code", "Sales");  //订单类型code
        jsonObject.put("refund", 0);   //非退款
        jsonObject.put("cod", false);
        //   jsonObject.put("order_settlement_code", ""); //结算方式code
        jsonObject.put("platform_code", "2016061214358");
        jsonObject.put("shop_code", "haiyun");//必须填 店铺id在erp后端配置，但必须和我们平台的数据库的店铺id对应起来
        jsonObject.put("express_code", "STO");//物流公司code 同上
        jsonObject.put("warehouse_code", "41");//仓库code 同上
        jsonObject.put("vip_code", "6000023");//必填 会员code 也就是用户id
        jsonObject.put("vip_name", "阿萨"); //会员名称
        jsonObject.put("receiver_name", "张小二");//收货人
        jsonObject.put("receiver_address", "浙江省杭州市西湖区五联西苑317号"); //收货地址
        jsonObject.put("receiver_zip", "310012");
        jsonObject.put("receiver_mobile", "15990103290");//必填收货人手机号
        jsonObject.put("receiver_phone", "");//收货人电话
        jsonObject.put("receiver_province", "浙江省"); //收货人省份
        jsonObject.put("receiver_city", "杭州市");    //收货人城市
        jsonObject.put("receiver_district", "西湖区"); //收货人区域
        jsonObject.put("tag_code", "");          //标记code
        jsonObject.put("deal_datetime", "2016-06-12 12:15:45");    //拍单时间 2015-03-17 15:03:45	必填
        jsonObject.put("pay_datetime", "2016-06-12 12:20:34");   //2015-03-17 15:03:45		付款时间
        jsonObject.put("business_man_code", ""); //业务员code
        jsonObject.put("post_fee", 0);    //物流费用 备注：double类型 0.0 默认0
        jsonObject.put("cod_fee", 0);    //到付服务费 备注：同上
        jsonObject.put("discount_fee", ""); //让利金额 备注：同上
        jsonObject.put("plan_delivery_date", "");  //date 标准时间格式：yyyy-MM-dd HH:mm:ss，例如：2010-11-11 11:11:11  预计发货日期
        jsonObject.put("buyer_memo", "");  //买家留言
        jsonObject.put("seller_memo", ""); //卖家备注
        jsonObject.put("seller_memo_late", ""); //二次备注
        jsonObject.put("vipIdCard", "");  //身份证号
        jsonObject.put("vipRealName", ""); //真实姓名
        jsonObject.put("vipEmail", "");    //电子邮箱

        List<ErpPayments> paymentsDOs = new ArrayList<ErpPayments>();   //支付信息数组

        ErpPayments erpPaymentsDO = new ErpPayments();
        erpPaymentsDO.setPay_type_code("zhifubao");
        erpPaymentsDO.setPaytime("1465615054");
        erpPaymentsDO.setPayment("120");
        erpPaymentsDO.setPay_code("");
        erpPaymentsDO.setAccount("");
        paymentsDOs.add(erpPaymentsDO);
        jsonObject.put("payments", paymentsDOs);

        List<ErpItemAttribute> items = new ArrayList<ErpItemAttribute>();
        ErpItemAttribute erpItemAttribute = new ErpItemAttribute();
        erpItemAttribute.setItem_code("BCW0013");
        erpItemAttribute.setNote("");
        erpItemAttribute.setSku_code("BCW0013");
        erpItemAttribute.setQty(2);
        erpItemAttribute.setPrice("60");
        erpItemAttribute.setOid("");
        erpItemAttribute.setRefund(0);
        items.add(erpItemAttribute);
        jsonObject.put("details", items);  //商品信息数组 不传
        //jsonObject.put("invoices","[]");//发票信息数组
        System.out.println("生成前：" + jsonObject.toString());
        String sign = GyERPSignUtil.sign(jsonObject.toString(), "4d97588127414cf6816994854c958a5d");
        System.out.println("sign{}" + sign);
        jsonObject.put("sign", sign);
        try {
            String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
            System.out.println("response{}" + response);
        } catch (RainbowException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test() {
        ErpOrder erpOrder = new ErpOrder();
        erpOrder.setAppkey("126225");
        erpOrder.setSessionkey("fb3e86be560c4faa9ec2310a35b105d7");
        erpOrder.setMethod("gy.erp.trade.add");
        erpOrder.setOrder_type_code("Sales");
        erpOrder.setRefund(0);
        erpOrder.setCod(false);
        erpOrder.setPlatform_code("201606121433");
        erpOrder.setShop_code("haiyun");
        erpOrder.setExpress_code("STO");
        erpOrder.setWarehouse_code("41");
        erpOrder.setVip_code("6000023");
        erpOrder.setVip_name("一级");
        erpOrder.setReceiver_name("李四");
        erpOrder.setReceiver_address("浙江省杭州市西湖区紫霞街80号");
        erpOrder.setReceiver_zip("310012");
        erpOrder.setReceiver_mobile("15990103291");
        erpOrder.setReceiver_phone("");
        erpOrder.setReceiver_province("浙江省");
        erpOrder.setReceiver_city("杭州市");
        erpOrder.setReceiver_district("西湖区");
        erpOrder.setTag_code("");
        erpOrder.setDeal_datetime("2016-06-13 12:15:45");
        erpOrder.setPay_datetime("2016-06-13 12:20:34");
        erpOrder.setBusiness_man_code("");
        erpOrder.setPost_fee("");
        erpOrder.setCod_fee("");
        erpOrder.setDiscount_fee("");
        erpOrder.setPlan_delivery_date(null);
        erpOrder.setBuyer_memo("");
        erpOrder.setSeller_memo("");
        erpOrder.setSeller_memo_late("");
        erpOrder.setVipIdCard("");
        erpOrder.setVipRealName("");
        erpOrder.setVipEmail("");
        List<ErpPayments> paymentsDOs = new ArrayList<ErpPayments>();   //支付信息数组
        ErpPayments erpPaymentsDO = new ErpPayments();
        erpPaymentsDO.setPay_type_code("zhifubao");
        erpPaymentsDO.setPaytime("1465615054");
        erpPaymentsDO.setPayment("120");
        erpPaymentsDO.setPay_code("");
        erpPaymentsDO.setAccount("");
        paymentsDOs.add(erpPaymentsDO);

        erpOrder.setPayments(paymentsDOs);
        List<ErpItemAttribute> items = new ArrayList<ErpItemAttribute>();
        ErpItemAttribute erpItemAttribute = new ErpItemAttribute();
        erpItemAttribute.setItem_code("BCW0013");
        erpItemAttribute.setNote("");
        erpItemAttribute.setSku_code("BCW0013");
        erpItemAttribute.setQty(2);
        erpItemAttribute.setPrice("60");
        erpItemAttribute.setOid("");
        erpItemAttribute.setRefund(0);
        items.add(erpItemAttribute);
        erpOrder.setDetails(items);

        String inParam = JsonUtil.toJson(erpOrder);
        System.out.println("inParam{}" + inParam);
        String sign = GyERPSignUtil.sign(inParam, "4d97588127414cf6816994854c958a5d");
        System.out.println("sign{}" + sign);
        erpOrder.setSign(sign);
        String signEndParam = JsonUtil.toJson(erpOrder);
        System.out.println("signEndParam{}" + signEndParam);
        try {
            String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), signEndParam);
            System.out.println("response{}" + response);
        } catch (RainbowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOrder() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("appkey", PropertyConfig.getGyErpAppkey()); //ERP租户appkey
        jsonObj.put("sessionkey", PropertyConfig.getGyErpSessionkey());//管易ERP租户sessionkey
        jsonObj.put("method", "gy.erp.trade.get");
        jsonObj.put("platform_code", "201606121433");

        String sign = GyERPSignUtil.sign(jsonObj.toString(), PropertyConfig.getGyerpSecret());
        jsonObj.put("sign", sign);
        try {
            String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObj.toString());
            System.out.println("response{}" + response);
        } catch (RainbowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDeliverys() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.deliverys.get");
        jsonObject.put("page_no", 2);
        jsonObject.put("page_size",100);
        jsonObject.put("start_delivery_date", "2016-06-19 13:45:10");
        jsonObject.put("end_delivery_date", "2016-06-22 13:45:10");
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        System.out.print("sign:{}" + sign);
        jsonObject.put("sign", sign);
        try {
            String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
            System.out.print("[{}] response{}" + response);
        } catch (RainbowException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addRrturn() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.return.add");
        jsonObject.put("type_code", "3");  // 是 退货类型代码 需要和数据库对应起来
        jsonObject.put("shop_code", SysFieldNameEnum.SHOP_ID.getValue());  //是   店铺代码
        jsonObject.put("vip_code", "1841975");   //是   会员代码
        jsonObject.put("note", "商品不符合买的要求");       //退货单备注
        jsonObject.put("trade_platform_code", "56690"); //关联的销售订单的外部单号

        //退入商品的列表
        List<ErpReturnItemDetail> returnItemDetailDOs = new ArrayList<ErpReturnItemDetail>();
        ErpReturnItemDetail erpReturnItemDetail = new ErpReturnItemDetail();
       // erpReturnItemDetail.setSku_code("BCW0013");
        erpReturnItemDetail.setItem_code("BCW0013");
        erpReturnItemDetail.setQty(1);
        erpReturnItemDetail.setOriginPrice(0.01);
        erpReturnItemDetail.setPrice(0.01);

        returnItemDetailDOs.add(erpReturnItemDetail);
        jsonObject.put("item_detail", returnItemDetailDOs);

        System.out.print("gy.erp.trade.return sign ago param {}" + jsonObject.toString());

        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);

        System.out.print("gy.erp.trade.return sign end param {}" + jsonObject.toString());

        String response = null;
        try {
            response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
            System.out.print("gy.erp.trade.return response {}" + response);
        } catch (RainbowException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addRefund() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.refund.add");
      //  jsonObject.put("refund_code","56687"); //退款单单号
        jsonObject.put("refund_type", 1); //0-仅退款 1-退货退款 退款单种类
        jsonObject.put("refund_reason","买错"); //退款原因
        jsonObject.put("trade_code", "56691"); //关联订单单号
        jsonObject.put("shop_code", SysFieldNameEnum.SHOP_ID.getValue());  //是 店铺代码 统一传我们的店铺嗨云商城
        jsonObject.put("vip_code","1841975"); //是	会员代码
        jsonObject.put("type_code", ""); //单据类型代码
        jsonObject.put("payment_type_code","zhifubao"); //退款支付方式代码
        jsonObject.put("amount", "0.01"); // 是 退款金额
        jsonObject.put("note", "");   //备注
        //  jsonObject.put("item_detail", "[]");

        List<ErpRefundItemDetail> refundItemDetails = new ArrayList<ErpRefundItemDetail>();
        ErpRefundItemDetail erpRefundItemDetail = new ErpRefundItemDetail();
        erpRefundItemDetail.setBarcode("");
        erpRefundItemDetail.setPrice(0.01);
        erpRefundItemDetail.setQty(1);
        refundItemDetails.add(erpRefundItemDetail);
        jsonObject.put("item_detail", refundItemDetails);
        System.out.print("gy.erp.trade.refund.add sign ago param {}:"+jsonObject.toString());

        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        System.out.print("gy.erp.trade.refund.add sign {}:"+sign);
        jsonObject.put("sign", sign);

        System.out.print("gy.erp.trade.refund.add sign end param {}:"+jsonObject.toString());

        String response = null;
        try {
            response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
            System.out.print("gy.erp.trade.refund response {}" + response);
        } catch (RainbowException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getGyErpstock() throws Exception{

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.stock.get");
        jsonObject.put("item_code", "AABBDD");
        jsonObject.put("warehouse_code", "26");
        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);
        String response = null;
        try {
            response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
            System.out.print("gy.erp.stock.get response {}" + response);

            JSONObject fastJsonObj = JSONObject.parseObject(response);
            String result = fastJsonObj.getString("success");
            if (result.equals("true")) {
                String stocks = fastJsonObj.getString("stocks");
                JSONArray jsonArray = new JSONArray(stocks);
                for (int i = 0; i < jsonArray.length(); i++) {
                    org.json.JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String del = jsonObj.get("del").toString();
                    if ("true".equals(del)) {
                        continue;
                    }

                    String salable_qty = jsonObj.get("salable_qty").toString();
                    System.out.print("salable_qty:{}" + salable_qty);
                }
            }
        } catch (RainbowException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getNewGyErpstock() throws Exception{

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.new.stock.get");
        jsonObject.put("page_no", "31");
        jsonObject.put("page_size", "100");
        jsonObject.put("start_date", "2015-01-01 00:00:00");
        jsonObject.put("end_date", "2016-10-21 15:35:20");
        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);
        String response = null;
        try {
            response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
            System.out.print("gy.erp.new.stock.get{}" + response);

        } catch (RainbowException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testDuibaLogin() throws Exception {

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("uid","1");
        baseRequest.setParam("credits","500");
        baseRequest.setCommand(ActionEnum.DUIBA_CREDIT_AUTO_LOGIN.getActionName());
        UnitTestUtils.assertAndPrint(rainbowService.execute(baseRequest));
    }

    @Test
    public void testStockSyncItemSku() throws Exception{
        SingleSkuSnStockSyncDTO singleSkuSnStockSyncDTO = new SingleSkuSnStockSyncDTO();
        singleSkuSnStockSyncDTO.setItemSkuId(22007L);
        singleSkuSnStockSyncDTO.setItemSkuSn("AABBDD");
        singleSkuSnStockSyncDTO.setStoreId(26L);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("singleSkuSnStockSyncDTO",singleSkuSnStockSyncDTO);
        baseRequest.setParam("appKey",APP_KEY);
        baseRequest.setCommand(ActionEnum.SINGLE_SKU_SN_STOCK_SYNC.getActionName());
        UnitTestUtils.assertAndPrint(rainbowService.execute(baseRequest));

    }


    @Test
    public void testgetVersionDeploy() throws Exception{
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey",APP_KEY);
        baseRequest.setCommand(ActionEnum.GET_VERSION_DEPLOY.getActionName());
        UnitTestUtils.assertAndPrint(rainbowService.execute(baseRequest));

    }


}

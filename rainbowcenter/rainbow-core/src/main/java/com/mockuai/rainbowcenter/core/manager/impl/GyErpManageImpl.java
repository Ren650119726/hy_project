package com.mockuai.rainbowcenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.constant.SysFieldNameEnum;
import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.common.dto.ErpStockDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.AppManager;
import com.mockuai.rainbowcenter.core.manager.CommonManage;
import com.mockuai.rainbowcenter.core.manager.ErpOrderManager;
import com.mockuai.rainbowcenter.core.manager.GyErpManage;
import com.mockuai.rainbowcenter.core.service.action.erp.entity.ErpReturnItemDetail;
import com.mockuai.rainbowcenter.core.util.GyERPSignUtil;
import com.mockuai.rainbowcenter.core.util.GyERPUtils;
import com.mockuai.rainbowcenter.core.util.PropertyConfig;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizg on 2016/6/4.
 */
@Service
public class GyErpManageImpl implements GyErpManage {

    private static final Logger logger = LoggerFactory.getLogger(GyErpManageImpl.class);

    @Resource
    private OrderClient orderClient;

    @Resource
    private AppManager appManager;

    @Resource
    private ErpOrderManager erpOrderManager;

    @Resource
    private StoreItemSkuClient storeItemSkuClient;

    @Resource
    private CommonManage commonManage;

    @Override
    public boolean getOrder(String orderSn) throws RainbowException {

        //拼接入参
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("appkey", PropertyConfig.getGyErpAppkey()); //ERP租户appkey
        jsonObj.put("sessionkey", PropertyConfig.getGyErpSessionkey());//管易ERP租户sessionkey
        jsonObj.put("method", "gy.erp.trade.get");
        jsonObj.put("order_state", 2);  //已审核
        jsonObj.put("platform_code", orderSn);
        String inParam = jsonObj.toString();
        logger.info("[{}] gy.erp.trade.get cerate ago {}", inParam);

        //对管易erp订单查询接口的入参进行签名
        String sign = GyERPSignUtil.sign(inParam, PropertyConfig.getGyerpSecret());
        jsonObj.put("sign", sign);
        logger.info("[{}] gy.erp.trade.get cerate last {}", jsonObj.toString());

        //调管易订单查询接口
        String getOrderResponse = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObj.toString());

        if (null == getOrderResponse) {
            throw new RainbowException(ResponseCode.GYERP_E_SERVICE_EXCEPTION);
        }

        JSONObject objResponse = JSONObject.parseObject(getOrderResponse);
        String result = objResponse.getString("success");
        boolean approve = false;  //是否已审核 true已审核
        if (result.equals("true")) {
            String orders = objResponse.getString("orders");
            logger.info("[{}] orders:{]", orders);
            if (!orders.equals("[]")) {
                approve = true;
            }
        }
        return approve;

    }

    @Override
    public String addRefund(OrderDTO orderDTO) throws RainbowException {
        String appKey = appManager.getAppKeyByBizCode(orderDTO.getBizCode());
        Long userId = orderDTO.getUserId();
        Long refundAmount = orderDTO.getPayAmount();
        logger.info("[{}] refund amount :{}" + refundAmount);

        //获取订单信息
        Response<OrderDTO> orderDTOResponse = this.orderClient.getOrder(orderDTO.getId(), userId, appKey);
        logger.info("get order response: {}", JSON.toJSONString(orderDTOResponse));
        if (orderDTOResponse.isSuccess()) {
            orderDTO = orderDTOResponse.getModule();
        } else {
            logger.error("get order error, order id: {},user id: {}", orderDTO.getId(), orderDTO.getUserId());
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, orderDTOResponse.getMessage());
        }
        //获取支付方式
        String paymentTypeCode = this.commonManage.paymentType(orderDTO);
        logger.info("[{}] payment type code:{}", paymentTypeCode);

        //获取erp单据编号
        logger.info("[{}] order id :{}", Long.toString(orderDTO.getId()));
        ErpOrderDTO erpOrderDTO = erpOrderManager.getGyerpCode(Long.toString(orderDTO.getId()));
        if (null == erpOrderDTO) {
            logger.error("erpCode is null ................");
        }
        String erpCode = erpOrderDTO.getGyerpCode();
        //拼接入参
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.refund.add");
        //   jsonObject.put("refund_code",orderId); //退款单单号指的是我们平台订单号
        jsonObject.put("refund_type", 0); //0-仅退款 1-退货退款 退款单种类
        jsonObject.put("refund_reason", orderDTO.getCancelReason()); //退款原因
        jsonObject.put("trade_code", erpCode); //关联订单单号指的是erp单据编号
        jsonObject.put("shop_code", SysFieldNameEnum.SHOP_ID.getValue());  //是 店铺代码 统一传我们的店铺嗨云商城
        jsonObject.put("vip_code", userId); //是	会员代码
        jsonObject.put("payment_type_code", paymentTypeCode); //退款支付方式代码
        jsonObject.put("amount", refundAmount / 100D); // 是 退款金额
        jsonObject.put("note", "");   //备注
        //  jsonObject.put("item_detail", "[]");
        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);
        String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
        logger.info("gy.erp.trade.refund response {}" + response);
        if (null == response) {
            throw new RainbowException(ResponseCode.GYERP_E_SERVICE_EXCEPTION);
        }
        JSONObject fastJsonObject = JSONObject.parseObject(response);
        String result = fastJsonObject.getString("success");
        logger.info("gy.erp.trade.refund result {}", result);
        String code = "";
        if (result.equals("true")) {
            code = fastJsonObject.getString("code");
        }
        return code;
    }

    @Override
    public String addReturn(RefundOrderItemDTO refundOrderItemDTO, ItemSkuDTO itemSkuDTO, Long storeId, String appKey) throws RainbowException {

        StoreItemSkuDTO storeItemSkuDTO;

        //根据仓库编号、itemSkuId查询skuSn
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setStoreId(storeId);
        storeItemSkuQTO.setItemSkuId(refundOrderItemDTO.getItemSkuId());
        com.mockuai.suppliercenter.common.api.Response<StoreItemSkuDTO> itemSkuDTOResponse = storeItemSkuClient.getItemSku(storeItemSkuQTO, appKey);
        if (itemSkuDTOResponse.isSuccess()) {
            storeItemSkuDTO = itemSkuDTOResponse.getModule();
        } else {
            logger.error("get supplier item sku sn error, store id: {},itemSku id: {}", storeId, refundOrderItemDTO.getItemSkuId());
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, itemSkuDTOResponse.getMessage());
        }
        logger.info("[{}] supplier item sku sn:{}", storeItemSkuDTO.getSupplierItmeSkuSn());
        //拼接入参
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.return.add");
        jsonObject.put("type_code", String.valueOf(refundOrderItemDTO.getRefundReasonId()));  // 是 退货类型代码 需要和数据库对应起来
        jsonObject.put("shop_code", SysFieldNameEnum.SHOP_ID.getValue());  //是   店铺代码
        jsonObject.put("vip_code", refundOrderItemDTO.getUserId());   //是   会员代码
        jsonObject.put("trade_platform_code", refundOrderItemDTO.getOrderSn()); //关联的销售订单的外部单号

        //退入商品的列表
        List<ErpReturnItemDetail> returnItemDetailDOs = new ArrayList<ErpReturnItemDetail>();
        ErpReturnItemDetail erpReturnItemDetail = new ErpReturnItemDetail();
        erpReturnItemDetail.setItem_code(storeItemSkuDTO.getSupplierItmeSkuSn());
        erpReturnItemDetail.setQty(refundOrderItemDTO.getNumber());
        erpReturnItemDetail.setOriginPrice(itemSkuDTO.getMarketPrice() / 100D);
        erpReturnItemDetail.setPrice(itemSkuDTO.getPromotionPrice() / 100D);

        returnItemDetailDOs.add(erpReturnItemDetail);
        jsonObject.put("item_detail", returnItemDetailDOs);
        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);
        String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
        logger.info("gy.erp.trade.return response {}", response);

        if (null == response) {
            throw new RainbowException(ResponseCode.GYERP_E_SERVICE_EXCEPTION);
        }

        JSONObject fastJsonObj = JSONObject.parseObject(response);
        String result = fastJsonObj.getString("success");
        logger.info("gy.erp.trade.return result {}", result);
        String code = "";
        if (result.equals("true")) {
            code = fastJsonObj.getString("code");
        }
        return code;

    }

    @Override
    public Integer getDeliverys(String orderSn) throws RainbowException {

        //拼接入参
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.deliverys.get");
        jsonObject.put("outer_code", orderSn);

        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);

        String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
        logger.info("gy.erp.trade.deliverys.get response: {}", response);
        if (null == response) {
            throw new RainbowException(ResponseCode.GYERP_E_SERVICE_EXCEPTION);
        }

        JSONObject fastJsonObj = JSONObject.parseObject(response);
        String result = fastJsonObj.getString("success");
        logger.info("[{}] gy.erp.trade.deliverys.get result: {}", result);

        Integer deliveryStatus = null;
        if (result.equals("true")) {
            String deliverys = fastJsonObj.getString("deliverys");
            JSONArray jsonArray = new JSONArray(deliverys);
            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject jsonObj = jsonArray.getJSONObject(i);
                String deliveryStatusInfo = jsonObj.get("delivery_statusInfo").toString();
                JSONObject deliveryStatusJson = JSONObject.parseObject(deliveryStatusInfo);
                deliveryStatus = deliveryStatusJson.getInteger("delivery");
            }
        }
        return deliveryStatus;
    }

    @Override
    public boolean getTradeReturn(String orderSn) throws RainbowException {
        //拼接入参
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.return.get");
        jsonObject.put("platform_code", orderSn);
        jsonObject.put("delivery", 2);  //已发货
        jsonObject.put("receive", 0);  //0-未入，1-已入

        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);
        String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
        logger.info("gy.erp.trade.return.get response:{}", response);
        if (null == response) {
            throw new RainbowException(ResponseCode.GYERP_E_SERVICE_EXCEPTION);
        }
        JSONObject objResponse = JSONObject.parseObject(response);
        String result = objResponse.getString("success");
        boolean approve = false;
        if (result.equals("true")) {
            String tradeReturns = objResponse.getString("tradeReturns");
            logger.info("[{}] tradeReturns:{]", tradeReturns);

            if (tradeReturns.equals("[]")) {
                approve = true;
            }

        }
        return approve;
    }

    @Override
    public ErpStockDTO getStock(String skuItemSn, Long storeId) throws RainbowException {

        //TODO 调管易库存查询接口 gy.erp.stock.get 当管易那边新的库存查询接口改造好了 需要换成gy.erp.new.stock.get
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", PropertyConfig.getGyErpStockMenthod());
        jsonObject.put("item_code", skuItemSn);
        jsonObject.put("warehouse_code", storeId);

        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        jsonObject.put("sign", sign);

        String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
        logger.info("gy.erp.stock.get response: {}", response);

        if (null == response) {
            throw new RainbowException(ResponseCode.GYERP_E_SERVICE_EXCEPTION);
        }

        JSONObject fastJsonObj = JSONObject.parseObject(response);
        String result = fastJsonObj.getString("success");
        logger.info("[{}] gy.erp.stock.get result: {}", result);
        ErpStockDTO erpStockDTO = null;
        if (result.equals("true")) {
            String stocks = fastJsonObj.getString("stocks");
            JSONArray jsonArray = new JSONArray(stocks);
            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject jsonObj = jsonArray.getJSONObject(i);
                String del = jsonObj.get("del").toString(); ////true-停用；false-未停用
                logger.info("[{}] del:{}",del);
                if ("true".equals(del)) {
                  continue;
                }

                erpStockDTO = new ErpStockDTO();
                String salableQty = jsonObj.optString("salable_qty");
                logger.info("[{}] salableQty:{}",salableQty);
                String itemCode = jsonObj.optString("item_code");
                String warehouseCode = jsonObj.optString("warehouseCode");
                String qty = jsonObj.optString("qty");
                erpStockDTO.setItemCode(itemCode);
                erpStockDTO.setWarehouseId(warehouseCode);
                erpStockDTO.setSalableQty(salableQty);
                erpStockDTO.setQty(qty);
            }
        }

        return erpStockDTO;
    }


}

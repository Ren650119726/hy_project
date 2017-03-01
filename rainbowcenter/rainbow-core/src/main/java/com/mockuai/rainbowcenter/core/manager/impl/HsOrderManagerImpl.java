package com.mockuai.rainbowcenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.rainbowcenter.common.constant.ExpressEnum;
import com.mockuai.rainbowcenter.common.constant.ParamEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.constant.SysFieldNameEnum;
import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.dao.DefaultExpressDAO;
import com.mockuai.rainbowcenter.core.domain.DefaultExpressDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.*;
import com.mockuai.rainbowcenter.core.service.action.erp.entity.*;
import com.mockuai.rainbowcenter.core.util.GyERPSignUtil;
import com.mockuai.rainbowcenter.core.util.GyERPUtils;
import com.mockuai.rainbowcenter.core.util.PropertyConfig;
import com.mockuai.suppliercenter.client.StockItemSkuClient;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.client.RefundClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.virtualwealthcenter.client.UserAuthonClient;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lizg on 2016/6/2.
 */
@Service
public class HsOrderManagerImpl implements HsOrderManager {

    private static final Logger log = LoggerFactory.getLogger(HsOrderManagerImpl.class);

    @Resource
    private GyErpManage gyErpManage;

    @Resource
    private OrderClient orderClient;

    @Resource
    private AppManager appManager;

    @Resource
    private ItemSkuClient itemSkuClient;

    @Resource
    private ErpOrderManager erpOrderManager;

    @Resource
    private UserClient userClient;

    @Resource
    private StoreItemSkuClient storeItemSkuClient;

    @Resource
    private RefundClient refundClient;

    @Resource
    private UserAuthonClient userAuthonClient;

    @Resource
    private DefaultExpressDAO defaultExpressDAO;

    @Resource
    private CommonManage commonManage;

    @Resource
    private StockItemSkuClient stockItemSkuClient;


    @Override
    public OrderDTO getOrder(Long orderId, Long userId, String appKey) throws RainbowException {
        Response<OrderDTO> response = this.orderClient.getOrder(orderId, userId, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("order does not exist, order id: {}, user id: {}", orderId, userId);
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, "order does not exist");
        }
    }

    /**
     * 支付成功的订单推送给管易ERP业务
     *
     * @param orderDTO
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     * @author lizg
     */
    @Override
    public boolean deliveryOrderToGyerp(OrderDTO orderDTO) throws RainbowException {
        log.info("orderDTO: {}", JSON.toJSONString(orderDTO));
        Long orderId = orderDTO.getId();
        String appKey = appManager.getAppKeyByBizCode(orderDTO.getBizCode());
        Long userId = orderDTO.getUserId();
        Date payTime = orderDTO.getPayTime();

        //获取订单信息
        Response<OrderDTO> orderDTOResponse = this.orderClient.getOrder(orderDTO.getId(), userId, appKey);
        //  log.info("get order response: {}", JSON.toJSONString(orderDTOResponse));

        if (orderDTOResponse.isSuccess()) {
            orderDTO = orderDTOResponse.getModule();
        } else {
            log.error("get order error, order id: {},user id: {}", orderDTO.getId(), orderDTO.getUserId());
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, orderDTOResponse.getMessage());
        }

        //订单收货地址信息
        OrderConsigneeDTO orderConsigneeDTO = orderDTO.getOrderConsigneeDTO();

        //订单支付信息
        OrderPaymentDTO orderPaymentDTO = orderDTO.getOrderPaymentDTO();
        if (null == payTime) {
            payTime = orderDTO.getPayTime();
            log.info("[{}] pay time :{}", payTime);
            if (null == payTime) {
                return false;
            }
        }

        //根据id获取用户
        com.mockuai.usercenter.common.dto.UserDTO userDTO;
        com.mockuai.usercenter.common.api.Response<com.mockuai.usercenter.common.dto.UserDTO> user = this.userClient.getUserById(userId, appKey);
        if (user.isSuccess()) {
            userDTO = user.getModule();
        } else {
            log.error("get user error,user id: {}", orderDTO.getUserId());
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, orderDTOResponse.getMessage());
        }
        String nickName = null;
        if (null != userDTO) {
            nickName = userDTO.getNickName();
        }

        //根据userid获取用户的真实姓名和身份证
        MopUserAuthonAppDTO mopUserAuthonAppDTO = null;
        com.mockuai.virtualwealthcenter.common.api.Response<MopUserAuthonAppDTO> userAuthonResponse = userAuthonClient.findWithdrawalsItem(userId, appKey);
        if (userAuthonResponse.isSuccess()) {
            mopUserAuthonAppDTO = userAuthonResponse.getModule();
        } else {
            log.error("get user error,user id: {}", orderDTO.getUserId());
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, userAuthonResponse.getMessage());
        }
        //TODO    我们这边的地区名称信息需要和管易得对应起来
        StringBuffer sb = new StringBuffer();
        String area, address;
        if (orderConsigneeDTO.getAreaCode().equals("888880") || orderConsigneeDTO.getAreaCode().equals("888881")) {
            area = null;
        } else {
            area = orderConsigneeDTO.getArea();
        }
        if (orderConsigneeDTO.getAddress().startsWith(orderConsigneeDTO.getProvince())) {
            address = orderConsigneeDTO.getAddress();
        } else {
            sb.append(orderConsigneeDTO.getProvince());
            sb.append(orderConsigneeDTO.getCity());
            if (null != area) {
                sb.append(area);
            }
            sb.append(orderConsigneeDTO.getAddress());
            address = sb.toString();
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //获取默认快递YTO  圆通速递 ZTO中通
        DefaultExpressDO defaultExpressDO = defaultExpressDAO.getDefaultExpress();
        String expressCode = "YTO";
        if (null != defaultExpressDO) {
            expressCode = defaultExpressDO.getExpressNo();
        }
        //拼接入参
        ErpOrder erpOrder = new ErpOrder();
        erpOrder.setAppkey(PropertyConfig.getGyErpAppkey());
        erpOrder.setSessionkey(PropertyConfig.getGyErpSessionkey());
        erpOrder.setMethod("gy.erp.trade.add");
        erpOrder.setOrder_type_code("Sales");  //默认写死销售订单，erp后台自动识别
        erpOrder.setRefund(0);
        erpOrder.setCod(false);
        erpOrder.setPlatform_code(String.valueOf(orderDTO.getOrderSn()));
        erpOrder.setShop_code(String.valueOf(SysFieldNameEnum.SHOP_ID.getValue()));  //店铺id在erp后端配置，但必须和我们平台的数据库的店铺id对应起来
        erpOrder.setExpress_code(expressCode);
        erpOrder.setWarehouse_code(String.valueOf(orderDTO.getStoreId()));
        erpOrder.setVip_code(String.valueOf(userId));
        erpOrder.setVip_name(nickName);
        erpOrder.setReceiver_name(orderConsigneeDTO.getConsignee());
        erpOrder.setReceiver_address(address);
        erpOrder.setReceiver_zip(orderConsigneeDTO.getZip());
        erpOrder.setReceiver_mobile(orderConsigneeDTO.getMobile());
        erpOrder.setReceiver_phone(orderConsigneeDTO.getPhone());
        erpOrder.setReceiver_province(orderConsigneeDTO.getProvince());
        erpOrder.setReceiver_city(orderConsigneeDTO.getCity());
        erpOrder.setReceiver_district(area);
        erpOrder.setDeal_datetime(df.format(orderDTO.getOrderTime()));
        erpOrder.setPay_datetime(df.format(payTime));
        erpOrder.setPost_fee(String.valueOf(orderDTO.getDeliveryFee() / 100D));
        erpOrder.setDiscount_fee(String.valueOf(orderDTO.getDiscountAmount() / 100D));
        erpOrder.setBuyer_memo(orderDTO.getUserMemo());
        erpOrder.setSeller_memo(orderDTO.getSellerMemo());
        if (null != mopUserAuthonAppDTO) {
            erpOrder.setVipRealName(mopUserAuthonAppDTO.getAuthonRealname());
            erpOrder.setVipIdCard(mopUserAuthonAppDTO.getAuthonPersonalid());
        }

        List<ErpPayments> paymentsDOs = new ArrayList<ErpPayments>();   //支付信息数组

        ErpPayments erpPaymentsDO = new ErpPayments();

        //获取支付方式
        String payTypeCode = this.commonManage.paymentType(orderDTO);
        //  log.info("[{}] pay type code:{}", payTypeCode);

        erpPaymentsDO.setPay_type_code(payTypeCode);
        erpPaymentsDO.setPaytime(String.valueOf(payTime.getTime()));
        erpPaymentsDO.setPayment(String.valueOf(orderPaymentDTO.getPayAmount() / 100D));
        erpPaymentsDO.setPay_code(orderPaymentDTO.getPaymentSn());  //支付交易号
        paymentsDOs.add(erpPaymentsDO);
        erpOrder.setPayments(paymentsDOs);
        List<ErpItemAttribute> items = new ArrayList<ErpItemAttribute>(); //商品信息数组

        Integer parentMark = orderDTO.getParentMark();  //父订单标志，拆单场景，父订单会打上该标志。0代表正常订单或者子订单，1代表父订单
        log.info("[{}] parent mark:{}", parentMark);

        List<OrderItemDTO> orderItemDTOs = null;
        if (parentMark == 0) {
            orderItemDTOs = orderDTO.getOrderItems();
        }
//        else if (parentMark == 1) {
//            OrderQTO orderQTO = new OrderQTO();
//            orderQTO.setOriginalOrder(orderId);
//            Response<List<OrderDTO>> queryOrder = this.orderClient.queryOrder(orderQTO, appKey);
//            if (!(queryOrder.isSuccess() && queryOrder.getModule().size() == 1)) {
//                throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR);
//            }
//            OrderDTO orderDTO2 = queryOrder.getModule().get(0);
//            log.info("orderDTO2: {}", JSON.toJSONString(orderDTO2));
//            distributorOrderItemList = orderDTO2.getDistributorOrderItemList();
//        }
        log.info("orderItemDTOs: {}", JSON.toJSONString(orderItemDTOs));

        if (null == orderItemDTOs) {
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR);
        }

        List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();  //实扣订单信息List集合

        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            StoreItemSkuDTO storeItemSkuDTO;

            //根据仓库编号、itemSkuId查询skuSn
            StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
            storeItemSkuQTO.setStoreId(orderDTO.getStoreId());
            storeItemSkuQTO.setItemSkuId(orderItemDTO.getItemSkuId());
            com.mockuai.suppliercenter.common.api.Response<StoreItemSkuDTO> itemSkuDTOResponse = this.storeItemSkuClient.getItemSku(storeItemSkuQTO, appKey);
            //  log.info("get store item sku response: {}", JSON.toJSONString(itemSkuDTOResponse));
            if (itemSkuDTOResponse.isSuccess()) {
                storeItemSkuDTO = itemSkuDTOResponse.getModule();
            } else {
                log.error("get supplier item sku sn error, store id: {},itemSku id: {}", orderDTO.getStoreId(), orderItemDTO.getItemSkuId());
                throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, orderDTOResponse.getMessage());
            }
            String supplierItemSkuSn = storeItemSkuDTO.getSupplierItmeSkuSn();
            log.info("[{}] supplier item sku sn:{}", supplierItemSkuSn);
            if (null != supplierItemSkuSn && supplierItemSkuSn.startsWith("msbm")) {
                supplierItemSkuSn = supplierItemSkuSn.substring(4);
                log.info("[{}] supplier item sku sn:{2}", supplierItemSkuSn);
            }
            ErpItemAttribute erpItemAttribute = new ErpItemAttribute();
            erpItemAttribute.setItem_code(supplierItemSkuSn);
            erpItemAttribute.setPrice(String.valueOf(orderItemDTO.getUnitPrice() / 100D));
            erpItemAttribute.setQty(orderItemDTO.getNumber());
            erpItemAttribute.setRefund(0);  //是否退款 0否
            erpItemAttribute.setOid(Long.toString(orderItemDTO.getItemId())); //子订单id
            items.add(erpItemAttribute);

            OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
            orderSku.setStoreId(orderDTO.getStoreId());
            orderSku.setSupplierId(orderDTO.getSupplierId());
            orderSku.setSkuId(orderItemDTO.getItemSkuId());
            orderSku.setNumber(orderItemDTO.getNumber());
            orderSkuList.add(orderSku);

        }
        erpOrder.setDetails(items);

        Integer invoiceMark = orderDTO.getInvoiceMark();
        //  log.info("invoiceMark:{}", invoiceMark);
        List<ErpInvoices> erpInvoicesList = new ArrayList<ErpInvoices>(); //发票信息数组
        if (invoiceMark != 0) {
            OrderInvoiceDTO orderInvoiceDTO = orderDTO.getOrderInvoiceDTO();  //订单发票信息
            ErpInvoices erpInvoices = new ErpInvoices();
            erpInvoices.setInvoice_type(orderInvoiceDTO.getInvoiceType());
            erpInvoices.setInvoice_title(orderInvoiceDTO.getInvoiceTitle());
            erpInvoices.setInvoice_amount(orderDTO.getPayAmount() / 100D);
            erpInvoicesList.add(erpInvoices);
            erpOrder.setInvoices(erpInvoicesList);
        }

        String inParam = JsonUtil.toJson(erpOrder);
        //    log.info("[{}] gyerp gy.erp.trade.add sign  ago param {}:", inParam);

        //签名
        String sign = GyERPSignUtil.sign(inParam, PropertyConfig.getGyerpSecret());
        //    log.info("[{}] gyerp gy.erp.trade.add add order sign {}", sign);
        erpOrder.setSign(sign);

        String signEndParam = JsonUtil.toJson(erpOrder);

        //签名完的入参
        //    log.info("[{}] gyerp gy.erp.trade.add sign  end param {}:", signEndParam);

        String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), signEndParam);
        //     log.info("gyerp gy.erp.trade.add response: {}", response);
        try {
            if (null != response) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                String result = jsonObject.getString("success");
                log.info("gy.erp.trade.add result: {}", result);
                if (result.equals("true")) {

                    //实扣库存
                    OrderStockDTO orderStockDTO = new OrderStockDTO();
                    orderStockDTO.setSellerId(orderDTO.getSellerId());
                    orderStockDTO.setOrderSn(orderDTO.getOrderSn());
                    orderStockDTO.setOrderSkuList(orderSkuList);
                    stockItemSkuClient.realReduceItemSkuSup(orderStockDTO, appKey);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //  String gyOrderId = jsonObject.getString("id");
                    String code = jsonObject.getString("code");
                    String createTime = jsonObject.getString("created");
                    ErpOrderDTO erpOrderDTO = new ErpOrderDTO();
                    erpOrderDTO.setOrderId(String.valueOf(orderId));
                    erpOrderDTO.setGyerpCode(code);
                    try {
                        erpOrderDTO.setCreateTime(format.parse(createTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    ErpOrderDTO erpOrderDTO1 = this.erpOrderManager.addErpOrder(erpOrderDTO);
                    //  log.info("[{}] gyerp order add return id{}", erpOrderDTO1.getId());

                    //更新订单状态待发货
                    OrderQTO orderQTO = new OrderQTO();
                    orderQTO.setId(orderId);
                    orderQTO.setOrderStatus(Integer.valueOf(EnumOrderStatus.UN_DELIVER.getCode()));
                    this.orderClient.updateOrderStatusById(orderQTO, appKey);
                    log.info("end to gyerp order ...........");
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new RainbowException(ResponseCode.BIZ_E_REQUEST_FORBIDDEN);
            }
        } catch (JSONException e) {
            log.info("response{}", response);
            e.printStackTrace();
            throw new RainbowException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }


    }

    @Override
    public boolean hsCancelErpOrder(OrderDTO orderDTO, Long itemId) throws RainbowException {

        //   log.info("[{}] order sn:{}", orderDTO.getOrderSn());
        //   log.info("[{]] item id:{}", Long.toString(itemId));
        //拼接入参
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.refund.update");
        jsonObject.put("tid", orderDTO.getOrderSn());
        jsonObject.put("oid", Long.toString(itemId));
        jsonObject.put("refund_state", 1); //退款状态	0-取消退款 1-标识退款
        //  log.info("gyerp gy.erp.trade.refund.update sign  ago param {}:" + jsonObject.toString());

        //签名
        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
        //   log.info("gyerp gy.erp.trade.refund.update sign {}", sign);
        jsonObject.put("sign", sign);
        //   log.info("gyerp gy.erp.trade.refund.update sign end param {}:", jsonObject.toString());

        //修改订单退款状态
        String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
        log.info("gyerp gy.erp.trade.refund.update response: {}", response);

        try {
            if (null != response) {
                JSONObject object = JSONObject.parseObject(response);
                String result = object.getString("success");
                if (result.equals("true")) {

                    //新增退款单
                    String code = this.gyErpManage.addRefund(orderDTO);
                    log.info("[{}] refund code:{}", code);
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new RainbowException(ResponseCode.BIZ_E_REQUEST_FORBIDDEN);
            }
        } catch (JSONException e) {
            log.info("response{}" + response);
            e.printStackTrace();
            throw new RainbowException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }


    }

    @Override
    public boolean hsResturnErpStore(RefundOrderItemDTO refundOrderItemDTO, String appKey) throws RainbowException {

        Long orderId = refundOrderItemDTO.getOrderId();

        Long userId = refundOrderItemDTO.getUserId();
        log.info("[{}] user id:{}", userId);

        OrderDTO orderDTO;
        //获取订单信息
        Response<OrderDTO> orderDTOResponse = this.orderClient.getOrder(orderId, userId, appKey);
        log.info("get order response: {}", JSON.toJSONString(orderDTOResponse));
        if (orderDTOResponse.isSuccess()) {
            orderDTO = orderDTOResponse.getModule();
        } else {
            log.error("get order error, order id: {},user id: {}", orderId, refundOrderItemDTO.getUserId());
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, orderDTOResponse.getMessage());
        }
        //获取支付方式
        String paymentTypeCode = this.commonManage.paymentType(orderDTO);
        log.info("[{}] payment type code:{}", paymentTypeCode);

        ItemSkuDTO itemSkuDTO = this.itemSkuClient.getItemSku(refundOrderItemDTO.getItemSkuId(), Long.parseLong(ParamEnum.SELLER_ID.getValue()), appKey).getModule();

        //新增退货单
        String code = this.gyErpManage.addReturn(refundOrderItemDTO, itemSkuDTO, orderDTO.getStoreId(), appKey);
        if (null != code) {
            log.info("[{}] order id:{}", Long.toString(orderId));
            //获取erp单据编号
            ErpOrderDTO erpOrderDTO = erpOrderManager.getGyerpCode(Long.toString(orderId));
            if (null == erpOrderDTO) {
                log.error("erpCode is null ................");
            }

            //新增退款单
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
            jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
            jsonObject.put("method", "gy.erp.trade.refund.add");
            jsonObject.put("refund_type", 1); //0-仅退款 1-退货退款 退款单种类
            jsonObject.put("refund_reason", orderDTO.getCancelReason()); //退款原因
            jsonObject.put("trade_code", erpOrderDTO.getGyerpCode()); //关联订单单号
            jsonObject.put("shop_code", SysFieldNameEnum.SHOP_ID.getValue());  //是 店铺代码
            jsonObject.put("vip_code", refundOrderItemDTO.getUserId()); //是	会员代码
            jsonObject.put("payment_type_code", paymentTypeCode); //退款支付方式代码
            jsonObject.put("amount", refundOrderItemDTO.getRefundAmount() / 100D); // 是 退款金额

            List<ErpRefundItemDetail> refundItemDetails = new ArrayList<ErpRefundItemDetail>();
            ErpRefundItemDetail erpRefundItemDetail = new ErpRefundItemDetail();
            erpRefundItemDetail.setBarcode("");  //itemSkuDTO.getBarCode()暂时不传
            erpRefundItemDetail.setPrice(itemSkuDTO.getMarketPrice() / 100D);
            erpRefundItemDetail.setQty(refundOrderItemDTO.getNumber());
            refundItemDetails.add(erpRefundItemDetail);
            jsonObject.put("item_detail", refundItemDetails);
            log.info("gy.erp.trade.refund.add sign ago param {}:", jsonObject.toString());
            //签名
            String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
            log.info("gy.erp.trade.refund.add sign {}:", sign);
            jsonObject.put("sign", sign);

            log.info("gy.erp.trade.refund.add sign end param {}:", jsonObject.toString());

            String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
            log.info("gy.erp.trade.refund response {}", response);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean deliveryItems(Map<String, Object> value,String appKey) throws RainbowException {
        log.info("delivery items params: {}", JSON.toJSONString(value));
        // 调用发货接口 OK
        String expressName = (String) value.get("expressName");  //物流公司名称
        String expressCode = (String) value.get("expressCode");  //物流公司代码
        log.info("[{}] expressCode1:{}", expressCode);
        String expressNo = (String) value.get("expressNo");  //物流单号

        if (expressCode.equals(ExpressEnum.YTO_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.YTO_EXPRESS_CODE.getCode2();
        } else if (expressCode.equals(ExpressEnum.ZTO_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.ZTO_EXPRESS_CODE.getCode2();
        } else if (expressCode.equals(ExpressEnum.STO_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.STO_EXPRESS_CODE.getCode2();
        } else if (expressCode.equals(ExpressEnum.EMS_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.EMS_EXPRESS_CODE.getCode2();
        } else if (expressCode.equals(ExpressEnum.SF_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.SF_EXPRESS_CODE.getCode2();
        } else if (expressCode.equals(ExpressEnum.TTKDEX_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.TTKDEX_EXPRESS_CODE.getCode2();
        } else if (expressCode.equals(ExpressEnum.YUNDA_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.YUNDA_EXPRESS_CODE.getCode2();
        } else if (expressCode.equals(ExpressEnum.YZXB_EXPRESS_CODE.getCode1())) {
            expressCode = ExpressEnum.YZXB_EXPRESS_CODE.getCode2();
        } else {
            expressCode = ExpressEnum.OTHER_EXPRESS_CODE.getCode2();
        }
        log.info("[{}] expressCode2:{}", expressCode);

        List<Map<String, String>> items = (List<Map<String, String>>) value.get("items");

        String orderSn = (String) value.get("orderSn");
        log.info("orderSn: {}", orderSn);
        String[] orderSns = orderSn.split(";");
        Response<Boolean> response = null;

        for (int i = 0; i < orderSns.length; i++) {
            String orderSnumber = orderSns[i];
            log.info("orderSnumber: {}", orderSnumber);

            OrderQTO orderQTO = new OrderQTO();
            orderQTO.setOrderSn(orderSnumber);
            Response<List<OrderDTO>> orderResponse = this.orderClient.queryOrder(orderQTO, appKey);
            if (!(orderResponse.isSuccess() && orderResponse.getModule().size() == 1)) {
                log.info("[{}] get order info error, ordersn:{}", ResponseCode.SYS_E_DATABASE_ERROR, orderSnumber);
                continue;
            }
            OrderDTO orderDTO = orderResponse.getModule().get(0);
            log.info("orderDTO: {}", JSON.toJSONString(orderDTO));

            Long userId = orderDTO.getUserId();
            log.info("[{}] userId:{}", userId);
            List<OrderDeliveryInfoDTO> deliveryList = new ArrayList<OrderDeliveryInfoDTO>();
            OrderDeliveryInfoDTO orderDeliveryInfoDTO = new OrderDeliveryInfoDTO();

            orderDeliveryInfoDTO.setDeliveryCode(expressNo);
            orderDeliveryInfoDTO.setDeliveryCompany(expressName);
            orderDeliveryInfoDTO.setDeliveryFee(0L);
            orderDeliveryInfoDTO.setDeliveryType(1);//有物流
            orderDeliveryInfoDTO.setExpressCode(expressCode);
            orderDeliveryInfoDTO.setOrderId(orderDTO.getId());
            orderDeliveryInfoDTO.setOrder_sn(orderSnumber);
            orderDeliveryInfoDTO.setUserId(userId);
            orderDeliveryInfoDTO.setUserId(orderDTO.getUserId());

            List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems();   //下单明细表

            log.info("items form erp: {}", JSON.toJSONString(items));
            List<Long> orderItemIdList = new ArrayList<Long>();
            for (OrderItemDTO orderItemDTO : orderItemDTOs) {
                orderItemIdList.add(orderItemDTO.getItemId());
            }

            log.info("orderItemIds = {}", JSON.toJSONString(orderItemIdList));
            orderDeliveryInfoDTO.setOrderItemIds(orderItemIdList);
            deliveryList.add(orderDeliveryInfoDTO);

            log.info("ready to delivery items: order id = {}, order sn = {},user id = {}, orderDeliveryInfoDTOs = {}", orderDTO.getId(), orderSnumber, userId, JSON.toJSONString(deliveryList));
            response = this.orderClient.batchDeliveryGoods(deliveryList, appKey);
            log.info("delivery goods response is success: {},user id = {},order_sn={}", response.isSuccess(), userId, orderSnumber);
            if (!response.isSuccess()) {
                continue;
            }
        }

        if (response.isSuccess()) {

            log.info("delivery goods response: {}", response.getModule());
            return response.getModule();
        } else {
            throw new RainbowException(ResponseCode.SYS_E_SERVICE_EXCEPTION, response.getMessage());
        }
    }

    @Override
    public boolean refundAudit(Long userId, Long itemCode, Long orderId, String appKey) throws RainbowException {
        RefundOrderItemDTO refundOrderItemDTO = new RefundOrderItemDTO();
        refundOrderItemDTO.setOrderItemId(itemCode);
        refundOrderItemDTO.setOrderId(orderId);
        refundOrderItemDTO.setAuditResult(0);
        refundOrderItemDTO.setUserId(userId);
        refundOrderItemDTO.setRefuseReason("该订单已发货，无法申请退款！");
        Response<Boolean> response = this.refundClient.auditRefund(refundOrderItemDTO, appKey);
        if (response.getCode() != 10000) {
            int errorCode = Integer.valueOf(response.getCode()).intValue();
            log.error("errorCode :{}", errorCode);
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, response.getMessage());
        }
        return response.getModule();
    }
}

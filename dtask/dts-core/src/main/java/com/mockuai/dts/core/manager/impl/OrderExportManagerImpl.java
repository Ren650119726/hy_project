package com.mockuai.dts.core.manager.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.ItemCategoryClient;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.TempFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.client.DeliveryInfoClient;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.dts.common.TaskStatusEnum;
import com.mockuai.dts.common.constant.Constants;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.OrderExportQTO;
import com.mockuai.dts.core.api.impl.OSSClientAPI;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.OrderExportManager;
import com.mockuai.dts.core.util.FileUtil;
import com.mockuai.dts.core.util.OSSFileLinkUtil;
import com.mockuai.dts.core.util.PoiExcelUtil;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.client.SupplierClient;
import com.mockuai.suppliercenter.client.SupplierStoreClient;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.common.qto.SupplierQTO;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.MoneyUtil;

@Service
public class OrderExportManagerImpl implements OrderExportManager {

    private static final Logger log = LoggerFactory.getLogger(OrderExportManagerImpl.class);
    private OrderClient orderClient;

    @Resource
    private ExportTaskDAO exportTaskDAO;

    @Resource
    private OSSClientAPI ossClientAPI;

    @Resource
    private SupplierClient supplierClient;


    @Resource
    private ItemCategoryClient itemCategoryClient;

    @Resource
    private SupplierStoreClient supplierStoreClient;

    @Resource
    private StoreItemSkuClient storeItemSkuClient;

    @Resource
    private DeliveryInfoClient deliveryInfoClient;

    public OrderClient getOrderClient() {
        return orderClient;
    }


    public String getSupplierInfo(Long supplierId, String appkey) {
        SupplierQTO supplierQTO = new SupplierQTO();
        supplierQTO.setId(supplierId);
        supplierQTO.setPageSize(1);
        supplierQTO.setCurrentPage(499);
        supplierQTO.setNeedPaging(true);
        supplierQTO.setOffset(0);
        String supplierName = null;
        try {
            com.mockuai.suppliercenter.common.api.Response<List<SupplierDTO>> result = supplierClient.querySupplier(supplierQTO, appkey);
            if (result == null) {
                log.error("supplierId[" + supplierId + "]对应的Response<List<SupplierDTO>> is empty or null ");
            }
            if (CollectionUtils.isEmpty(result.getModule())) {
                log.error("supplierId[" + supplierId + "]对应的List<SupplierDTO> is empty or null ");
            } else {
                if (result.getModule().get(0) == null) {
                    log.error("supplierId[" + supplierId + "]对应的SupplierDTO is empty or null ");
                }
                supplierName = result.getModule().get(0).getName();
                if (StringUtils.isBlank(supplierName)) {
                    log.error("supplierId[" + supplierId + "]对应的supplierName[" + supplierName + "] is empty or null ");
                    supplierName = "";
                }
            }

        } catch (Exception e) {
            log.error("供应商服务异常： call supplierClient.querySupplier error");
        }
        return supplierName;
    }

    public String getStoreInfo(Long storeId, String appkey) {

        StoreQTO storeQTO = new StoreQTO();
        storeQTO.setId(storeId);
        storeQTO.setPageSize(1);
        storeQTO.setCurrentPage(499);
        storeQTO.setNeedPaging(true);
        storeQTO.setOffset(0);
        String storeName = null;
        try {
            com.mockuai.suppliercenter.common.api.Response<List<StoreDTO>> result = supplierStoreClient.queryStoreForOrder(storeQTO, appkey);
            if (result == null) {
                log.error("storeId[" + storeId + "]对应的Response<List<StoreDTO>> is empty or null ");
            }
            if (CollectionUtils.isEmpty(result.getModule())) {
                log.error("storeId[" + storeId + "]对应的List<StoreDTO> is empty or null ");
            } else {
                if (result.getModule().get(0) == null) {
                    log.error("storeId[" + storeId + "]对应的StoreDTO is empty or null ");
                }
                storeName = result.getModule().get(0).getName();
                if (StringUtils.isBlank(storeName)) {
                    log.error("storeId[" + storeId + "]对应的storeName[" + storeName + "] is empty or null ");
                    storeName = "";
                }
            }

        } catch (Exception e) {
            log.error("供应商服务异常： call storeClient.queryStoreForOrder error");
        }
        return storeName;

    }

    public StoreItemSkuDTO getItemSkuSn(Long storeId, Long itemSkuId, String appKey) {
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setStoreId(storeId);
        storeItemSkuQTO.setItemSkuId(itemSkuId);
        com.mockuai.suppliercenter.common.api.Response<StoreItemSkuDTO> itemSkuDTOResponse = this.storeItemSkuClient.getItemSku(storeItemSkuQTO, appKey);
        if (itemSkuDTOResponse != null) {
            return itemSkuDTOResponse.getModule();
        }
        return null;
    }

    public void setOrderClient(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @SuppressWarnings("rawtypes")
    private Response getOrdersDTOs(OrderQTO query, String appKey) throws DtsException {
        Response<List<OrderDTO>> response = orderClient.querySellerOrder(query, appKey);
        if (response.getCode() != Constants.SERVICE_PROCESS_SUCCESS) {
            int errorCode = Integer.valueOf(response.getCode());
            log.error("getOrdersDTOs error", response.getCode() + "," + response.getMessage());
            throw new DtsException(errorCode, response.getMessage());
        }
        return response;
    }

    private ItemCategoryDTO getItemCategoryByItemId(Long itemId, String appKey) throws DtsException {
        com.mockuai.itemcenter.common.api.Response response = itemCategoryClient.getItemCategoryByItemId(itemId,appKey);
        if (!response.isSuccess()){
            log.error("getItemCategoryByItemId error",response.getCode()+","+response.getMessage());
            throw new DtsException(response.getMessage());
        }
        return (ItemCategoryDTO) response.getModule();
    }


    private OrderDTO getOrder(Long orderId, Long userId, String appKey) throws DtsException {
        Response<OrderDTO> response = orderClient.getOrder(orderId, userId, appKey);
        if (response.getCode() != Constants.SERVICE_PROCESS_SUCCESS) {
            int errorCode = Integer.valueOf(response.getCode());
            log.error("getOrder error", response.getCode() + "," + response.getMessage());
            throw new DtsException(errorCode, response.getMessage());
        }
        return response.getModule();
    }

    //优惠券
    private Long getCouponDiscountAmount(List<OrderDiscountInfoDTO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }
        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDTO orderDiscountInfo : orderDiscountInfoDOs) {
            if (orderDiscountInfo.getDiscountType() == 1 &&
                    orderDiscountInfo.getDiscountCode().equals("SYS_MARKET_TOOL_000001")) {
                totalDiscountAmount += orderDiscountInfo.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    //满减送
    private Long getReachMultiReduceDiscountAmount(List<OrderDiscountInfoDTO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }
        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDTO orderDiscountInfo : orderDiscountInfoDOs) {
            if (orderDiscountInfo.getDiscountType() == 1 &&
                    orderDiscountInfo.getDiscountCode().equals("ReachMultipleReduceTool")) {
                totalDiscountAmount += orderDiscountInfo.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    //余额
    private Long getVouchersDiscountAmount(List<OrderDiscountInfoDTO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }

        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDTO orderDiscountInfo : orderDiscountInfoDOs) {
            if (orderDiscountInfo.getDiscountType() == 2 &&
                    orderDiscountInfo.getDiscountCode().equals("1")) {
                totalDiscountAmount += orderDiscountInfo.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    private Long getPointDiscountAmount(List<OrderDiscountInfoDTO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }
        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDTO orderDiscountInfo : orderDiscountInfoDOs) {
            if (orderDiscountInfo.getDiscountType() == 2 &&
                    orderDiscountInfo.getDiscountCode().equals("2")) {
                totalDiscountAmount += orderDiscountInfo.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    public static Long getMemberDiscountAmount(List<OrderDiscountInfoDTO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }
        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDTO orderDiscountInfoDTO : orderDiscountInfoDOs) {
            if (orderDiscountInfoDTO.getDiscountType() == 3) {
                totalDiscountAmount += orderDiscountInfoDTO.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    private List<DeliveryInfoDTO> queryDeliveryInfo(long orderId, long userId, String appkey) {
        try {
            DeliveryInfoQTO deliveryInfoQTO = new DeliveryInfoQTO();
            deliveryInfoQTO.setOrderId(orderId);
            deliveryInfoQTO.setUserId(userId);

            com.mockuai.deliverycenter.common.api.Response<?> response = deliveryInfoClient.queryDeliveryInfo(deliveryInfoQTO, appkey);
            if (response.getCode() == Constants.SERVICE_PROCESS_SUCCESS) {
                return (List<DeliveryInfoDTO>) response.getModule();
            } else {
                throw new DtsException(response.getCode(), response.getMessage());
            }
        } catch (Exception e) {
            log.error("queryDeliveryInfo", e);
            return null;
        }

    }

    private OrderQTO modelConvert(OrderExportQTO exportQuery) {
        OrderQTO query = new OrderQTO();
        OrderQTOTemp temp = new OrderQTOTemp();
        BeanUtils.copyProperties(exportQuery, temp);
        BeanUtils.copyProperties(temp, query);
        query.setOffset(new Integer(exportQuery.getOffset()));
        return query;
    }

    private OrderQTO modelConvertOrderQTO(OrderExportQTO exportQuery) {
        OrderQTO query = new OrderQTO();
        BeanUtils.copyProperties(exportQuery, query);
        query.setOffset(new Integer(exportQuery.getOffset()));
        return query;
    }


    private class OrderQTOTemp {
        /**
         * 订单ID
         */
        private Long id;

        /**
         * 卖家ID
         */
        private Long sellerId;

        /**
         * 买家ID
         */
        private Long userId;

        /**
         * 订单类型
         */
        private Integer type;

        /**
         * 订单流水号
         */
        private String orderSn;

        /**
         * 订单状态
         */
        private Integer orderStatus;
        /**
         * 配送方式ID
         */
        private Integer deliveryId;

        /**
         * 支付方式ID
         */
        private Integer paymentId;
        /**
         * 是否需要发票，0代表不需要，1代表需要
         */
        private Integer invoiceMark;
        /**
         * 优惠标记，0代表没有任何优惠信息，1代表有优惠
         */
        private Integer discountMark;

        /**
         * 订单开始时间
         */
        private Date orderTimeStart;

        /**
         * 订单结束时间
         */
        private Date orderTimeEnd;

        /**
         * 收货人手机
         */
        private String consigneeMobile;

        /**
         * 收货人姓名
         */
        private String consignee;

        /**
         * 0 代表 不是加星。 1 代表是加星
         */
        private Integer asteriskMark;

        /**
         * 超时分钟数
         */
        private Integer timeoutMinuteNumber;

        private Long itemId;


        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public Integer getAsteriskMark() {
            return asteriskMark;
        }

        public void setAsteriskMark(Integer asteriskMark) {
            this.asteriskMark = asteriskMark;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getSellerId() {
            return sellerId;
        }

        public void setSellerId(Long sellerId) {
            this.sellerId = sellerId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Integer getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(Integer deliveryId) {
            this.deliveryId = deliveryId;
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public Integer getInvoiceMark() {
            return invoiceMark;
        }

        public void setInvoiceMark(Integer invoiceMark) {
            this.invoiceMark = invoiceMark;
        }

        public Integer getDiscountMark() {
            return discountMark;
        }

        public void setDiscountMark(Integer discountMark) {
            this.discountMark = discountMark;
        }

        public Date getOrderTimeStart() {
            return orderTimeStart;
        }

        public void setOrderTimeStart(Date orderTimeStart) {
            this.orderTimeStart = orderTimeStart;
        }

        public Date getOrderTimeEnd() {
            return orderTimeEnd;
        }

        public void setOrderTimeEnd(Date orderTimeEnd) {
            this.orderTimeEnd = orderTimeEnd;
        }

        public String getConsigneeMobile() {
            return consigneeMobile;
        }

        public void setConsigneeMobile(String consigneeMobile) {
            this.consigneeMobile = consigneeMobile;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public Integer getTimeoutMinuteNumber() {
            return timeoutMinuteNumber;
        }

        @SuppressWarnings("unused")
        public void setTimeoutMinuteNumber(Integer timeoutMinuteNumber) {
            this.timeoutMinuteNumber = timeoutMinuteNumber;
        }

    }


    public enum EnumDeliveryId {

        OTHER("0", "其他"),

        EXPRESS("1", "快递邮寄"),

        GO_STORE("2", "到店自提"),

        DOOR_TO_DOOR_SERVICE("3", "送货上门"),;

        EnumDeliveryId(String code, String description) {
            this.code = code;
            this.description = description;
        }

        private String code;

        private String description;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public static EnumDeliveryId getByCode(String code) {
            for (EnumDeliveryId type : values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            return null;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean exportOrders(OrderExportQTO query, ExportTaskDO exportTaskDO) throws DtsException {

        // 文件名;
        String tmpFileName = exportTaskDO.getOssObjectKey();
        log.info("tmpFileName：" + FileUtil.getTmpFilePath(tmpFileName));
        File file = new File(FileUtil.getTmpFilePath(tmpFileName));
        int offset = 0;
        int count = 20;

        LinkedHashMap<String, String> hederMap = new LinkedHashMap<String, String>();
        //需要自动单元格宽度的列
        List<Integer> autoSizeColumns = new ArrayList<Integer>();
        hederMap.put("0", "序号");
        hederMap.put("1", "订单号");
        hederMap.put("2", "店主id");
        hederMap.put("3", "商品名称【属性】");
        hederMap.put("4", "供应商编码");
        hederMap.put("5", "数量");
        hederMap.put("6", "单价");
        hederMap.put("7", "商品状态");
        hederMap.put("8", "下单人");
        hederMap.put("9", "下单时间");
        hederMap.put("10", "订单状态");

        hederMap.put("11", "支付方式");
        hederMap.put("12", "第三方支付单号");
        hederMap.put("13", "发货方式");
        hederMap.put("14", "管理员备注");
        hederMap.put("15", "收货地址");
        hederMap.put("16", "收货人姓名");
        hederMap.put("17", "身份证号");
        hederMap.put("18", "收货人手机");
        hederMap.put("19", "买家备注");
        hederMap.put("20", "付款时间");
        hederMap.put("21", "发货时间");
        hederMap.put("22", "收货时间");
        hederMap.put("23", "买家支付积分");
        hederMap.put("24", "物流公司");
        hederMap.put("25", "物流单号");
        hederMap.put("26", "运费");
        hederMap.put("27", "税费");
        hederMap.put("28", "优惠券");
        hederMap.put("29", "满减送");
        hederMap.put("30", "会员折扣");
        hederMap.put("31", "积分");
        hederMap.put("32", "余额");
        hederMap.put("33", "实付金额");
        hederMap.put("34", "订单总价");
        hederMap.put("35", "仓库");
        hederMap.put("36", "供应商");
        hederMap.put("37","商品品类");//hsq 2016 1215


        for (int i = 2; i < 24; i++) {
            autoSizeColumns.add(i);
        }

        //变量设置
        int rowTitleIndex = 0;                    //标题行索引
        int rowContextHeight = 14;                //正文行高

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = PoiExcelUtil.createSheet(workbook, tmpFileName, 10);
        //设置正文字体
        HSSFFont fontContext = PoiExcelUtil.createFont(workbook, "宋体", HSSFFont.BOLDWEIGHT_NORMAL, (short) 11);
        //生成并设置正文样式(无边框)
        HSSFCellStyle styleContext = PoiExcelUtil.createCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
        //生成并设置正文样式(用于金额)
        HSSFCellStyle styleMoney = PoiExcelUtil.createMoneyCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
        //标题行
        HSSFRow titleRow = PoiExcelUtil.createRow(sheet, rowTitleIndex, rowContextHeight);

        //写入文件头部
        for (Iterator headerIterator = hederMap.entrySet().iterator(); headerIterator.hasNext(); ) {
            Entry<String, String> propertyEntry = (Entry<String, String>) headerIterator.next();
            PoiExcelUtil.createCell(titleRow, Integer.parseInt(propertyEntry.getKey()), styleContext, propertyEntry.getValue());
        }


        int currentRow = 1; //excel要写入的当前行

        //序号
        int num=0;
        
        while (true) {
            try {
                // 20条一页;
                OrderQTO orderQTO = modelConvertOrderQTO(query);
                orderQTO.setCount(count);
                orderQTO.setOffset(offset);
                String appkey = query.getAppKey();
                Response response = getOrdersDTOs(orderQTO, exportTaskDO.getAppKey());
                List<OrderDTO> orderDTOs = (List<OrderDTO>) response.getModule();

                for (OrderDTO o : orderDTOs) {

                    List<DeliveryInfoDTO> deliveryInfoDTOs = queryDeliveryInfo(o.getId(), o.getUserId(), exportTaskDO.getAppKey());
                    Map<Long, DeliveryInfoDTO> orderItemIdMap = new HashMap<Long, DeliveryInfoDTO>();
                    if (deliveryInfoDTOs != null && deliveryInfoDTOs.isEmpty() == false) {
                        for (DeliveryInfoDTO deliverInfoDTO : deliveryInfoDTOs) {

                            if (deliverInfoDTO.getOrderItemList() != null && deliverInfoDTO.getOrderItemList().isEmpty() == false) {
                                for (OrderItemDTO orderItemDTO : deliverInfoDTO.getOrderItemList()) {
                                    orderItemIdMap.put(orderItemDTO.getId(), deliverInfoDTO);
                                }
                            }
                        }
                    }

                    String supplierName = getSupplierInfo(o.getSupplierId(), exportTaskDO.getAppKey());
                    String supplierStoreName = getStoreInfo(o.getStoreId(), exportTaskDO.getAppKey());
                    // TODO: 2016/12/15
//                    String categoryName = null;
//                    if (null!=o.getOrderItemDTO()) {
//                        ItemCategoryDTO itemCategoryDTO = null;
//                        OrderItemDTO orderItemDTO = o.getOrderItemDTO();
//                        if (orderItemDTO.getItemId()!=null){
//                            itemCategoryDTO = getItemCategoryByItemId(orderItemDTO.getItemId(),exportTaskDO.getAppKey());
//                            categoryName = itemCategoryDTO.getCateName();
//                        }
//                    }

                    List<OrderItemDTO> orderItems = o.getOrderItems();

                    int startRow = currentRow; //excel要写入的当前行
                    if (null != orderItems && orderItems.size() > 0) {
                    	
                    	// 合并行处理
                    	String orderSn = "";
                    	
                        for (OrderItemDTO oItem : orderItems) {
                            HSSFRow workingRow = PoiExcelUtil.createRow(sheet, currentRow, rowContextHeight);
                            
                            // 序号
                        	if(!orderSn.equals(o.getOrderSn())){
                        		orderSn = o.getOrderSn();
                        		PoiExcelUtil.createCell(workingRow, 0, styleContext, ++num + "");
                        	}else{
                        		PoiExcelUtil.createCell(workingRow, 0, styleContext, num + "");
                        	}
                            
                            if(!StringUtils.isBlank(o.getOrderSn())){

                            	PoiExcelUtil.createCell(workingRow, 1, styleContext, o.getOrderSn());

                            }else{
                            	PoiExcelUtil.createCell(workingRow, 1, styleContext, "");
                            }
//                            PoiExcelUtil.createCell(workingRow, 0, styleContext, orderSn);
                            PoiExcelUtil.createCell(workingRow, 2, styleContext, oItem.getDistributorId() + "");
                            PoiExcelUtil.createCell(workingRow, 3, styleContext, oItem.getItemName() + "【" + oItem.getItemSkuDesc() + "】");

                            StoreItemSkuDTO storeItemSkuDTO = getItemSkuSn(o.getStoreId(), oItem.getItemSkuId(), exportTaskDO.getAppKey());
                            String itemSkuSn = "";
                            if (storeItemSkuDTO != null && !StringUtils.isBlank(storeItemSkuDTO.getSupplierItmeSkuSn())) {
                                itemSkuSn = storeItemSkuDTO.getSupplierItmeSkuSn();
                            }
                            PoiExcelUtil.createCell(workingRow, 4, styleContext, itemSkuSn);

                            PoiExcelUtil.createCell(workingRow, 5, styleMoney, Double.parseDouble(oItem.getNumber() + ""));

                            if (null == oItem.getUnitPrice())
                                oItem.setPaymentAmount(0l);
                            PoiExcelUtil.createCell(workingRow, 6, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(oItem.getUnitPrice())));

                            String itemStatus = "";
                            if (oItem.getRefundStatus() != null) {
                                itemStatus = EnumRefundStatus.getByCode(oItem.getRefundStatus() + "").getDescription();
                            }
                            PoiExcelUtil.createCell(workingRow, 7, styleContext, itemStatus);

                            PoiExcelUtil.createCell(workingRow, 8, styleContext, oItem.getUserName());
                            PoiExcelUtil.createCell(workingRow, 9, styleContext, o.getOrderTimeStr());
                            PoiExcelUtil.createCell(workingRow, 10, styleContext, EnumOrderStatus.getByCode(o.getOrderStatus() + "").getDescription());
                            if (0 == o.getPaymentId()) {
                                PoiExcelUtil.createCell(workingRow, 11, styleContext, "优惠折扣");
                            } else {
                                EnumPaymentMethod paymentMethod = EnumPaymentMethod.getByCode(o.getPaymentId() + "");
                                String paymentMethodStr = "";
                                if (null != paymentMethod)
                                    paymentMethodStr = paymentMethod.getDescription();
                                PoiExcelUtil.createCell(workingRow, 11, styleContext, paymentMethodStr);
                            }

                            String tradeNo = "";
                            if (null != o.getOrderPaymentDTO() && null != o.getOrderPaymentDTO().getOutTradeNo())
                                tradeNo = o.getOrderPaymentDTO().getOutTradeNo();

                            PoiExcelUtil.createCell(workingRow, 12, styleContext, tradeNo);

//		    	       			PoiExcelUtil.createCell(workingRow, 12, styleMoney,Double.parseDouble(MoneyUtil.getMoneyStr(o.getTotalAmount())));
                            PoiExcelUtil.createCell(workingRow, 13, styleContext, EnumDeliveryId.getByCode(o.getDeliveryId() + "").getDescription());

		    	       			/*门店信息去除*/
		    	       			/*String storeName = "";
		    	       			if(o.getOrderStoreDTO()!=null)
		    	       				storeName = o.getOrderStoreDTO().getStoreName();
		    	       			PoiExcelUtil.createCell(workingRow, 11, styleMoney,storeName);*/

                            PoiExcelUtil.createCell(workingRow, 14, styleContext, o.getSellerMemo());

                            StringBuffer address = new StringBuffer();

                            if (null != o.getOrderConsigneeDTO() && StringUtils.isNotBlank(o.getOrderConsigneeDTO().getCountry())) {
                                address.append(o.getOrderConsigneeDTO().getCountry());
                            }

                            if (null != o.getOrderConsigneeDTO() && StringUtils.isNotBlank(o.getOrderConsigneeDTO().getProvince())) {
                                address.append(o.getOrderConsigneeDTO().getProvince());
                            }

                            if (null != o.getOrderConsigneeDTO() && StringUtils.isNotBlank(o.getOrderConsigneeDTO().getCity())) {
                                address.append(o.getOrderConsigneeDTO().getCity());
                            }
                            if (null != o.getOrderConsigneeDTO() && StringUtils.isNotBlank(o.getOrderConsigneeDTO().getArea())) {
                                address.append(o.getOrderConsigneeDTO().getArea());
                            }
                            if (null != o.getOrderConsigneeDTO() && StringUtils.isNotBlank(o.getOrderConsigneeDTO().getAddress())) {
                                address.append(o.getOrderConsigneeDTO().getAddress());
                            }
                            PoiExcelUtil.createCell(workingRow, 15, styleContext, address.toString());

                            String consignee = "";
                            if (!StringUtils.isBlank(o.getOrderConsigneeDTO().getConsignee())) {
                                consignee = o.getOrderConsigneeDTO().getConsignee();
                            }
                            PoiExcelUtil.createCell(workingRow, 16, styleContext, consignee);

                            String idCardNo = "";
                            if (!StringUtils.isBlank(o.getOrderConsigneeDTO().getIdCardNo())) {
                                idCardNo = o.getOrderConsigneeDTO().getIdCardNo();
                            }
                            PoiExcelUtil.createCell(workingRow, 17, styleContext, idCardNo);

                            if (null != o.getOrderConsigneeDTO() && null != o.getOrderConsigneeDTO().getMobile()) {
                                PoiExcelUtil.createCell(workingRow, 18, styleContext, o.getOrderConsigneeDTO().getMobile());
                            } else {
                                PoiExcelUtil.createCell(workingRow, 18, styleContext, "");
                            }


                            PoiExcelUtil.createCell(workingRow, 19, styleContext, o.getUserMemo());

                            if (null != o.getPayTime()) {
                                PoiExcelUtil.createCell(workingRow, 20, styleContext, DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", o.getPayTime()));
                            } else {
                                PoiExcelUtil.createCell(workingRow, 20, styleContext, "");
                            }


                            if (null != o.getConsignTime()) {
                                PoiExcelUtil.createCell(workingRow, 21, styleContext, DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", o.getConsignTime()));
                            } else {
                                PoiExcelUtil.createCell(workingRow, 21, styleContext, "");
                            }


                            if (null != o.getReceiptTime()) {
                                PoiExcelUtil.createCell(workingRow, 22, styleContext, DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", o.getReceiptTime()));
                            } else {
                                PoiExcelUtil.createCell(workingRow, 22, styleContext, "");
                            }

                            //支付积分
                            PoiExcelUtil.createCell(workingRow, 23, styleMoney, Double.parseDouble(o.getPoint() + ""));

                            DeliveryInfoDTO deliveryInfoDTO = orderItemIdMap.get(oItem.getId());
                            String deliveryCompay = "";
                            String deliveryCode = "";
                            if (deliveryInfoDTO != null) {
                                deliveryCompay = deliveryInfoDTO.getExpress();
                                deliveryCode = deliveryInfoDTO.getExpressNo();
                            }
                            //快递公司
                            PoiExcelUtil.createCell(workingRow, 24, styleContext, deliveryCompay);

                            //快递公司编码
                            PoiExcelUtil.createCell(workingRow, 25, styleContext, deliveryCode);

                            //运费
                            PoiExcelUtil.createCell(workingRow, 26, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(o.getDeliveryFee())));

                            //税费
                            long taxFee = 0l;
                            if (o.getTaxFee() != null) {
                                taxFee = o.getTaxFee();
                            }
                            PoiExcelUtil.createCell(workingRow, 27, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(taxFee)));


                            long couponDiscountAmount = getCouponDiscountAmount(o.getOrderDiscountInfoDTOs());
                            PoiExcelUtil.createCell(workingRow, 28, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(couponDiscountAmount)));

                            long fullReduceAmount = getReachMultiReduceDiscountAmount(o.getOrderDiscountInfoDTOs());
                            PoiExcelUtil.createCell(workingRow, 29, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(fullReduceAmount)));


                            long memberDiscountAmount = getMemberDiscountAmount(o.getOrderDiscountInfoDTOs());
                            PoiExcelUtil.createCell(workingRow, 30, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(memberDiscountAmount)));


                            long pointDiscountAmount = getPointDiscountAmount(o.getOrderDiscountInfoDTOs());
                            PoiExcelUtil.createCell(workingRow, 31, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(pointDiscountAmount)));


                            long vouchersDiscountAmount = getVouchersDiscountAmount(o.getOrderDiscountInfoDTOs());
                            PoiExcelUtil.createCell(workingRow, 32, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(vouchersDiscountAmount)));

                            PoiExcelUtil.createCell(workingRow, 33, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(o.getTotalAmount())));

                            PoiExcelUtil.createCell(workingRow, 34, styleMoney, Double.parseDouble(MoneyUtil.getMoneyStr(o.getTotalPrice() + o.getDeliveryFee() + o.getTaxFee() + o.getFloatingPrice())));

                            PoiExcelUtil.createCell(workingRow, 35, styleContext, supplierStoreName);

                            PoiExcelUtil.createCell(workingRow, 36, styleContext, supplierName);

                            // TODO: 2016/12/15

                            String categoryName = null;
                            ItemCategoryDTO itemCategoryDTO = null;
                            if (oItem.getItemId()!=null){
                                itemCategoryDTO = getItemCategoryByItemId(oItem.getItemId(),exportTaskDO.getAppKey());
                            }
                            if (itemCategoryDTO != null){
                                if (itemCategoryDTO.getCateName()!=null){
                                    categoryName = itemCategoryDTO.getCateName();
                                    PoiExcelUtil.createCell(workingRow,37,styleContext,categoryName);
                                }else {
                                    PoiExcelUtil.createCell(workingRow,37,styleContext,"");
                                }
                            }
                            currentRow++;

                        }
                    }

                    if (orderItems != null && orderItems.size() > 1) {
//                        PoiExcelUtil.mergeCell(sheet, startRow, currentRow - 1, 0, 0);
                    	for (int j = 0; j <= 1; j++) {
                    		PoiExcelUtil.mergeCell(sheet, startRow, currentRow - 1, j, j);
                    	}

                        for (int i = 8; i <= 23; i++) {
                            PoiExcelUtil.mergeCell(sheet, startRow,currentRow - 1,i,i);
                        }

                        for (int ii = 26; ii <= 37; ii++) {

                            PoiExcelUtil.mergeCell(sheet, startRow,currentRow - 1,ii, ii);
                        }

                    }

                }

                long total = response.getTotalCount();
                // 更新进度;
                int process = 0;
                if (total == 0) {
                    process = 0;
                } else {
                    process = (int) (offset / total) * 100 / 2;
                }

                exportTaskDO.setTaskProcess(process);
                exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
                exportTaskDAO.updateTask(exportTaskDO);

                offset += orderDTOs.size();
                if (orderDTOs.size() < 20) {
                    log.info(" exit export for : " + JSONObject.toJSONString(orderDTOs.get(0)));
                    // 最后一页可以退出了;
                    break;
                }
		                
		                /*每次查完停1秒*/
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    log.error("interrupted error", e1);
                }

            } catch (Exception e) {
                FileUtil.destroyFile(tmpFileName);
                log.error("export oders error", e);
                throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getCause());
            }

//		            for(Integer i : autoSizeColumns) {
//		       			sheet.autoSizeColumn(i);
//		       			int width = sheet.getColumnWidth(i);
//		       			//宽度加长
////		       			sheet.setColumnWidth(i, width+1000);
//		       		}

        }

        try {
            PoiExcelUtil.writeWorkbook(workbook, file);
        } catch (IOException e) {
            log.error("OrderExportManagerImpl exportOrders writeWorkbook error", e);
            FileUtil.destroyFile(tmpFileName);
            throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getCause());
        }
        log.info(" 上传 阿里云 oss tmpFileName : " + tmpFileName);
        // 上传到OSS;
//    		  tmpFileName = DateUtil.convertDateToString(new Date())+"/"+tmpFileName ;
//        ossClientAPI.uploadFile(tmpFileName, FileUtil.getTmpFilePath(tmpFileName));
        String key = DateUtil.convertDateToString(new Date())+"/"+tmpFileName ;
        ossClientAPI.uploadFile(key, FileUtil.getTmpFilePath(tmpFileName));
        // 完成;
        exportTaskDO.setTaskProcess(100);
        exportTaskDO.setTaskStatus(TaskStatusEnum.COMPLETE_TASK.getStatus());
        exportTaskDO.setOssObjectKey(key);
        exportTaskDO.setFileLink(OSSFileLinkUtil.
                generateFileLink(exportTaskDO.getOssBucketName(), exportTaskDO.getOssObjectKey()));
        exportTaskDAO.updateTask(exportTaskDO);
        // 上传完之后删除;
        FileUtil.destroyFile(tmpFileName);
        return true;
    }
}

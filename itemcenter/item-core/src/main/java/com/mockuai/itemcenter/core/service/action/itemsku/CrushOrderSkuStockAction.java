package com.mockuai.itemcenter.core.service.action.itemsku;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.StockFrozenRecordDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.domain.qto.StockFrozenRecordQTO;
import com.mockuai.itemcenter.common.util.StockStatusUtil;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.message.producer.TransmitMsgProducer;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 以订单为单位减去sku库存
 */

@Service
public class CrushOrderSkuStockAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(CrushOrderSkuStockAction.class);
    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private StoreStockManager storeStockManager;

    @Resource
    private StockFrozenRecordManager stockFrozenRecordManager;


    @Resource
    private OrderManager orderManager;

    @Resource
    private TransmitMsgProducer transmitMsgProducer;

    @Resource
    private ItemManager itemManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {


        ItemRequest request = context.getRequest();

        if (request.getString("orderSn") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "orderSn is missing");
        }


        String bizCode = (String) context.get("bizCode");

        String appKey = request.getString("appKey");

        String orderSn = request.getString("orderSn");

        return crush(orderSn, bizCode, appKey);

    }


    /**
     * 根据冻结记录删除订单所有的冻结库存
     *
     * @param orderSn
     * @param bizCode
     * @param appKey
     * @return
     * @throws ItemException
     */
    public ItemResponse crush(String orderSn, String bizCode, String appKey) throws ItemException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OrderQTO orderQTO = new OrderQTO();
        orderQTO.setBizCode(bizCode);
        orderQTO.setOrderSn(orderSn);

        List<OrderDTO> orderDTOList = orderManager.queryOrder(orderQTO, appKey);

        if (CollectionUtils.isEmpty(orderDTOList)) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "没有查询到订单: " + orderSn);
        }

        Long orderId = orderDTOList.get(0).getOriginalOrder();

        if (null == orderId) {
            //查到的订单不存在母单

            StockFrozenRecordQTO query1 = new StockFrozenRecordQTO();
            query1.setBizCode(bizCode);
            query1.setOrderSn(orderSn);
            query1.setStatus(StockFrozenRecordStatusEnum.FROZEN.getStatus());

            List<StockFrozenRecordDTO> recordDTOList1 = stockFrozenRecordManager.queryStockFrozenRecord(query1);

            if (CollectionUtils.isEmpty(recordDTOList1)) {
                //情况2
                log.info("消息重复,已去重 订单 : {}", orderSn);
                return ResponseUtil.getSuccessResponse(true);
            }

            return crushCore(recordDTOList1, orderSn, bizCode, appKey);
        }

        Long userId = orderDTOList.get(0).getUserId();

        OrderDTO orderDTO = orderManager.getOrderDTO(orderId, userId, appKey);

        if (orderDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "没有查询到母单 :" + orderSn);
        }

        StockFrozenRecordQTO query2 = new StockFrozenRecordQTO();
        query2.setBizCode(bizCode);
        query2.setOrderSn(orderDTO.getOrderSn());
        query2.setStatus(StockFrozenRecordStatusEnum.FROZEN.getStatus());

        List<StockFrozenRecordDTO> recordDTOList2 = stockFrozenRecordManager.queryStockFrozenRecord(query2);

//         存在并发问题,舍弃此设计
//        if (CollectionUtils.isEmpty(recordDTOList2)) {
//            //情况2
//            log.info("拆单后的重复减库存调用,已去重 子单 : {} 母单 : {}",orderSn,orderDTO.getOrderSn());
//            return ResponseUtil.getSuccessResponse(true);
//        }

        OrderDTO orderDTO1 = orderManager.getOrderDTO(orderDTOList.get(0).getId(),userId,appKey);
        List<OrderItemDTO> orderItemDTOList = orderDTO1.getOrderItems();

        Set<Long> skuIdSet = Sets.newHashSetWithExpectedSize(orderItemDTOList.size());

        for(OrderItemDTO orderItemDTO : orderItemDTOList){
            skuIdSet.add(orderItemDTO.getItemSkuId());
        }

        List<StockFrozenRecordDTO> subRecordList = Lists.newArrayListWithExpectedSize(recordDTOList2.size());

        for(StockFrozenRecordDTO stockFrozenRecordDTO : recordDTOList2){

            if(skuIdSet.contains(stockFrozenRecordDTO.getSkuId())){
                subRecordList.add(stockFrozenRecordDTO);
            }
        }

        log.info("拆单后的减库存调用 子单 : {} 母单 : {}", orderSn, orderDTO.getOrderSn());
        return crushCore(subRecordList, orderDTO.getOrderSn(), bizCode, appKey);
    }


    public ItemResponse crushCore(List<StockFrozenRecordDTO> recordDTOList, String orderSn, String bizCode, String appKey) throws ItemException {


        Long sellerId = recordDTOList.get(0).getSellerId();

        List<Long> skuIdList = Lists.newArrayList();

        List<SupplierOrderStockDTO.OrderSku> skuList = Lists.newArrayList();

        for (StockFrozenRecordDTO stockFrozenRecordDTO : recordDTOList) {

            Long skuId = stockFrozenRecordDTO.getSkuId();
            Integer number = stockFrozenRecordDTO.getNumber();
            skuIdList.add(skuId);

            SupplierOrderStockDTO.OrderSku sku = new SupplierOrderStockDTO.OrderSku();

            sku.setSkuId(skuId);
            sku.setNumber(number);

            skuList.add(sku);
        }


        //冻结仓库库存,如果在这里发生异常,前面的数据库操作将回滚
        SupplierOrderStockDTO supplierOrderStockDTO = new SupplierOrderStockDTO();
        supplierOrderStockDTO.setOrderSn(orderSn);
        //supplierOrderStockDTO.setSellerId(sellerId);
        supplierOrderStockDTO.setOrderSkuList(skuList);
        storeStockManager.reomveStoreSkuStock(supplierOrderStockDTO, appKey);


        //如果前两部操作都成功,则修改冻结记录为已减状态;

        stockFrozenRecordManager.updateStockFrozenRecordStatus(orderSn, sellerId, StockFrozenRecordStatusEnum.CRUSHED.getStatus(), bizCode);

//        context.put(HookEnum.BATCH_STOCK_CHANGE_HOOK.getHookName(), "");
//        context.put("skuIdList", skuIdList);
//        context.put("sellerId", sellerId);

        Collections.sort(recordDTOList, new Comparator<StockFrozenRecordDTO>() {
            @Override
            public int compare(StockFrozenRecordDTO o1, StockFrozenRecordDTO o2) {

                if (o1.getSkuId() > o2.getSkuId()) {
                    return 1;
                } else {
                    return -1;
                }

                   /* if (o1.getSkuId() > o2.getSkuId()) {
                        return 1;
                    } else if (o1.getSkuId() < o2.getSkuId()) {
                        return -1;
                    } else {
                        if (o1.getDistributorId() > o2.getDistributorId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }*/
            }
        });

        for (StockFrozenRecordDTO stockFrozenRecordDTO : recordDTOList) {

            Long skuId = stockFrozenRecordDTO.getSkuId();
            Integer number = stockFrozenRecordDTO.getNumber();
            skuIdList.add(skuId);

            itemSkuManager.crushItemSkuStock(skuId, stockFrozenRecordDTO.getSellerId(), number, bizCode);

        }

        for (Long skuId : skuIdList) {

            ItemSkuDTO itemSkuDTO = itemSkuManager.getItemSku(skuId, sellerId, bizCode);

            transmitMsgProducer.send(
                    MessageTopicEnum.ITEM_SKU_UPDATE.getTopic(),
                    MessageTagEnum.STOCK.getTag(),
                    itemSkuDTO);

            ItemDTO itemDTO = itemManager.getItem(itemSkuDTO.getItemId(), sellerId, bizCode);

            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setItemId(itemDTO.getId());
            itemSkuQTO.setSellerId(itemDTO.getSellerId());
            itemSkuQTO.setBizCode(bizCode);

            List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

            long stockNum = 0;
            long frozenStockNum = 0;
            if (itemSkuDTOList.size() > 0) {

                StockStatus stockStatus = StockStatusUtil.genStockStatus(itemSkuDTOList);

                for (ItemSkuDTO skuDTO : itemSkuDTOList) {
                    stockNum += skuDTO.getStockNum();
                    frozenStockNum += skuDTO.getFrozenStockNum();
                }

                itemDTO.setStockStatus(stockStatus.getStatus());
                itemDTO.setStockNum(stockNum);
                itemDTO.setFrozenStockNum(frozenStockNum);
                itemManager.updateItemStockNum(itemDTO);
            }
        }
        return ResponseUtil.getSuccessResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.CRUSH_ORDER_SKU_STOCK.getActionName();
    }
}

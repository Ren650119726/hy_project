package com.mockuai.tradecenter.core.service.action.order.add.step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.SupplierManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;

/**
 * Created by zengzhangqiang on 5/19/16.
 */
public class CheckOrderItemStep extends TradeBaseStep {
    private static final String SEPERATOR = "-";

    @Override
    public StepName getName() {
        return StepName.CHECK_ORDER_ITEM_STEP;
    }

    @Override
    public TradeResponse execute() {
        String appKey = (String) this.getAttr("appKey");
//        Map<Long, ItemDTO> itemMap = (Map<Long, ItemDTO>) this.getAttr("itemMap");
//        Map<Long, ItemSkuDTO> itemSkuMap = (Map<Long, ItemSkuDTO>) this.getAttr("itemSkuMap");
//        Map<Long, List<OrderItemDO>> orderItemDOListMap =
//                (Map<Long, List<OrderItemDO>>) this.getAttr("orderItemListMap");
        List<OrderItemDO> orderItemList = (List<OrderItemDO>) this.getAttr("orderItemList");

//        Map<Long, ItemDTO> combItemMap = (Map<Long, ItemDTO>) this.getAttr("combItemMap");
//        Map<Long, ItemSkuDTO> combItemSkuMap = (Map<Long, ItemSkuDTO>) this.getAttr("combItemSkuMap");
//        Map<Long, List<OrderItemDO>> orderCombItemListMap =
//                (Map<Long, List<OrderItemDO>>) this.getAttr("orderCombItemListMap");
//        Map<Long,List<Long>> combineMap = (Map<Long,List<Long>>) this.getAttr("combineMap");
        List<OrderItemDO> orderCombItemList = (List<OrderItemDO>) this.getAttr("orderCombItemList");
//        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");

        
        try {

            List<OrderItemDO> orderTotalItemList = new ArrayList<OrderItemDO>();
            if(CollectionUtils.isNotEmpty(orderItemList)){
        		orderTotalItemList.addAll(orderItemList);
        	}
        	if(CollectionUtils.isNotEmpty(orderCombItemList)){
        		orderTotalItemList.addAll(orderCombItemList);
        	}
            
            Map<Long,Integer> skuNumbers = new HashMap<Long, Integer>();
            
            this.genSkuNumbersMap(orderTotalItemList,skuNumbers);
            
            //秒杀商品不检查库存
            if(CollectionUtils.isNotEmpty(orderItemList)){
            	for(OrderItemDO orderItemDO:orderItemList){
                	if(orderItemDO.getItemType()==ItemType.SECKILL.getType()){
                    	return ResponseUtils.getSuccessResponse(null);
                    }
                }
            }            
                        
            for(Map.Entry<Long, Integer> entry : skuNumbers.entrySet()){
            	checkItemStockNew(entry.getKey(), entry.getValue(),appKey);
            }
        	
        	/*if(orderItemDOListMap!=null && orderItemDOListMap.size() >0){
        		for (Map.Entry<Long,List<OrderItemDO>> entry : orderItemDOListMap.entrySet()) {
                    List<OrderItemDO> orderItemDOList = entry.getValue();

                    int number = 0;
                    for(OrderItemDO orderItemDO:orderItemDOList){
                        number+=orderItemDO.getNumber();
                    }

                    ItemSkuDTO itemSkuDTO = itemSkuMap.get(entry.getKey());
                    if (itemSkuDTO == null) { // 该商品不存在
                        logger.error("itemSku doesn't exist : " + entry.getKey());
                        throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "itemSku doesn't exist");
                    }

                    ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());

                    //检查商品限购情况
//                    checkItemBuyLimit(itemDTO, orderDTO, itemDTO.getSellerId(), number, appKey);

                    //检查商品状态是否正常
                    checkItemStatus(itemDTO);

                    //检查商品库存
//                    for(OrderItemDO orderItemDO:orderItemDOList){
                	logger.info(" orderItemDO.getItemType() : "+itemDTO.getItemType());
                	if(itemDTO.getItemType() != ItemType.SECKILL.getType()){
                		checkItemStockNew(entry.getKey(), number,appKey);
                	}
//                    }
//                    checkItemStockNew(entry.getKey(), number,appKey);
//                    checkItemStock(itemSkuDTO, number);
                }
        	}*/

        	
        } catch (TradeException e) {
            logger.error("", e);
            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
        }

        return ResponseUtils.getSuccessResponse(null);
    }

    private void genSkuNumbersMap(List<OrderItemDO> orderTotalItemList,Map<Long,Integer> skuNumbers){
    	for(OrderItemDO orderItemDO :orderTotalItemList){
    		if(skuNumbers.get(orderItemDO.getItemSkuId())==null){
    			skuNumbers.put(orderItemDO.getItemSkuId(), orderItemDO.getNumber());
    		}else{
    			skuNumbers.put(orderItemDO.getItemSkuId(),(Integer)skuNumbers.get(orderItemDO.getItemSkuId())+orderItemDO.getNumber());
    		}
    	}
    }

    /**
     * 检查商品状态是否正常
     * @param itemDTO
     * @throws TradeException
     */
    private void checkItemStatus(ItemDTO itemDTO) throws TradeException{
        //检查商品是否处于上架状态
        if (itemDTO.getItemStatus() != ItemStatus.ON_SALE.getStatus()) {
            throw new TradeException(ResponseCode.BZI_E_ITEM_NOT_GROUNDING, "该商品未上架");
        }
    }
    


    /**
     * 校验商品库存
     * @param itemSkuDTO
     * @param buyNumber
     * @throws TradeException
     */
    private void checkItemStockNew(Long itemSkuId, int buyNumber,String appKey) throws TradeException {

        SupplierManager supplierManager = (SupplierManager) this.getBean("supplierManager");
    	// 从仓库获取sku库存
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
    	storeItemSkuQTO.setItemSkuId(itemSkuId);
    	logger.info(" storeItemSkuQTO : "+JSONObject.toJSONString(storeItemSkuQTO));
    	List<StoreItemSkuDTO> storeItemSkuDTOs = supplierManager.queryStoreItemSku(storeItemSkuQTO, appKey);
    	logger.info(" >>>>> storeItemSkuDTOs : "+JSONObject.toJSONString(storeItemSkuDTOs));
    	if(storeItemSkuDTOs.get(0).getSalesNum() != null && buyNumber > storeItemSkuDTOs.get(0).getSalesNum().intValue()) {
            logger.error("orderItem number : " + buyNumber + " out of stock number : "
                    + storeItemSkuDTOs.get(0).getSalesNum().intValue());
            throw new TradeException(ResponseCode.BIZ_E_ITEM_OUT_OF_STOCK);
        }
    }

    /**
     * 校验用户购买限制
     * @param itemDTO
     * @param orderDTO
     * @param sellerId
     * @param number
     * @param appKey
     * @throws TradeException
     */
    private void checkItemBuyLimit(ItemDTO itemDTO, OrderDTO orderDTO, Long sellerId, Integer number, String appKey)
            throws TradeException {
        if (null == itemDTO.getId() || null == sellerId) {
            throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID,
                    ResponseCode.PARAM_E_PARAM_INVALID.getComment());
        }

        //校验单次交易的购买量限制
        // TODO 销售时间的验证需要重构，不能直接使用应用服务器时间
        if (itemDTO.getSaleMaxNum() != null && itemDTO.getSaleMaxNum() != 0
                && number > itemDTO.getSaleMaxNum().intValue()) { // 购买最大量限制
            throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT,
                    "该商品最多只能购买" + itemDTO.getSaleMaxNum().intValue() + "件");
        } else if (itemDTO.getSaleMinNum() != null && itemDTO.getSaleMinNum() != 0
                && number < itemDTO.getSaleMinNum().intValue()) { // 购买最小量限制
            throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MIN_AMOUNT,
                    "该商品最少需要购买" + itemDTO.getSaleMinNum().intValue() + "件");
        }


        //校验账号级别的购买量限制
        OrderQTO query = new OrderQTO();
        query.setUserId(orderDTO.getUserId());
        //查询账号级别的商品限购配置
//        logger.info("【itemDTO】"+JSONObject.toJSONString(itemDTO)+"【sellerId】"+sellerId);
        ItemManager itemManager = (ItemManager) this.getBean("itemManager");
        Integer itemBuyLimitCount = itemManager.getItemBuyLimit(itemDTO.getId(), sellerId, appKey);
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");

        //限购校验
        if (null != itemBuyLimitCount && itemBuyLimitCount != 0) {
            //查询该用户已购买数量
            Integer hasBuyCount = orderManager.getHasBuyCount(orderDTO.getUserId(), itemDTO.getId());
//            logger.info("【itemBuyLimitCount】"+itemBuyLimitCount+"【hasBuyCount】"+hasBuyCount+"【orderDTO.getUserId()】"+orderDTO.getUserId()+"【itemDTO.getId()】"+itemDTO.getId());
            if (itemBuyLimitCount > hasBuyCount) {
                Integer canBuyCount = itemBuyLimitCount - hasBuyCount;
                if (number > canBuyCount) {
                    logger.error("item over the purchase quantity : " + itemDTO.getId());
                    throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "item over the purchase quantity");
                }
            } else {
                throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
            }
        }
    }
    
    /**
     * 校验商品库存
     * @param itemSkuDTO
     * @param buyNumber
     * @throws TradeException
     */
    private void checkItemStock(ItemSkuDTO itemSkuDTO, int buyNumber) throws TradeException {
        if(itemSkuDTO.getStockNum() != null
                && buyNumber > itemSkuDTO.getStockNum().longValue()) {
            logger.error("orderItem number " + buyNumber + " out of stock number"
                    + itemSkuDTO.getStockNum().intValue());
            throw new TradeException(ResponseCode.BIZ_E_ITEM_OUT_OF_STOCK);
        }
    }
}

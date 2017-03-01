package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.distributioncenter.common.domain.dto.SellerOrderDTO.OrderItem;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderSeqManager;
import com.mockuai.tradecenter.core.manager.SupplierManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 冻结指定商品库存，并对商品进行分组，按照商品所属仓库进行分组
 * Created by zengzhangqiang on 5/19/16.
 */
public class HandleItemFreezingStep extends TradeBaseStep {

	private static final Logger log = LoggerFactory.getLogger(HandleItemFreezingStep.class);
	
    @Override
    public StepName getName() {
        return StepName.HANDLE_ITEM_FREEZING_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        OrderDTO orderDTONor = (OrderDTO) this.getAttr("orderDTONor");
        OrderDTO orderDTOComb = (OrderDTO) this.getAttr("orderDTOComb");
        String appKey = (String) this.getAttr("appKey");
        List<OrderItemDO> orderItemList = (List<OrderItemDO>) this.getAttr("orderItemList");
        List<OrderItemDO> orderCombItemList = (List<OrderItemDO>) this.getAttr("orderCombItemList");
//        ItemManager itemManager = (ItemManager) this.getBean("itemManager");
        SupplierManager supplierManager = (SupplierManager) this.getBean("supplierManager");
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");

        try {

            OrderSeqManager orderSeqManager = (OrderSeqManager) this.getBean("orderSeqManager");
            // 组合订单和普通订单的父订单
            String rootOrderSn = "" ;
            List<String> subOrderSnList = new ArrayList<String>();
            if(CollectionUtils.isNotEmpty(orderItemList) && CollectionUtils.isNotEmpty(orderCombItemList)){
            	rootOrderSn = orderSeqManager.getOrderSn(orderDTO.getUserId());
            	subOrderSnList = orderSeqManager.getRootSubOrderSnList(rootOrderSn, 2);
            	orderDTO.setOrderSn(rootOrderSn);
            }
            
        	if(CollectionUtils.isNotEmpty(orderItemList)){
        		//生成订单流水号
//                String orderSn = orderSeqManager.getOrderSn(orderDTO.getUserId()); //按照订单号生成规则生成订单编号
        		String orderSn ;
        		if(CollectionUtils.isNotEmpty(orderCombItemList)){
        			orderSn = subOrderSnList.get(0);
        		}else{
        			orderSn = orderSeqManager.getOrderSn(orderDTO.getUserId()); //按照订单号生成规则生成订单编号
        		}
//                String orderSn = orderSeqManager.getOrderSn(orderDTO.getUserId()); //按照订单号生成规则生成订单编号
                //往orderDTO中填充主订单流水号
                orderDTONor.setOrderSn(orderSn);
                
//                log.info(" 【HandleItemFreezingStep orderDTO】 "+JSONObject.toJSONString(orderDTO));
                
                for(int i=0;i<orderItemList.size();i++){
                	OrderItemDO orderItemDO = orderItemList.get(i);
                	if(orderItemDO.getItemType() == ItemType.SECKILL.getType()){
                		OrderItemQTO query = new OrderItemQTO();
                		query.setUserId(orderDTO.getUserId());
                		query.setItemSkuId(orderItemDO.getItemSkuId());       
                		query.setDeleteMark(0);              	
                    	
                		OrderDO preOrderDO = orderManager.getPreOrderByItemQTO(query);
                		OrderStockDTO orderStockDTO = new OrderStockDTO();
    					orderStockDTO.setOrderSn(preOrderDO.getOrderSn());
    					orderStockDTO.setSellerId(preOrderDO.getSellerId());
    					List<OrderSku> orderSkus = new ArrayList<OrderSku>();
    					OrderSku orderSku = new OrderSku();
    					orderSku.setSkuId(orderItemDO.getItemSkuId());
    					orderSku.setNumber(orderItemDO.getNumber());
    					orderSku.setStoreId(preOrderDO.getStoreId());
    					orderSku.setSupplierId(preOrderDO.getSupplierId());
    					orderSkus.add(orderSku);
    					orderStockDTO.setOrderSkuList(orderSkus);
    					
                		supplierManager.thawOrderSkuStock(orderStockDTO, appKey);

                    }
                }
                
                //冻结商品库存

                OrderStockDTO orderStockDTO = new OrderStockDTO();
                orderStockDTO.setOrderSn(orderDTONor.getOrderSn());
    			orderStockDTO.setSellerId(orderDTONor.getSellerId());
    			List<OrderSku> orderSkus = new ArrayList<OrderSku>();			
    			for(OrderItemDO orderItemDO :orderItemList){
    				OrderSku orderSku = new OrderSku();						
    				orderSku.setSkuId(orderItemDO.getItemSkuId());
    				orderSku.setNumber(orderItemDO.getNumber());
    				orderSkus.add(orderSku);
    			}
    			orderStockDTO.setOrderSkuList(orderSkus);
    			
                OrderStockDTO orderStock = supplierManager.freezeOrderSkuStock(orderStockDTO, appKey);
                
            	//将订单商品列表按照仓库进行分组，便于后续的分仓分单逻辑
                Map<Long, List<OrderItemDTO>> storeOrderItemMap =
                        this.classifyOrderItemList(orderDTONor.getOrderItems(), orderStock);

                //如果订单商品列表涉及到多个仓库的商品，则需要根据仓库进行拆单处理
                boolean needSplitOrder = false;
                if (storeOrderItemMap.size() > 1) {
                    needSplitOrder = true;
                }
                
                //将分好仓库的商品信息放入管道上下文，便于后续拆单逻辑的处理
                this.setAttr("needSplitOrder", needSplitOrder);
                this.setAttr("storeOrderItemMap", storeOrderItemMap);                       
                this.setAttr("storeSupplierMap", getStoreSupplierMap(orderStock));
                this.setAttr("mainOrderSn", orderSn);//主订单的订单流水号
        	}
        	
        	// 停滞1毫秒确保订单编号的唯一性
        	/*try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
        	
            if(CollectionUtils.isNotEmpty(orderCombItemList)){
//            	log.info(" >>>>>orderDTOComb :"+JSONObject.toJSONString(orderDTOComb));
            	//生成订单流水号
//                String combOrderSn = orderSeqManager.getOrderSn(orderDTOComb.getUserId()); //按照订单号生成规则生成订单编号
                String combOrderSn ;
        		if(CollectionUtils.isNotEmpty(orderItemList)){
        			combOrderSn = subOrderSnList.get(1);
        		}else{
        			combOrderSn = orderSeqManager.getOrderSn(orderDTOComb.getUserId()); //按照订单号生成规则生成订单编号
        		}
//                log.info(" >>>>>combOrderSn :"+JSONObject.toJSONString(combOrderSn));
                //往orderDTO中填充主订单流水号
                orderDTOComb.setOrderSn(combOrderSn);
            	//冻结商品库存

                OrderStockDTO orderStockDTO = new OrderStockDTO();
                orderStockDTO.setOrderSn(orderDTOComb.getOrderSn());
    			orderStockDTO.setSellerId(orderDTOComb.getSellerId());
    			
    			// TODO
    			Map<Long,OrderSku> orderSkuMap = new HashMap<Long, OrderStockDTO.OrderSku>();
    			for(OrderItemDO orderItemDO :orderCombItemList){
    				if(orderSkuMap.get(orderItemDO.getItemSkuId())==null){
    					OrderSku orderSku = new OrderSku();	
    					orderSku.setSkuId(orderItemDO.getItemSkuId());
        				orderSku.setNumber(orderItemDO.getNumber());
    					orderSkuMap.put(orderItemDO.getItemSkuId(), orderSku);
    				}else{
    					orderSkuMap.get(orderItemDO.getItemSkuId()).setNumber(orderSkuMap.get(orderItemDO.getItemSkuId()).getNumber()+orderItemDO.getNumber());
    				}
    				
    			}
    			
//    			log.info(" >>>>>>>>> orderSkuMap "+JSONObject.toJSONString(orderSkuMap));
    			List<OrderSku> orderSkus = new ArrayList<OrderSku>();	
    			for(Map.Entry<Long,OrderSku> entry : orderSkuMap.entrySet()){
    				orderSkus.add(entry.getValue());
    			}
    			/*for(OrderItemDO orderItemDO :orderCombItemList){
    				OrderSku orderSku = new OrderSku();						
    				orderSku.setSkuId(orderItemDO.getItemSkuId());
    				orderSku.setNumber(orderItemDO.getNumber());
    				orderSkus.add(orderSku);
    			}*/
    			orderStockDTO.setOrderSkuList(orderSkus);
    			
                OrderStockDTO orderStock = supplierManager.freezeOrderSkuStock(orderStockDTO, appKey);
                
            	//将订单商品列表按照仓库进行分组，便于后续的分仓分单逻辑
                Map<Long, List<OrderItemDTO>> storeCombOrderItemMap =
                        this.classifyOrderItemList(orderDTOComb.getOrderItems(), orderStock);

                //如果订单商品列表涉及到多个仓库的商品，则需要根据仓库进行拆单处理
                boolean needSplitCombOrder = false;
                if (storeCombOrderItemMap.size() > 1) {
                	needSplitCombOrder = true;
                }
                
                //将分好仓库的商品信息放入管道上下文，便于后续拆单逻辑的处理
                this.setAttr("needSplitCombOrder", needSplitCombOrder);
                this.setAttr("storeCombOrderItemMap", storeCombOrderItemMap);                       
                this.setAttr("storeCombSupplierMap", getStoreSupplierMap(orderStock));
                this.setAttr("combOrderSn", combOrderSn);//组合订单的订单流水号
            }
            

            this.setAttr("rootOrderSn", rootOrderSn);//根订单的订单流水号

        } catch (TradeException e) {
            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
        }

        return ResponseUtils.getSuccessResponse();
    }

    /**
     * 将订单商品列表按照仓库进行分组
     * @param orderItemDTOList
     * @param orderStock
     * @return
     * @throws TradeException
     */
    private Map<Long, List<OrderItemDTO>> classifyOrderItemList(List<OrderItemDTO> orderItemDTOList,
                                                                OrderStockDTO orderStock) throws TradeException{

        //将冻结的商品列表按照仓库进行分组
        Map<Long, List<OrderItemDTO>> storeOrderItemMap = new HashMap<Long, List<OrderItemDTO>>();
        if(orderStock!=null && orderStock.getOrderSkuList()!=null){
        	for (OrderStockDTO.OrderSku orderSku : orderStock.getOrderSkuList()) {
                Long storeId = orderSku.getStoreId();
                if (storeOrderItemMap.containsKey(storeId) == false) {
                    storeOrderItemMap.put(storeId, new ArrayList<OrderItemDTO>());
                }

                for (OrderItemDTO orderItemDTO : orderItemDTOList) {
                    if (orderItemDTO.getItemSkuId().longValue() == orderSku.getSkuId().longValue()) {
                        storeOrderItemMap.get(storeId).add(orderItemDTO);
                    }
                }
            }
        }
        

        return storeOrderItemMap;
    }

    private Map<Long, Long> getStoreSupplierMap(OrderStockDTO orderStockDTO) {
    	if(orderStockDTO==null){
    		return null;
    	}
        List<OrderStockDTO.OrderSku> orderSkuList = orderStockDTO.getOrderSkuList();
        Map<Long, Long> storeSupplierMap = new HashMap<Long, Long>();
        for (OrderStockDTO.OrderSku orderSku : orderSkuList) {
            storeSupplierMap.put(orderSku.getStoreId(), orderSku.getSupplierId());
        }

        return storeSupplierMap;
    }
}
